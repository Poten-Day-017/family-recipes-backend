package com.bside.familyrecipes.users.presentation;

import com.bside.familyrecipes.users.application.UserService;
import com.bside.familyrecipes.common.dto.response.ResponseDto;
import com.bside.familyrecipes.users.dto.request.UserUpdateNicknameRequest;
import com.bside.familyrecipes.users.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "UserController", description = "Users API")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(description = "유저 프로필을 조회한다.")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId){
        val response = userService.getUserProfile(userId);
        return ResponseDto.ok(response);
    }

    @PutMapping("/{userId}/nickname")
    @Operation(description = "유저 닉네임을 변경한다.")
    public String updateUserNickname(
            @PathVariable Long userId,
            @RequestBody UserUpdateNicknameRequest request){
        userService.updateUserNickname(request);
        return "success";
    }
}
