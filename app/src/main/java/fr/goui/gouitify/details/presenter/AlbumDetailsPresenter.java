package fr.goui.gouitify.details.presenter;

import fr.goui.gouitify.MyApplication;
import fr.goui.gouitify.details.view.IAlbumDetailsView;
import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.network.NetworkService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class AlbumDetailsPresenter implements IDetailsPresenter<IAlbumDetailsView> {

    private static final String ERROR_EMPTY_ID_STRING = "Error - empty id string";

    private IAlbumDetailsView mAlbumDetailsView;

    private Subscription mSubscription;

    private Album mAlbum;

    @Override
    public void attachView(IAlbumDetailsView view) {
        mAlbumDetailsView = view;
    }

    @Override
    public void detachView() {
        mAlbumDetailsView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void loadDetails(String id) {
        if (id == null || id.equals("")) {
            mAlbumDetailsView.showError(ERROR_EMPTY_ID_STRING);
        } else {
            mAlbumDetailsView.showProgressBar();
            loadAlbumDetails(id);
        }
    }

    private void loadAlbumDetails(String id) {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        MyApplication application = MyApplication.get(mAlbumDetailsView.getContext());
        NetworkService service = application.getNetworkService();
        mSubscription = service.getAlbum(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Album>() {
                    @Override
                    public void onCompleted() {
                        mAlbumDetailsView.showAlbumDetails(mAlbum);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAlbumDetailsView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Album album) {
                        mAlbum = album;
                    }
                });
    }
}
