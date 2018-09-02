CREATE DATABASE traveldb;

USE [traveldb];

CREATE TABLE Continent (
    ContinentName       VARCHAR(30) NOT NULL,
    CONSTRAINT          ContinentPK PRIMARY KEY (ContinentName)
    );

CREATE TABLE Country (
    CountryName         VARCHAR(75) NOT NULL,
	ContinentName       VARCHAR(30) NOT NULL,
    CONSTRAINT          CountryPK PRIMARY KEY (CountryName),
    CONSTRAINT          ContinentCountryRelationship FOREIGN KEY (ContinentName)
	                    REFERENCES Continent(ContinentName)
    );

CREATE TABLE State (
    StateName           VARCHAR(30) NOT NULL,
	CountryName         VARCHAR(75) NOT NULL,
    RegionName          VARCHAR(70),
    CONSTRAINT          StatePK PRIMARY KEY (StateName),
    CONSTRAINT          StateFK FOREIGN KEY (CountryName)
	                    REFERENCES Country(CountryName)
						ON UPDATE CASCADE
    );

CREATE TABLE City (
    CityName            VARCHAR(70) NOT NULL,
    CountryName         VARCHAR(75) NOT NULL,
    IsCapital           BIT NOT NULL,
    CONSTRAINT          CityPK PRIMARY KEY (CityName),
    CONSTRAINT          CityFK FOREIGN KEY (CountryName)
	                    REFERENCES Country(CountryName) /* make this better */
						ON UPDATE CASCADE
    );

CREATE TABLE UserAccount (
    UserID              INTEGER NOT NULL,
    EmailAddr           VARCHAR(100) NOT NULL,
    FirstName           VARCHAR(100) NOT NULL,
    LastName            VARCHAR(100) NOT NULL,
    CONSTRAINT          UserAccountPK PRIMARY KEY (UserID, EmailAddr),
    CHECK               (LEN(FirstName)>=1),
    CHECK               (LEN(LastName)>=1)
    );

CREATE TABLE Itinerary (
    ItineraryID         INTEGER NOT NULL,
    UserID              INTEGER NOT NULL,
    EmailAddr           VARCHAR(100) NOT NULL,
    ItineraryName       VARCHAR(70),
    StartDate           DATE, /* stores year, month, and day values */
    EndDate             DATE,
    CONSTRAINT          ItineraryPK PRIMARY KEY (ItineraryID),
    CONSTRAINT          ItineraryFK FOREIGN KEY (UserID, EmailAddr) /* must be from a user account */
	                    REFERENCES UserAccount(UserID, EmailAddr)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE ItineraryEntry (
    ItineraryEntryID    INTEGER NOT NULL,
    ItineraryID         INTEGER NOT NULL,
	AttractionID        BIGINT,
	StartDate           DATE,
	EndDate             DATE,
    StartTime           TIME, /* Stores hour, minute, and second values */
    EndTime             TIME,
    CONSTRAINT          ItineraryEntryPK PRIMARY KEY (ItineraryEntryID, ItineraryID),
    CONSTRAINT          ItineraryEntryFK FOREIGN KEY (ItineraryID)
	                    REFERENCES Itinerary(ItineraryID)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
	CHECK              (EndTime >= StartTime)
    );

CREATE TABLE Attraction (
    AttractionID        BIGINT NOT NULL,
	AttractionName      VARCHAR(150),
	CONSTRAINT          AttractionPK PRIMARY KEY (AttractionID)
	);

CREATE TABLE Accommodation (
    AttractionID        BIGINT NOT NULL,
    AccommodationName   VARCHAR(150) NOT NULL,
    CityName            VARCHAR(70) NOT NULL,
    Latitude            REAL,
    Longitude           REAL,
    PhoneNumber         BIGINT,
	Website             VARCHAR(350),
    CONSTRAINT          AccommodationPK PRIMARY KEY (AttractionID),
    CONSTRAINT          AccommodationFK FOREIGN KEY (CityName)
	                    REFERENCES City(CityName)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
    CONSTRAINT          AttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE Room (
    RoomID              INTEGER NOT NULL,
    AttractionID        BIGINT NOT NULL,
    AccommodationName   VARCHAR(150) NOT NULL,
    FloorNumber         INTEGER,
    Beds                INTEGER,
    Sleeps              INTEGER,
    RoomView            VARCHAR(50),
    CONSTRAINT          RoomPK PRIMARY KEY (RoomID, AttractionID),
    CONSTRAINT          RoomFK FOREIGN KEY (AttractionID)
	                    REFERENCES Accommodation(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE RoomAvailability (
    RoomID             INTEGER NOT NULL,
	AttractionID       BIGINT NOT NULL,
	Date               DATE NOT NULL,
	Price              INTEGER,
	IsAvailable        BIT,
	CONSTRAINT         RoomAvailPK PRIMARY KEY (RoomID, AttractionID, Date),
	CONSTRAINT         RoomAvailRoomRel FOREIGN KEY (RoomID, AttractionID)
	                   REFERENCES Room(RoomID, AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE AttractionAddress (
    AttractionID        BIGINT NOT NULL,
    AttractionName      VARCHAR(150) NOT NULL,
    StreetAddr          VARCHAR(100),
    City                VARCHAR(70),
    State               VARCHAR(70),
    Country             VARCHAR(70),
    PostCode            INTEGER,
    CONSTRAINT          AttractionAddressPK PRIMARY KEY (AttractionID),
    CONSTRAINT          AttractionAddressFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE Park (
    AttractionID        BIGINT NOT NULL,
    ParkName            VARCHAR(150) NOT NULL,
    CityName            VARCHAR(70) NOT NULL,
    Latitude            REAL,
    Longitude           REAL,
    PhoneNumber         BIGINT,
    Website             VARCHAR(350),
    CONSTRAINT          ParkPK PRIMARY KEY (AttractionID),
    CONSTRAINT          ParkCityFK FOREIGN KEY (CityName)
	                    REFERENCES City(CityName)
						ON UPDATE CASCADE,
    CONSTRAINT          PKAttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE HistoricMonument (
    AttractionID        BIGINT NOT NULL,
    MonumentName        VARCHAR(150) NOT NULL,
    CityName            VARCHAR(70) NOT NULL,
    Latitude            REAL,
    Longitude           REAL,
    PhoneNumber         BIGINT,
    Website             VARCHAR(350),
    CONSTRAINT          HistoricMonumentPK PRIMARY KEY (AttractionID),
    CONSTRAINT          HistoricMonumentFK FOREIGN KEY (CityName)
	                    REFERENCES City(CityName)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
    CONSTRAINT          HMAttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE Museum (
    AttractionID        BIGINT NOT NULL,
    MuseumName          VARCHAR(150) NOT NULL,
    CityName            VARCHAR(70) NOT NULL,
    Latitude            REAL,
    Longitude           REAL,
    PhoneNumber         BIGINT,
    Website             VARCHAR(350),
    CONSTRAINT          MuseumPK PRIMARY KEY (AttractionID),
    CONSTRAINT          MuseumFK FOREIGN KEY (CityName)
	                    REFERENCES City(CityName)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
    CONSTRAINT          MAttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE GeographicalFeature (
    AttractionID        BIGINT NOT NULL,
    GeoFeatureName      VARCHAR(150) NOT NULL,
	Latitude            REAL,
	Longitude           REAL,
	PhoneNumber         BIGINT,
    CONSTRAINT          GeographicalFeaturePK PRIMARY KEY (AttractionID),
    CONSTRAINT          GFAttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE GeographicalFeatureCountry (
    AttractionID        BIGINT NOT NULL,
	GeoFeatureName      VARCHAR(150) NOT NULL,
    CountryName         VARCHAR(75) NOT NULL,
	CONSTRAINT          GeoFeatCounPK PRIMARY KEY (AttractionID, CountryName),
	CONSTRAINT          GeoFeatGeoFeatRela FOREIGN KEY (AttractionID)
	                    REFERENCES GeographicalFeature(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
	);

CREATE TABLE Restaurant (
    AttractionID        BIGINT NOT NULL,
    RestaurantName      VARCHAR(150) NOT NULL,
    CityName            VARCHAR(70) NOT NULL,
    Latitude            REAL,
    Longitude           REAL,
    PriceRange          INTEGER, /* code between 1 and 4, with 4 being the most expensive */
    PhoneNumber         BIGINT,
    CONSTRAINT          RestaurantPK PRIMARY KEY (AttractionID),
    CONSTRAINT          RestaurantFK FOREIGN KEY (CityName)
	                    REFERENCES City(CityName)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
    CONSTRAINT          RAttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE PerformanceVenue (
    AttractionID        BIGINT NOT NULL,
    VenueName           VARCHAR(150) NOT NULL,
    CityName            VARCHAR(70) NOT NULL,
    Latitude            REAL,
    Longitude           REAL,
    PhoneNumber         BIGINT,
    Website             VARCHAR(350),
    CONSTRAINT          PerformanceVenuePK PRIMARY KEY (AttractionID),
    CONSTRAINT          PerformanceVenueFK FOREIGN KEY (CityName)
	                    REFERENCES City(CityName)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
    CONSTRAINT          PVAttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE PerformanceEvent (
    PerformanceID       INTEGER NOT NULL,
    AttractionID        BIGINT NOT NULL,
    VenueName           VARCHAR(150) NOT NULL,
    PerformanceGenre    VARCHAR(40),
    LowestPriceTickets  INTEGER,
    HighestPriceTickets INTEGER,
    StartDate           DATE,
    EndDate             DATE,
    StartTime           TIME,
    EndTime             TIME,
    CONSTRAINT          PerformanceEventPK PRIMARY KEY (PerformanceID),
    CONSTRAINT          PerformanceEventFK FOREIGN KEY (AttractionID)
	                    REFERENCES PerformanceVenue(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
	CHECK               ((StartDate < EndDate) OR (StartDate = EndDate AND StartTime <= EndTime))
    );

CREATE TABLE AirlineCompany (
    AirlineCompanyName  VARCHAR(50) NOT NULL,
    PhoneNumber         BIGINT,
    Website             VARCHAR(350),
    CONSTRAINT          AirlineCompanyPK PRIMARY KEY (AirlineCompanyName),
    );

CREATE TABLE Airport (
    AttractionID        BIGINT NOT NULL,
    AirportCode         VARCHAR(5) NOT NULL,
    AirportName         VARCHAR(150) NOT NULL,
    CityName            VARCHAR(70) NOT NULL,
    Latitude            REAL,
    Longitude           REAL,
    PhoneNumber         BIGINT,
    IsInternational     BIT,
    Website             VARCHAR(350),
    CONSTRAINT          AirportPK PRIMARY KEY (AttractionID),
    CONSTRAINT          AirportFK FOREIGN KEY (CityName)
	                    REFERENCES City(CityName)
						ON UPDATE CASCADE
						ON DELETE CASCADE,
    CONSTRAINT          APAttractionFK FOREIGN KEY (AttractionID)
	                    REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE Flight (
    RouteID             INTEGER NOT NULL,
    FlightID            INTEGER NOT NULL,
    AirlineCompanyName  VARCHAR(50) NOT NULL,
    CONSTRAINT          FlightPK PRIMARY KEY (RouteID, FlightID),
    CONSTRAINT          FlightFK FOREIGN KEY (AirlineCompanyName)
	                    REFERENCES AirlineCompany(AirlineCompanyName)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE AirportFlightRelationship (
    FlightSegmentID     INTEGER NOT NULL,
    RouteID             INTEGER NOT NULL,
    FlightID            INTEGER NOT NULL,
    DepartureAirport    VARCHAR(10),
    ArrivalAirport      VARCHAR(10),
    DateOfArrival       DATE,
    TimeOfArrival       TIME,
    DateOfDeparture     DATE,
    TimeOfDeparture     TIME,
    Gate                VARCHAR(15),
    CONSTRAINT          AirportFlightRelationshipPK PRIMARY KEY (FlightSegmentID, RouteID, FlightID),
    CONSTRAINT          AirportFlightRelationshipFK FOREIGN KEY (RouteID, FlightID)
	                    REFERENCES Flight(RouteID, FlightID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE AttractionPricing (
    PricingID          INTEGER NOT NULL,
    AttractionID       BIGINT NOT NULL,
    ChildPrice         INTEGER,
	ChildPriceAge      INTEGER,
    StandardPrice      INTEGER,
    StudentPrice       INTEGER,
    SeniorPrice        INTEGER,
    CONSTRAINT         AttractionPricingPK PRIMARY KEY (PricingID),
    CONSTRAINT         AttractionPricingFK FOREIGN KEY (AttractionID)
	                   REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

CREATE TABLE AttractionSchedule (
    ScheduleID         INTEGER NOT NULL,
    AttractionID       BIGINT NOT NULL,
    MondayOpenTime     TIME,
    MondayCloseTime    TIME,
    TuesdayOpenTime    TIME,
    TuesdayCloseTime   TIME,
    WednesdayOpenTime  TIME,
    WednesdayCloseTime TIME,
    ThursdayOpenTime   TIME,
    ThursdayCloseTime  TIME,
    FridayOpenTime     TIME,
    FridayCloseTime    TIME,
    SaturdayOpenTime   TIME,
    SaturdayCloseTime  TIME,
    SundayOpenTime     TIME,
    SundayCloseTime    TIME,
    CONSTRAINT         AttractionSchedulePK PRIMARY KEY (ScheduleID),
    CONSTRAINT         AttractionScheduleFK FOREIGN KEY (AttractionID)
	                   REFERENCES Attraction(AttractionID)
						ON UPDATE CASCADE
						ON DELETE CASCADE
    );

INSERT INTO Continent (ContinentName) VALUES 
    ('Asia'),
    ('Africa'),
    ('North America'),
    ('South America'),
    ('Antartica'),
    ('Europe'),
    ('Australia');

/*build country table*/
INSERT INTO Country (CountryName, ContinentName) VALUES 
    ('Afghanistan', 'Asia'),
    ('Albania', 'Europe'),
    ('Algeria', 'Africa'),
	('France', 'Europe'),
	('India', 'Asia'),
	('United States of America', 'North America'),
	('Australia', 'Australia'),
	('Spain', 'Europe'),
	('Namibia', 'Africa'),
    ('Andorra', 'Europe'),
	('Luxembourg', 'Europe'),
	('Germany', 'Europe'),
	('Japan', 'Asia'),
	('Belgium', 'Europe'),
    ('United Kingdom', 'Europe'),
    ('Canada', 'North America');

/*build state table*/
INSERT INTO State (StateName, CountryName, RegionName) VALUES 
('Washington', 'United States of America', 'Pacific Northwest'),
('Oregon', 'United States of America', 'Pacific Northwest'),
('Arizona', 'United States of America', 'Southwest'),
('California', 'United States of America', 'Southwest'),
('Illinois', 'United States of America', 'Midwest'),
('New York', 'United States of America', 'Northeast'),
('Florida', 'United States of America', 'Southeast'),
('Texas', 'United States of America', 'South'),
('Tennessee', 'United States of America', 'South'),
('Maine', 'United States of America', 'Northeast');

/*build City table*/
INSERT INTO City (CityName, CountryName, IsCapital) VALUES
('Paris', 'France', 1),
('Seattle', 'United States of America', 0),
('Portland', 'United States of America', 1),
('Lyon', 'France', 0),
('Andorra la Vella', 'Andorra', 1),
('Oran', 'Algeria', 0),
('Melbourne', 'Australia', 0),
('Metz', 'France', 0),
('Luxembourg City', 'Luxembourg', 1),
('Arlon', 'Belgium', 0),
('SaarLouis', 'Germany', 0),
('Kyoto', 'Japan', 0),
('Kolkata', 'India', 0),
('London', 'United Kingdom', 1),
('Pelly Bay', 'Canada', 0),
('Hamburg', 'Germany', 0);

/*build user account table*/
INSERT INTO UserAccount (UserID, EmailAddr, FirstName, LastName) VALUES
(100000, 'thompsonjoe@email.add', 'Joe', 'Thompson'),
(100001, 'keperkatherine@email.add', 'Katherine', 'Keper'),
(100002, 'haptonhenry@email.add', 'Henry', 'Hapton'),
(100003, 'mattisonmatthew@email.add', 'Matthew', 'Mattison'),
(100004, 'gregorygeorgia@email.add', 'Georgia', 'Gregory'),
(100005, 'rochesterricky@email.add', 'Ricky', 'Rochester'),
(100006, 'fredricksonfrancis@email.add', 'Francis', 'Fredrickson'),
(100007, 'brickardbenny@email.add', 'Benny', 'Brickard'),
(100008, 'tokogawatanaka@email.add', 'Tanaka', 'Tokogawa'),
(100009, 'bonaparte@email.add', 'Napoleon', 'Bonaparte');

/*build itinerary table*/
INSERT INTO Itinerary (ItineraryID, UserID, EmailAddr, ItineraryName, StartDate, EndDate) VALUES
(1234, 100000, 'thompsonjoe@email.add', 'Trip to Europe', '2019-11-02', '2019-11-12'),
(1235, 100001, 'keperkatherine@email.add', 'Tour of France', '2019-08-14', '2019-08-22'),
(1236, 100002, 'haptonhenry@email.add', 'Pacific Northwest Vacation', '2018-08-10', '2018-08-15'),
(1237, 100002, 'haptonhenry@email.add', 'French Vacation', '2019-08-10', '2019-08-15'),
(1238, 100003, 'mattisonmatthew@email.add', NULL, '2019-08-14', '2019-08-20'),
(1239, 100004, 'gregorygeorgia@email.add', 'Paris Trip', '2019-08-19', '2019-08-22'),
(1240, 100004, 'gregorygeorgia@email.add', 'Namibia Safari', '2019-05-20', '2019-06-25'),
(1241, 100005, 'rochesterricky@email.add', 'Paris', NULL, NULL),
(1242, 100006, 'fredricksonfrancis@email.add', 'European Vacay', '2025-12-30', '2026-01-07'),
(1243, 100009, 'bonaparte@email.add', 'Corsica, France, and Elba', '2021-08-15', '2023-05-05');

/*build itinerary entry table*/
INSERT INTO ItineraryEntry (ItineraryEntryID, ItineraryID, AttractionID, StartDate, EndDate, StartTime, EndTime ) VALUES
(00001, 1234, 454546230380, '2019-11-03', '2019-11-03', '11:00:00 AM', '07:00:00 PM'),
(00002, 1234, 454546263744, '2019-11-02', '2019-11-12', '00:00:00 AM', '00:00:00 AM'), /*ACCOMMODATION*/
(00003, 1234, 454546263744, '2019-11-03', '2019-11-03', '08:00:00 AM', '10:30:00 AM'),
(00004, 1234, 454546263744, '2019-11-04', '2019-11-04', '09:00:00 AM', '12:30:00 PM'),
(00001, 1235, 457345897456, '2019-11-02', '2019-11-02', '08:00:00 AM', '12:00:00 PM'),
(00002, 1235, 457345897455, '2019-11-02', '2019-11-02', '02:00:00 PM', '06:00:00 PM'),
(00003, 1235, 457309364755, '2019-11-03', '2019-11-03', '12:00:00 PM', '02:00:00 PM'),
(00004, 1235, 457309248152, '2019-11-03', '2019-11-03', '04:00:00 PM', '10:00:00 PM'),
(00001, 1239, 457345897456, '2019-11-02', '2019-11-02', '04:00:00 PM', '10:00:00 PM'),
(00001, 1238, 100253145645, '2019-11-02', '2019-11-02', '04:00:00 PM', '10:00:00 PM');

/*build attraction table*/
INSERT INTO Attraction (AttractionID, AttractionName) VALUES
(457345897456, 'Notre-Dame de Paris'),
(457345897455, 'Eiffel Tower'),
(457345987449, 'Louvre Museum'),
(457309364755, 'Vieux Lyon'),
(452872546398, 'Eglise St Donat'),
(454546230380, 'Casa de la Vall'),
(209097274113, 'Fort Santa Cruz'),
(100253487009, 'Eureka Skydeck 88'),
(457099334329, 'Maison Natale de Paul Verlaine'),
(453670983476, 'Edmund Klein Park'),
(457345000192, 'Hotel Cecilia Arc De Triomphe'),
(457345004565, 'Saint James Albany Hotel-Spa'),
(457309264788, 'Quality Suites Lyon Confluence'),
(452872547874, 'Best Western Hotel Arlux'),
(454546263744, 'NH Hesperia Andorra la Vella'),
(100253124515, 'Quest South Melbourne'),
(453670741544, 'Le Place d''Armes Hotel'),
(899564213327, 'Hotel New-Beach'),
(457309264774, 'Cour des Loges'),
(457309264720, 'Mercure Lyon Centre Chateau Perrache'),
(457309264789, 'Parc de la Tete d''Or'),
(457345045648, 'Luxembourg Gardens'),
(100253145648, 'Royal Botanic Gardens Victoria'),
(453670151697, 'Parc de Merl-Belair'),
(454546263745, 'Circuit de les Fonts'),
(954122168181, 'Rabindra Sarobar'),
(100253145644, 'Fitzroy Gardens'),
(457345000182, 'Parc des Buttes Chaumont'),
(457345000170, 'Parc de Bagatelle'),
(457345000171, 'Jardin des Tuileries'),
(457345045554, 'Sainte-Chapelle'),
(457345045412, 'Arc de Triomphe'),
(457309451874, 'Place Bellecour'),
(454546263746, 'La Noblesse du Temps'),
(453670151698, 'Monument of Remembrance'),
(100253145645, 'Parliament House of Victoria'),
(675124412574, 'Space Needle'),
(675184581234, 'Oregon Holocaust Memorial'),
(457345045832, 'Basilica du Sacre-Coeur de Montmartre'),
(457345045833, 'Arc de Triomphe'),
(457309248152, 'Museum of Fine Arts of Lyon'),
(457345035815, 'Musee d''Orsay'),
(457345185144, 'Musee du quai Branly – Jacques Chirac'),
(453670151699, 'Luxembourg City History Museum'),
(100253145646, 'Melbourne Museum'),
(100253145647, 'Immigration Museum'),
(954122986513, 'Indian Museum'),
(675124416851, 'Seattle Art Museum'),
(675184489153, 'Portland Art Museum'),
(457345184662, 'Musee Rodin'),
(954784132541, 'Mount Everest'),
(954156849165, 'Dead Sea'),
(000135254811, 'Bouvet Island'),
(675451325532, 'Mammoth-Flint Ridge Cave System'),
(457465123548, 'Pyrenees Mountains'),
(675198616851, 'Niagara Falls'),
(468754315864, 'Plitvice Lakes'),
(135186454353, 'Victoria Falls'),
(100135848413, 'Great Barrier Reef'),
(675168484133, 'Mount Rushmore'),
(457309551636, 'Bagettes du Pain'),
(457345198623, 'Cafe du Paris'),
(457345098752, 'Cafe du Paris'),
(454546151210, 'Meshoune'),
(453670138481, 'Ambrosia Restaurant'),
(100253165848, 'EZARD'),
(954122048710, 'Peter Cat'),
(675124512254, 'Kraken Bar and Grill'),
(675184058001, 'Tiko''s'),
(457345478125, 'Cafe du Triomphe'),
(457309861381, 'Opera National de Lyon'),
(457345684204, 'AccorHotels Arena'),
(457345160868, 'Philharmonie de Paris'),
(457345651350, 'Paris Opera'),
(454546654321, 'Estadi Comunal d''Andorra la Vella'),
(453670987512, 'Philharmonie Luxembourg'),
(100253684651, 'Melbourne Cricket Ground'),
(954122986312, 'GD Birla Sabhaghar'),
(675124651351, 'Neumos'),
(675184916504, 'The Old Church Concert Hall'),
(457309861502, 'Lyon–Saint-Exupéry Airport'),
(457345981350, 'Charles de Gaulle Airport'),
(453670852488, 'Luxembourg Airport'),
(100253685202, 'Melbourne Airport'),
(954122055410, 'Netaji Subhas Chandra Bose International Airport'),
(675124505065, 'Seattle–Tacoma International Airport'),
(675184840254, 'Portland International Airport'),
(412612496888, 'London Heathrow Airport'),
(687568665424, 'Kugaaruk Airport'),
(429984651323, 'Hamburg Hbf');

/*build accommodation table*/
INSERT INTO Accommodation (AttractionID, AccommodationName, CityName, Latitude, Longitude, PhoneNumber, Website ) VALUES
(457345000192, 'Hotel Cecilia Arc De Triomphe', 'Paris', 48.875818, 2.294369, 00133143803210, 'http://www.hotelcecilia.com/'),
(457345004565, 'Saint James Albany Hotel-Spa', 'Paris', 48.864497, 2.330855, 001330144584321, 'http://www.saintjamesalbany.com/en/'),
(457309264788, 'Quality Suites Lyon Confluence', 'Lyon', 45.745306, 4.822819, 001330437231604, 'http://www.qualitysuiteslyonconfluence.com/'),
(452872547874, 'Best Western Hotel Arlux', 'Arlon', 49.676434, 5.784313, 003263232211, 'https://www.bestwestern.com/en_US/book/hotels-in-arlon/best-western-hotel-arlux/propertyCode.92927.html'),
(454546263744, 'NH Hesperia Andorra la Vella', 'Andorra la Vella', 42.509166, 1.529287, 2122455462, 'https://www.nh-hotels.com/booking/step1-rates?fini=03/06/2018&fout=04/06/2018&nadults1=2&nchilds1=0&hotelId=AD07.ANDOR&campid=8436102&ct=287498657&dclid=CO6H7vCWn9sCFdh2YgodrbsCAg#v=compact'),
(100253124515, 'Quest South Melbourne', 'Melbourne', -37.833105, 144.969712, 61396981500, 'http://www.questapartments.com.au/properties/vic/south-melbourne/quest-south-melbourne/overview'),
(453670741544, 'Le Place d''Armes Hotel', 'Luxembourg City', 49.611548, 6.128775, 01135227862107, 'http://hotel-leplacedarmes.com/en/'),
(899564213327, 'Hotel New-Beach', 'Oran', 35.703245, -0.894929, NULL, NULL),
(457309264774, 'Cour des Loges', 'Lyon', 45.763350, 4.827202, 33472774444, 'http://www.courdesloges.com'),
(457309264720, 'Mercure Lyon Centre Chateau Perrache', 'Lyon', 45.749473, 4.824701, 01133472771500, 'https://www.accorhotels.com/gb/hotel-1292-mercure-lyon-centre-chateau-perrache-hotel/index.shtml');

/*build room table*/
INSERT INTO Room (RoomID, AttractionID, AccommodationName, FloorNumber, Beds, Sleeps, RoomView ) VALUES
(0045, 457345000192, 'Hotel Cecilia Arc De Triomphe', 4, 3, 6, 'Garden'),
(012, 457345000192, 'Hotel Cecilia Arc De Triomphe', 3, 2, 4, 'City'),
(123, 457345004565, 'Saint James Albany Hotel-Spa', 5, 2, 8, 'City'),
(098, 457345004565, 'Saint James Albany Hotel-Spa', 3, 1, 4, 'City'),
(298, 457345004565, 'Saint James Albany Hotel-Spa', 1, 1, 3, 'Street'),
(4565, 453670741544, 'Le Place d''Armes Hotel', 1, 2, 2, 'Garden'),
(154, 457309264774, 'Cour des Loges', 2, 2, 5, NULL),
(78, 457309264720, 'Mercure Lyon Centre Chateau Perrache', 1, 1, 2, 'Street'),
(003, 452872547874, 'Best Western Hotel Arlux', 1, 1, 2, 'Street'),
(2899, 100253124515, 'Quest South Melbourne', 5, 2, 4, 'City');

/*build room availability table*/
INSERT INTO RoomAvailability (RoomID, AttractionID, Date, Price, IsAvailable) VALUES
(0045, 457345000192, '2019-08-14', 147, 1),
(0045, 457345000192, '2019-08-15', 149, 0),
(0045, 457345000192, '2019-08-16', 160, 1),
(0045, 457345000192, '2019-08-17', 160, 0),
(0045, 457345000192, '2019-08-18', 160, 1),
(0045, 457345000192, '2019-08-19', 101, 0),
(123, 457345004565, '2019-08-30', 199, 1),
(154, 457309264774, '2019-08-30', 150, 1),
(2899, 100253124515, '2019-08-30', 60, 1),
(2899, 100253124515, '2019-08-31', 50, 1),
(4565, 453670741544, '2019-08-30', 150, 1),
(78, 457309264720, '2019-08-30', 120, 1),
(78, 457309264720, '2019-08-31', 110, 1);

/*build attraction address table*/
INSERT INTO AttractionAddress (AttractionID, AttractionName, StreetAddr, City, State, Country, PostCode) VALUES
(457345897456, 'Notre-Dame de Paris', '6 Parvis Notre-Dame - Pl. Jean-Paul II', 'Paris', NULL, 'France', 75004),
(457345897455, 'Eiffel Tower', 'Champ de Mars, 5 Avenue Anatole', 'Paris', NULL, 'France', 75007),
(457345987449, 'Louvre Museum', 'Rue de Rivoli', 'Paris', NULL, 'France', 75001),
(457309364755, 'Vieux Lyon', NULL, 'Lyon', NULL, 'France', 69000),
(452872546398, 'Eglise St Donat', 'Square Elisabeth 2', 'Arlon', NULL, 'Belgium', 6700),
(454546230380, 'Casa de la Vall', 'Andorra la Vella', 'Andorra la Vella', NULL, 'Andorra', 500),
(209097274113, 'Fort Santa Cruz', NULL, 'Oran', NULL, 'Algeria', 46000),
(100253487009, 'Eureka Skydeck 88', '7 Riverside Quay', 'Southbank VIC', NULL, 'Australia', 3006),
(457099334329, 'Maison Natale de Paul Verlaine', '2 Rue Haute Pierre', 'Metz', NULL, 'France', 57000),
(453670983476, 'Edmund Klein Park', 'Boulevard de Prince Henri', 'Luxembourg City', NULL, 'Luxembourg',  1321),
(687568665424, 'Kugaaruk Airport', null, 'Pelly Bay', null, 'Canada', 334522),
(100253124515, 'Quest South Melbourne', '126 S H St.', 'Melbourne', null, 'Australia', 1235444),
(100253145644, 'Fitzroy Gardens', 'Tory St.', 'Melbourne', null, 'Australia', 1230264),
(100253145645, 'Parliament House of Victoria', 'Whig St.', 'Melbourne', null, 'Australia', 1230264);

/*build park table*/
INSERT INTO Park (AttractionID, ParkName, CityName, Latitude, Longitude, PhoneNumber, Website) VALUES
(457309264789, 'Parc de la Tete d''Or', 'Lyon', 45.777027, 4.844629, 33472103030, NULL),
(457345045648, 'Luxembourg Gardens', 'Paris', 48.843235, 2.313604, NULL, NULL),
(100253145648, 'Royal Botanic Gardens Victoria', 'Melbourne', -37.833858, 144.982527, 61392522300, NULL),
(453670151697, 'Parc de Merl-Belair', 'Luxembourg City', 49.606304, 6.112239, 352222809, NULL),
(454546263745, 'Circuit de les Fonts', 'Andorra la Vella', 42.500314, 1.525359, NULL, 'http://visitandorra.com/es/itinerarios/ruta-de-senderismo-circuit-de-les-fonts-itinerari-curt/'),
(954122168181, 'Rabindra Sarobar', 'Kolkata', 22.512145, 88.363695, 919600017031, 'http://gautamandgautamgroup.org/'),
(100253145644, 'Fitzroy Gardens', 'Melbourne', -37.816005, 144.982445, 61396589658, 'http://www.fitzroygardens.com/'),
(457345000182, 'Parc des Buttes Chaumont', 'Paris', 48.876913, 2.381105, 33148038310, 'https://en.parisinfo.com/paris-museum-monument/71468/Parc-des-Buttes-Chaumont'),
(457345000170, 'Parc de Bagatelle', 'Paris', 48.863869, 2.239796, 33153645380, NULL),
(457345000171, 'Jardin des Tuileries', 'Paris', 48.852254, 2.332658, 3314428911, 'https://en.parisinfo.com/paris-museum-monument/71304/Jardin-des-Tuileries');

/*build historic monument table*/
INSERT INTO HistoricMonument (AttractionID, MonumentName, CityName, Latitude, Longitude, PhoneNumber, Website) VALUES
(457345045554, 'Sainte-Chapelle', 'Paris', 48.867445, 2.284542, 3312584544, 'http://www.sainte-chapelle.fr/en/'),
(457345045412, 'Arc de Triomphe', 'Paris', 48.854125, 2.341285, 3314812023, 'http://www.paris-arc-de-triomphe.fr/en/'),
(457309451874, 'Place Bellecour', 'Lyon', 45.7578, 4.8323, 33472776969, NULL),
(454546263746, 'La Noblesse du Temps', 'Andorra la Vella', 42.508613, 1.529212, 376877779, NULL),
(453670151698, 'Monument of Remembrance', 'Luxembourg City', 49.608103, 6.107215, 352482791, NULL),
(100253145645, 'Parliament House of Victoria', 'Melbourne', -37.815235, 144.974840,  61396518911, 'http://www.parliament.vic.gov.au/visit/public-tours'),
(675124412574, 'Space Needle', 'Seattle', 47.620423, -122.349355, 18009379582, 'https://www.spaceneedle.com/'),
(675184581234, 'Oregon Holocaust Memorial', 'Portland', 45.512640, -122.712736,  15038237529, 'http://www.ojmche.org/educate/education/holocaust-memorial'),
(457345045832, 'Basilica du Sacre-Coeur de Montmartre', 'Paris', 48.886706, 2.343023, 3314812023, 'http://www.sacre-coeur-montmartre.com/english/'),
(457345045833, 'Arc de Triomphe', 'Paris', 48.854125, 2.341285, 3314812023, 'http://www.paris-arc-de-triomphe.fr/en/');

/*build museum table*/
INSERT INTO Museum (AttractionID, MuseumName, CityName, Latitude, Longitude, PhoneNumber, Website) VALUES
(457309248152, 'Museum of Fine Arts of Lyon', 'Lyon', 45.767132, 45.767132, 3348157468, 'http://www.mba-lyon.fr/mba/sections/languages/english/museum/welcome-museum-lyon'),
(457345035815, 'Musee d''Orsay', 'Paris', 48.867151, 2.284156, 33141874568, 'http://www.musee-orsay.fr/en/home.html'),
(457345185144, 'Musee du quai Branly – Jacques Chirac', 'Paris', 48.874114, 2.318905, 3314148564, 'http://www.quaibranly.fr/en/exhibitions-and-events/at-the-museum/exhibitions/'),
(453670151699, 'Luxembourg City History Museum', 'Luxembourg City', 49.601548, 6.148521, 3521847485, 'http://citymuseum.lu/en/'),
(100253145646, 'Melbourne Museum', 'Melbourne', -37.820448, 144.904856, 61398156325, 'https://museumsvictoria.com.au/melbournemuseum/'),
(100253145647, 'Immigration Museum', 'Melbourne', -37.840584, 144.915484, 61398510695, 'https://museumsvictoria.com.au/immigrationmuseum/'),
(954122986513, 'Indian Museum', 'Kolkata', 22.534358, 88.359596, 9194559634471, NULL),
(675124416851, 'Seattle Art Museum', 'Seattle', 47.625841, -122.391154, 12534094815, 'http://www.seattleartmuseum.org/'),
(675184489153, 'Portland Art Museum', 'Portland', 45.549512, -122.734851, 15486128484, 'https://portlandartmuseum.org/'),
(457345184662, 'Musee Rodin', 'Paris', 48.861945, 2.321501, 3314025841, 'http://www.musee-rodin.fr/');

/*build attraction pricing table*/
INSERT INTO AttractionPricing (PricingID, AttractionID, ChildPrice, ChildPriceAge, StandardPrice, StudentPrice, SeniorPrice) VALUES
(15132334, 457309248152, NULL, 12, 12, 10, 10),
(42465233, 457345035815, 10, 12, 24, 12, 12),
(73456212, 457309451874, NULL, NULL, NULL, NULL, NULL), /*every attraction must have a pricing row*/
(35234634, 675124412574, 12, NULL, 12, 12, 12),
(58657623, 675124416851, NULL, 8, 20, 20, 18),
(95679635, 675184489153, 10, NULL, 10, 10, 10),
(94725742, 457345045648, 4, 12, 6, 4, 4),
(90570563, 457345897455, 12, 6, 20, 16, 16),
(87313455, 457345184662, NULL, 18, 32, NULL, 10),
(85468354, 457345045412, NULL, NULL, NULL, NULL, NULL);

/*build geographical feature table*/
INSERT INTO GeographicalFeature (AttractionID, GeoFeatureName, Latitude, Longitude, PhoneNumber) VALUES
(954156849165, 'Dead Sea', 31.5590, 35.4732, NULL),
(954784132541, 'Mount Everest', 27.9881, 86.9250, NULL),
(000135254811, 'Bouvet Island', 54.4208, 3.3464, NULL),
(675451325532, 'Mammoth-Flint Ridge Cave System', 37.1870, 86.1005, 2707582180),
(457465123548, 'Pyrenees Mountains', 42.6682, 1.0012, NULL),
(675198616851, 'Niagara Falls', 43.0962, 79.0377, 7162868579),
(468754315864, 'Plitvice Lakes', 44.8654, 15.5820, 38553751015),
(135186454353, 'Victoria Falls', 17.9243, 25.8572, NULL),
(100135848413, 'Great Barrier Reef', 18.2871, 147.6992, NULL),
(675168484133, 'Mount Rushmore', 43.8791, 103.4591, 6055742523);

/*build geographical feature country relationship table*/
INSERT INTO GeographicalFeatureCountry (AttractionID, GeoFeatureName, CountryName) VALUES
(954156849165, 'Dead Sea', 'Israel'),
(954156849165, 'Dead Sea', 'Palestine'),
(954156849165, 'Dead Sea', 'Jordan'),
(954784132541, 'Mount Everest', 'Nepal'),
(000135254811, 'Bouvet Island', 'Norway'),
(675451325532, 'Mammoth-Flint Ridge Cave System', 'United States of America'),
(457465123548, 'Pyrenees Mountains', 'France'),
(457465123548, 'Pyrenees Mountains', 'Spain'),
(675198616851, 'Niagara Falls', 'United States of America'),
(675198616851, 'Niagara Falls', 'Canada'),
(468754315864, 'Plitvice Lakes', 'Croatia'),
(135186454353, 'Victoria Falls', 'Zimbabwe'),
(135186454353, 'Victoria Falls', 'Zambia'),
(100135848413, 'Great Barrier Reef', 'Australia');

/*build restaurant table*/
INSERT INTO Restaurant (AttractionID, RestaurantName, CityName, Latitude, Longitude, PriceRange, PhoneNumber) VALUES
(457309551636, 'Bagettes du Pain', 'Lyon', 45.718681, 45.981465, 1, 3341718584),
(457345198623, 'Cafe du Paris', 'Paris', 48.870894, 2.313541, 1, 3314084576),
(457345098752, 'Cafe du Paris', 'Paris', 48.870894, 2.313541, 1, 3314084576),
(454546151210, 'Meshoune', 'Andorra la Vella', 42.578945, 1.505481, 2, 376877779),
(453670138481, 'Ambrosia Restaurant', 'Luxembourg City', 49.645812, 6.175411, 1, 352482791),
(100253165848, 'EZARD', 'Melbourne', -37.840584, 144.915484, 3, 6139485123657),
(954122048710, 'Peter Cat', 'Kolkata', 22.518456, 88.384156, 2, 9194815662641),
(675124512254, 'Kraken Bar and Grill', 'Seattle', 47.648552, -122.398485, 2, 12534098756),
(675184058001, 'Tiko''s', 'Portland', 45.518165, -122.781653, 2, 15486542189),
(457345478125, 'Cafe du Triomphe', 'Paris', 48.854138, 2.341218, 1, 3314812023);

/*build performance venue table*/
INSERT INTO PerformanceVenue (AttractionID, VenueName, CityName, Latitude, Longitude, PhoneNumber, Website) VALUES
(457309861381, 'Opera National de Lyon', 'Lyon', 45.778451, 45.715123, 3343512125, 'https://www.opera-lyon.com/fr'),
(457345684204, 'AccorHotels Arena', 'Paris', 48.811254, 2.256984, 3312015468, 'https://en.parisinfo.com/paris-museum-monument/71599/_1'),
(457345160868, 'Philharmonie de Paris', 'Paris', 48.881185, 2.398115, 3319565855, 'https://en.parisinfo.com/paris-museum-monument/118357/_1'),
(457345651350, 'Paris Opera', 'Paris', 48.820315, 2.264545, 3314578852, 'https://www.operadeparis.fr/en'),
(454546654321, 'Estadi Comunal d''Andorra la Vella', 'Andorra la Vella', 42.515485, 1.513213, 3767488965, NULL),
(453670987512, 'Philharmonie Luxembourg', 'Luxembourg City', 49.681532, 6.204452, 3523541212, 'https://www.philharmonie.lu/en/programm'),
(100253684651, 'Melbourne Cricket Ground', 'Melbourne', -37.865232, 144.995123, 6131523354812, 'http://www.mcg.org.au/'),
(954122986312, 'GD Birla Sabhaghar', 'Kolkata', 22.521465, 88.303251, 913324615579, 'http://gdbirlasabhagar.com/'),
(675124651351, 'Neumos', 'Seattle', 47.619861, -122.365436, 12532086545, 'https://www.neumos.com/'),
(675184916504, 'The Old Church Concert Hall', 'Portland', 45.589155, -122.765412, 15484598685, 'https://www.theoldchurch.org/');

/*build performance event table*/
INSERT INTO PerformanceEvent (PerformanceID, AttractionID, VenueName, PerformanceGenre, LowestPriceTickets, HighestPriceTickets, StartDate, EndDate, StartTime, EndTime ) VALUES
(102, 457309861381, 'Opera National de Lyon', 'Opera', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(103, 457309861381, 'Opera National de Lyon', 'Opera', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(113, 457345160868, 'Philharmonie de Paris', 'Symphony Orchestra', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(023, 453670987512, 'Philharmonie Luxembourg', 'Ballet', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(124, 453670987512, 'Philharmonie Luxembourg', 'Ballet', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(145, 675124651351, 'Neumos', 'Modern Music', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(186, 675184916504, 'The Old Church Concert Hall', 'Modern Music', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(154, 100253684651, 'Melbourne Cricket Ground', 'Sporting Event', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(131, 954122986312, 'GD Birla Sabhaghar', 'Modern Music', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00'),
(150, 454546654321, 'Estadi Comunal d''Andorra la Vella', 'Stageplay', 108, 556, '2019-08-14', '2019-08-14', '19:00:00', '22:45:00');

/*build airline company table*/
INSERT INTO AirlineCompany (AirlineCompanyName, PhoneNumber, Website) VALUES
('Delta Airlines', 18006542345, 'https://www.delta.com'),
('Emirates', 18886164565, 'https://www.emirates.com/us/english/'),
('Singapore Airlines', 18005422333, 'https://www.singaporeair.com'),
('Lufthansa', 18007665454, 'https://www.Lufthansa.com'),
('Air France', 18006513598, 'https://www.airfrance.com'),
('British Airways', 180078137458, 'https://www.britishairways.com'),
('Korean Air', 18002627891, 'https://www.koreanair.com/us/eng/'),
('Alaska Airlines', 18884613485, 'https://www.alaskaairlines.com'),
('Turkish Airlines', 18007631588, 'https://www.turkishair.com/eng/'),
('Ethiopian Air', 18008441358, 'https://www.ethiopianair.com/english/');

/*build airport table*/
INSERT INTO Airport (AttractionID, AirportCode, AirportName, CityName, Latitude, Longitude, PhoneNumber, IsInternational, Website) VALUES
(457309861502, 'LYS', 'Lyon–Saint-Exupéry Airport', 'Lyon', 45.715612, 45.745612, 3346591298, 1, 'https://www.lyonaeroports.com/en/'),
(457345981350, 'CDG', 'Charles de Gaulle Airport', 'Paris', 48.824646, 2.268468, 33170363950, 1, 'https://www.parisaeroport.fr/en/homepage'),
(453670852488, 'LUX', 'Luxembourg Airport', 'Luxembourg City', 49.616815, 6.218695, 352482791, 0, 'https://www.lux-airport.lu/'),
(100253685202, 'MEL', 'Melbourne Airport', 'Melbourne', -37.841066, 144.968949, 61392971600, 1, 'https://www.melbourneairport.com.au/'),
(954122055410, 'CCU', 'Netaji Subhas Chandra Bose International Airport', 'Kolkata', 22.598745, 88.316554, 913325118036, 1, 'https://www.calcuttaairport.com/'),
(675124505065, 'SEA', 'Seattle–Tacoma International Airport', 'Seattle', 47.698515, -122.332151, 12534894125, 1, 'https://www.portseattle.org/sea-tac'),
(675184840254, 'PDX', 'Portland International Airport', 'Portland', 45.584651, -122.787484, 15482174568, 1, 'https://www.flypdx.com/'),
(412612496888, 'LHR', 'London Heathrow Airport', 'London', 51.4706,-0.461941, 448443351980, 1, 'https://www.heathrow.com/'),
(687568665424, 'YBB', 'Kugaaruk Airport', 'Pelly Bay', 68.534401,-89.808098, NULL, 0, NULL),
(429984651323, 'ZMB', 'Hamburg Hbf', 'Hamburg', 53.552776,10.006683, 494050750, 1, 'https://www.hamburg-airport.de/en/');

/*build flight table*/
INSERT INTO Flight (RouteID, FlightID, AirlineCompanyName) VALUES
(105874, 180235, 'Delta Airlines'),
(198616, 894654, 'Air France'),
(796354, 469135, 'Air France'),
(385138, 165186, 'Air France'),
(168186, 498616, 'Lufthansa'),
(681654, 264884, 'Emirates'),
(354861, 356548, 'Emirates'),
(965153, 946565, 'British Airways'),
(354151, 564656, 'British Airways'),
(254664, 269599, 'Singapore Airlines');

/*build airport flight relationship table*/
INSERT INTO AirportFlightRelationship (FlightSegmentID, RouteID, FlightID, DepartureAirport, ArrivalAirport, DateOfArrival, TimeOfArrival, DateOfDeparture, TimeOfDeparture, Gate) VALUES
(01, 105874, 180235, 'SEA', 'PDX', '2019-08-19', '11:30:00', '2019-08-19', '13:45:00', 'A54'),
(02, 105874, 180235, 'PDX', 'SEA', '2019-08-19', '14:30:00', '2019-08-19', '16:10:00', 'G8'),
(01, 198616, 894654, 'CDG', 'LYS', '2019-08-17', '13:00:00', '2019-08-14', '15:00:00', 'H001'),
(02, 198616, 894654, 'LYS', 'CDG', '2019-08-17', '19:00:00', '2019-08-14', '21:10:00', 'TB03'),
(03, 198616, 894654, 'CDG', 'LHR', '2019-08-18', '01:00:00', '2019-08-14', '05:00:00', 'B1'),
(01, 796354, 469135, 'CDG', 'LYS', '2019-08-19', '12:40:00', '2019-08-14', '14:45:00', 'H001'),
(01, 168186, 498616, 'LHR', 'SEA', '2018-05-30', '06:30:00', '2018-06-01', '15:00:00', 'H34'),
(01, 965153, 946565, 'CDG', 'CCU', '2019-01-12', '10:50:00', '2019-01-12', '16:10:00', 'T61'),
(02, 965153, 946565, 'CCU', 'CDG', '2019-01-13', '04:35:00', '2019-01-13', '10:55:00', 'TB01'),
(01, 254664, 269599, 'CCU', 'ZMB', '2020-01-01', '17:00:00', '2020-01-01', '22:35:00', 'HBZ');

/*build attraction schedule table*/
INSERT INTO AttractionSchedule (ScheduleID, AttractionID, MondayOpenTime, MondayCloseTime, TuesdayOpenTime, TuesdayCloseTime, 
            WednesdayOpenTime, WednesdayCloseTime, ThursdayOpenTime, ThursdayCloseTime, FridayOpenTime, FridayCloseTime, 
			SaturdayOpenTime, SaturdayCloseTime, SundayOpenTime, SundayCloseTime) VALUES
(46841685, 100253145645, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL),
(16854864, 457309861381, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL, NULL, NULL),
(94986481, 457309248152, NULL, NULL, NULL, NULL, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL, '08:00:00', '18:30:00'),
(16518186, 457345035815, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL),
(65466186, 457345185144, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL),
(16385181, 453670151699,  NULL, NULL, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL),
(18716851, 100253145644,  NULL, NULL, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL),
(65468416, 100253145646, '08:00:00', '18:30:00', NULL, NULL, NULL, NULL, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL),
(18168877, 954122986513, '08:00:00',  NULL, NULL, NULL, NULL, '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL),
(35468488, 675124416851, '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', '08:00:00', '18:30:00', NULL, NULL, NULL, NULL); /*MUSEUMS OPEN ON SUNDAY WOULD BE GOOD QUERY*/

