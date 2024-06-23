INSERT INTO USERS(username, password)
VALUES ('user', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'),--password
       ('admin', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'),--password
       ('read_only', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'); --password

INSERT INTO ROLES(name)
VALUES ('USER'),
       ('ADMIN'),
       ('READ_ONLY');

INSERT INTO ROLE_USER(user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 3);

INSERT INTO CATEGORIES(category)
VALUES ('Hudica'),
       ('Kratka majica'),
       ('Torba'),
       ('Boca'),
       ('Kišobran'),
       ('Kapa');

INSERT INTO COLORS(color)
VALUES ('Crvena'),
       ('Crna'),
       ('Bijela'),
       ('Žuta'),
       ('Narandžasta'),
       ('Roza'),
       ('Ljubičasta'),
       ('Smeđa'),
       ('Siva');


INSERT INTO ARTICLE(name, category_id, price, color_id, available, description, image_link)
VALUES
    ('eSTUDENT Hudica', 1, 20.00, 1, 10, 'Majica sa kapuljačom', 'https://i.ibb.co/jfN7MzV/Crvena-hudica.png'),
    ('eSTUDENT Kratka majica', 2, 15.00, 2, 10, 'Kratka majica', 'https://i.ibb.co/4s1ySXY/crvena-majica.png'),
    ('eSTUDENT Torba', 3, 25.00, 2, 10, 'Torba', 'https://i.ibb.co/QMqWJ2D/bijela-majica-bijela-torba.png'),
    ('eSTUDENT Boca', 4, 10.00, 3, 10, 'Boca', 'https://i.ibb.co/5BqL0j4/Crna-boca.png'),
    ('eSTUDENT Kišobran', 5, 15.00, 1, 10, 'Kišobran', 'https://i.ibb.co/hXHcvH3/ki-obran.jpg'),
    ('eSTUDENT Kapa', 6, 10.00, 2, 10, 'Kapa', 'https://i.ibb.co/jrDrVg7/bijeli-bucket-hat.png');


