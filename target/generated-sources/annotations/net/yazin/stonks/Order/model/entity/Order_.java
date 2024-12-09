package net.yazin.stonks.Order.model.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;
import net.yazin.stonks.Common.model.enums.OrderSide;
import net.yazin.stonks.Common.model.enums.OrderStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, OrderSide> side;
	public static volatile SingularAttribute<Order, String> assetAgainst;
	public static volatile SingularAttribute<Order, Long> createdDate;
	public static volatile SingularAttribute<Order, Long> size;
	public static volatile SingularAttribute<Order, Integer> orderId;
	public static volatile SingularAttribute<Order, Double> price;
	public static volatile SingularAttribute<Order, String> customerId;
	public static volatile SingularAttribute<Order, String> assetName;
	public static volatile SingularAttribute<Order, OrderStatus> status;

	public static final String SIDE = "side";
	public static final String ASSET_AGAINST = "assetAgainst";
	public static final String CREATED_DATE = "createdDate";
	public static final String SIZE = "size";
	public static final String ORDER_ID = "orderId";
	public static final String PRICE = "price";
	public static final String CUSTOMER_ID = "customerId";
	public static final String ASSET_NAME = "assetName";
	public static final String STATUS = "status";

}

