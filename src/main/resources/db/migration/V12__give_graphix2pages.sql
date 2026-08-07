BEGIN;
    ALTER TABLE element 
        DROP Column graphix CASCADE;
COMMIT;

BEGIN;
    ALTER TABLE pages
        add column graphix UUID REFERENCES element_graphix(graphix_id);
COMMIT;
