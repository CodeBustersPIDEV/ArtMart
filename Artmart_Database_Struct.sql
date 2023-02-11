-- user 

CREATE TABLE `User` (
    user_ID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    picture BLOB,
    dateOfCreation DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Artist` (
    artist_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    nbr_artwork INT NOT NULL,
    portfolio BLOB,
    qr_code BLOB,
    FOREIGN KEY (user_ID) REFERENCES User(user_ID)
);

CREATE TABLE `Admin` (
    admin_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES User(user_ID)
);

CREATE TABLE `Client` (
    client_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    nbr_orders INT NOT NULL,
    nbm_demands INT NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES User(user_ID)
);

CREATE TABLE `Premium` (
    sub_ID INT AUTO_INCREMENT PRIMARY KEY,
    start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_ID INT NOT NULL,
    duration INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES User(user_ID)
);

-- Blog 

CREATE TABLE `Blogs` (
  blogs_ID INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  date DATETIME NOT NULL,
  author INT NOT NULL,
  FOREIGN KEY (author) REFERENCES user(user_ID)
);

CREATE TABLE `Comments` (
  comments_ID INT AUTO_INCREMENT PRIMARY KEY,
  content TEXT NOT NULL,
  date DATETIME NOT NULL,
  author INT NOT NULL,
  blog_ID INT NOT NULL,
  FOREIGN KEY (author) REFERENCES user(user_ID),
  FOREIGN KEY (blog_ID) REFERENCES blogs(blogs_ID)
);

CREATE TABLE `Categories` (
  categories_ID INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE `Tags` (
  tags_ID INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE `Media` (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  file_name VARCHAR(255) NOT NULL,
  file_type VARCHAR(255) NOT NULL,
  file_path VARCHAR(255) NOT NULL
);

-- Product 

CREATE TABLE `Product` (
    product_ID INT AUTO_INCREMENT PRIMARY KEY,
    category_ID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    dimensions VARCHAR(255) NOT NULL,
    weight DECIMAL(10,2) NOT NULL,
    material VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL
    FOREIGN KEY (category_ID) REFERENCES Categories(categories_ID)
);

CREATE TABLE `ReadyProduct` (
    ready_product_ID INT AUTO_INCREMENT PRIMARY KEY,
    product_ID INT NOT NULL,
    FOREIGN KEY (product_ID) REFERENCES Product(product_ID)
);

CREATE TABLE `ProductReview` (
    review_ID INT AUTO_INCREMENT PRIMARY KEY,
    ready_product_ID INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    rating INT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ready_product_ID) REFERENCES ReadyProduct(ready_product_ID)
);

CREATE TABLE `CustomProduct` (
    custom_product_ID INT AUTO_INCREMENT PRIMARY KEY,
    product_ID INT NOT NULL,
    FOREIGN KEY (product_ID) REFERENCES Product(product_ID)
);

CREATE TABLE `Chat` (
    chat_ID INT AUTO_INCREMENT PRIMARY KEY,
    custom_product_ID INT NOT NULL,
    artist_ID INT NOT NULL,
    history TEXT NOT NULL,
    FOREIGN KEY (custom_product_ID) REFERENCES CustomProduct(custom_product_ID)
    FOREIGN KEY (artist_ID) REFERENCES Artist(artist_ID)
);

-- Order

CREATE TABLE `Order` (
    order_ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    ProductID INT,
    Quantity INT,
    ShippingAddress VARCHAR(255),
    PaymentMethod VARCHAR(255),
    OrderDate DATE,
    TotalCost DECIMAL(10,2),
    FOREIGN KEY (UserID) REFERENCES User(user_ID),
    FOREIGN KEY (ProductID) REFERENCES Product(product_ID)
);

CREATE TABLE `OrderStatus` (
    orderStatus_ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    Status VARCHAR(255),
    Date DATE,
    FOREIGN KEY (OrderID) REFERENCES `Order`(order_ID)
);

CREATE TABLE `OrderUpdate` (
    orderUpdate_ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    UpdateMessage TEXT,
    Date DATE,
    FOREIGN KEY (OrderID) REFERENCES `Order`(order_ID)
);

CREATE TABLE `Receipt` (
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

CREATE TABLE `Wishlist` (
    wishlist_ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    ProductID INT,
    Date DATE,
    FOREIGN KEY (UserID) REFERENCES User(user_ID),
    FOREIGN KEY (ProductID) REFERENCES Product(product_ID)
);

CREATE TABLE `OrderRefund` (
    orderRefund_ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    RefundAmount DECIMAL(10,2),
    Reason TEXT,
    Date DATE,
    FOREIGN KEY (OrderID) REFERENCES `Order`(order_ID)
);

CREATE TABLE `ShippingOption` (
    shippingOption_ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255),
    Carrier VARCHAR(255),
    ShippingSpeed VARCHAR(255),
    ShippingFee DECIMAL(10,2),
    AvailableRegions VARCHAR(255)
);

CREATE TABLE `PaymentOption` (
    paymentOption_ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255),
    AvailableCountries VARCHAR(255)
);

CREATE TABLE `SalesReport` (
    salesReport_ID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT,
    TotalSales DECIMAL(10,2),
    AverageSalesPerDay DECIMAL(10,2),
    Date DATE,
    FOREIGN KEY (ProductID) REFERENCES Product(product_ID)
);

-- Event

CREATE TABLE `Event` (
    event_ID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    entryFee DECIMAL(10,2) NOT NULL,
    capacity INT NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL
);

CREATE TABLE `Activity` (
    activity_ID INT AUTO_INCREMENT PRIMARY KEY,
    event_ID INT NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    title VARCHAR(255) NOT NULL,
    host VARCHAR(255) NOT NULL,
    FOREIGN KEY (event_ID) REFERENCES Event(event_ID)
);

CREATE TABLE `EventReport` (
    report_ID INT AUTO_INCREMENT PRIMARY KEY,
    event_ID INT NOT NULL,
    attendance INT NOT NULL,
    FOREIGN KEY (event_ID) REFERENCES Event(event_ID)
);

CREATE TABLE `Feedback` (
    feedback_ID INT AUTO_INCREMENT PRIMARY KEY,
    eventReport_ID INT NOT NULL,
    rating INT NOT NULL,
    comment TEXT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (eventReport_ID) REFERENCES EventReport(report_ID)
);
