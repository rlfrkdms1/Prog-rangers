CREATE TABLE IF NOT EXISTS `comment`
(
    `id`          BIGINT NOT NULL auto_increment,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `status`      VARCHAR(255),
    `content`     VARCHAR(255) NOT NULL,
    `parent_id`   BIGINT,
    `member_id`   BIGINT NOT NULL,
    `solution_id` BIGINT NOT NULL,
    'usable'      TINYINT(1) NOT NULL,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `likes`
(
    `id`          BIGINT NOT NULL auto_increment,
    `member_id`   BIGINT NOT NULL,
    `solution_id` BIGINT NOT NULL,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `member`
(
    `id`                    BIGINT NOT NULL auto_increment,
    `social_id`             BIGINT,
    `email`                 VARCHAR(255),
    `github`                VARCHAR(255),
    `introduction`          VARCHAR(255),
    `nickname`              VARCHAR(255) NOT NULL,-- unique
    `password`              VARCHAR(255),
    `photo`                 VARCHAR(255),
    `type`                  VARCHAR(255) NOT NULL,
    `currently_modified_at` DATE,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `problem`
(
    `id`      BIGINT NOT NULL auto_increment,
    `link`    VARCHAR(255) NOT NULL,-- unique
    `oj_name` VARCHAR(255) NOT NULL,
    `title`   VARCHAR(255) NOT NULL,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `review`
(
    `id`               BIGINT NOT NULL auto_increment,
    `code_line_number` INT NOT NULL,
    `created_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `content`          VARCHAR(255) NOT NULL,
    `parent_id`        BIGINT,
    `member_id`        BIGINT NOT NULL,
    `solution_id`      BIGINT NOT NULL,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `solution`
(
    `id`             BIGINT NOT NULL auto_increment,
    `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `is_public`      TINYINT(1) NOT NULL,
    `code`           VARCHAR(255) NOT NULL,
    `description`    VARCHAR(255) NOT NULL,
    `level`          INT NOT NULL,
    `title`          VARCHAR(255) NOT NULL,
    `algorithm`      VARCHAR(255),
    `data_structure` VARCHAR(255),
    `language`       VARCHAR(255),
    `member_id`      BIGINT NOT NULL,
    `problem_id`     BIGINT NOT NULL,
    `scrap_id`       BIGINT,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `notification`
(
    `id`          BIGINT NOT NULL auto_increment,
    `title`       VARCHAR(255) NOT NULL,
    `type`        VARCHAR(255) NOT NULL,
    `created_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `id_read`     TINYINT(1) NOT NULL,
    `member_id`   BIGINT NOT NULL,
    `solution_id` BIGINT NOT NULL,
    `comment_id`  BIGINT,
    `review_id`   BIGINT,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `follow`
(
    `id`           BIGINT NOT NULL auto_increment,
    `following_id` BIGINT NOT NULL,
    `follower_id`  BIGINT NOT NULL,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `badge`
(
    `id`         BIGINT NOT NULL auto_increment,
    `member_id`  BIGINT NOT NULL,
    `badge_type` VARCHAR(255) NOT NULL,
    PRIMARY KEY ( `id` )
)
    engine = innodb
    auto_increment = 1
    DEFAULT charset = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;