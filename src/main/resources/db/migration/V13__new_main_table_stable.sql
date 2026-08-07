BEGIN;

    CREATE type page_status AS ENUM ('deployed', 'editing', 'new');

    CREATE TABLE handling (
        stable_id UUID  not null PRIMARY KEY default gen_random_uuid(),
        deployment_dir text not null,
        editing_dir text not null,
        storage_dir text not null,
        dump_dir text not null,

        owned_by UUID not null references app_users(user_id)
    );

    ALTER TABLE pages
        ADD COLUMN status_is page_status not null;

COMMIT;
