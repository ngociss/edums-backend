ALTER TABLE course_sections
    DROP INDEX uk_section_code_semester;

ALTER TABLE course_sections
    ADD CONSTRAINT uk_course_section_group UNIQUE (course_id, semester_id, section_code);
