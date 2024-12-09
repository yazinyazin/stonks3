package net.yazin.stonks.Order.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.yazin.stonks.Common.model.enums.OrderSide;
import net.yazin.stonks.Common.model.enums.OrderStatus;
import net.yazin.stonks.Common.model.dto.CustomerInfo;
import net.yazin.stonks.Common.util.AssetUtils;
import net.yazin.stonks.Order.model.dto.GenerateOrderDTO;

@Entity
@Table(name = "STONK_ORDERS")
@Getter
@Setter
@NoArgsConstructor
public class Order implements CustomerInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int orderId;

    private String customerId;

    private String assetName;

    private String assetAgainst;

    private OrderSide side;

    private OrderStatus status;

    private long size;

    private double price;

    private long createdDate;


    public void setAssetAgainst(String assetAgainst) {

        if(!AssetUtils.isCashAsset(assetAgainst) || this.assetName.equalsIgnoreCase(assetAgainst)){
            throw new IllegalStateException("Invalid order");
        }

        this.assetAgainst = assetAgainst;
    }

    public static Order generateNewOrder(GenerateOrderDTO orderDTO){

        if(!AssetUtils.isCashAsset(orderDTO.getAssetAgainst()) || orderDTO.getAssetName().equalsIgnoreCase(orderDTO.getAssetAgainst())){
            throw new IllegalStateException("Invalid order");
        }

        Order order = new Order(orderDTO.getCustomerId(), orderDTO.getAssetName(), orderDTO.getAssetAgainst(), orderDTO.getSide(), orderDTO.getSize(), orderDTO.getPrice());
        order.setStatus(OrderStatus.TENTATIVE);
        order.setCreatedDate(System.currentTimeMillis());
        return order;
    }

    private Order(String customerId, String assetName, String assetAgainst, OrderSide side, long size, double price) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.assetAgainst = assetAgainst;
        this.side = side;
        this.size = size;
        this.price = price;
    }

    public void checkIfMatchable(){
        if(this.status != OrderStatus.PENDING){
            throw new IllegalStateException("Cannot match an order in this state.");
        }
    }

    public void match(){
        checkIfMatchable();
        status = OrderStatus.MATCHED;
    }

    public void checkIfCancelable(){
        if(this.status != OrderStatus.PENDING){
            throw new IllegalStateException("Cannot cancel an order in this state.");
        }
    }

    public void cancel(){
        checkIfCancelable();
        status = OrderStatus.CANCELLED;
    }

}
