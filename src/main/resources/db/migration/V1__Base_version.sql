DROP TABLE IF EXISTS data_set;

CREATE TABLE data_set
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    name       VARCHAR(255) UNIQUE,
    path       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_data_set PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    tag         VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id)
);