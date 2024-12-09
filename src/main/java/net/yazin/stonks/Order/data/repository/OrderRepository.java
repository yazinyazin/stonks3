package net.yazin.stonks.Order.data.repository;

import net.yazin.stonks.Common.model.enums.OrderStatus;
import net.yazin.stonks.Order.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Modifying
    @Query("UPDATE Order o SET o.status = :var1 WHERE o.orderId = :var2")
    int updateOrderStatus(@Param("var1") OrderStatus orderStatus,@Param("var2") int orderId);

    Page<Order> findByCustomerIdAndCreatedDateGreaterThanAndCreatedDateLessThan(String customerId, long startDate, long endDate, Pageable pageable);
}
