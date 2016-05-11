package fr.goui.gouitify.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 */
public class ArtistsContainer {

    @SerializedName("items")
    private List<Artist> artists;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
