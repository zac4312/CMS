BEGIN;
    ALTER TABLE element 
        DROP COLUMN by_user CASCADE;
COMMIT;
