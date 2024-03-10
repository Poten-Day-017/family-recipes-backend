package com.bside.familyrecipes.users.dto.response;

import com.bside.familyrecipes.users.domain.User;
import lombok.Builder;

@Builder
public record UserProfileResponse(
        Long userId,
        String userNickname,
        int numberOfRecipes
) {
    public static UserProfileResponse of(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .userNickname(user.getUserNickname())
                .numberOfRecipes(user.getRecipes().size())
                .build();
    }
}
