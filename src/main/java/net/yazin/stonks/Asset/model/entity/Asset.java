package net.yazin.stonks.Asset.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.yazin.stonks.Common.model.enums.OrderSide;
import net.yazin.stonks.Common.model.dto.CustomerInfo;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "asset_type",
        discriminatorType = DiscriminatorType.INTEGER)
@NoArgsConstructor
public abstract class Asset implements CustomerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assetId;

    private String customerId;

    private String assetName;

    protected double size;

    protected double usableSize;

    public double reservedSize() {
        return size - usableSize;
    }

    public boolean reserve(double requestedSize) {


        if (requestedSize > usableSize) {
            return false;
        }

        usableSize -= requestedSize;

        return true;
    }

    public abstract void updateAfterMatchedOrder(OrderSide side, double requestedSize);

    public abstract void updateAfterCancelledOrder(OrderSide side, double requestedSize);

}
