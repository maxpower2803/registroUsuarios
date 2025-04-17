-- Tabla de teléfonos
CREATE TABLE phone (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    citycode VARCHAR(255),
    countrycode VARCHAR(255),
    number VARCHAR(255)
);

-- Tabla de usuarios
CREATE TABLE tbl_user (
    id UUID NOT NULL PRIMARY KEY,
    created TIMESTAMP(6),
    email VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    last_login TIMESTAMP(6),
    modified TIMESTAMP(6),
    name VARCHAR(255),
    password VARCHAR(255),
    token VARCHAR(255)
);

-- Tabla de relación entre usuarios y teléfonos
CREATE TABLE tbl_user_phones (
    user_id UUID NOT NULL,
    phones_id BIGINT NOT NULL
);

-- Constraint: correo único
ALTER TABLE tbl_user 
ADD CONSTRAINT mailuUnico UNIQUE (email);

-- Constraint: phones_id único
ALTER TABLE tbl_user_phones 
ADD CONSTRAINT idPhoneUnico UNIQUE (phones_id);

-- Foreign key: phones_id referencia a phone
ALTER TABLE tbl_user_phones 
ADD CONSTRAINT idPhoneRef FOREIGN KEY (phones_id) REFERENCES phone;

-- Foreign key: user_id referencia a tbl_user
ALTER TABLE tbl_user_phones 
ADD CONSTRAINT idUserRef FOREIGN KEY (user_id) REFERENCES tbl_user;
