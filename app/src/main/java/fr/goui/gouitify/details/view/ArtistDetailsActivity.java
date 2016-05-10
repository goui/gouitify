package fr.goui.gouitify.details.view;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouitify.R;
import fr.goui.gouitify.details.presenter.ArtistDetailsPresenter;
import fr.goui.gouitify.details.presenter.IArtistDetailsPresenter;
import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.search.adapter.SearchByAlbumAdapter;

public class ArtistDetailsActivity extends AppCompatActivity implements IArtistDetailsView {

    private IArtistDetailsPresenter mPresenter;

    private SearchByAlbumAdapter mAlbumsAdapter;

    @BindView(R.id.artist_image)
    ImageView mArtistImageView;

    @BindView(R.id.artist_name)
    TextView mArtistNameTextView;

    @BindView(R.id.artist_albums_recycler_view)
    RecyclerView mAlbumsRecyclerView;

    @BindView(R.id.artist_details_progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAlbumsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlbumsRecyclerView.setAdapter(null);

        mPresenter = new ArtistDetailsPresenter();
        mPresenter.attachView(this);

        String artistId = getIntent().getStringExtra(getString(R.string.intent_extra_artist_id));
        mPresenter.loadDetails(artistId);
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
    public void showArtistDetails(Artist artist) {
        mArtistNameTextView.setText(artist.getName());
        Glide.with(this).load(artist.getImages().get(1).getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_person_128dp)
                .into(mArtistImageView);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showArtistAlbums(List<Album> albums) {
        if (mAlbumsAdapter == null) {
            mAlbumsAdapter = new SearchByAlbumAdapter(this);
        }
        mAlbumsAdapter.setListOfAlbums(albums);
        mAlbumsRecyclerView.setAdapter(mAlbumsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }
}
