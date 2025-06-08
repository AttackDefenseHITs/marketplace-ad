CREATE TABLE user_balances (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    balance DECIMAL(19,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
); 