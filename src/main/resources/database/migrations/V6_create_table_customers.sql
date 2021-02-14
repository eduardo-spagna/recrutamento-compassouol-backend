CREATE TABLE customers (
   customer_id BIGSERIAL PRIMARY KEY,
   customer_name VARCHAR(90) NOT NULL,
   customer_birthdate DATE NOT NULL,
   state_id BIGINT NOT NULL,
   FOREIGN KEY (state_id) REFERENCES states (state_id)
);