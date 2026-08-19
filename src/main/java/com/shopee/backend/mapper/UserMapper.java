package com.shopee.backend.mapper;

import com.shopee.backend.dto.response.AddressResponse;
import com.shopee.backend.dto.response.UserResponse;
import com.shopee.backend.entity.Address;
import com.shopee.backend.entity.Enum.RoleName;
import com.shopee.backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return toResponse(user, null);
    }

    public UserResponse toResponse(User user, Long shopId) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .emailVerified(user.getEmailVerified())
                .walletBalance(user.getWalletBalance())
                .shopeeCoin(user.getShopeeCoin())
                .roles(user.getRoles().stream().map(RoleName::name).collect(Collectors.toSet()))
                .shopId(shopId)
                .createdAt(user.getCreateAt())
                .build();
    }

    public AddressResponse toResponse (Address address){
        if (address == null) {
            return null;
        }
        return AddressResponse.builder()
                .id(address.getId())
                .fullName(address.getFullName())
                .phone(address.getPhone())
                .province(address.getProvince())
                .district(address.getDistrict())
                .ward(address.getWard())
                .detailAddress(address.getDetailAddress())
                .fullAddress(address.toFullAddress())
                .type(address.getType())
                .isDefault(address.getIsDefault())
                .build();

    }
}
