INSERT INTO users(name, last_name)
VALUES ('Maxim', 'Yarosh'),
       ('Kate', 'Yarmoshyk'),
       ('Vladik', 'Gorodcev');

INSERT INTO banks(title)
VALUES ('Bank of America'),
       ('Belarus Bank'),
       ('France Bank'),
       ('Russia Bank');


INSERT INTO accounts(number, cash, user_id, bank_id)
VALUES ('12376589567', '70.77',  1, 1),
       ('7658904325', '2000.15', 2,1),
       ('89654365840', '100.00', 3, 1),
       ('AA78591102', '50.00', 1, 2),
       ('AK785301U21', '14.23', 2, 2),
       ('ML51891H12', '555.12',3, 2),
       ('dhj2419hjk12', '30000.00',1,3),
       ('dgdj124924kl', '1223.42', 2,3),
       ('qqe1241db2', '22.12', 3,3);