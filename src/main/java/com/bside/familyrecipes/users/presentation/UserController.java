package com.bside.familyrecipes.users.presentation;

import com.bside.familyrecipes.users.application.UserService;
import com.bside.familyrecipes.common.dto.response.ResponseDto;
import com.bside.familyrecipes.users.dto.request.UserOnboardRequestDTO;
import com.bside.familyrecipes.users.dto.request.UserUpdateNicknameRequestDTO;
import com.bside.familyrecipes.users.dto.response.UserOnboardResponseDTO;
import com.bside.familyrecipes.users.dto.response.UserProfileResponse;
import com.bside.familyrecipes.users.dto.response.UserUpdateNicknameResponseDTO;
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

    @PostMapping("/nickname")
    @Operation(description = "유저 닉네임을 변경한다.")
    public ResponseEntity<UserUpdateNicknameResponseDTO> updateUserNickname(
            @RequestBody UserUpdateNicknameRequestDTO request){
        val response = userService.updateUserNickname(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/onboard")
    @Operation(description = "유저 온보딩을 진행한다.")
    public ResponseEntity<UserOnboardResponseDTO> onboardUser(
            @RequestBody UserOnboardRequestDTO request){
        val response = userService.onboardUser(request);
        return ResponseDto.ok(response);
    }
}
