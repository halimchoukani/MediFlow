CREATE TABLE departments (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             description VARCHAR(500),
                             active BOOLEAN NOT NULL DEFAULT TRUE,
                             created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                             updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_departments_name
    ON departments(name);