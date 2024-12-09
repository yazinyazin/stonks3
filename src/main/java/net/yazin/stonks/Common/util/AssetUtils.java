package net.yazin.stonks.Common.util;

public class AssetUtils {

    public static final String ASSET_NAME_TURKISH_LIRA = "TRY";

    public static boolean isCashAsset(String assetName) {
        //change this if we ever support other currencies
        return assetName.equalsIgnoreCase(ASSET_NAME_TURKISH_LIRA);
    }
}
