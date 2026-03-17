package com.G5C.EduMS.common.enums;

import lombok.Getter;

/**
 * Thứ trong tuần theo chuẩn ISO: 1 = Thứ Hai, 7 = Chủ Nhật
 */
@Getter
public enum DayOfWeek {
    MONDAY(1, "Thứ Hai"),
    TUESDAY(2, "Thứ Ba"),
    WEDNESDAY(3, "Thứ Tư"),
    THURSDAY(4, "Thứ Năm"),
    FRIDAY(5, "Thứ Sáu"),
    SATURDAY(6, "Thứ Bảy"),
    SUNDAY(7, "Chủ Nhật");

    private final int value;
    private final String displayName;

    DayOfWeek(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * Lấy enum từ giá trị số (1–7)
     */
    public static DayOfWeek fromValue(int value) {
        for (DayOfWeek dow : values()) {
            if (dow.value == value) return dow;
        }
        throw new IllegalArgumentException(
                "Ngày trong tuần không hợp lệ: " + value + ". Phải từ 1 (Thứ Hai) đến 7 (Chủ Nhật)");
    }

    /**
     * Chuyển sang java.time.DayOfWeek (tương thích ISO)
     */
    public java.time.DayOfWeek toJavaDayOfWeek() {
        return java.time.DayOfWeek.of(this.value);
    }
}

