package com.bside.familyrecipes.users.dto.response;

import com.bside.familyrecipes.users.domain.User;
import lombok.Builder;

@Builder
public record UserProfileResponse(
        Long userId,
        String userNickname
) {
    public static UserProfileResponse of(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .userNickname(user.getUserNickname())
                .build();
    }
}
