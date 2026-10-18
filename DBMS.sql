	CREATE TABLE users (
		id BIGSERIAL PRIMARY KEY,
		name VARCHAR(100) NOT NULL,
		email VARCHAR(100) UNIQUE NOT NULL,
		created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	);	
	
	BEGIN TRANSACTION;
	UPDATE accounts SET balance = balance - 100 WHERE account_id = 1;
	SAVEPOINT before_transfer;
	UPDATE accounts SET balance = balance + 100 WHERE account_id = 2;
	ROLLBACK TO before_transfer;  -- Rolls back only to the savepoint.