package com.bside.familyrecipes.users.dto.request;

public record UserUpdateNicknameRequest(
        Long userId,
        String nickname
) {
}
