package net.yazin.stonks.Order.service;

import net.yazin.stonks.Common.model.dto.events.AssetReserveResponseMessage;
import net.yazin.stonks.Order.model.dto.GenerateOrderDTO;
import net.yazin.stonks.Order.model.dto.OrderSearchParamsDTO;
import net.yazin.stonks.Order.model.entity.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    void generateOrder(GenerateOrderDTO orderDTO);

    void updateOrderAfterAssetReserved(AssetReserveResponseMessage res);

    void matchOrder(int orderId);

    void cancelOrder(int orderId);


    Page<Order> search(OrderSearchParamsDTO params);
}
