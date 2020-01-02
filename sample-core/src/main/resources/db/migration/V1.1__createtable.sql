CREATE TABLE test
(
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    created_by VARCHAR(32) NOT NULL,
    updated_by VARCHAR(32) NOT NULL,
    active boolean DEFAULT TRUE ,
    id integer unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
    test_key VARCHAR(256) NOT NULL,
    test_value VARCHAR(256) NOT NULL
);

