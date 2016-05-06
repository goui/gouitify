package fr.goui.gouitify.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 */
public class PagingObject {

    @SerializedName("artists")
    private ArtistContainer artistsContainer;

    @SerializedName("albums")
    private AlbumsContainer albumsContainer;

    @SerializedName("tracks")
    private TracksContainer tracksContainer;

    public ArtistContainer getArtistsContainer() {
        return artistsContainer;
    }

    public void setArtistsContainer(ArtistContainer artistsContainer) {
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

    public class ArtistContainer {

        @SerializedName("items")
        private List<Artist> artists;

        public List<Artist> getArtists() {
            return artists;
        }

        public void setArtists(List<Artist> artists) {
            this.artists = artists;
        }
    }

    public class AlbumsContainer {

        @SerializedName("items")
        private List<Album> albums;

        public List<Album> getAlbums() {
            return albums;
        }

        public void setAlbums(List<Album> albums) {
            this.albums = albums;
        }
    }

    public class TracksContainer {

        @SerializedName("items")
        private List<Track> tracks;

        public List<Track> getTracks() {
            return tracks;
        }

        public void setTracks(List<Track> tracks) {
            this.tracks = tracks;
        }
    }
}
