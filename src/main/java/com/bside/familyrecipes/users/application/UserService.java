package com.bside.familyrecipes.users.application;

import com.bside.familyrecipes.users.dto.response.UserProfileResponse;

public interface UserService {

    UserProfileResponse getUserProfile(Long userId);
}
