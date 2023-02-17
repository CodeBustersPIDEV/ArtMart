package com.artmart.interfaces;

import com.artmart.models.Artist;

public interface IArtistDao {

    int createAccountAr(Artist artist);

    Artist getArtist(int artist_id);

    boolean deleteAccountAr(int artist_d);

    boolean updateAccountAr(int atist_id, Artist artist);
}
