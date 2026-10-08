package com.shopee.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Integer rating;
    private String comment;
    @Builder.Default
    private List<String> images = new ArrayList<>();
    private String variantName;
    private Integer likeCount;
    /** Nguoi dang xem da bam "Huu ich" cho danh gia nay chua. */
    private Boolean isLiked;
    private String sellerReply;
    private LocalDateTime sellerReplyAt;
    private LocalDateTime createdAt;

    /** Tom tat danh gia cua 1 san pham. */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingSummary {
        private Double average;
        private Long total;
        private Long withImages;
        private Long withComment;
        /** rating (1-5) -> so luong. */
        private Map<Integer, Long> breakdown;
    }
}
