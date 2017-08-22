-- Create dual view to make Oracle SQL compatible with PostgresSQL when SELECT is run without a FROM clause
CREATE VIEW dual AS
  SELECT 'unused' :: VARCHAR AS unused;

CREATE ROLE cloudbaboons LOGIN PASSWORD 'cloudbaboons';