BEGIN;
    ALTER TABLE element 
         DROP COLUMN img_path;
COMMIT;
