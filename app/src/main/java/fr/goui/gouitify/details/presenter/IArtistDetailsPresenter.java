package fr.goui.gouitify.details.presenter;

import fr.goui.gouitify.IPresenter;
import fr.goui.gouitify.details.view.IArtistDetailsView;

/**
 *
 */
public interface IArtistDetailsPresenter extends IPresenter<IArtistDetailsView> {

    void loadDetails(String id);
}
