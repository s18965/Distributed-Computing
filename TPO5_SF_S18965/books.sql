CREATE TABLE `book` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `releaseDate` date NOT NULL,
  `price` int(11) NOT NULL,
  `pubId` int(11) NOT NULL,
  `authId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `author` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
  );


CREATE TABLE `publishing_house` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
);

ALTER TABLE `author`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `publishing_house`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD FOREIGN KEY(authId) REFERENCES author(id),
  ADD FOREIGN KEY(pubId) REFERENCES publishing_house(id);

INSERT INTO `PUBLISHING_HOUSE` (`id`, `NAME`) VALUES ('1', 'Egmont');
INSERT INTO `PUBLISHING_HOUSE` (`id`, `NAME`) VALUES ('2', 'PWN');
INSERT INTO `PUBLISHING_HOUSE` (`id`, `NAME`) VALUES ('3', 'Zysk');
INSERT INTO `AUTHOR` (`id`, `NAME`) VALUES ('1', 'Jerzy Bralczyk');
INSERT INTO `AUTHOR` (`id`, `NAME`) VALUES ('2', 'Miller P.');

INSERT INTO `book` (`id`, `title`,`releaseDate`,`price`, `pubId`,`authId`) VALUES
(1, 'O obrotach cial niebieskich', '2020-05-17',20,1,1),
(2, 'Bazy danych. Tworzenie aplikacji','2020-05-17',30,2,2),
(3, 'Java', '2020-05-17',40,3,2),
(4, 'Programowanie sieciowe', '2020-02-13',100,1,1),
(5, 'Rok 1984', '1984-05-21',39,1,1);
(6, 'Apokalipsa', '2004-05-05',20,2,2);

ALTER TABLE `book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE `author`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

ALTER TABLE `publishing_house`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

