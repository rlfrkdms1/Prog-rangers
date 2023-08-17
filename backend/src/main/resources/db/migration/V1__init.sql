CREATE TABLE IF NOT EXISTS `comment` (
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `create_date`  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    `is_fixed`     TINYINT(1)            DEFAULT '0',
    `content`      varchar(255) NOT NULL,
    `mention`      varchar(255),
    `parent_id`    bigint,
    `member_id`    bigint       NOT NULL,
    `solution_id`  bigint       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `likes` (
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `member_id`   bigint,
    `solution_id` bigint,
     PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `member` (
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `email`        varchar(255),
    `github`       varchar(255),
    `introduction` varchar(255),
    `name`         varchar(255),
    `nickname`     varchar(255),
    `password`     varchar(255),
    `phone_number` varchar(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `problem` (
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `link`    varchar(255),
    `oj_name` varchar(255),
    `title`   varchar(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `review` (
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `code_line_number` int,
    `date`             date,
    `order_child`      int,
    `order_parent`     int,
    `content`          varchar(255),
    `mention`          varchar(255),
    `member_id`        bigint,
    `parent_id`        bigint,
    `solution_id`      bigint,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `solution` (
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `date`              date,
    `is_public`         TINYINT(1)   NOT NULL,
    `scraps`            int,
    `algorithm_id`      bigint,
    `data_structure_id` bigint,
    `member_id`         bigint,
    `problem_id`        bigint,
    `scrap_id`          bigint,
    `code`              varchar(255),
    `description`       varchar(255),
    `level`             varchar(255),
    `title`             varchar(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



