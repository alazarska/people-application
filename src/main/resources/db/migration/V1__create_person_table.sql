CREATE TABLE IF NOT EXISTS person
(
    id BIGINT NOT NULL PRIMARY KEY,
    dob DATE NOT NULL,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    salary NUMERIC(19,2) NOT NULL,
    photo_file_name VARCHAR(255)
)
