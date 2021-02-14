-- create a new table
CREATE TABLE new_states (
   state_id BIGSERIAL PRIMARY KEY,
   state_name VARCHAR(20) NOT NULL,
   state_name_normalized VARCHAR(20),
   state_short_name VARCHAR(2) NOT NULL
);

-- copy data from old table to auxiliary table
INSERT INTO new_states (state_id, state_name, state_short_name) SELECT state_id, state_name, state_short_name FROM states;

-- change the foreign key to the new table
ALTER TABLE cities DROP CONSTRAINT cities_state_id_fkey;
ALTER TABLE cities ADD CONSTRAINT cities_state_id_fkey FOREIGN KEY (state_id) REFERENCES new_states(state_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- delete old table
DROP TABLE states CASCADE;

-- rename the new table
ALTER TABLE new_states RENAME TO states;

-- update data
UPDATE states SET state_name_normalized = 'acre' WHERE state_name LIKE 'Acre';
UPDATE states SET state_name_normalized = 'alagoas' WHERE state_name LIKE 'Alagoas';
UPDATE states SET state_name_normalized = 'amapa' WHERE state_name LIKE 'Amapá';
UPDATE states SET state_name_normalized = 'amazonas' WHERE state_name LIKE 'Amazonas';
UPDATE states SET state_name_normalized = 'bahia' WHERE state_name LIKE 'Bahia';
UPDATE states SET state_name_normalized = 'ceara' WHERE state_name LIKE 'Ceará';
UPDATE states SET state_name_normalized = 'distrito_federal' WHERE state_name LIKE 'Distrito Federal';
UPDATE states SET state_name_normalized = 'espirito_santo' WHERE state_name LIKE 'Espírito Santo';
UPDATE states SET state_name_normalized = 'goias' WHERE state_name LIKE 'Goiás';
UPDATE states SET state_name_normalized = 'maranhao' WHERE state_name LIKE 'Maranhão';
UPDATE states SET state_name_normalized = 'mato_grosso' WHERE state_name LIKE 'Mato Grosso';
UPDATE states SET state_name_normalized = 'mato_grosso_do_sul' WHERE state_name LIKE 'Mato Grosso do Sul';
UPDATE states SET state_name_normalized = 'minas_gerais' WHERE state_name LIKE 'Minas Gerais';
UPDATE states SET state_name_normalized = 'para' WHERE state_name LIKE 'Pará';
UPDATE states SET state_name_normalized = 'paraiba' WHERE state_name LIKE 'Paraíba';
UPDATE states SET state_name_normalized = 'parana' WHERE state_name LIKE 'Paraná';
UPDATE states SET state_name_normalized = 'pernambuco' WHERE state_name LIKE 'Pernambuco';
UPDATE states SET state_name_normalized = 'piaui' WHERE state_name LIKE 'Piauí';
UPDATE states SET state_name_normalized = 'rio_de_janeiro' WHERE state_name LIKE 'Rio de Janeiro';
UPDATE states SET state_name_normalized = 'rio_grande_do_norte' WHERE state_name LIKE 'Rio Grande do Norte';
UPDATE states SET state_name_normalized = 'rio_grande_do_sul' WHERE state_name LIKE 'Rio Grande do Sul';
UPDATE states SET state_name_normalized = 'rondonia' WHERE state_name LIKE 'Rondônia';
UPDATE states SET state_name_normalized = 'roraima' WHERE state_name LIKE 'Roraima';
UPDATE states SET state_name_normalized = 'santa_catarina' WHERE state_name LIKE 'Santa Catarina';
UPDATE states SET state_name_normalized = 'sao_paulo' WHERE state_name LIKE 'São Paulo';
UPDATE states SET state_name_normalized = 'sergipe' WHERE state_name LIKE 'Sergipe';
UPDATE states SET state_name_normalized = 'tocantins' WHERE state_name LIKE 'Tocantins';

-- modify colum to add state_name_normalized NOT NULL
ALTER TABLE states ALTER COLUMN state_name_normalized SET NOT NULL;