package com.G5C.EduMS.common.enums;

import lombok.Getter;

/**
 * Tiết học trong ngày.
 * Hệ thống nhà trường gồm 10 tiết:
 *   Tiết 1: 07:00 – 07:50
 *   Tiết 2: 07:50 – 08:40
 *   Tiết 3: 09:00 – 09:50
 *   Tiết 4: 09:50 – 10:40
 *   Tiết 5: 10:40 – 11:30
 *   Tiết 6: 13:00 - 13:50
 *   Tiết 7: 13:50 - 14:40
 *   Tiết 8: 15:00  - 15:50
 *   Tiết 9: 15:50 - 16:40
 *   Tiết 10: 16:40 - 17:30
 */
@Getter
public enum SchoolPeriod {
    PERIOD_1(1, "07:00 – 07:50"),
    PERIOD_2(2, "07:50 – 08:40"),
    PERIOD_3(3, "09:00 – 09:50"),
    PERIOD_4(4, "09:50 – 10:40"),
    PERIOD_5(5, "10:40 – 11:30"),
    PERIOD_6(6, "13:00 - 13:50"),
    PERIOD_7(7, "13:50 - 14:40"),
    PERIOD_8(8, "15:00  - 15:50"),
    PERIOD_9(9, "15:50 - 16:40"),
    PERIOD_10(10, "16:40 - 17:30");

    private final int value;
    private final String timeRange;

    SchoolPeriod(int value, String timeRange) {
        this.value = value;
        this.timeRange = timeRange;
    }

    public static final int MIN_PERIOD = 1;
    public static final int MAX_PERIOD = 10;

    /**
     * Kiểm tra tiết học có hợp lệ (1–10) không
     */
    public static boolean isValid(int value) {
        return value >= MIN_PERIOD && value <= MAX_PERIOD;
    }

    /**
     * Lấy chuỗi khung giờ theo số tiết
     */
    public static String getTimeRange(int value) {
        for (SchoolPeriod p : values()) {
            if (p.value == value) return p.timeRange;
        }
        return null;
    }
}

