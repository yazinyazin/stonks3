package net.yazin.stonks.Asset.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.yazin.stonks.Common.model.dto.CustomerInfo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssetSearchParamsDTO implements CustomerInfo {
    private String customerId;
    private int pageNumber;
    private int itemCount;

}
