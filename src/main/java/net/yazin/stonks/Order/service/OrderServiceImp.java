package net.yazin.stonks.Order.service;

import lombok.RequiredArgsConstructor;
import net.yazin.stonks.Common.model.dto.events.AssetReserveRequestMessage;
import net.yazin.stonks.Common.model.dto.events.AssetReserveResponseMessage;
import net.yazin.stonks.Common.model.enums.OrderSide;
import net.yazin.stonks.Common.model.enums.OrderStatus;
import net.yazin.stonks.Common.util.SecurityUtils;
import net.yazin.stonks.Order.data.repository.OrderRepository;
import net.yazin.stonks.Order.model.dto.GenerateOrderDTO;
import net.yazin.stonks.Order.model.dto.OrderSearchParamsDTO;
import net.yazin.stonks.Order.model.entity.Order;
import net.yazin.stonks.Order.model.mapper.OrderMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;
    private final OrderMapper orderMapper;

    private AssetReserveRequestMessage getReservationMessage(Order order){

        return order.getSide() == OrderSide.BUY ?
                //Buying stock. Reserve cash.
                AssetReserveRequestMessage.builder()
                                            .orderId(order.getOrderId())
                                            .requestedSize(order.getSize()*order.getPrice())
                                            .customerId(order.getCustomerId())
                                            .assetName(order.getAssetAgainst())
                                            .build()
                //Selling stock. Reserve stock.
                :AssetReserveRequestMessage.builder()
                                            .orderId(order.getOrderId())
                                            .requestedSize(order.getSize())
                                            .customerId(order.getCustomerId())
                                            .assetName(order.getAssetName())
                                            .build();

    }

    @Override
    @Transactional
    public void generateOrder(GenerateOrderDTO orderDTO) {

        SecurityUtils.restrict(orderDTO);

        var order = orderRepository.save(Order.generateNewOrder(orderDTO));

        publisher.publishEvent(getReservationMessage(order));

    }

    @Override
    @ApplicationModuleListener
    public void updateOrderAfterAssetReserved(AssetReserveResponseMessage res) {

        if(res.isSuccess()){
            orderRepository.updateOrderStatus(OrderStatus.PENDING,res.getOrderId());
        }
        else{
            orderRepository.deleteById(res.getOrderId());
        }
    }

    @Override
    @Transactional
    public void matchOrder(int orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

        order.match();

        orderRepository.save(order);

        publisher.publishEvent(orderMapper.getOrderMatchedMessage(order));

    }

    @Override
    @Transactional
    public void cancelOrder(int orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

        SecurityUtils.checkCustomer(order);

        order.cancel();

        orderRepository.save(order);

        publisher.publishEvent(orderMapper.getOrderCancelledMessage(order));

    }

    @Override
    public Page<Order> search(OrderSearchParamsDTO params){

        SecurityUtils.restrict(params);

        return orderRepository.findByCustomerIdAndCreatedDateGreaterThanAndCreatedDateLessThan(params.getCustomerId(), params.getStartDate(), params.getEndDate(), PageRequest.of(params.getPageNumber(),params.getItemCount()));

    }
}
