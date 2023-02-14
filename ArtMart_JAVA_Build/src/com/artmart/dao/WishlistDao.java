package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.Wishlist;
import java.sql.SQLException;
import java.util.List;

public class WishlistDao implements IWishlistDao{
    
    private Connection connection;

    public WishlistDao() {
    try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createWishlist(Wishlist wishlist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Wishlist getWishlist(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Wishlist> getWishlists() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateWishlist(Wishlist wishlist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteWishlist(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
