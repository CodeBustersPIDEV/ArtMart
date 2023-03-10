CREATE TABLE `user` (
    user_ID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    picture VARCHAR(255),
    blocked boolean,
    enabled boolean,
    token VARCHAR(255) DEFAULT NULL,
    dateOfCreation DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `artist` (
    artist_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    nbr_artwork INT NOT NULL,
    bio VARCHAR(255),
    FOREIGN KEY (user_ID) REFERENCES User(user_ID)
);

CREATE TABLE `admin` (
    admin_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    department VARCHAR(255),
    FOREIGN KEY (user_ID) REFERENCES User(user_ID)
);

CREATE TABLE `client` (
    client_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    nbr_orders INT NOT NULL,
    nbr_demands INT NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES User(user_ID)
);

-- Blog 

CREATE TABLE `blogs` (
  blogs_ID INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  rating FLOAT DEFAULT NULL,
  nb_views INT NOT NULL DEFAULT 0,
  author INT NOT NULL,
  FOREIGN KEY (author) REFERENCES user(user_ID)
);

CREATE TABLE `comments` (
  comments_ID INT AUTO_INCREMENT PRIMARY KEY,
  content TEXT NOT NULL,
  date DATETIME DEFAULT CURRENT_TIMESTAMP,
  rating INT DEFAULT NULL,
  author INT NOT NULL,
  blog_ID INT NOT NULL,
  FOREIGN KEY (author) REFERENCES user(user_ID),
  FOREIGN KEY (blog_ID) REFERENCES blogs(blogs_ID)
);

CREATE TABLE `blogcategories` (
  categories_ID INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE `tags` (
  tags_ID INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE `has_blog_category` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  blog_id INT NOT NULL,
  category_id INT NOT NULL,
  FOREIGN KEY (blog_id) REFERENCES blogs(blogs_ID),
  FOREIGN KEY (category_id) REFERENCES blogcategories(categories_ID)
);

CREATE TABLE `blog_tags` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  blog_id INT NOT NULL,
  tag_id INT NOT NULL,
  FOREIGN KEY (blog_id) REFERENCES blogs(blogs_ID),
  FOREIGN KEY (tag_id) REFERENCES tags(tags_ID)
);

CREATE TABLE `media` (
  media_ID INT AUTO_INCREMENT PRIMARY KEY,
  blog_id INT NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  file_type VARCHAR(255) NOT NULL,
  file_path VARCHAR(255) NOT NULL,
  FOREIGN KEY (blog_id) REFERENCES blogs(blogs_ID)
);

-- Product 

CREATE TABLE `categories` (
    categories_ID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE `product` (
    product_ID INT AUTO_INCREMENT PRIMARY KEY,
    category_ID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    dimensions VARCHAR(255) NOT NULL,
    weight DECIMAL(10,2) NOT NULL,
    material VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_ID) REFERENCES Categories(categories_ID)
);

CREATE TABLE `readyProduct` (
    ready_product_ID INT AUTO_INCREMENT PRIMARY KEY,
    price INT NOT NULL,
    product_ID INT NOT NULL,
    user_ID INT NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES user(user_ID),
    FOREIGN KEY (product_ID) REFERENCES Product(product_ID)
);

CREATE TABLE `productReview` (
    review_ID INT AUTO_INCREMENT PRIMARY KEY,
    ready_product_ID INT NOT NULL,
    user_ID INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    rating INT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_ID) REFERENCES user(user_ID),
    FOREIGN KEY (ready_product_ID) REFERENCES ReadyProduct(ready_product_ID)
);

CREATE TABLE `customProduct` (
    custom_product_ID INT AUTO_INCREMENT PRIMARY KEY,
    product_ID INT NOT NULL,
    client_ID INT NOT NULL,
    FOREIGN KEY (product_ID) REFERENCES Product(product_ID),
    FOREIGN KEY (client_ID) REFERENCES user(user_ID)
);

CREATE TABLE `apply` (
    apply_ID INT AUTO_INCREMENT PRIMARY KEY,
    customproduct_ID INT NOT NULL,
    artist_ID INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (customproduct_ID) REFERENCES customProduct(custom_product_ID),
    FOREIGN KEY (artist_ID) REFERENCES user(user_ID)
);

-- Orderom
CREATE TABLE `shippingOption` (
    shippingOption_ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255),
    Carrier VARCHAR(255),
    ShippingSpeed VARCHAR(255),
    ShippingFee DECIMAL(10,2),
    AvailableRegions VARCHAR(255)
);

CREATE TABLE `paymentOption` (
    paymentOption_ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255),
    AvailableCountries VARCHAR(255)
);

CREATE TABLE `order` (
    order_ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    ProductID INT,
    Quantity INT,
    ShippingMethod INT,
    ShippingAddress TEXT,
    PaymentMethod INT,
    OrderDate DATE DEFAULT CURRENT_TIMESTAMP,
    TotalCost DECIMAL(10,2),
    FOREIGN KEY (UserID) REFERENCES `user`(user_ID),
    FOREIGN KEY (ShippingMethod) REFERENCES `shippingOption`(shippingOption_ID),
    FOREIGN KEY (PaymentMethod) REFERENCES `paymentOption`(paymentOption_ID),
    FOREIGN KEY (ProductID) REFERENCES `product`(product_ID)
);

CREATE TABLE `orderStatus` (
    orderStatus_ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    Status VARCHAR(255),
    Date DATE,
    FOREIGN KEY (OrderID) REFERENCES `Order`(order_ID)
);

CREATE TABLE `orderUpdate` (
    orderUpdate_ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    UpdateMessage TEXT,
    Date DATE,
    FOREIGN KEY (OrderID) REFERENCES `Order`(order_ID)
);

CREATE TABLE `receipt` (
    receipt_ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    ProductID INT,
    Quantity INT,
    Price DECIMAL(10,2),
    Tax DECIMAL(10,2),
    TotalCost DECIMAL(10,2),
    Date DATE,
    FOREIGN KEY (OrderID) REFERENCES `Order`(order_ID),
    FOREIGN KEY (ProductID) REFERENCES Product(product_ID)
);

CREATE TABLE `wishlist` (
    wishlist_ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    ProductID INT,
    Date DATE,
    Quantity INT,
    Price FLOAT,
    FOREIGN KEY (UserID) REFERENCES `user`(user_ID),
    FOREIGN KEY (ProductID) REFERENCES Product(product_ID)
);

CREATE TABLE `orderRefund` (
    orderRefund_ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    RefundAmount DECIMAL(10,2),
    Reason TEXT,
    Date DATE,
    FOREIGN KEY (OrderID) REFERENCES `Order`(order_ID)
);

CREATE TABLE `salesReport` (
    salesReport_ID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT,
    TotalSales DECIMAL(10,2),
    AverageSalesPerDay DECIMAL(10,2),
    Date DATE,
    FOREIGN KEY (ProductID) REFERENCES Product(product_ID)
);

-- Event
CREATE TABLE `event` (
    eventID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    entryFee float NOT NULL,
    capacity INT NOT NULL,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    image VARCHAR(255) NULL,
    status VARCHAR(255) DEFAULT 'Scheduled',
    FOREIGN KEY (userID) REFERENCES user(user_ID)
);

CREATE TABLE `activity` (
    activityID INT AUTO_INCREMENT PRIMARY KEY,
    eventID INT NOT NULL,
    date DATETIME NOT NULL,
    title VARCHAR(255) NOT NULL,
    host VARCHAR(255) NOT NULL,
    FOREIGN KEY (eventID) REFERENCES event(eventID)
);

CREATE TABLE eventReport (
    reportID INT AUTO_INCREMENT PRIMARY KEY,
    eventID INT NOT NULL,
    attendance INT NOT NULL,
    FOREIGN KEY (eventID) REFERENCES event(eventID)
);

CREATE TABLE feedback (
    feedbackID INT AUTO_INCREMENT PRIMARY KEY,
    eventID INT NOT NULL,
    userID INT NOT NULL,    
    rating INT NOT NULL,
    comment TEXT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (eventID) REFERENCES event(eventID),
    FOREIGN KEY (userID) REFERENCES user(user_ID)
);

CREATE TABLE `participation` (
    participationID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL,
    eventID INT NOT NULL,
    attendanceStatus VARCHAR(255) DEFAULT 'Not attending',
    registrationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (eventID, userID),
    FOREIGN KEY (eventID) REFERENCES event(eventID),
    FOREIGN KEY (userID) REFERENCES user(user_ID)
);