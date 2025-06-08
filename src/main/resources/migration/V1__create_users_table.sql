CREATE TABLE users (
      id UUID PRIMARY KEY,
      nick_name VARCHAR(100) NOT NULL,
      email VARCHAR(30) UNIQUE NOT NULL,
      role VARCHAR(30) NOT NULL,
      registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      phone VARCHAR(100) UNIQUE,
      password VARCHAR NOT NULL
);

INSERT INTO users (id, nick_name, email, role, phone, password)
VALUES ('cca0c39a-a22c-4c1b-bb00-de771a9cbb49', 'BatyaServera', 'admin@example.com', 'ADMIN', '123456789', 'admin_ochen_krut1');

INSERT INTO users (id, nick_name, email, role, phone, password)
VALUES ('8123a73e-96cb-448f-9e20-cd0edd912c3d', 'I_Love_CTF', 'love_ctf@bababe.com', 'USER', '88005553535', 'qwerty123');

INSERT INTO users (id, nick_name, email, role, phone, password)
VALUES ('ed786965-a40c-4b1f-ad8f-903e68629d7a', 'Troll228', 'i_mister_liid@kek.com', 'USER', 'HITS{Рsdffsdff}', 'Zazha1A1');