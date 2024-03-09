package com.bside.familyrecipes.users.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserUpdateNicknameRequestDTO(
        @NotEmpty(message = "user id 는 null일 수 없습니다.")
        Long userId,
        @NotEmpty(message = "user nickname 는 null일 수 없습니다.")
        String userNickname
) {
}
