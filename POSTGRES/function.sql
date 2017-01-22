CREATE OR REPLACE FUNCTION public.selectdate(
    planeid integer,
    airportid integer)
  RETURNS timestamp without time zone AS
$BODY$
declare

expectedDate timestamp;
expectedArrDate timestamp;
expectedDepDate timestamp;
my_current_time timestamp;
weekCount integer;
dayCount integer;
hourCount integer;
loopDate timestamp;
hourLoopDate timestamp;
maxFlightsPerHour integer;

begin

my_current_time := current_date;
--MAXFLGHTS OF AIRPORT
select maxFlights into maxFlightsPerHour from airport where id = airportId;

--Last but not passed date of arrival of the plane
select f.expectedArrivalDate into expectedArrDate 
from plane as p join Flight f on p.id = f.plane_id 
where p.id = planeId and f.expectedArrivalDate > current_timestamp order by f.expectedArrivalDate desc limit 1;

select f.expectedDepartureDate into expectedDepDate 
from plane as p join Flight f on p.id = f.plane_id 
where p.id = planeId and f.expectedDepartureDate > current_timestamp order by f.expectedDepartureDate desc limit 1;

if expectedDate is null then

/*Honek momentuko data + 1h-ko margina jartzen dau, ez daukalako hegaldirik hegazkinak */
	expectedDate := current_timestamp + INTERVAL '1' HOUR;
else 
/*Honek 4h-ko margina jartzen dotza, eta gertuen dagon ordura borobiltzen dau*/
RAISE NOTICE 'Hona badator';
	if(expectedArrDate > expectedDepDate) then
		expectedDate := expectedArrDate + INTERVAL '4' HOUR;
	else
		expectedDate := expectedDepDate + INTERVAL '4' HOUR;
	end if;
end if;

RAISE NOTICE 'Expected date(%)', maxFlightsPerHour;

select count(*) into weekCount from flight as f
	where (f.expectedDepartureDate between my_current_time and my_current_time + INTERVAL '7' DAY) or (f.expectedArrivalDate between my_current_time and my_current_time + INTERVAL '7' DAY);
	/*borobildute current_time igual hobeto*/
  
  

if (weekCount < (24 * 7 * maxFlightsPerHour) ) then  
	loopDate := my_current_time; /* HEMEN BE BOROBILDUTE HOBETO */
	for i in 0..6 LOOP
   	/*HEMEN loop bat jarrikou, eta maximo 7 buelta emongo dau, aurretiik batenbaten maximua baino gutxiau badago
	day horretako orduak begiratuko dittu, 7 buelta emon ostian ez badau topau null bueltauko dau*/
		select count(*) into dayCount from flight as f
		where (f.expectedDepartureDate between loopDate and loopDate + INTERVAL '1' DAY) or (f.expectedArrivalDate between loopDate and loopDate + INTERVAL '1' DAY);

		RAISE NOTICE 'Day Count(%)', dayCount;
		
		if (dayCount < (24 * maxFlightsPerHour)  ) then
			hourLoopDate := loopDate;
      for j in 0..23 LOOP
		/*HEMEN loop bat jarrikou, eta maximo 24 buelta emongo dau, libre badago ordu horretakua data + minutuak
		bialtzen da*/
			select count(*) into hourCount from flight as f
			where (f.expectedDepartureDate between hourLoopDate and hourLoopDate + INTERVAL '1' HOUR) or (f.expectedArrivalDate between hourLoopDate and hourLoopDate + INTERVAL '1' HOUR);
    		
      if(hourCount < maxFlightsPerHour  ) then
      	expectedDate := hourLoopDate + (INTERVAL '1' HOUR) / maxFlightsPerHour * hourCount;
        return expectedDate;
      end if;
      hourLoopDate := hourLoopDate + INTERVAL '1' HOUR;
			end loop;
    	
		end if;
  	loopDate := loopDate + INTERVAL '1' DAY;
	end loop;
 
end if;

return null;
end
$BODY$
  LANGUAGE plpgsql;