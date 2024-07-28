CREATE TABLE my_users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(64) NOT NULL,
    country VARCHAR(60) NOT NULL,
    password VARCHAR(200) NOT NULL,
    role VARCHAR(10)
);
CREATE TABLE project (
    id SERIAL PRIMARY KEY,
    project_name VARCHAR(150) NOT NULL
);
CREATE TABLE task (
   id SERIAL PRIMARY KEY,
   priority INT CHECK (priority >= 1 AND priority <= 10),
   task_name VARCHAR(255) NOT NULL,
   project_id BIGINT REFERENCES project(id) ON DELETE CASCADE
);
CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    comment_text VARCHAR(255),
    task_id BIGINT NOT NULL,
    CONSTRAINT fk_task_id FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE
);
CREATE TABLE user_tasks (
    user_id UUID,
    task_id BIGINT,
    PRIMARY KEY (user_id, task_id),
    FOREIGN KEY (user_id) REFERENCES my_users(id),
    FOREIGN KEY (task_id) REFERENCES task(id),
    status VARCHAR(255),
    comment VARCHAR(255),
    finished_at TIMESTAMP
);