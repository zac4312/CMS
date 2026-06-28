BEGIN;
    CREATE TABLE pages (
        page_id UUID default gen_random_uuid() not null PRIMARY KEY,
        parent_dir text not null,
        path2stable text not null,
        elements UUID references element(element_id),
        owned_by UUID references app_users(user_id)
    );
COMMIT;
