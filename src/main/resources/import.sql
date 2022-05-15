-- #############################################################
-- ####################     CATEGORIES      ####################
-- #############################################################
INSERT INTO category(name, description) VALUES('Romantic', 'You can`t get away from love!');

INSERT INTO category(name, description) VALUES('Leisure', 'Let`s get fun!');

INSERT INTO category(name, description) VALUES('Activity', 'Are you brave enough?');

INSERT INTO category(name, description) VALUES('Breakaway', 'Need a break? That`s for you');

INSERT INTO category(name, description) VALUES('Culture', 'The best theater, cinema and much more events');

-- #############################################################
-- ######################      SHOWS      ######################
-- #############################################################
INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status) VALUES('Candlelight','Super amazing', 'candlelight-image-url', 20, 1, 'HOURS', 50, 1653085664813, '0');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status) VALUES('Scape room','Don`t let pass this stunning promo, it`s FREE!', 'scape-room-image-url', 0, 90, 'MINUTES', 15, 1653085664813, '0');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status) VALUES('Ferrari Drive','Awesome experience driving a Ferrari', 'drive-exp-image-url', 50, 30, 'MINUTES', 10, 1653085664813, '1');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status) VALUES('Hotel Night','Break with everything wherever you prefer!', 'hotel-night-image-url', 150, 1, 'DAYS', 50, 1653085664813, '1');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status) VALUES('Spa','Get relaxed!', 'spa-exp-image-url', 25, 120, 'MINUTES', 150, 1653085664813, '1');

-- #############################################################
-- ##################     SHOW_CATEGORIES     ##################
-- #############################################################

-- Candlelight + Culture
INSERT INTO show_categories(show_id, category_id) VALUES(1, 5);
-- Candlelight + Leisure
INSERT INTO show_categories(show_id, category_id) VALUES(1, 2);
-- Candlelight + Romantic
INSERT INTO show_categories(show_id, category_id) VALUES(1, 1);
-- Scape room + Culture
INSERT INTO show_categories(show_id, category_id) VALUES(2, 5);
-- Scape room + Leisure
INSERT INTO show_categories(show_id, category_id) VALUES(2, 2);
-- Ferrari Drive + Activity
INSERT INTO show_categories(show_id, category_id) VALUES(3, 3);
-- Ferrari Drive + Breakaway
INSERT INTO show_categories(show_id, category_id) VALUES(3, 4);
-- Hotel Night + Romantic
INSERT INTO show_categories(show_id, category_id) VALUES(4, 1);
-- Hotel Night + Breakaway
INSERT INTO show_categories(show_id, category_id) VALUES(4, 4);
-- Spa + Breakaway
INSERT INTO show_categories(show_id, category_id) VALUES(5, 4);

-- #############################################################
-- ####################     PERFORMANCE     ####################
-- #############################################################

-- show=Candlelight
INSERT INTO show_performances(date, time, streaming_url, remaining_seats, show_id) VALUES('2022-05-20','15:00', 'url', 20, 1);

-- show=Scape room
INSERT INTO show_performances(date, time, streaming_url, remaining_seats, show_id) VALUES('2022-05-20','15:00', 'url', 20, 2);

-- show=Ferrari Drive
INSERT INTO show_performances(date, time, streaming_url, remaining_seats, show_id) VALUES('2022-05-20','15:00', 'url', 20, 3);

-- show=Hotel Night
INSERT INTO show_performances(date, time, streaming_url, remaining_seats, show_id) VALUES('2022-05-20','15:00', 'url', 20, 4);

-- show=Spa
INSERT INTO show_performances(date, time, streaming_url, remaining_seats, show_id) VALUES('2022-05-20','15:00', 'url', 20, 5);