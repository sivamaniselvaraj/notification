
CREATE TABLE IF NOT EXISTS Notifications (
       notification_id     BIGINT PRIMARY KEY,
       user_id             BIGINT NOT NULL,
       order_id             BIGINT NOT NULL,
       notification_type   VARCHAR(50),
       title               VARCHAR(255),
       message             TEXT,
       channel             VARCHAR(50),
       created_at          TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS DeliveryStatus (
        delivery_id     BIGINT PRIMARY KEY,
        notification_id BIGINT NOT NULL,
        delivery_status VARCHAR(50),
        retry_count     INT DEFAULT 0,
        delivered_at    TIMESTAMP,
        error_message   TEXT,
        FOREIGN KEY (notification_id) REFERENCES Notifications(notification_id)
);

CREATE INDEX IF NOT EXISTS idx_notification_id ON DeliveryStatus(idx_notification_id);