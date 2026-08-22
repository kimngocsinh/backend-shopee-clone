package com.shopee.backend.service;

import com.shopee.backend.dto.request.UserRequests.AddressRequest;
import com.shopee.backend.dto.request.UserRequests.UpdateProfileRequest;
import com.shopee.backend.dto.response.AddressResponse;
import com.shopee.backend.dto.response.UserResponse;
import com.shopee.backend.entity.Address;
import com.shopee.backend.entity.User;
import com.shopee.backend.exception.BusinessException;
import com.shopee.backend.exception.ResourceNotFoundException;
import com.shopee.backend.mapper.UserMapper;
import com.shopee.backend.repository.AddressRepository;
import com.shopee.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse getProfile(Long userId) {
        User user = getUser(userId);
        Long shopId = 1L;
        return userMapper.toResponse(user, shopId);
    }

    /**
     * update profile user
     * @param userId
     * @param request
     * @return UserResponse
     */
    public UserResponse updateProfile (Long userId, UpdateProfileRequest request) {
        User user = getUser(userId);
        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BusinessException("Email da duoc su dung boi tai khoan khac");
            }
            user.setEmail(request.getEmail());
            user.setEmailVerified(false);
        }
        if (request.getPhone() != null && !request.getPhone().isBlank() && request.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new BusinessException("So dien thoai da duoc su dung");
            }
            user.setPhone(request.getPhone());
        }
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        userRepository.save(user);
        Long shopId = 1L;
        return userMapper.toResponse(user, shopId);
    }

    /**
     * Update avatar user
     * @param userId
     * @param avatarUrl
     * @return UserResponse
     */
    @Transactional
    public UserResponse updateAvatar(Long userId, String avatarUrl) {
        User user = getUser(userId);
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    /**  ---------Address---------------   */
    @Transactional(readOnly = true)
    public List<AddressResponse> listAddresses(Long userId) {
        return addressRepository.findByUserIdOrderByIsDefaultDescIdDesc(userId).stream()
                .map(userMapper::toResponse).toList();
    }

    /**
     * Tao moi address
     * @param userId
     * @param request
     * @return AddressResponse
     */
    @Transactional
    public AddressResponse createAddress(Long userId, AddressRequest request) {
        User user = getUser(userId);
        boolean first = addressRepository.countByUserId(userId) == 0;
        boolean makeDefault = Boolean.TRUE.equals(request.getIsDefault()) || first;
        if (makeDefault) {
            addressRepository.clearDefault(userId);
        }

        Address address = Address.builder()
                .user(user)
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .province(request.getProvince())
                .district(request.getDistrict())
                .ward(request.getWard())
                .detailAddress(request.getDetailAddress())
                .type(request.getType() != null ? request.getType() : "HOME")
                .build();
        return userMapper.toResponse(addressRepository.save(address));
    }

    public AddressResponse updateAddress(Long addressId, Long userId, AddressRequest request) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Dia chi", addressId));
        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());
        address.setProvince(request.getProvince());
        address.setDistrict(request.getDistrict());
        address.setWard(request.getWard());
        address.setDetailAddress(request.getDetailAddress());
        if (request.getType() != null) {
            address.setType(request.getType());
        }
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.clearDefault(userId);
            address.setIsDefault(true);
        }
        return userMapper.toResponse(addressRepository.save(address));
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Dia chi", addressId));

        boolean wasDefault = Boolean.TRUE.equals(address.getIsDefault());
        addressRepository.delete(address);

        if (wasDefault) {
            addressRepository.findByUserIdOrderByIsDefaultDescIdDesc(userId).stream()
                    .findFirst()
                    .ifPresent(a -> {
                        a.setIsDefault(true);
                        addressRepository.save(a);
                    });
        }
    }

    @Transactional
    public AddressResponse setDefaultAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Dia chi", addressId));
        addressRepository.clearDefault(userId);
        address.setIsDefault(true);
        return userMapper.toResponse(addressRepository.save(address));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Nguoi dung", userId));
    }
}
