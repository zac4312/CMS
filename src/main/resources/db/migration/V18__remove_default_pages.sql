BEGIN;
    ALTER TABLE pages
         ALTER COLUMN page_id drop DEFAULT;
COMMIT;
