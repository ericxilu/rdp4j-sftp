drop table file_audit
create table FILE_AUDIT (
                            ID BIGINT IDENTITY NOT NULL,
                            FILE_NAME VARCHAR(250) NOT NULL,
                            CREATED_DATETIME DATETIME DEFAULT (GETUTCDATE()) NOT NULL,
                            PRIMARY KEY (ID)
)