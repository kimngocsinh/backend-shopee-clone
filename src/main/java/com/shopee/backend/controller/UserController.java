package com.shopee.backend.controller;

import com.shopee.backend.common.ApiResponse;
import com.shopee.backend.dto.request.UpdateAvatarRequest;
import com.shopee.backend.dto.request.UserRequests;
import com.shopee.backend.dto.response.AddressResponse;
import com.shopee.backend.dto.response.UserResponse;
import com.shopee.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* -----------Ho so -----------*/
    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(id)));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @PathVariable Long id, //Tam thoi de id sau se dung tai khoan login de lay id
            @Valid @RequestBody UserRequests.UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateProfile(id, request)));
    }

    @PutMapping("/avatar/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateAvatar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAvatarRequest request
            ) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateAvatar(id, request.avatarUrl())));
    }

    /*  ------------------Địa chỉ--------------------*/

    @GetMapping("/addresses")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> listAddresses(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.listAddresses(id)));
    }

    @PostMapping("/address/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress (
            @PathVariable Long userId, // tạm thời để id vậy sau sẽ get từ security
            @RequestBody UserRequests.AddressRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.createAddress(userId, request)));
    }

    @PutMapping("/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress (
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody UserRequests.AddressRequest request
            ) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateAddress(addressId, userId, request)));
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> deleteAddress (
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        userService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(ApiResponse.success(null, "Da xoa dia chi"));
    }

    @PutMapping("/{userId}/address/default/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> setDefaultAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        return ResponseEntity.ok(ApiResponse.success(userService.setDefaultAddress(userId, addressId)));
    }
}
