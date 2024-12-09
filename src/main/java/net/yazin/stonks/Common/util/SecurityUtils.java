package net.yazin.stonks.Common.util;

import net.yazin.stonks.Common.model.enums.UserRole;
import net.yazin.stonks.Common.model.dto.CustomerInfo;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {

    public static boolean checkRole(UserRole role){
      return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(r -> r.getAuthority().equalsIgnoreCase(role.onServer));
    }

    public static void restrict(CustomerInfo customerInfo){
        if(!checkRole(UserRole.ADMIN)){
            customerInfo.setCustomerId(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }

    public static void checkCustomer(CustomerInfo customerInfo){
        if(!checkRole(UserRole.ADMIN)
            && customerInfo.getCustomerId() != null
            && !customerInfo.getCustomerId().equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())
        ){
            throw new RuntimeException("You do not have access to this resource.");
        }
    }
}
