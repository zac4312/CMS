BEGIN;
    ALTER TABLE pages
        alter COLUMN page_id DROP DEFAULT;
COMMIT;
