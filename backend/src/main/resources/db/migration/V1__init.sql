CREATE TABLE IF NOT EXISTS `comment` (
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `created_at`   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    `status`       varchar(255),
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
    `member_id`   bigint NOT NULL,
    `solution_id` bigint NOT NULL,
     PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `member` (
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `social_id`    bigint,
    `email`        varchar(255),
    `github`       varchar(255),
    `introduction` varchar(255),
    `nickname`     varchar(255) NOT NULL, -- unique
    `password`     varchar(255),
    `photo`        varchar(255),
    `type`         varchar(255),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `problem` (
    `id`      bigint       NOT NULL AUTO_INCREMENT,
    `link`    varchar(255) NOT NULL, -- unique
    `oj_name` varchar(255) NOT NULL,
    `title`   varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `review` (
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `code_line_number` int          NOT NULL,
    `created_at`       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    `content`          varchar(255) NOT NULL,
    `mention`          varchar(255),
    `parent_id`        bigint,
    `member_id`        bigint       NOT NULL,
    `solution_id`      bigint       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `solution` (
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `created_at`        TIMESTAMP    DEFAULT  CURRENT_TIMESTAMP,
    `is_public`         TINYINT(1)   NOT NULL,
    `code`              varchar(255) NOT NULL,
    `description`       varchar(255) NOT NULL,
    `level`             int          NOT NULL,
    `title`             varchar(255) NOT NULL,
    `algorithm`         varchar(255),
    `data_structure`    varchar(255),
    `language`          varchar(255),
    `member_id`         bigint       NOT NULL,
    `problem_id`        bigint       NOT NULL,
    `scrap_id`          bigint,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `notification` (
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `title`       varchar(255) NOT NULL,
    `type`        varchar(255) NOT NULL,
    `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `id_read`     TINYINT(1)   NOT NULL,
    `member_id`   bigint       NOT NULL,
    `solution_id` bigint       NOT NULL,
    `comment_id`  bigint,
    `review_id`   bigint,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;





