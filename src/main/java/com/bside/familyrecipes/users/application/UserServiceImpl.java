package com.bside.familyrecipes.users.application;

import com.bside.familyrecipes.common.message.ExceptionMessage;
import com.bside.familyrecipes.users.domain.User;
import com.bside.familyrecipes.users.dto.request.UserOnboardRequestDTO;
import com.bside.familyrecipes.users.dto.request.UserUpdateNicknameRequestDTO;
import com.bside.familyrecipes.users.dto.response.UserOnboardResponseDTO;
import com.bside.familyrecipes.users.dto.response.UserProfileResponse;
import com.bside.familyrecipes.users.dto.response.UserUpdateNicknameResponseDTO;
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
    public UserUpdateNicknameResponseDTO updateUserNickname(UserUpdateNicknameRequestDTO request) {
        val user = findUser(request.userId());
        user.setUserNickname(request.userNickname());

        return UserUpdateNicknameResponseDTO.of(user.getUserNickname());
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    @Transactional
    @Override
    public UserOnboardResponseDTO onboardUser(UserOnboardRequestDTO request) {
        val userNickname = request.userNickname();
        val userId = request.userId();

        val user = findUser(userId);

        user.onboardUser(userNickname);

        return UserOnboardResponseDTO.of(userId, user.getUserNickname());
    }
}
