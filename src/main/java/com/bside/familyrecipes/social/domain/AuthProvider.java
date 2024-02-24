package com.bside.familyrecipes.social.domain;

import com.bside.familyrecipes.common.TimeStamped;
import com.bside.familyrecipes.social.enums.ProviderType;
import com.bside.familyrecipes.users.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "\"AuthProvider\"")
public class AuthProvider extends TimeStamped {

    @Id
    @Column(name = "auth_provider_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="provider_type")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
}