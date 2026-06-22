BEGIN;

    CREATE TABLE app_users (
        user_id UUID default gen_random_uuid() PRIMARY KEY
    );

    CREATE TABLE element (
        element_id UUID PRIMARY KEY default gen_random_uuid(),
        title text,
        description text,
        img_path text,
        by_user UUID references app_users(user_id) not null
    );

COMMIT;
