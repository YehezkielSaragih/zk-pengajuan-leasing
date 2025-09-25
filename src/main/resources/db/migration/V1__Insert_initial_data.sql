-- V1__Insert_initial_data.sql

-- Insert admin user
INSERT INTO users (id, email, password, role) 
VALUES (1, 'admin', 'admin', 'ADMIN');

-- Insert loan types
INSERT INTO loan_types (id, name) VALUES (1, 'Car');
INSERT INTO loan_types (id, name) VALUES (2, 'Motorcycle');
INSERT INTO loan_types (id, name) VALUES (3, 'Electronic');