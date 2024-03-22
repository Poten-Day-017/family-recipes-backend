package com.bside.familyrecipes.oauth;

import com.bside.familyrecipes.users.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserAuthentication extends AbstractAuthenticationToken {

    private static Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"))    ;
    private final String deviceToken;
    private final Long userId;


    public UserAuthentication(User user) {
        super(authorities);
        this.userId = user.getId();
        this.deviceToken = user.getDeviceToken();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
