USE traveldb;

/*show all french hotels with availabilities during users Tour of France*/
SELECT n.AccommodationName, n.CityName, n.Date
FROM (
SELECT Country.CountryName, City.CityName
FROM Country
JOIN City ON Country.CountryName = City.CountryName
WHERE Country.CountryName = 'France'
) m JOIN
(SELECT Accommodation.AccommodationName, Accommodation.CityName, RoomAvailability.Date
FROM Accommodation
JOIN RoomAvailability ON Accommodation.AttractionID = RoomAvailability.AttractionID
WHERE RoomAvailability.IsAvailable = 1 AND EXISTS (
    SELECT UserAccount.EmailAddr, Itinerary.StartDate, Itinerary.EndDate
    FROM UserAccount
    JOIN Itinerary ON UserAccount.UserID = Itinerary.UserID
	WHERE CAST(Itinerary.EndDate AS DATE) < '2019-08-18')) n ON n.CityName = m.CityName;

/*show airport with the most flights arriving and departing in 2019*/
SELECT TOP 1 alias.ArrivalAirport, SUM(alias.NumberOfArrivals) AS NumberOfFlights
FROM(
    SELECT AirportFlightRelationship.ArrivalAirport, COUNT(*) AS NumberOfArrivals
    FROM AirportFlightRelationship
    WHERE year(AirportFlightRelationship.DateOfArrival) = 2019
    GROUP BY AirportFlightRelationship.ArrivalAirport
UNION
    SELECT AirportFlightRelationship.DepartureAirport, COUNT(*) AS NumberOfDepartures
    FROM AirportFlightRelationship
    WHERE year(AirportFlightRelationship.DateOfDeparture) = 2019
    GROUP BY AirportFlightRelationship.DepartureAirport) alias
GROUP BY alias.ArrivalAirport
ORDER BY NumberOfFlights DESC;

/*show museums open on sunday*/
SELECT Museum.MuseumName, Museum.CityName
FROM Museum
JOIN AttractionSchedule ON Museum.AttractionID = AttractionSchedule.AttractionID
WHERE AttractionSchedule.SundayOpenTime IS NULL
GROUP BY Museum.CityName, Museum.MuseumName;

/*show the airline companies with currently scheduled flights servicing paris*/
SELECT AirlineCompanyName
FROM Airport
JOIN (
    SELECT Flight.AirlineCompanyName, AirportFlightRelationship.DepartureAirport, AirportFlightRelationship.ArrivalAirport
    FROM FLIGHT
    JOIN AirportFlightRelationship ON (Flight.FlightID = AirportFlightRelationship.FlightID AND Flight.RouteID = AirportFlightRelationship.RouteID)) alias ON Airport.AirportCode = alias.ArrivalAirport
WHERE CityName = 'Paris';

/*show emails of travelers planning on visiting notre dame*/
SELECT alias.EmailAddr
FROM (
    SELECT UserAccount.EmailAddr, ItineraryEntry.AttractionID
    FROM (UserAccount
    JOIN Itinerary ON UserAccount.UserID = Itinerary.UserID), ItineraryEntry
    WHERE ItineraryEntry.ItineraryID = Itinerary.ItineraryID) alias
JOIN (
    SELECT *
    FROM Attraction
    WHERE Attraction.AttractionName = 'Notre-Dame de Paris'
) alias2 ON alias.AttractionID = alias2.AttractionID;

/* show street address of free attractions*/
SELECT *
FROM AttractionPricing
JOIN AttractionAddress ON AttractionPricing.AttractionID = AttractionAddress.AttractionID
WHERE StandardPrice IS NULL;

/* show museums with discounts on student admission, and price is less than 20 dollars */
SELECT MuseumName, CityName, Website, StudentPrice
FROM AttractionPricing
JOIN Museum ON AttractionPricing.AttractionID = Museum.AttractionID
WHERE StandardPrice > StudentPrice AND StudentPrice < 20;

/* show latitude and longitude of geographical features in at least two different countries */
SELECT alias.GeoFeatureName, alias.NumberOfCountries
FROM (
    SELECT GeographicalFeature.GeoFeatureName, COUNT(CountryName) AS NumberOfCountries
    FROM GeographicalFeature
    JOIN GeographicalFeatureCountry ON GeographicalFeature.AttractionID = GeographicalFeatureCountry.AttractionID
    GROUP BY GeographicalFeature.GeoFeatureName) alias
WHERE alias.NumberOfCountries >= 2;

/* show performance venues with sporting events */
SELECT *
FROM PerformanceVenue
JOIN PerformanceEvent ON PerformanceVenue.AttractionID = PerformanceEvent.AttractionID
WHERE PerformanceGenre = 'Sporting Event';

/* show all flights flying to countries with vacancies in hotels with a garden view */
SELECT Flight.AirlineCompanyName, CityName, alias2.AccommodationName, alias2.RoomID
FROM Flight
JOIN AirportFlightRelationship ON (Flight.FlightID = AirportFlightRelationship.FlightID AND FLIGHT.RouteID = AirportFlightRelationship.RouteID)
JOIN (
    SELECT AirportCode, Airport.CityName, alias.AccommodationName, alias.RoomID
    FROM Airport
    JOIN (
        SELECT CityName, Accommodation.AccommodationName, room.RoomID
        FROM Accommodation
        JOIN Room ON Accommodation.AttractionID = Room.AttractionID
        WHERE Room.RoomView = 'Garden') alias ON alias.CityName = Airport.CityName) alias2 ON AirportFlightRelationship.ArrivalAirport = alias2.AirportCode;

/*show names of hotels in europe with rooms that sleep at least 3 with availabilities in august 2019*/

SELECT alias3.AccommodationName, alias3.Sleeps, alias3.Date, alias3.CityName, alias3.CountryName
FROM Country
JOIN (
    SELECT alias2.AccommodationName, alias2.Sleeps, alias2.Date, alias2.CityName, City.CountryName
    FROM City
    JOIN (
        SELECT alias.AttractionID, alias.AccommodationName, alias.Sleeps, alias.Date, Accommodation.CityName
        FROM Accommodation
        JOIN (
            SELECT Room.AttractionID, Room.AccommodationName, Room.Sleeps, RoomAvailability.Date
            FROM Room
            JOIN RoomAvailability ON Room.AttractionID = RoomAvailability.AttractionID
                WHERE year(RoomAvailability.Date) = 2019 AND month(RoomAvailability.Date) = 08 AND IsAvailable = 1 AND Room.Sleeps >= 3) alias 
			ON Accommodation.AttractionID = alias.AttractionID) alias2 
		ON City.CityName = alias2.CityName) alias3 
	ON Country.CountryName = alias3.CountryName
WHERE ContinentName = 'Europe';