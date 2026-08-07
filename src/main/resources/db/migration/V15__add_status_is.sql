BEGIN;
    ALTER TABLE pages
    ADD COLUMN status_is page_status not null;
COMMIT;
