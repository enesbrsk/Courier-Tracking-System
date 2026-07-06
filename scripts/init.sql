CREATE TABLE couriers (
    id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE courier_distances (
    id               BIGSERIAL PRIMARY KEY,
    courier_id       VARCHAR(64) NOT NULL UNIQUE,
    total_distance   DOUBLE PRECISION NOT NULL DEFAULT 0,
    last_latitude    DOUBLE PRECISION,
    last_longitude   DOUBLE PRECISION,
    last_time        TIMESTAMPTZ,
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_courier_distances_courier FOREIGN KEY (courier_id) REFERENCES couriers(id) ON DELETE CASCADE
);

INSERT INTO couriers (id, name) VALUES ('courier-1', 'Test Courier 1') ON CONFLICT (id) DO NOTHING;
INSERT INTO couriers (id, name) VALUES ('courier-2', 'Test Courier 2') ON CONFLICT (id) DO NOTHING;

CREATE TABLE courier_store_entries (
    id                 BIGSERIAL PRIMARY KEY,
    courier_id         VARCHAR(64) NOT NULL,
    store_name         VARCHAR(100) NOT NULL,
    time               TIMESTAMPTZ NOT NULL,
    entered_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_store_entries_courier FOREIGN KEY (courier_id) REFERENCES couriers(id) ON DELETE CASCADE
);
