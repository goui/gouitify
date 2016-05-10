package fr.goui.gouitify.search.presenter;

import fr.goui.gouitify.IPresenter;
import fr.goui.gouitify.search.view.ISearchView;

/**
 *
 */
public interface ISearchPresenter extends IPresenter<ISearchView> {

    void setSearchType(int searchType);

    void setSearchString(String searchString);
}
