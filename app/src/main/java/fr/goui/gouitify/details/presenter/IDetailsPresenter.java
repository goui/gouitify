package fr.goui.gouitify.details.presenter;

import fr.goui.gouitify.IPresenter;

/**
 *
 */
public interface IDetailsPresenter<T> extends IPresenter<T> {

    void loadDetails(String id);
}
