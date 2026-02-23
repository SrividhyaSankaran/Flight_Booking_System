\connect flight_db;

INSERT INTO airports (code, name, city, country)
VALUES
('MAA', 'Chennai International Airport', 'Chennai', 'India'),
('DEL', 'Indira Gandhi International Airport', 'Delhi', 'India'),
('BLR', 'Kempegowda International Airport', 'Bangalore', 'India'),
('DXB', 'Dubai International Airport', 'Dubai', 'UAE'),
('SIN', 'Changi Airport', 'Singapore', 'Singapore')
ON CONFLICT (code) DO NOTHING;

INSERT INTO airlines (code, name)
VALUES
('AI', 'Air India'),
('6E', 'IndiGo'),
('EK', 'Emirates'),
('SQ', 'Singapore Airlines')
ON CONFLICT (code) DO NOTHING;

-- ROUTES

-- MAA → DEL
INSERT INTO routes (source_airport_id, destination_airport_id)
SELECT s.id, d.id
FROM airports s, airports d
WHERE s.code = 'MAA' AND d.code = 'DEL'
ON CONFLICT DO NOTHING;

-- DEL → DXB
INSERT INTO routes (source_airport_id, destination_airport_id)
SELECT s.id, d.id
FROM airports s, airports d
WHERE s.code = 'DEL' AND d.code = 'DXB'
ON CONFLICT DO NOTHING;

-- BLR → SIN
INSERT INTO routes (source_airport_id, destination_airport_id)
SELECT s.id, d.id
FROM airports s, airports d
WHERE s.code = 'BLR' AND d.code = 'SIN'
ON CONFLICT DO NOTHING;

-- FLIGHTS (Scheduled Instances)

-- Air India MAA → DEL
INSERT INTO flights (
    airline_id,
    route_id,
    flight_number,
    departure_time,
    arrival_time,
    base_price,
    available
)
SELECT a.id, r.id, 'AI101',
       NOW() + INTERVAL '1 day',
       NOW() + INTERVAL '1 day 2 hours',
       4500.00,
       TRUE
FROM airlines a, routes r, airports s, airports d
WHERE a.code = 'AI'
  AND r.source_airport_id = s.id
  AND r.destination_airport_id = d.id
  AND s.code = 'MAA'
  AND d.code = 'DEL'
ON CONFLICT DO NOTHING;

-- IndiGo MAA → DEL
INSERT INTO flights (
    airline_id,
    route_id,
    flight_number,
    departure_time,
    arrival_time,
    base_price,
    available
)
SELECT a.id, r.id, '6E202',
       NOW() + INTERVAL '1 day',
       NOW() + INTERVAL '1 day 2 hours 15 minutes',
       3800.00,
       TRUE
FROM airlines a, routes r, airports s, airports d
WHERE a.code = '6E'
  AND r.source_airport_id = s.id
  AND r.destination_airport_id = d.id
  AND s.code = 'MAA'
  AND d.code = 'DEL'
ON CONFLICT DO NOTHING;

-- Emirates DEL → DXB
INSERT INTO flights (
    airline_id,
    route_id,
    flight_number,
    departure_time,
    arrival_time,
    base_price,
    available
)
SELECT a.id, r.id, 'EK501',
       NOW() + INTERVAL '2 days',
       NOW() + INTERVAL '2 days 3 hours',
       18000.00,
       TRUE
FROM airlines a, routes r, airports s, airports d
WHERE a.code = 'EK'
  AND r.source_airport_id = s.id
  AND r.destination_airport_id = d.id
  AND s.code = 'DEL'
  AND d.code = 'DXB'
ON CONFLICT DO NOTHING;

-- Singapore Airlines BLR → SIN
INSERT INTO flights (
    airline_id,
    route_id,
    flight_number,
    departure_time,
    arrival_time,
    base_price,
    available
)
SELECT a.id, r.id, 'SQ403',
       NOW() + INTERVAL '3 days',
       NOW() + INTERVAL '3 days 4 hours',
       22000.00,
       TRUE
FROM airlines a, routes r, airports s, airports d
WHERE a.code = 'SQ'
  AND r.source_airport_id = s.id
  AND r.destination_airport_id = d.id
  AND s.code = 'BLR'
  AND d.code = 'SIN'
ON CONFLICT DO NOTHING;
