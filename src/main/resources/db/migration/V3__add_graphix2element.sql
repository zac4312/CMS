BEGIN;
    ALTER TABLE element 
         add COLUMN graphix UUID REFERENCES element_graphix(graphix_id);
COMMIT;
