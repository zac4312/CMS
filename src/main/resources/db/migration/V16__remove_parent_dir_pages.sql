BEGIN;
    ALTER TABLE pages 
         drop column parent_dir;
COMMIT;
