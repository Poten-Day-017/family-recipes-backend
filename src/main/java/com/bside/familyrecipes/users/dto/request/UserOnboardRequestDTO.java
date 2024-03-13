package com.bside.familyrecipes.users.dto.request;

public record UserOnboardRequestDTO(
        Long userId,
        String userNickname
) {
}
