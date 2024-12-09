package net.yazin.stonks.Order.model.mapper;

import javax.annotation.processing.Generated;
import net.yazin.stonks.Common.model.dto.events.OrderCancelledMessage;
import net.yazin.stonks.Common.model.dto.events.OrderMatchedMessage;
import net.yazin.stonks.Order.model.entity.Order;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-11T02:16:01+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl extends OrderMapper {

    @Override
    public OrderMatchedMessage getOrderMatchedMessage(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderMatchedMessage.OrderMatchedMessageBuilder orderMatchedMessage = OrderMatchedMessage.builder();

        orderMatchedMessage.assetName( order.getAssetName() );
        orderMatchedMessage.assetAgainst( order.getAssetAgainst() );
        orderMatchedMessage.side( order.getSide() );
        orderMatchedMessage.size( order.getSize() );
        orderMatchedMessage.price( order.getPrice() );
        orderMatchedMessage.customerId( order.getCustomerId() );

        return orderMatchedMessage.build();
    }

    @Override
    public OrderCancelledMessage getOrderCancelledMessage(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderCancelledMessage.OrderCancelledMessageBuilder orderCancelledMessage = OrderCancelledMessage.builder();

        orderCancelledMessage.assetName( order.getAssetName() );
        orderCancelledMessage.assetAgainst( order.getAssetAgainst() );
        orderCancelledMessage.side( order.getSide() );
        orderCancelledMessage.size( order.getSize() );
        orderCancelledMessage.price( order.getPrice() );
        orderCancelledMessage.customerId( order.getCustomerId() );

        return orderCancelledMessage.build();
    }
}
