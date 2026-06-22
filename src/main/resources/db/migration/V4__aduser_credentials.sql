BEGIN;
    ALTER TABLE app_users 
        ADD COLUMN username varchar(15) not null UNIQUE,
        ADD COLUMN password text not null;
COMMIT;

