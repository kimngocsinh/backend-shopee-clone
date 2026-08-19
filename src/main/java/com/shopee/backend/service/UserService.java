package com.shopee.backend.service;

import com.shopee.backend.entity.User;
import com.shopee.backend.exception.ResourceNotFoundException;
import com.shopee.backend.mapper.UserMapper;
import com.shopee.backend.repository.AddressRepository;
import com.shopee.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Nguoi dung", userId));
    }
}
