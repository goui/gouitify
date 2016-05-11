package fr.goui.gouitify.details.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouitify.R;
import fr.goui.gouitify.details.presenter.IDetailsPresenter;
import fr.goui.gouitify.details.presenter.TrackDetailsPresenter;
import fr.goui.gouitify.model.Track;

public class TrackDetailsActivity extends AppCompatActivity implements ITrackDetailsView {

    private IDetailsPresenter<ITrackDetailsView> mPresenter;

    private Track mTrack;

    @BindView(R.id.track_name)
    TextView mTrackNameTextView;

    @BindView(R.id.track_artist_name)
    TextView mArtistNameTextView;

    @BindView(R.id.track_album_name)
    TextView mAlbumNameTextView;

    @BindView(R.id.track_details_progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPresenter = new TrackDetailsPresenter();
        mPresenter.attachView(this);

        String trackId = getIntent().getStringExtra(getString(R.string.intent_extra_track_id));
        mPresenter.loadDetails(trackId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // if we press the up button, going back
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.track_artist_name)
    public void onArtistNameClick() {
        Intent intent = new Intent(this, ArtistDetailsActivity.class);
        intent.putExtra(getString(R.string.intent_extra_artist_id), mTrack.getArtists().get(0).getId());
        startActivity(intent);
    }

    @OnClick(R.id.track_album_name)
    public void onAlbumNameClick() {
        Intent intent = new Intent(this, AlbumDetailsActivity.class);
        intent.putExtra(getString(R.string.intent_extra_album_id), mTrack.getAlbum().getId());
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTrackDetails(Track track) {
        mTrack = track;
        mTrackNameTextView.setText(track.getName());
        mArtistNameTextView.setText(track.getArtists().get(0).getName());
        mAlbumNameTextView.setText(track.getAlbum().getName());
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }
}
