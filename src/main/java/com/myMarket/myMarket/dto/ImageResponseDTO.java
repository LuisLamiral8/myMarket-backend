package com.myMarket.myMarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDTO {
    private String fileName;
    private String contentType;
    private byte[] data;
}
