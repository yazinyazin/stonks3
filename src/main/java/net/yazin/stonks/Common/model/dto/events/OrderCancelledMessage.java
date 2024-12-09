package net.yazin.stonks.Common.model.dto.events;

import lombok.Builder;
import lombok.Getter;
import net.yazin.stonks.Common.model.enums.OrderSide;

@Getter
@Builder
public class OrderCancelledMessage {

    String assetName;

    String assetAgainst;

    OrderSide side;

    double size;

    double price;

    String customerId;


}
