package fr.goui.gouitify.details.view;

import fr.goui.gouitify.IView;
import fr.goui.gouitify.model.Album;

/**
 *
 */
public interface IAlbumDetailsView extends IView {

    void showProgressBar();

    void showAlbumDetails(Album album);
}
