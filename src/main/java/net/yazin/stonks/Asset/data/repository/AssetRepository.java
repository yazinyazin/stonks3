package net.yazin.stonks.Asset.data.repository;

import net.yazin.stonks.Asset.model.entity.Asset;
import net.yazin.stonks.Asset.model.entity.CashAsset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset,Integer> {
    @Query("SELECT a FROM Asset a WHERE TYPE(a) = CashAsset AND a.assetName = :var1 AND a.customerId = :var2")
    Optional<CashAsset> findCashAsset(@Param("var1")String assetName, @Param("var2")String customerId);

    @Query("SELECT a FROM Asset a WHERE a.assetName = :var1 AND a.customerId = :var2")
    Optional<Asset> findAsset(@Param("var1")String assetName, @Param("var2")String customerId);

    Page<Asset> findByCustomerId(String customerId, Pageable pageable);

}
