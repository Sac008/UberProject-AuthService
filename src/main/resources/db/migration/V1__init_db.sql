CREATE TABLE IF NOT EXISTS booking
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     time NOT NULL,
    updated_at     time NOT NULL,
    review_id      BIGINT NULL,
    booking_status ENUM('SCHEDULED' , 'CANCELLED' , 'CAB_ARRIVED' , 'ASSIGNING_DRIVER' , 'IN_RIDE' , 'COMPLETED') NULL,
    start_time     datetime NULL,
    end_time       datetime NULL,
    total_distance BIGINT NULL,
    driver_id      BIGINT NULL,
    passenger_id   BIGINT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bookingreview
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at time         NOT NULL,
    updated_at time         NOT NULL,
    content    VARCHAR(255) NOT NULL,
    rating DOUBLE NULL,
    CONSTRAINT pk_bookingreview PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS driver
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     time         NOT NULL,
    updated_at     time         NOT NULL,
    name           VARCHAR(255) NULL,
    license_number VARCHAR(255) NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS passenger
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at time NOT NULL,
    updated_at time NOT NULL,
    name       VARCHAR(255) NULL,
    CONSTRAINT pk_passenger PRIMARY KEY (id)
);

CREATE TABLE IF not exists passenger_review
(
    passenger_review_id      BIGINT NOT NULL,
    passenger_review_comment VARCHAR(255) NULL,
    passenger_rating         VARCHAR(255) NULL,
    CONSTRAINT pk_passengerreview PRIMARY KEY (passenger_review_id)
);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_licensenumber UNIQUE (license_number);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_PASSENGER FOREIGN KEY (passenger_id) REFERENCES passenger (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_REVIEW FOREIGN KEY (review_id) REFERENCES bookingreview (id);

ALTER TABLE passenger_review
    ADD CONSTRAINT FK_PASSENGERREVIEW_ON_PASSENGER_REVIEW FOREIGN KEY (passenger_review_id) REFERENCES bookingreview (id);