CREATE TABLE shops (
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE designers (
    id                        BIGINT        NOT NULL AUTO_INCREMENT,
    name                      VARCHAR(100)  NOT NULL,
    shop_id                   BIGINT        NOT NULL,
    profile_image_url         VARCHAR(2048) NOT NULL,
    region                    VARCHAR(100)  NOT NULL,
    completed_treatment_count INT           NOT NULL DEFAULT 0,

    PRIMARY KEY (id),

    CONSTRAINT fk_designers_shop
        FOREIGN KEY (shop_id) REFERENCES shops (id)
);

CREATE TABLE shop_images (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    shop_id       BIGINT        NOT NULL,
    image_url     VARCHAR(2048) NOT NULL,
    display_order INT           NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_shop_images_shop_id_display_order (shop_id, display_order),

    CONSTRAINT fk_shop_images_shop
        FOREIGN KEY (shop_id) REFERENCES shops (id)
);

CREATE TABLE posts (
    id             BIGINT                                   NOT NULL AUTO_INCREMENT,
    designer_id    BIGINT                                   NOT NULL,
    title          VARCHAR(255)                             NOT NULL,
    content        TEXT                                     NOT NULL,
    post_type      ENUM ('RECOMMENDED', 'GENERAL')          NOT NULL,
    treatment_types JSON                                    NOT NULL,
    price_type     ENUM ('REVIEW', 'FREE', 'NEGOTIABLE')    NOT NULL,
    original_price INT,
    display_price  INT,
    discount_rate  DECIMAL(5, 2),
    available_times JSON                                    NOT NULL,
    signatures     JSON                                     NOT NULL,
    shooting_ranges JSON,
    is_liked       BOOLEAN                                  NOT NULL DEFAULT FALSE,
    created_at     DATETIME                                 NOT NULL,

    PRIMARY KEY (id),
    KEY idx_posts_post_type_created_at (post_type, created_at),

    CONSTRAINT chk_posts_treatment_types_array
        CHECK (JSON_TYPE(treatment_types) = 'ARRAY'),
    CONSTRAINT chk_posts_available_times_array
        CHECK (JSON_TYPE(available_times) = 'ARRAY'),
    CONSTRAINT chk_posts_signatures_array
        CHECK (JSON_TYPE(signatures) = 'ARRAY'),
    CONSTRAINT chk_posts_shooting_ranges_array
        CHECK (shooting_ranges IS NULL OR JSON_TYPE(shooting_ranges) = 'ARRAY'),
    CONSTRAINT chk_posts_original_price_non_negative
        CHECK (original_price IS NULL OR original_price >= 0),
    CONSTRAINT chk_posts_display_price_non_negative
        CHECK (display_price IS NULL OR display_price >= 0),
    CONSTRAINT chk_posts_discount_rate_range
        CHECK (discount_rate IS NULL OR discount_rate BETWEEN 0 AND 100),
    CONSTRAINT fk_posts_designer
        FOREIGN KEY (designer_id) REFERENCES designers (id)
);

CREATE TABLE post_images (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    post_id       BIGINT        NOT NULL,
    image_url     VARCHAR(2048) NOT NULL,
    display_order INT           NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_post_images_post_id_display_order (post_id, display_order),

    CONSTRAINT fk_post_images_post
        FOREIGN KEY (post_id) REFERENCES posts (id)
);

CREATE TABLE portfolio_images (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    designer_id   BIGINT        NOT NULL,
    image_url     VARCHAR(2048) NOT NULL,
    display_order INT           NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_portfolio_images_designer_id_display_order (designer_id, display_order),

    CONSTRAINT fk_portfolio_images_designer
        FOREIGN KEY (designer_id) REFERENCES designers (id)
);
