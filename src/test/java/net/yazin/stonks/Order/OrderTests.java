package net.yazin.stonks.Order;

import net.yazin.stonks.Common.model.enums.OrderSide;
import net.yazin.stonks.Common.util.AssetUtils;
import net.yazin.stonks.Order.model.dto.GenerateOrderDTO;
import net.yazin.stonks.Order.model.entity.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTests {


    @Test
    void place_order_against_cash_asset_only(){
        assertThrows(IllegalStateException.class,()-> Order.generateNewOrder(new GenerateOrderDTO("a", "KCO","KCOTAS", OrderSide.SELL,100,52.2)));
    }

    @Test
    void do_not_order_against_itself(){
        assertThrows(IllegalStateException.class,()-> Order.generateNewOrder(new GenerateOrderDTO("a", AssetUtils.ASSET_NAME_TURKISH_LIRA,AssetUtils.ASSET_NAME_TURKISH_LIRA, OrderSide.SELL,100,52.2)));
    }

    @Test
    void do_not_match_tentative_order(){
        Order order = Order.generateNewOrder(new GenerateOrderDTO("aa","KCOTAS",AssetUtils.ASSET_NAME_TURKISH_LIRA,OrderSide.SELL,100,23));
        assertThrows(IllegalStateException.class,()->order.match());
    }
}
