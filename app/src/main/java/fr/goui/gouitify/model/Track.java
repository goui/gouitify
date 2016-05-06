package fr.goui.gouitify.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 */
public class Track {

    public transient static final String TYPE_STRING = "track";

    public transient static final int TYPE_INT = 0;

    private Album album;

    private List<Artist> artists;

    @SerializedName("duration_ms")
    private int duration;

    private String id;

    private String name;

    private int popularity;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
