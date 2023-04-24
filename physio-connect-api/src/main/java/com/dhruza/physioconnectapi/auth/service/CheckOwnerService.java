package com.dhruza.physioconnectapi.auth.service;

import com.dhruza.physioconnectapi.auth.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CheckOwnerService {
    public boolean hasResourceOwnership(Authentication authentication, Long id) {
        if(authentication.getPrincipal() instanceof SecurityUser){
            SecurityUser user = (SecurityUser) authentication.getPrincipal();
            return Objects.equals(user.getAppUserId(), id);
        }
        return false;
    }
}
