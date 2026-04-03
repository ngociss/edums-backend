ALTER TABLE semesters
    ADD COLUMN status VARCHAR(30) NOT NULL DEFAULT 'PLANNING' AFTER total_weeks;

UPDATE semesters
SET status = 'PLANNING'
WHERE status IS NULL OR status = '';
