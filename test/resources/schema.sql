CREATE TABLE airport
(
  id                BIGINT       NOT NULL
    CONSTRAINT pk_airport
    PRIMARY KEY,
  ident             VARCHAR(255) NOT NULL,
  type              VARCHAR(255) NOT NULL,
  name              VARCHAR(255) NOT NULL,
  latitude_deg      REAL         NOT NULL,
  longitude_deg     REAL         NOT NULL,
  elevation_ft      BIGINT,
  continent         VARCHAR(255) NOT NULL,
  iso_country       VARCHAR(255) NOT NULL,
  iso_region        VARCHAR(255) NOT NULL,
  municipality      VARCHAR(255),
  scheduled_service VARCHAR(255),
  gps_code          VARCHAR(255),
  iata_code         VARCHAR(255),
  local_code        VARCHAR(255),
  home_link         VARCHAR(255),
  wikipedia_link    VARCHAR(255),
  keywords          VARCHAR(255)
);

CREATE UNIQUE INDEX airport_ident_uindex
  ON airport (ident);

CREATE TABLE country
(
  id             BIGINT       NOT NULL
    CONSTRAINT pk_country
    PRIMARY KEY,
  code           VARCHAR(255) NOT NULL,
  name           VARCHAR(255) NOT NULL,
  continent      VARCHAR(255) NOT NULL,
  wikipedia_link VARCHAR(255) NOT NULL,
  keywords       VARCHAR(255)
);

CREATE UNIQUE INDEX country_code_uindex
  ON country (code);

ALTER TABLE airport
  ADD CONSTRAINT airport_country_code_fk
FOREIGN KEY (iso_country) REFERENCES country (code);

CREATE TABLE runway
(
  id                        BIGINT       NOT NULL
    CONSTRAINT pk_runway
    PRIMARY KEY,
  airport_ref               BIGINT       NOT NULL
    CONSTRAINT runway_airport_id_fk
    REFERENCES airport,
  airport_ident             VARCHAR(255) NOT NULL,
  length_ft                 BIGINT,
  width_ft                  BIGINT,
  surface                   VARCHAR(255),
  lighted                   SMALLINT,
  closed                    SMALLINT,
  le_ident                  VARCHAR(255),
  le_latitude_deg           REAL,
  le_longitude_deg          REAL,
  le_elevation_ft           BIGINT,
  le_heading_degt           REAL,
  le_displaced_threshold_ft BIGINT,
  he_ident                  VARCHAR(255),
  he_latitude_deg           REAL,
  he_longitude_deg          REAL,
  he_elevation_ft           BIGINT,
  he_heading_degt           REAL,
  he_displaced_threshold_ft BIGINT
);