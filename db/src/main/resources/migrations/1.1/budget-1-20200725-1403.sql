INSERT INTO global_roles
VALUES (1,'admin'),
       (2,'user');

INSERT INTO roles
VALUES (1,'groupAdmin'),
       (2,'groupMember');

ALTER TABLE category
ADD is_income boolean;

ALTER TABLE money_transactions
DROP is_income;

INSERT INTO category
VALUES
    (1,'Еда и напитки',false),(2,'Покупки',false),
    (3,'Жилье',false),(4,'Общ. транспорт',false),
    (5,'Личный траспорт',false),(6,'Развлечения',false),
    (7,'Связь и интернет',false),(8,'Инвестиции',false),
    (9,'Налоги',false),(10,'Штрафы',false),
    (11,'Перевод',false),(12,'Приход',true)
