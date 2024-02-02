package com.bside.familyrecipes.users.application;

import com.bside.familyrecipes.users.dto.request.UserUpdateNicknameRequest;
import com.bside.familyrecipes.users.dto.response.UserProfileResponse;

public interface UserService {

    UserProfileResponse getUserProfile(Long userId);
    void updateUserNickname(UserUpdateNicknameRequest request);
}
