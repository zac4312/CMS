BEGIN;
    drop type page_status CASCADE ; 
COMMIT;

BEGIN;
    CREATE type page_status AS ENUM ('deployed', 'pending', 'created', 'archived', 'failed');
COMMIT;

