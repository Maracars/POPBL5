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



  -------------------------------------------------------------------------------------------------------------------------------


  create or replace function planePositionChangedTriggerFunction () returns trigger as
$$
declare
marginx integer := 50;
marginy integer := 50;
posx integer;
posy integer;
begin
--Iual begiratu biharko da aireportua, triggerdun funtzinotan ezin da parametroik bialdu
select an.positionx, an.positiony into posx, posy from airport a join airportNode an on a.positionNode_id = an.id;
--Hemen margin bat jarri biharko da, aireportuan erdittik zenbatera begiratu bihar dan jakitzeko


--if NEW.positionx between posx + marginx and posx - marginx and NEW.positiony between posy + marginy and posy - marginy then
	PERFORM pg_notify('mezua',TG_TABLE_NAME || '>' ||row_to_json(NEW)); 
    --Hemen mezua pentsau biharko da eta gero hori entzun javatik eta mapara bialdu informazinua
--end if;

        RETURN NEW;


end
$$ language plpgsql;


CREATE TRIGGER planePositionChange
  AFTER UPDATE
  ON plane
  FOR EACH ROW
  when (old.positionx is distinct from new.positionx or old.positiony is distinct from new.positiony)
  EXECUTE PROCEDURE planePositionChangedTriggerFunction();