CREATE TABLE cities (
   city_id BIGSERIAL PRIMARY KEY,
   city_name VARCHAR(40) NOT NULL,
   state_id BIGINT NOT NULL,
   FOREIGN KEY (state_id) REFERENCES states (state_id)
);