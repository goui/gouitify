package fr.goui.gouitify.details.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouitify.R;
import fr.goui.gouitify.adapter.TrackAdapter;
import fr.goui.gouitify.details.presenter.AlbumDetailsPresenter;
import fr.goui.gouitify.details.presenter.IDetailsPresenter;
import fr.goui.gouitify.listener.OnTrackClickListener;
import fr.goui.gouitify.model.Album;

public class AlbumDetailsActivity extends AppCompatActivity implements IAlbumDetailsView, OnTrackClickListener {

    private IDetailsPresenter<IAlbumDetailsView> mPresenter;

    private TrackAdapter mTrackAdapter;

    private Album mAlbum;

    @BindView(R.id.album_image)
    ImageView mAlbumImageView;

    @BindView(R.id.album_artist_name)
    TextView mArtistNameTextView;

    @BindView(R.id.album_tracks_recycler_view)
    RecyclerView mTracksRecyclerView;

    @BindView(R.id.album_details_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.album_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.album_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTracksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTracksRecyclerView.setAdapter(null);

        mPresenter = new AlbumDetailsPresenter();
        mPresenter.attachView(this);

        String albumId = getIntent().getStringExtra(getString(R.string.intent_extra_album_id));
        mPresenter.loadDetails(albumId);
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
    public void showAlbumDetails(Album album) {
        mAlbum = album;
        mCollapsingToolbar.setTitle(album.getName());
        mArtistNameTextView.setText(album.getArtists().get(0).getName());
        if (album.getImages() != null && album.getImages().size() > 1) {
            Glide.with(this).load(album.getImages().get(1).getUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_my_library_music_128dp)
                    .into(mAlbumImageView);
        }
        if (mTrackAdapter == null) {
            mTrackAdapter = new TrackAdapter(this);
            mTrackAdapter.setOnTrackClickListener(this);
        }
        mTrackAdapter.setListOfTracks(album.getTrackContainer().getTracks());
        mTracksRecyclerView.setAdapter(mTrackAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.album_artist_name)
    public void onArtistNameClick() {
        Intent intent = new Intent(this, ArtistDetailsActivity.class);
        intent.putExtra(getString(R.string.intent_extra_artist_id), mAlbum.getArtists().get(0).getId());
        startActivity(intent);
    }

    @Override
    public void onTrackClick(String trackId) {
        Intent intent = new Intent(this, TrackDetailsActivity.class);
        intent.putExtra(getString(R.string.intent_extra_track_id), trackId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }
}
