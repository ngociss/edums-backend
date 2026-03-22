-- =========================================================================
-- PHẦN 1: XỬ LÝ RIÊNG CHO BẢNG ADMISSION_BLOCKS (ĐỔI TỪ INT SANG BIT)
-- Lệnh MODIFY này sẽ tự động convert dữ liệu cũ: 0 -> b'0', 1 -> b'1'
-- =========================================================================
ALTER TABLE admission_blocks MODIFY COLUMN deleted BIT(1) NOT NULL DEFAULT b'0';