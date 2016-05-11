package fr.goui.gouitify.details.presenter;

import fr.goui.gouitify.MyApplication;
import fr.goui.gouitify.details.view.IArtistDetailsView;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.model.PagingObject;
import fr.goui.gouitify.network.NetworkService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class ArtistDetailsPresenter implements IDetailsPresenter<IArtistDetailsView> {

    private static final String ERROR_EMPTY_ID_STRING = "Error - empty id string";

    private IArtistDetailsView mArtistDetailsView;

    private Subscription mDetailsSubscription;

    private Subscription mAlbumsSubscription;

    private Artist mArtist;

    private PagingObject.AlbumsContainer mAlbumsContainer;

    @Override
    public void attachView(IArtistDetailsView view) {
        mArtistDetailsView = view;
    }

    @Override
    public void detachView() {
        mArtistDetailsView = null;
        if (mDetailsSubscription != null) {
            mDetailsSubscription.unsubscribe();
        }
        if (mAlbumsSubscription != null) {
            mAlbumsSubscription.unsubscribe();
        }
    }

    @Override
    public void loadDetails(String id) {
        if (id == null || id.equals("")) {
            mArtistDetailsView.showError(ERROR_EMPTY_ID_STRING);
        } else {
            mArtistDetailsView.showProgressBar();
            loadArtistDetails(id);
            loadArtistAlbums(id);
        }
    }

    private void loadArtistDetails(String id) {
        if (mDetailsSubscription != null) {
            mDetailsSubscription.unsubscribe();
        }
        MyApplication application = MyApplication.get(mArtistDetailsView.getContext());
        NetworkService service = application.getNetworkService();
        mDetailsSubscription = service.getArtist(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Artist>() {
                    @Override
                    public void onCompleted() {
                        mArtistDetailsView.showArtistDetails(mArtist);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mArtistDetailsView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Artist artist) {
                        mArtist = artist;
                    }
                });
    }

    private void loadArtistAlbums(String id) {
        if (mAlbumsSubscription != null) {
            mAlbumsSubscription.unsubscribe();
        }
        MyApplication application = MyApplication.get(mArtistDetailsView.getContext());
        NetworkService service = application.getNetworkService();
        mAlbumsSubscription = service.getArtistAlbums(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PagingObject.AlbumsContainer>() {
                    @Override
                    public void onCompleted() {
                        mArtistDetailsView.showArtistAlbums(mAlbumsContainer.getAlbums());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mArtistDetailsView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PagingObject.AlbumsContainer albumsContainer) {
                        mAlbumsContainer = albumsContainer;
                    }
                });
    }
}
