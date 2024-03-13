package com.bside.familyrecipes.users.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserUpdateNicknameResponseDTO(
        @NotEmpty(message = "user nickname은 null일 수 없습니다.")
        String userNickname
) {
    public static UserUpdateNicknameResponseDTO of (String userNickname){
        return UserUpdateNicknameResponseDTO.builder()
                .userNickname(userNickname)
                .build();
    }

}
