package fr.goui.gouitify.network;

import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.model.AlbumsContainer;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.model.PagingObject;
import fr.goui.gouitify.model.Track;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */
public interface NetworkService {

    String BASE_URL = "https://api.spotify.com/v1/";

    @GET("search?type=" + Track.TYPE_STRING)
    Observable<PagingObject> searchTrack(@Query("q") String search);

    @GET("search?type=" + Artist.TYPE_STRING)
    Observable<PagingObject> searchArtist(@Query("q") String search);

    @GET("search?type=" + Album.TYPE_STRING)
    Observable<PagingObject> searchAlbum(@Query("q") String search);

    @GET("tracks/{id}")
    Observable<Track> getTrack(@Path("id") String id);

    @GET("artists/{id}")
    Observable<Artist> getArtist(@Path("id") String id);

    @GET("albums/{id}")
    Observable<Album> getAlbum(@Path("id") String id);

    @GET("artists/{id}/albums")
    Observable<AlbumsContainer> getArtistAlbums(@Path("id") String id);

    class Factory {
        public static NetworkService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(NetworkService.class);
        }
    }
}
