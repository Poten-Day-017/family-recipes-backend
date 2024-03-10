package com.bside.familyrecipes.users.dto.response;

import com.bside.familyrecipes.users.domain.User;
import lombok.Builder;

@Builder
public record UserOnboardResponseDTO(
        Long userId,
        String userNickname
) {
    public static UserOnboardResponseDTO of(Long userId, String userNickname) {
        return UserOnboardResponseDTO.builder()
                .userId(userId)
                .userNickname(userNickname)
                .build();
    }
}
