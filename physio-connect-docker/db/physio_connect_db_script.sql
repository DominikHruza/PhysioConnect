
--DROP DATABASE IF EXISTS physio_connect;

-- CREATE DATABASE physio_connect;

CREATE TABLE app_users (
	id serial PRIMARY KEY,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	password TEXT NOT NULL,
	UNIQUE(email)
);

CREATE TABLE roles (
	id serial PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

CREATE TABLE users_roles (
	app_user_id integer not null,
	role_id integer not null,
	FOREIGN KEY (app_user_id) REFERENCES app_users (id),
	FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE practitioners (
	id integer NOT NULL UNIQUE,
	uid varchar(255),
	FOREIGN KEY (id) REFERENCES app_users (id)
);

CREATE TABLE patients (
	id integer NOT NULL UNIQUE,
	practitioner_id integer NOT NULL,
	diagnosis VARCHAR(255),
	reg_code VARCHAR(255),
	reg_complete boolean,
	FOREIGN KEY (practitioner_id) REFERENCES practitioners (id),
	FOREIGN KEY (id) REFERENCES app_users (id)
);

CREATE TABLE visits (
	id serial PRIMARY KEY,
	practitioner_id integer NOT NULL,
	patient_id integer NOT NULL,
	scheduled_for timestamp NULL,
	
	FOREIGN KEY (practitioner_id) REFERENCES practitioners (id),
	FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE documents (
	id serial PRIMARY KEY,
	patient_id integer NOT NULL,
	url_location text NOT NULL,
	file_name varchar(255),
	added_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	
	FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE notes (
	id serial PRIMARY KEY,
	practitioner_id integer NOT NULL,
	patient_id integer NOT NULL,
	added_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	note_content text NOT NULL,
	
	FOREIGN KEY (practitioner_id) REFERENCES practitioners (id),
	FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE workout_plans (
	id serial PRIMARY KEY,
	practitioner_id integer NOT NULL,
	patient_id integer NOT NULL,
	start_at date,
	end_at date,
	description text,
	effort_level integer,
	pain_level integer,
	is_active boolean,
	
	FOREIGN KEY (practitioner_id) REFERENCES practitioners (id),
	FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE exercises (
	id serial PRIMARY KEY,
	plan_id integer NOT NULL,
	exrx_name varchar(255) NOT NULL,
	exrx_sets integer NOT NULL,
	exrx_repetition integer NOT NULL,
	video_instr_url text,
	description text,
	effort_level integer,
	pain_level integer,
	patient_comment text,
	is_completed boolean,
	
	FOREIGN KEY (plan_id) REFERENCES workout_plans (id)
);

CREATE TABLE IF NOT EXISTS exercise_sessions
(
    id serial PRIMARY KEY,
	plan_id integer NOT NULL,
	started_at timestamp NOT NULL,
	completed_at timestamp NOT NULL,
    patient_comment text,
    CONSTRAINT sessions_plan_id_fkey FOREIGN KEY (plan_id)
        REFERENCES public.workout_plans (id)
);

CREATE TABLE IF NOT EXISTS completed_exercises
(
    id serial PRIMARY KEY,
    plan_id integer NOT NULL,
	exercise_id integer NOT NULL,
	session_id integer NOT NULL,
    completed_sets integer NOT NULL,
    completed_repetition integer NOT NULL,
    effort_level integer,
    pain_level integer,
    patient_comment text,
	CONSTRAINT completed_exercises_plan_id_fkey FOREIGN KEY (plan_id)
        REFERENCES public.workout_plans (id),
	CONSTRAINT completed_exercises_exercise_id_fkey FOREIGN KEY (exercise_id)
        REFERENCES public.exercises (id),
	CONSTRAINT completed_exercises_session_id_fkey FOREIGN KEY (session_id)
        REFERENCES public.exercise_sessions (id)
);

insert into roles (name) values ('PRACTITIONER');
insert into roles (name) values ('PATIENT');

INSERT INTO app_users(firstname, lastname, email, password)
	VALUES ('John', 'Doe', 'admin@email.com', '$2a$12$JJSUddkNKlCOHRA.KJFLXuVbMT5VIUUjGJsk5PVOFQ.nx0SzaVvCy');

INSERT INTO practitioners(id, uid)
SELECT id, 12345678
FROM app_users
WHERE email = 'admin@email.com';


INSERT INTO users_roles(app_user_id, role_id)
SELECT id, (SELECT id from roles where name = 'PRACTITIONER')
FROM app_users
WHERE email = 'admin@email.com';
