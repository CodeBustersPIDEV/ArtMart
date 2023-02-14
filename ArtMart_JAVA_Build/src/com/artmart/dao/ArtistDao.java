package com.artmart.dao;

import com.artmart.interfaces.IArtistDao;
import com.artmart.models.Artist;
import java.sql.Connection;

public class ArtistDao implements IArtistDao{
private Connection connection;

    public ArtistDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Artist createAccountAr(Artist artist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Artist getArtist(int artist_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountAr(int artist_d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateAccountAr(int atist_id, Artist artist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
