BEGIN;
    ALTER TABLE pages
    ADD COLUMN parent_dir text not null;
COMMIT;
