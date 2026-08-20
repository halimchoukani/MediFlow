CREATE UNIQUE INDEX uq_departments_name_lower
    ON departments (LOWER(name));