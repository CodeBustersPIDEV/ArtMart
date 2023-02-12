package com.artmart.services;

import com.artmart.interfaces.IProductService;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.connectors.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import com.artmart.models.Product;

public class ProductService implements IProductService {
    private final SQLConnection connection;

    public ProductService() {
        this.connection = SQLConnection.getInstance();
    }

    @Override
    public Product createProduct(Product product) {
        try {
            connection.connect();
            String query = "INSERT INTO product (name, description, price) VALUES (?, ?, ?)";
            Prep
