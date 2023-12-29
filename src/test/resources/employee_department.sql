CREATE TABLE departments (
  department_id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE employees (
  employee_id SERIAL PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  department_id INTEGER NOT NULL REFERENCES departments(department_id)
);

INSERT INTO departments (name) VALUES
  ('Sales'),
  ('Marketing'),
  ('Engineering'),
  ('Product'),
  ('Customer Support');

INSERT INTO employees (first_name, last_name, email, department_id) VALUES
  ('John', 'Doe', 'john.doe@example.com', 1),
  ('Jane', 'Doe', 'jane.doe@example.com', 2),
  ('Peter', 'Parker', 'peter.parker@example.com', 3),
  ('Mary', 'Jane', 'mary.jane@example.com', 4),
  ('Bruce', 'Wayne', 'bruce.wayne@example.com', 5);

INSERT INTO employees (first_name, last_name, email, department_id) VALUES
  ('Ram', 'Verma', 'ram.verma@example.com', 1),
  ('Anurag', 'Basu', 'anurag.basu@example.com', 2),
  ('Raj', 'Seth', 'raj.seth@example.com', 3);
