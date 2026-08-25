package com.shopee.backend.controller;

import com.shopee.backend.common.ApiResponse;
import com.shopee.backend.dto.request.UpdateAvatarRequest;
import com.shopee.backend.dto.request.UserRequests;
import com.shopee.backend.dto.response.UserResponse;
import com.shopee.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
}
