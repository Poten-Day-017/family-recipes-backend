package com.bside.familyrecipes.users.application;

import com.bside.familyrecipes.common.message.ExceptionMessage;
import com.bside.familyrecipes.users.domain.User;
import com.bside.familyrecipes.users.dto.request.UserUpdateNicknameRequest;
import com.bside.familyrecipes.users.dto.response.UserProfileResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        val user = findUser(userId);
        return UserProfileResponse.of(user);
    }

    @Transactional
    @Override
    public void updateUserNickname(UserUpdateNicknameRequest request) {
        val user = findUser(request.userId());
        user.setUserNickname(request.nickname());
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }
}
