package com.shopee.backend.service;

import com.shopee.backend.entity.Enum.NotificationType;
import com.shopee.backend.entity.Notification;
import com.shopee.backend.entity.User;
import com.shopee.backend.mapper.MiscMapper;
import com.shopee.backend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final MiscMapper miscMapper;

    public Notification push(User user, String title, String content, NotificationType type, String link) {
        Notification notification = notificationRepository.save(Notification.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(type)
                .link(link)
                .build());

        try {
            messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/notifications",
                    miscMapper.toResponse(notification));
        } catch (Exception ex) {
            log.debug("Khong the day thong bao realtime: {}", ex.getMessage());
        }
        return notification;
    }
}
