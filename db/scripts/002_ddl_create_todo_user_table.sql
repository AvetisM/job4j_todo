CREATE TABLE IF NOT EXISTS todo_user (
   id SERIAL PRIMARY KEY,
   name TEXT,
   login TEXT,
   password TEXT
);