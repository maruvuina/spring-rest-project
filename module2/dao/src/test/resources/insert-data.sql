INSERT INTO tag (name) VALUES ('новыйгод');
INSERT INTO tag (name) VALUES ('рождество');
INSERT INTO tag (name) VALUES ('подаркинановыйгод');

INSERT INTO gift_certificate (name, description, price, duration,
                              create_date, last_update_date)
VALUES ('Новогодний обед', '10 разнообразных блюд', 100.00, 10,
        {ts '2021-12-10 14:47:52.69'},
        {ts '2021-12-10 14:47:55.45'});

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 1);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 2);
