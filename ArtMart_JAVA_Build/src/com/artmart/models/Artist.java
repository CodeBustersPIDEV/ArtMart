package com.artmart.models;

public class Artist extends User {

    private int artist_id, nbr_artwork;
    private String bio;

    public Artist() {
    }

    public Artist(int artist_id, int nbr_artwork, String bio) {
        this.artist_id = artist_id;
        this.nbr_artwork = nbr_artwork;
        this.bio = bio;
    }

    public Artist(User user) {
        super(user.getPhone_nbr(), user.getName(), user.getEmail(), user.getUsername(), user.getPwd(), user.getBirthday(), user.getRole());
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public Artist(int nbr_artwork, String bio) {
        this.nbr_artwork = nbr_artwork;
        this.bio = bio;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public int getNbr_artwork() {
        return nbr_artwork;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public void setNbr_artwork(int nbr_artwork) {
        this.nbr_artwork = nbr_artwork;
    }
}
