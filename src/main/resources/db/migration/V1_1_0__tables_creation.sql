CREATE TABLE attendees
(
    id  BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)     NULL,
    first_name   varchar(30) NOT NULL,
    last_name    varchar(30) NOT NULL,
    title        varchar(40) NULL,
    company      varchar(50) NULL,
    email        varchar(80) NOT NULL,
    phone_number varchar(20) NULL
);

CREATE TABLE ticket_types
(
    ticket_type_code  varchar(1) PRIMARY KEY,
    ticket_type_name  varchar(30)  NOT NULL,
    description       varchar(100) NOT NULL,
    includes_workshop boolean      NOT NULL
);

CREATE TABLE pricing_categories
(
    pricing_category_code varchar(1) PRIMARY KEY,
    pricing_category_name varchar(20) NOT NULL,
    pricing_start_date    date        NOT NULL,
    pricing_end_date      date        NOT NULL
);

CREATE TABLE ticket_prices
(
    id       BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)        NULL,
    ticket_type_code      varchar(1)    NOT NULL REFERENCES ticket_types (ticket_type_code),
    pricing_category_code varchar(1)    NOT NULL REFERENCES pricing_categories (pricing_category_code),
    base_price            numeric(8, 2) NOT NULL
);

CREATE TABLE discount_codes
(
    id BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)     NULL,
    discount_code    varchar(20)   NOT NULL,
    discount_name    varchar(30)   NOT NULL,
    discount_type    varchar(1)    NOT NULL,
    discount_amount  numeric(8, 2) NOT NULL
);

CREATE TABLE attendee_tickets
(
    id BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)        NULL,
    attendee_id        BIGINT(20)       NOT NULL REFERENCES attendees (attendee_id),
    ticket_price_id    BIGINT(20)       NOT NULL REFERENCES ticket_prices (ticket_price_id),
    discount_code_id   BIGINT(20)       NULL REFERENCES discount_codes (discount_code_id),
    net_price          numeric(8, 2) NOT NULL
);

CREATE TABLE time_slots
(
    id         BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)        NULL,
    time_slot_date       date                   NOT NULL,
    start_time           time  					NOT NULL,
    end_time             time  					NOT NULL,
    is_keynote_time_slot boolean default false  NOT NULL
);

CREATE TABLE sessions
(
    id          BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)         NULL,
    session_name        varchar(80)   NOT NULL,
    session_description varchar(1024) NOT NULL,
    session_length      integer       NOT NULL

);

CREATE TABLE session_schedule
(
    id  BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)        NULL,
    time_slot_id BIGINT(20)     NOT NULL REFERENCES time_slots (time_slot_id),
    session_id   BIGINT(20)     NOT NULL REFERENCES sessions (session_id),
    room         varchar(30) NOT NULL
);

CREATE TABLE tags
(
    id      BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)        NULL,
    description varchar(30) NOT NULL
);

CREATE TABLE session_tags
(
    session_id BIGINT(20) NOT NULL REFERENCES sessions (session_id),
    tag_id     BIGINT(20) NOT NULL REFERENCES tags (tag_id)
);

CREATE TABLE speakers
(
    id    BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)        NULL,
    first_name    varchar(30)   NOT NULL,
    last_name     varchar(30)   NOT NULL,
    title         varchar(40)   NOT NULL,
    company       varchar(50)   NOT NULL,
    speaker_bio   varchar(2000) NOT NULL,
    speaker_photo BLOB   		NULL
);

CREATE TABLE session_speakers
(
    session_id BIGINT(20) NOT NULL REFERENCES sessions (session_id),
    speaker_id BIGINT(20) NOT NULL REFERENCES speakers (speaker_id)
);

CREATE TABLE workshops
(
    id   BIGINT(20) PRIMARY KEY,
    created_at       TIMESTAMP     NULL,
    created_by       VARCHAR(100)   NULL,
    update_at        TIMESTAMP      NULL,
    update_by        VARCHAR(100)   NULL,
    version          INT(11)     NULL,
    workshop_name varchar(60)   NOT NULL,
    description   varchar(1024) NOT NULL,
    requirements  varchar(1024) NOT NULL,
    room          varchar(30)   NOT NULL,
    capacity      integer       NOT NULL
);

CREATE TABLE workshop_speakers
(
    workshop_id BIGINT(20) NOT NULL REFERENCES workshops (workshop_id),
    speaker_id  BIGINT(20) NOT NULL REFERENCES speakers (speaker_id)
);

CREATE TABLE workshop_registrations
(
    workshop_id        BIGINT(20) NOT NULL REFERENCES workshops (workshop_id),
    attendee_ticket_id BIGINT(20) NOT NULL REFERENCES attendee_tickets (attendee_ticket_id)
);