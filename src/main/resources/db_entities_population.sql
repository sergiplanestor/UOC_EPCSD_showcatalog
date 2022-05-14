-- CATEGORIES

INSERT INTO category(name, description) VALUES('Romantic', 'You can`t get away from love!');
INSERT INTO category(name, description) VALUES('Leisure', 'Let`s get fun!');
INSERT INTO category(name, description) VALUES('Activity', 'Are you brave enough?');
INSERT INTO category(name, description) VALUES('Breakaway', 'Need a break? That`s for you');
INSERT INTO category(name, description) VALUES('Culture', 'The best theater, cinema and much more events');

-- SHOWS

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status)
VALUES('Candlelight','Super amazing', 'candlelight-image-url', 20, 1, 'HOURS', 50, 1653085664813, '0');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status)
VALUES('Scape room','Don`t let pass this stunning promo, it`s FREE!', 'scape-room-image-url', 0, 90, 'MINUTES', 15, 1653085664813, '0');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status)
VALUES('Ferrari Drive','Awesome experience driving a Ferrari', 'drive-exp-image-url', 50, 30, 'MINUTES', 10, 1653085664813, '1');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status)
VALUES('Hotel Night','Break with everything wherever you prefer!', 'hotel-night-image-url', 150, 1, 'DAYS', 50, 1653085664813, '1');

INSERT INTO show(name, description, image_url, price, duration_value, duration_unit, capacity, start_sale_date, status)
VALUES('Spa','Get relaxed!', 'spa-exp-image-url', 25, 120, 'MINUTES', 150, 1653085664813, '1');

-- CATEGORIES_SHOW

INSERT INTO show_categories(show_id, category_id) VALUES(11, 28); -- Candlelight + Culture
INSERT INTO show_categories(show_id, category_id) VALUES(11, 25); -- Candlelight + Leisure
INSERT INTO show_categories(show_id, category_id) VALUES(11, 24); -- Candlelight + Romantic
INSERT INTO show_categories(show_id, category_id) VALUES(12, 28); -- Scape room + Culture
INSERT INTO show_categories(show_id, category_id) VALUES(12, 25); -- Scape room + Leisure
INSERT INTO show_categories(show_id, category_id) VALUES(13, 26); -- Ferrari Drive + Activity
INSERT INTO show_categories(show_id, category_id) VALUES(13, 27); -- Ferrari Drive + Breakaway
INSERT INTO show_categories(show_id, category_id) VALUES(14, 24); -- Hotel Night + Romantic
INSERT INTO show_categories(show_id, category_id) VALUES(14, 27); -- Hotel Night + Breakaway
INSERT INTO show_categories(show_id, category_id) VALUES(15, 27); -- Spa + Breakaway

-- PERF

INSERT INTO performance(date, time, streaming_url, remaining_seats, status, show_id)
VALUES('2022-05-20','15:00', 'url', 20, 1, 11);

INSERT INTO performance(date, time, streaming_url, remaining_seats, status, show_id)
VALUES('2022-05-20','15:00', 'url', 20, 1, 12);

INSERT INTO performance(date, time, streaming_url, remaining_seats, status, show_id)
VALUES('2022-05-20','15:00', 'url', 20, 1, 13);

INSERT INTO performance(date, time, streaming_url, remaining_seats, status, show_id)
VALUES('2022-05-20','15:00', 'url', 20, 1, 14);

INSERT INTO performance(date, time, streaming_url, remaining_seats, status, show_id)
VALUES('2022-05-20','15:00', 'url', 20, 1, 15);