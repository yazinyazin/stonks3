package net.yazin.stonks.Asset.service;

import lombok.RequiredArgsConstructor;
import net.yazin.stonks.Asset.data.repository.AssetRepository;
import net.yazin.stonks.Asset.model.dto.AssetSearchParamsDTO;
import net.yazin.stonks.Asset.model.dto.CashRequestDTO;
import net.yazin.stonks.Asset.model.entity.Asset;
import net.yazin.stonks.Asset.model.entity.CashAsset;
import net.yazin.stonks.Asset.model.entity.StockAsset;
import net.yazin.stonks.Common.model.dto.events.AssetReserveRequestMessage;
import net.yazin.stonks.Common.model.dto.events.AssetReserveResponseMessage;
import net.yazin.stonks.Common.model.dto.events.OrderCancelledMessage;
import net.yazin.stonks.Common.model.dto.events.OrderMatchedMessage;
import net.yazin.stonks.Common.util.SecurityUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.yazin.stonks.Common.util.AssetUtils.isCashAsset;

@Service
@RequiredArgsConstructor
public class AssetServiceImp implements AssetService {

    private final AssetRepository assetRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public void depositCash(CashRequestDTO cashRequest) {

        SecurityUtils.restrict(cashRequest);

        CashAsset cashAsset = assetRepository.findCashAsset(cashRequest.getAssetName(), cashRequest.getCustomerId()).orElseGet(() -> new CashAsset(cashRequest.getAssetName(),cashRequest.getCustomerId()));

        cashAsset.deposit(cashRequest.getAmount());

        assetRepository.save(cashAsset);

    }

    @Override
    @Transactional
    public void withdrawCash(CashRequestDTO cashRequest) {

        SecurityUtils.restrict(cashRequest);

        CashAsset cashAsset = assetRepository.findCashAsset(cashRequest.getAssetName(),cashRequest.getCustomerId()).orElseGet(() -> new CashAsset(cashRequest.getAssetName(),cashRequest.getCustomerId()));

        cashAsset.withdraw(cashRequest.getAmount());

        assetRepository.save(cashAsset);

    }

    private Asset generateNewAsset(String assetName,String customerId){
        return isCashAsset(assetName) ? new CashAsset(assetName,customerId) : new StockAsset(assetName,customerId);
    }

    @Override
    @ApplicationModuleListener
    public void reserveAsset(AssetReserveRequestMessage msg) {

        Asset asset = assetRepository.findAsset(msg.getAssetName(), msg.getCustomerId()).orElse(new StockAsset(msg.getAssetName(),msg.getCustomerId()));

        if(!asset.reserve(msg.getRequestedSize())){
            publisher.publishEvent(AssetReserveResponseMessage.builder().success(false).orderId(msg.getOrderId()).build());
            return;
        }

        assetRepository.save(asset);

        publisher.publishEvent(AssetReserveResponseMessage.builder().success(true).orderId(msg.getOrderId()).build());

    }

    @Override
    @ApplicationModuleListener
    public void updateAssets(OrderCancelledMessage msg) {

        Asset asset = assetRepository.findAsset(msg.getAssetName(),msg.getCustomerId()).orElseGet(()->generateNewAsset(msg.getAssetName(),msg.getCustomerId()));
        CashAsset cashAsset = assetRepository.findCashAsset(msg.getAssetAgainst(),msg.getCustomerId()).orElseGet(()->new CashAsset(msg.getAssetAgainst(),msg.getCustomerId()));

        asset.updateAfterCancelledOrder(msg.getSide(), msg.getSize());
        cashAsset.updateAfterCancelledOrder(msg.getSide(), msg.getSize() * msg.getPrice());

        assetRepository.saveAll(List.of(asset,cashAsset));
    }

    @Override
    @ApplicationModuleListener
    public void updateAssets(OrderMatchedMessage msg) {

        Asset asset = assetRepository.findAsset(msg.getAssetName(),msg.getCustomerId()).orElseGet(()->generateNewAsset(msg.getAssetName(), msg.getCustomerId()));
        CashAsset cashAsset = assetRepository.findCashAsset(msg.getAssetAgainst(),msg.getCustomerId()).orElseGet(()->new CashAsset(msg.getAssetAgainst(),msg.getCustomerId()));

        asset.updateAfterMatchedOrder(msg.getSide(), msg.getSize());
        cashAsset.updateAfterMatchedOrder(msg.getSide(), msg.getSize() * msg.getPrice());

        assetRepository.saveAll(List.of(asset,cashAsset));

    }

    @Override
    public Asset getAsset(int assetId) {
        return assetRepository.findById(assetId).orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    @Override
    public Page<Asset> search(AssetSearchParamsDTO params) {

        SecurityUtils.restrict(params);

        return assetRepository.findByCustomerId(params.getCustomerId(), PageRequest.of(params.getPageNumber(), params.getItemCount()));
    }
}
