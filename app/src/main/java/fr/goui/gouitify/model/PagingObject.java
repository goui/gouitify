package fr.goui.gouitify.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class PagingObject {

    @SerializedName("artists")
    private ArtistsContainer artistsContainer;

    @SerializedName("albums")
    private AlbumsContainer albumsContainer;

    @SerializedName("tracks")
    private TracksContainer tracksContainer;

    public ArtistsContainer getArtistsContainer() {
        return artistsContainer;
    }

    public void setArtistsContainer(ArtistsContainer artistsContainer) {
        this.artistsContainer = artistsContainer;
    }

    public AlbumsContainer getAlbumsContainer() {
        return albumsContainer;
    }

    public void setAlbumsContainer(AlbumsContainer albumsContainer) {
        this.albumsContainer = albumsContainer;
    }

    public TracksContainer getTracksContainer() {
        return tracksContainer;
    }

    public void setTracksContainer(TracksContainer tracksContainer) {
        this.tracksContainer = tracksContainer;
    }
}
