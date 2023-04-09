INSERT INTO `unit_of_measure`(`description`) VALUES ('Grama (g)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Quilograma (kg)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Miligrama (mg)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Litro (L)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Mililitro (mL)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Unidade (un)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Pacote (pac)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Caixa (cx)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Cartela (ct)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Kit (kit)');
INSERT INTO `unit_of_measure`(`description`) VALUES ('Conjunto (conj)');

INSERT INTO `roles` (`id`, `name`) VALUES (NULL, 'ROLE_ADMIN'), (NULL, 'ROLE_USER');

INSERT INTO `users` (`id`, `email`, `name`, `password`, `password_reset_token`, `username`, `address_id`) VALUES (NULL, 'silvio.dionizio23@gmail.com', 'SILVIO FILIPE DIONIZIO JUNIOR', '$2a$10$o5lKkrbmtrb237UbJ573fOLZ2414FK1kb0xTci96BabFYH20B.qwa', NULL, 'silvio', NULL);
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES ('1', '1');