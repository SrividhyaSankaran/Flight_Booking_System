\connect flight_db;

CREATE TABLE airports (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    city VARCHAR(150),
    country VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE airlines (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE routes (
    id BIGSERIAL PRIMARY KEY,
    source_airport_id BIGINT NOT NULL,
    destination_airport_id BIGINT NOT NULL,
    CONSTRAINT fk_source FOREIGN KEY (source_airport_id) REFERENCES airports(id),
    CONSTRAINT fk_destination FOREIGN KEY (destination_airport_id) REFERENCES airports(id),
    CONSTRAINT unique_route UNIQUE (source_airport_id, destination_airport_id)
);

CREATE TABLE flights (
    id BIGSERIAL PRIMARY KEY,
    airline_id BIGINT NOT NULL,
    route_id BIGINT NOT NULL,
    flight_number VARCHAR(20) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    base_price NUMERIC(10,2),
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_airline FOREIGN KEY (airline_id) REFERENCES airlines(id),
    CONSTRAINT fk_route FOREIGN KEY (route_id) REFERENCES routes(id)
);