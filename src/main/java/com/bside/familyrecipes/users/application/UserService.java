package com.bside.familyrecipes.users.application;

import com.bside.familyrecipes.users.dto.request.UserOnboardRequestDTO;
import com.bside.familyrecipes.users.dto.request.UserUpdateNicknameRequestDTO;
import com.bside.familyrecipes.users.dto.response.UserOnboardResponseDTO;
import com.bside.familyrecipes.users.dto.response.UserProfileResponse;
import com.bside.familyrecipes.users.dto.response.UserUpdateNicknameResponseDTO;

public interface UserService {

    UserProfileResponse getUserProfile(Long userId);
    UserUpdateNicknameResponseDTO updateUserNickname(UserUpdateNicknameRequestDTO request);
    UserOnboardResponseDTO onboardUser(UserOnboardRequestDTO request);
}
