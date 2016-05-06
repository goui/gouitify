package fr.goui.gouitify;

/**
 *
 */
public interface IPresenter<T> {

    void attachView(T view);

    void detachView();
}
