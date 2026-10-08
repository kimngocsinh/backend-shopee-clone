package com.shopee.backend.service;

import com.shopee.backend.dto.request.AuthRequests.*;
import com.shopee.backend.dto.response.AuthResponse;
import com.shopee.backend.dto.response.UserResponse;
import com.shopee.backend.entity.*;
import com.shopee.backend.entity.Enum.NotificationType;
import com.shopee.backend.entity.Enum.RoleName;
import com.shopee.backend.entity.Enum.UserStatus;
import com.shopee.backend.exception.BusinessException;
import com.shopee.backend.exception.ResourceNotFoundException;
import com.shopee.backend.mapper.UserMapper;
import com.shopee.backend.repository.*;
import com.shopee.backend.security.JwtService;
import com.shopee.backend.security.UserPrincipal;
import com.shopee.backend.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final NotificationService notificationService;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ShopRepository shopRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Ten dang nhap da duoc su dung");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email da duoc su dung");
        }
        if (request.getPhone() != null && request.getPhone().isBlank() && userRepository.existsByPhone(request.getPhone())) {
            throw new BusinessException("So dien thoai da duoc dang ky");
        }

        Set<RoleName> roles = new HashSet<>();
        roles.add(RoleName.ROLE_USER);

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(emptyToNull(request.getPhone()))
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName() != null ? request.getFullName() : request.getUsername())
                .status(UserStatus.ACTIVE)
                .roles(roles)
                .build();
        user = userRepository.save(user);

        cartRepository.save(Cart.builder().user(user).build());

        issueOtp(user, "EMAIL_VERIFY");

        notificationService.push(user, "Chao mung den voi Shopee Clone!",
                "Tai khoan cua ban da duoc tao thanh cong. Kham pha ngay hang trieu san pham gia tot.",
                NotificationType.SYSTEM, null);

        return buildAuthResponse(new UserPrincipal(user), user);
    }

    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Nguoi dung", userPrincipal.getId()));

        if(user.getStatus() == UserStatus.BANNED) {
            throw new BusinessException("Tai khoan cua ban da bi khoa", HttpStatus.FORBIDDEN);
        }

        //Dam bao nguoi dung luon co gio hang
        cartRepository.findByUserId(user.getId()).orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));

        return buildAuthResponse(userPrincipal, user);
    }

    private String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }

    private String issueOtp(User user, String purpose) {
        String otp = CodeGenerator.otp();
        verificationTokenRepository.save(VerificationToken.builder()
                        .token(otp)
                        .purpose(purpose)
                        .user(user)
                        .expiresAt(Instant.now().plusSeconds(900))
                .build());
        log.info("OTP [{}] cho user {}: {}", purpose, user.getUsername(), otp);
        return otp;
    }

    private AuthResponse buildAuthResponse(UserPrincipal principal, User user) {
        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);
        refreshTokenRepository.save(RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiresAt(Instant.now().plusMillis(jwtService.getRefreshTokenExpiration()))
                .build());

        Long shopId = shopRepository.findByOwnerId(user.getId()).map(BaseEntity::getId).orElse(null);
        UserResponse userResponse = userMapper.toResponse(user, shopId);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtService.getAccessTokenExpiration() / 1000) // đổi về giây
                .user(userResponse)
                .build();
    }

}
