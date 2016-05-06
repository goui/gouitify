package fr.goui.gouitify.search.presenter;

import java.util.ArrayList;
import java.util.List;

import fr.goui.gouitify.IPresenter;
import fr.goui.gouitify.MyApplication;
import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.model.PagingObject;
import fr.goui.gouitify.model.Track;
import fr.goui.gouitify.network.NetworkService;
import fr.goui.gouitify.search.view.ISearchView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class SearchPresenter implements IPresenter<ISearchView> {

    private ISearchView mSearchView;

    private int mSearchType = Track.TYPE_INT;

    private String mSearchText;

    private Subscription mSubscription;

    private List<Track> mListOfTracks;

    private List<Artist> mListOfArtists;

    private List<Album> mListOfAlbums;

    @Override
    public void attachView(ISearchView view) {
        mSearchView = view;
    }

    @Override
    public void detachView() {
        mSearchView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void setType(int type) {
        mSearchType = type;
        if (mSearchText != null && mSearchText.length() > 2) {
            search(mSearchText);
        }
    }

    public void search(String searchString) {
        mSearchText = searchString;
        mSearchView.showProgressBar();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        MyApplication application = MyApplication.get(mSearchView.getContext());
        NetworkService service = application.getNetworkService();
        switch (mSearchType) {
            case Track.TYPE_INT:
                searchTrack(service, searchString);
                break;
            case Artist.TYPE_INT:
                searchArtist(service, searchString);
                break;
            case Album.TYPE_INT:
                searchAlbum(service, searchString);
                break;
        }

    }

    private void searchTrack(NetworkService service, String searchString) {
        mSubscription = service.searchTrack(searchString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PagingObject>() {
                    @Override
                    public void onCompleted() {
                        mSearchView.showTracks(mListOfTracks);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSearchView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PagingObject pagingObject) {
                        mListOfTracks = pagingObject.getTracksContainer().getTracks();
                    }
                });
    }

    private void searchArtist(NetworkService service, String searchString) {
        mSubscription = service.searchArtist(searchString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PagingObject>() {
                    @Override
                    public void onCompleted() {
                        mSearchView.showArtists(mListOfArtists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSearchView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PagingObject pagingObject) {
                        mListOfArtists = pagingObject.getArtistsContainer().getArtists();
                    }
                });
    }

    private void searchAlbum(NetworkService service, String searchString) {
        mSubscription = service.searchAlbum(searchString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PagingObject>() {
                    @Override
                    public void onCompleted() {
                        mSearchView.showAlbums(mListOfAlbums);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSearchView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PagingObject pagingObject) {
                        mListOfAlbums = pagingObject.getAlbumsContainer().getAlbums();
                    }
                });
    }
}
