CREATE TABLE certificate (
    id BIGSERIAL PRIMARY KEY,
    gift_name CHARACTER VARYING(30),
    description CHARACTER VARYING(500),
    price DOUBLE PRECISION,
    date_of_creation DATE,
    date_of_modification DATE,
    duration SMALLINT
);
CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name_tag CHARACTER VARYING(30)
);
CREATE TABLE tag_certificate (
	tag_id BIGINT REFERENCES tag (id) ON DELETE CASCADE,
    certificate_id BIGINT REFERENCES certificate (id) ON DELETE CASCADE
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    user_login CHARACTER VARYING(30),
	user_password CHARACTER VARYING(100),
	user_role CHARACTER VARYING(10)
);


CREATE TABLE purchase (
    id BIGSERIAL PRIMARY KEY,
    price DOUBLE PRECISION,
	timestamp DATE,
    user_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    certificate_id BIGINT REFERENCES certificate (id) ON DELETE CASCADE
)
