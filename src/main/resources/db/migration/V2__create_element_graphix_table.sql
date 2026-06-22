BEGIN;

    CREATE TABLE element_graphix (
        graphix_id UUID default gen_random_uuid() PRIMARY KEY,
        parent_dir text not null, 
        file_path text unique,
        file_size BigInt not null
    );

Commit;
