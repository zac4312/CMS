BEGIN;
    ALTER TABLE element 
        ALTER COLUMN element_id drop DEFAULT;
COMMIT;

BEGIN;
    ALTER TABLE element_graphix
        ALTER COLUMN graphix_id drop DEFAULT;
COMMIT;
