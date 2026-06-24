BEGIN;
    ALTER TABLE element_graphix 
         ADD COLUMN original_file text not null;
COMMIT;
