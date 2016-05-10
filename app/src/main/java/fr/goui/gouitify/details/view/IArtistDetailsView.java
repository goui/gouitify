package fr.goui.gouitify.details.view;

import java.util.List;

import fr.goui.gouitify.IView;
import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.model.Artist;

/**
 *
 */
public interface IArtistDetailsView extends IView {

    void showProgressBar();

    void showArtistDetails(Artist artist);

    void showArtistAlbums(List<Album> albums);
}
