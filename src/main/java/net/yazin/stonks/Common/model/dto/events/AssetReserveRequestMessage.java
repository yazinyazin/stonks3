package net.yazin.stonks.Common.model.dto.events;

import lombok.Builder;
import lombok.Getter;
import net.yazin.stonks.Common.model.enums.OrderSide;

@Getter
@Builder
public class AssetReserveRequestMessage {

    String assetName;
    int orderId;
    String customerId;
    double requestedSize;

}
