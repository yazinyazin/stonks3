package net.yazin.stonks.Common.model.enums;

public enum OrderStatus {
    TENTATIVE, //sent asset reservation request, asset info unknown
    PENDING,  //succesfully reserved asset, matchable
    MATCHED,
    CANCELLED
}
