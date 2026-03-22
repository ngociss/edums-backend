package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockRequest {

    @NotBlank(message = "Mã khối không được để trống")
    @Size(max = 5, message = "Mã khối tối đa 5 ký tự")
    private String blockName;

    @Size(max = 50, message = "Mô tả tối đa 50 ký tự")
    private String description;
}