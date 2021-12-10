CREATE TABLE tag (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE gift_certificate (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  price NUMERIC(10, 2),
  duration INTERVAL DAY,
  create_date TIMESTAMP,
  last_update_date TIMESTAMP
);

CREATE TABLE gift_certificate_tag (
  gift_certificate_id INTEGER REFERENCES gift_certificate (id) ON UPDATE CASCADE ON DELETE CASCADE,
  tag_id INTEGER REFERENCES tag (id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT gift_certificate_tag_pkey PRIMARY KEY (gift_certificate_id, tag_id)
);
