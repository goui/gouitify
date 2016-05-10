package fr.goui.gouitify.details.view;

import fr.goui.gouitify.IView;
import fr.goui.gouitify.model.Track;

/**
 *
 */
public interface ITrackDetailsView extends IView {

    void showProgressBar();

    void showTrackDetails(Track track);
}
