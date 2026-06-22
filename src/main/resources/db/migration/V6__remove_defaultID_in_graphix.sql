BEGIN;
    ALTER TABLE element_graphix 
         ALTER COLUMN graphix_id DROP DEFAULT;
COMMIT;
