/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.Artist;

/**
 *
 * @author 21697
 */
public interface IArtistDao {
    int createAccountAr(Artist artist);
      Artist getArtist(int artist_id);
    boolean deleteAccountAr(int artist_d);
    boolean updateAccountAr(int atist_id, Artist artist);
}
