-- Allow multiple registration periods in the same semester.
-- Keep the foreign-key supporting index (FK_REGISTRATION_PERIODS_ON_SEMESTER) intact.
ALTER TABLE `registration_periods`
    DROP INDEX `semester_id`;

