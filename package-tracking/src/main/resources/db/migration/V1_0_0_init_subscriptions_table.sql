CREATE TABLE IF NOT EXISTS delivery_package_subscriptions (
    subscription_id SERIAL PRIMARY KEY,

    package_id VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255)
);