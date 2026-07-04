CREATE TABLE users (
      id UUID PRIMARY KEY,

      first_name VARCHAR(100) NOT NULL,
      last_name VARCHAR(100) NOT NULL,

      email VARCHAR(255) NOT NULL UNIQUE,
      password VARCHAR(255) NOT NULL,

      phone_number VARCHAR(20),

      role VARCHAR(20) NOT NULL,

      status VARCHAR(20) NOT NULL,

      email_verified BOOLEAN NOT NULL DEFAULT FALSE,

      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);