package net.yazin.stonks.Asset.rest;

import lombok.RequiredArgsConstructor;
import net.yazin.stonks.Asset.model.dto.AssetSearchParamsDTO;
import net.yazin.stonks.Asset.model.dto.CashRequestDTO;
import net.yazin.stonks.Asset.model.entity.Asset;
import net.yazin.stonks.Asset.service.AssetService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/assets")
public class AssetController {

    private final AssetService assetService;

    @PostMapping("withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody CashRequestDTO cashRequest){
        assetService.withdrawCash(cashRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("deposit")
    public ResponseEntity<Void> deposit(@RequestBody CashRequestDTO cashRequest){
        assetService.depositCash(cashRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("search")
    public ResponseEntity<Page<Asset>> search(@RequestBody AssetSearchParamsDTO params){
        return ResponseEntity.ok(assetService.search(params));
    }
}
