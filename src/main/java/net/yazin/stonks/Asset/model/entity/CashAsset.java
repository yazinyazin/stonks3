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
@DiscriminatorValue(value = "1")
@NoArgsConstructor
public class CashAsset extends Asset {

    public CashAsset(String assetName, String customerId) {
        setCustomerId(customerId);
        setAssetName(assetName);
    }

    @Override
    public void setAssetName(String assetName) {
        if (!isCashAsset(assetName)) {
            throw new IllegalStateException();
        }

        super.setAssetName(assetName);
    }

    public void deposit(double depositSize) {

        this.size += depositSize;
        usableSize += depositSize;

    }

    public void withdraw(double withdrawalSize) {

        if (withdrawalSize > size || withdrawalSize > usableSize) {
            throw new IllegalStateException("Insufficient funds!");
        }

        size -= withdrawalSize;
        usableSize -= withdrawalSize;

    }

    @Override
    public void updateAfterMatchedOrder(OrderSide side, double requestedSizeTimesPrice) {
        if (side == OrderSide.SELL) {

            size += requestedSizeTimesPrice;
            usableSize += requestedSizeTimesPrice;

        } else {

            if (requestedSizeTimesPrice > reservedSize()) {
                throw new IllegalStateException("Record mismatch. Seek help.");
            }

            size -= requestedSizeTimesPrice;

        }
    }

    @Override
    public void updateAfterCancelledOrder(OrderSide side, double requestedSize) {
        if (side == OrderSide.BUY) {

            if (requestedSize > reservedSize()) {
                throw new IllegalStateException("Record mismatch. Seek help.");
            }

            usableSize += requestedSize;

        }
    }
}
