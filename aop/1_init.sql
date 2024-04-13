CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;

CREATE TABLE methods_metric
(
    time         TIMESTAMPTZ           NOT NULL,
    method_name  TEXT               NOT NULL,
    speed_execution DOUBLE PRECISION NOT NULL
);

SELECT create_hypertable('methods_metric', by_range('time'));

create
    index if not exists methods_metric_method_name_index
    on methods_metric (method_name);

INSERT INTO methods_metric(time, method_name, speed_execution) VALUES(NOW(), 'test_method', 13.0)