-- Add new attributes
ALTER TABLE customers ADD COLUMN customer_name_normalized VARCHAR(90) NOT NULL;
ALTER TABLE customers ADD COLUMN customer_created_at TIMESTAMP NOT NULL;
ALTER TABLE customers ADD COLUMN customer_updated_at TIMESTAMP NOT NULL;

-- Rename an incorrect attribute
ALTER TABLE customers RENAME COLUMN state_id TO city_id;

-- Add new foreign key with genders table
ALTER TABLE customers ADD COLUMN gender_id BIGINT NOT NULL;
ALTER TABLE customers ADD CONSTRAINT customers_gender_id_fkey FOREIGN KEY (gender_id) REFERENCES genders(gender_id) ON DELETE CASCADE ON UPDATE CASCADE;