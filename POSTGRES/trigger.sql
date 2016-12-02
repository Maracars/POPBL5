  CREATE OR REPLACE FUNCTION conversation_notify()
  RETURNS trigger AS
$$
    BEGIN
        PERFORM pg_notify('mezua',TG_TABLE_NAME || '>' ||row_to_json(NEW));
        RETURN NEW;
    END; 
$$
  LANGUAGE plpgsql ;


CREATE TRIGGER conversation_notify
  AFTER INSERT OR UPDATE
  ON proba
  FOR EACH ROW
  EXECUTE PROCEDURE conversation_notify();