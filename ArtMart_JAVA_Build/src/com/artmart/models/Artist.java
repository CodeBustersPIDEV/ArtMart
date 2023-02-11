/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.models;

import java.util.ArrayList;

/**
 *
 * @author 21697
 */
public class Artist extends User {
    private int artist_id,nbr_artwork;
    private ArrayList<String> portfolio;

    public Artist() {
    }

    public Artist(int artist_id, int nbr_artwork, ArrayList<String> portfolio) {
        this.artist_id = artist_id;
        this.nbr_artwork = nbr_artwork;
        this.portfolio = portfolio;
    }

    public Artist(int nbr_artwork, ArrayList<String> portfolio) {
        this.nbr_artwork = nbr_artwork;
        this.portfolio = portfolio;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public int getNbr_artwork() {
        return nbr_artwork;
    }

    public ArrayList<String> getPortfolio() {
        return portfolio;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public void setNbr_artwork(int nbr_artwork) {
        this.nbr_artwork = nbr_artwork;
    }

    public void setPortfolio(ArrayList<String> portfolio) {
        this.portfolio = portfolio;
    }
    
}
