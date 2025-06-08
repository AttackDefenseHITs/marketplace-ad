CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    seller_id UUID NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    FOREIGN KEY (seller_id) REFERENCES users(id)
); 