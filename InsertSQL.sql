INSERT INTO user(name, email, birthday, phoneNumber, role, username, password) VALUES ('John Doe', 'johndoe@example.com', '1990-01-01', '+1234567890', 'client', 'johndoe', 'password123');

INSERT INTO artist(user_ID, nbr_artwork, bio) VALUES (1, 10, 'I am a passionate artist who loves to explore different art styles.');

INSERT INTO admin(user_ID, department) VALUES (1, 'Human Resources');

INSERT INTO client(user_ID, nbr_orders, nbr_demands) VALUES (1, 0, 0);

INSERT INTO blogs(title, content, author) VALUES ('The Importance of Art Education', 'In this blog post, I discuss the importance of art education and how it can positively impact a child’s life.', 1);

INSERT INTO blogcategories(name) VALUES ('Education');

INSERT INTO has_blog_category(blog_id, category_id) VALUES (1, 1);

INSERT INTO tags(name) VALUES ('art education');

INSERT INTO blog_tags(blog_id, tag_id) VALUES (1, 1);

INSERT INTO media(blog_id, file_name, file_type, file_path) VALUES (1, 'art_education.jpg', 'image/jpeg', '/media/art_education.jpg');

INSERT INTO categories(name) VALUES ('Furniture');

INSERT INTO product(category_ID, name, description, dimensions, weight, material, image) VALUES (1, 'Modern Sofa', 'A comfortable and stylish sofa for any living room.', '72" L x 30" H x 36" D', 75.50, 'Leather', 'sofa.jpg');

INSERT INTO productReview(ready_product_ID, username, title, text, rating) VALUES (1, 'johndoe', 'Great Sofa!', 'This sofa is comfortable and looks great in my living room. Highly recommend!', 5);

INSERT INTO chat(client_ID, custom_product_ID, artist_ID, history) VALUES (1, 1, 1, 'Hi, I am interested in purchasing a custom version of the Modern Sofa.');

INSERT INTO shippingOption(Name, Carrier, ShippingSpeed, ShippingFee, AvailableRegions) VALUES ('Standard Shipping', 'UPS', '5-7 business days', 10.00, 'US');

INSERT INTO paymentOption(Name, AvailableCountries) VALUES ('Credit Card', 'US');

INSERT INTO salesReport (productID, totalSales, averageSalesPerDay, date) 
VALUES (1, 5000.0, 200.0, '2023-02-16');

INSERT INTO event (userID, name, location, type, description, entryFee, capacity, startDate, endDate) 
VALUES (1, 'Spring Festival', 'Central Park', 'Festival', 'Join us for a day of music, food, and fun!', 10.0, 1000, '2023-05-20 10:00:00', '2023-05-20 22:00:00');

INSERT INTO activity (eventID, date, title, host) 
VALUES (1, '2023-03-10 10:00:00', 'Yoga Class', 'John Doe');

INSERT INTO eventReport (eventID, attendance) 
VALUES (1, 50);