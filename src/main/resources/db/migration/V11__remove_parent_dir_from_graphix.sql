BEGIN;
    ALTER TABLE element_graphix 
        DROP COLUMN parent_dir;
COMMIT;
