package com.shopee.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long id;
    private String fullName;
    private String phone;
    private String province;
    private String district;
    private String ward;
    private String detailAddress;
    private String fullAddress;
    private String type;
    private Boolean isDefault;
}
