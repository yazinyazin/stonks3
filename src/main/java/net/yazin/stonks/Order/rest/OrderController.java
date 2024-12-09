package net.yazin.stonks.Order.rest;

import lombok.RequiredArgsConstructor;
import net.yazin.stonks.Order.model.dto.GenerateOrderDTO;
import net.yazin.stonks.Order.model.dto.OrderSearchParamsDTO;
import net.yazin.stonks.Order.model.entity.Order;
import net.yazin.stonks.Order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("generate")
    public ResponseEntity<Void> generateOrder(@RequestBody GenerateOrderDTO orderDTO){
        orderService.generateOrder(orderDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("match")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> matchOrder(@RequestParam("id")int id){
        orderService.matchOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("cancel")
    public ResponseEntity<Void> cancelOrder(@RequestParam("id")int id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("search")
    public ResponseEntity<Page<Order>> search(@RequestBody OrderSearchParamsDTO params){
        return ResponseEntity.ok(orderService.search(params));
    }
}
