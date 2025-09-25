-- V2__Insert_initial_data.sql

-- Insert default roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('GUEST');

-- Insert admin user
INSERT INTO users (email, password, created_at, updated_at)
VALUES ('admin', 'admin', NOW(), NOW());

-- Assign role ADMIN to admin user
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE email = 'admin'),
    (SELECT id FROM roles WHERE name = 'ADMIN')
);

-- Insert loan types
INSERT INTO loan_types (name) VALUES ('Car');
INSERT INTO loan_types (name) VALUES ('Motorcycle');
INSERT INTO loan_types (name) VALUES ('Electronic');

-- =========================
-- Custom sample data
-- =========================

-- Insert creditor: Yehez, 21 years old, Jakarta, income 20jt
INSERT INTO creditors (name, age, address, income, created_at, updated_at)
VALUES ('Yehez', 21, 'Jakarta', 20000000, NOW(), NOW());

-- Insert loan: Xpander, Car type, loan 250jt, down payment 30%
INSERT INTO loans
(creditor_id, loan_name, loan_type_id, loan_amount, down_payment, status, created_at, updated_at)
VALUES (
    (SELECT id FROM creditors WHERE name = 'Yehez'),
    'Xpander',
    (SELECT id FROM loan_types WHERE name = 'Car'),
    250000000,
    ROUND(250000000 * 0.3),
    'PENDING',
    NOW(),
    NOW()
);

-- Insert loan: RTX 4060 Ti 16GB GDDR6, Electronic type, loan 10jt, down payment 30%
INSERT INTO loans
(creditor_id, loan_name, loan_type_id, loan_amount, down_payment, status, created_at, updated_at)
VALUES (
    (SELECT id FROM creditors WHERE name = 'Yehez'),
    'VGA RTX 4060 Ti 16GB GDDR6',
    (SELECT id FROM loan_types WHERE name = 'Electronic'),
    10000000,
    ROUND(10000000 * 0.3),
    'PENDING',
    NOW(),
    NOW()
);