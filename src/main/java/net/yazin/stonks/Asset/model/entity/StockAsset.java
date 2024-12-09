package net.yazin.stonks.Asset.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.yazin.stonks.Common.model.enums.OrderSide;

import static net.yazin.stonks.Common.util.AssetUtils.isCashAsset;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "2")
@NoArgsConstructor
public class StockAsset extends Asset {


    public StockAsset(String assetName, String customerId){

        setCustomerId(customerId);
        setAssetName(assetName);
    }

    @Override
    public void setAssetName(String assetName) {

        if(isCashAsset(assetName)){
            throw new IllegalStateException();
        }

        super.setAssetName(assetName);

    }

    public void updateAfterMatchedOrder(OrderSide side, double requestedSize) {

        if (side == OrderSide.SELL) {

            if (requestedSize > reservedSize()) {
                throw new IllegalStateException("Record mismatch. Seek help.");
            }

            size -= requestedSize;

        } else {

            size += requestedSize;
            usableSize += requestedSize;

        }
    }

    public void updateAfterCancelledOrder(OrderSide side, double requestedSize) {

        if (side == OrderSide.SELL) {

            if (requestedSize > reservedSize()) {
                throw new IllegalStateException("Record mismatch. Seek help.");
            }

            usableSize += requestedSize;

        }

    }


}
