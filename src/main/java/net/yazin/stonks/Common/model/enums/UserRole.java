package net.yazin.stonks.Common.model.enums;

public enum UserRole {

    ADMIN("admin"),CUSTOMER("customer");

    public String onServer;

    UserRole(String onServer){
        this.onServer = onServer;
    }
}
