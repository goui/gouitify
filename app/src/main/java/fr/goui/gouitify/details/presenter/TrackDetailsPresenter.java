package fr.goui.gouitify.details.presenter;

import fr.goui.gouitify.MyApplication;
import fr.goui.gouitify.details.view.ITrackDetailsView;
import fr.goui.gouitify.model.Track;
import fr.goui.gouitify.network.NetworkService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class TrackDetailsPresenter implements ITrackDetailsPresenter {

    private static final String ERROR_EMPTY_ID_STRING = "Error - empty id string";

    private ITrackDetailsView mTrackDetailsView;

    private Subscription mSubscription;

    private Track mTrack;

    @Override
    public void attachView(ITrackDetailsView view) {
        mTrackDetailsView = view;
    }

    @Override
    public void detachView() {
        mTrackDetailsView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void loadDetails(String id) {
        if (id == null || id.equals("")) {
            mTrackDetailsView.showError(ERROR_EMPTY_ID_STRING);
        } else {
            mTrackDetailsView.showProgressBar();
            loadTrackDetails(id);
        }
    }

    private void loadTrackDetails(String id) {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        MyApplication application = MyApplication.get(mTrackDetailsView.getContext());
        NetworkService service = application.getNetworkService();
        mSubscription = service.getTrack(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Track>() {
                    @Override
                    public void onCompleted() {
                        mTrackDetailsView.showTrackDetails(mTrack);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTrackDetailsView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Track track) {
                        mTrack = track;
                    }
                });
    }
}
