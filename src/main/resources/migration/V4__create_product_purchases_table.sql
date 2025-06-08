CREATE TABLE product_purchases (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    buyer_id UUID NOT NULL,
    purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (buyer_id) REFERENCES users(id)
); 