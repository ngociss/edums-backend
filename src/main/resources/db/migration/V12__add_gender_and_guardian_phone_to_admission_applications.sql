ALTER TABLE admission_applications
    ADD gender BIT(1) NULL;

ALTER TABLE admission_applications
    ADD guardian_phone VARCHAR(20) NULL;