package net.yazin.stonks.Order.model.mapper;

import net.yazin.stonks.Common.model.dto.events.OrderCancelledMessage;
import net.yazin.stonks.Common.model.dto.events.OrderMatchedMessage;
import net.yazin.stonks.Order.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public abstract OrderMatchedMessage getOrderMatchedMessage(Order order);
    public abstract OrderCancelledMessage getOrderCancelledMessage(Order order);

}
