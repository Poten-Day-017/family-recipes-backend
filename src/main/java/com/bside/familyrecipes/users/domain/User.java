package com.bside.familyrecipes.users.domain;

import com.bside.familyrecipes.recipes.domain.Recipe;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_profile_image")
    private String profileImage;

    @OneToMany(mappedBy = "user")
    List<Recipe> recipes = new ArrayList<>();

    @Builder
    public User(String userNickname, String profileImage){
        this.userNickname = userNickname;
        this.profileImage = profileImage;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
