package fr.goui.gouitify.search.view;

import java.util.List;

import fr.goui.gouitify.IView;
import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.model.Track;

/**
 *
 */
public interface ISearchView extends IView {

    void showProgressBar();

    void showTracks(List<Track> listOfTracks);

    void showArtists(List<Artist> listOfArtists);

    void showAlbums(List<Album> listOfAlbums);
}
