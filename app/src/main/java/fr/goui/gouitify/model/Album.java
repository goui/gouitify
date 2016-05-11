package fr.goui.gouitify.model;

import java.util.List;

/**
 *
 */
public class Album {

    public transient static final String TYPE_STRING = "album";

    public transient static final int TYPE_INT = 2;

    private List<Artist> artists;

    private List<String> genres;

    private String id;

    private List<Image> images;

    private String name;

    private int popularity;

    private TracksContainer tracks;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

    public TracksContainer getTrackContainer() {
        return tracks;
    }

    public void setTracks(TracksContainer tracks) {
        this.tracks = tracks;
    }
}
