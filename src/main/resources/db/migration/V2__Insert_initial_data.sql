-- V2__Insert_initial_data.sql

-- Insert admin user
INSERT INTO users (email, password, role)
VALUES ('admin', 'admin', 'ADMIN');

-- Insert loan types
INSERT INTO loan_types (name) VALUES ('Car');
INSERT INTO loan_types (name) VALUES ('Motorcycle');
INSERT INTO loan_types (name) VALUES ('Electronic');
