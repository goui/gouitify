package fr.goui.gouitify.details.presenter;

import fr.goui.gouitify.IPresenter;
import fr.goui.gouitify.details.view.ITrackDetailsView;

/**
 *
 */
public interface ITrackDetailsPresenter extends IPresenter<ITrackDetailsView> {

    void loadDetails(String id);
}
