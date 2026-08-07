BEGIN;
    ALTER TABLE handling 
        ADD COLUMN of_page UUID REFERENCES pages(page_id);
COMMIT;

BEGIN;
    ALTER TABLE handling
        DROP COLUMN stable_id,
        ADD COLUMN handling_id UUID not null DEFAULT gen_random_uuid();
COMMIT;
