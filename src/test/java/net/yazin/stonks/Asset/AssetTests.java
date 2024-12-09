package net.yazin.stonks.Asset;

import net.yazin.stonks.Asset.model.entity.Asset;
import net.yazin.stonks.Asset.model.entity.CashAsset;
import net.yazin.stonks.Asset.model.entity.StockAsset;
import net.yazin.stonks.Common.model.enums.OrderSide;
import net.yazin.stonks.Common.util.AssetUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssetTests {

    @Test
    void polymorphism_check(){
        Asset cashAsset1 = new CashAsset();
        assertThrows(IllegalStateException.class,()-> cashAsset1.setAssetName("KCOTAS"));
        assertThrows(IllegalStateException.class,()-> new CashAsset("KCOTAS","c"));

        Asset stockAsset1 = new StockAsset();
        assertThrows(IllegalStateException.class,()-> stockAsset1.setAssetName(AssetUtils.ASSET_NAME_TURKISH_LIRA));
        assertThrows(IllegalStateException.class,()-> new StockAsset(AssetUtils.ASSET_NAME_TURKISH_LIRA,"c"));

    }

    @Test
    void reserve_successfully(){
        Asset cashAsset1 = new CashAsset();
        cashAsset1.setSize(200);
        cashAsset1.setUsableSize(200);
        assertTrue(cashAsset1.reserve(150));
    }

    @Test
    void reserve_fail(){
        Asset cashAsset1 = new CashAsset();
        cashAsset1.setSize(200);
        assertFalse(cashAsset1.reserve(250));
    }

    @Test
    void cash_asset_update_after_succesful_purchase(){
        Asset cashAsset1 = new CashAsset();
        cashAsset1.setSize(200);
        cashAsset1.setUsableSize(200);
        cashAsset1.reserve(150);

        assertEquals(50,cashAsset1.getUsableSize());
        assertEquals(200,cashAsset1.getSize());

        cashAsset1.updateAfterMatchedOrder(OrderSide.BUY,150);

        assertEquals(50,cashAsset1.getUsableSize());
        assertEquals(50,cashAsset1.getSize());
    }

    @Test
    void cash_asset_update_after_succesful_sale(){
        Asset cashAsset1 = new CashAsset();

        cashAsset1.updateAfterMatchedOrder(OrderSide.SELL,150);

        assertEquals(150,cashAsset1.getUsableSize());
        assertEquals(150,cashAsset1.getSize());
    }

    @Test
    void cash_asset_withdraw(){
        CashAsset cashAsset1 = new CashAsset();
        cashAsset1.setSize(200);
        cashAsset1.setUsableSize(200);
        cashAsset1.reserve(100);

        assertDoesNotThrow(()->cashAsset1.withdraw(100));
        assertEquals(0,cashAsset1.getUsableSize());
        assertThrows(IllegalStateException.class,()->cashAsset1.withdraw(100));

    }

    @Test
    void stock_asset_update_after_succesful_sale(){
        Asset stockAsset1 = new StockAsset();
        stockAsset1.setSize(200);
        stockAsset1.setUsableSize(200);
        stockAsset1.reserve(150);

        assertEquals(50,stockAsset1.getUsableSize());
        assertEquals(200,stockAsset1.getSize());

        stockAsset1.updateAfterMatchedOrder(OrderSide.SELL,150);

        assertEquals(50,stockAsset1.getUsableSize());
        assertEquals(50,stockAsset1.getSize());
    }

    @Test
    void stock_asset_update_after_succesful_purchase(){
        Asset stockAsset1 = new StockAsset();

        stockAsset1.updateAfterMatchedOrder(OrderSide.BUY,150);

        assertEquals(150,stockAsset1.getUsableSize());
        assertEquals(150,stockAsset1.getSize());
    }
}
