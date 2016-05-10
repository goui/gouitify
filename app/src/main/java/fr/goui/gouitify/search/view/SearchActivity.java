package fr.goui.gouitify.search.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouitify.R;
import fr.goui.gouitify.details.view.TrackDetailsActivity;
import fr.goui.gouitify.listener.OnTrackClickListener;
import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.model.Track;
import fr.goui.gouitify.search.adapter.SearchByAlbumAdapter;
import fr.goui.gouitify.search.adapter.SearchByArtistAdapter;
import fr.goui.gouitify.search.adapter.SearchByTrackAdapter;
import fr.goui.gouitify.search.presenter.ISearchPresenter;
import fr.goui.gouitify.search.presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity implements ISearchView, OnTrackClickListener {

    // TODO add debounce rx function to avoid multiple search triggers if the user is typing fast

    private ISearchPresenter mPresenter;

    private SearchByTrackAdapter mSearchByTrackAdapter;

    private SearchByArtistAdapter mSearchByArtistAdapter;

    private SearchByAlbumAdapter mSearchByAlbumAdapter;

    @BindView(R.id.search_edit_text)
    EditText mSearchEditText;

    @BindView(R.id.search_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.search_progress_bar)
    ProgressBar mProgressBar;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // do nothing
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null) {
                mPresenter.setSearchString(s.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        mPresenter = new SearchPresenter();
        mPresenter.attachView(this);

        mSearchEditText.addTextChangedListener(mTextWatcher);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(null);
    }

    @OnClick({R.id.search_by_track_radio_button, R.id.search_by_artist_radio_button, R.id.search_by_album_radio_button})
    public void onRadioButtonChecked(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        if (checked) {
            int searchType = Track.TYPE_INT;
            int id = radioButton.getId();
            switch (id) {
                case R.id.search_by_track_radio_button:
                    searchType = Track.TYPE_INT;
                    break;
                case R.id.search_by_artist_radio_button:
                    searchType = Artist.TYPE_INT;
                    break;
                case R.id.search_by_album_radio_button:
                    searchType = Album.TYPE_INT;
                    break;
            }

            mPresenter.setSearchType(searchType);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String message) {
        mRecyclerView.setAdapter(null);
        showRecyclerView();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void showRecyclerView() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTracks(List<Track> listOfTracks) {
        if (mSearchByTrackAdapter == null) {
            mSearchByTrackAdapter = new SearchByTrackAdapter(this);
            mSearchByTrackAdapter.setOnTrackClickListener(this);
        }
        mSearchByTrackAdapter.setListOfTracks(listOfTracks);
        mRecyclerView.setAdapter(mSearchByTrackAdapter);
        showRecyclerView();
    }

    @Override
    public void showArtists(List<Artist> listOfArtists) {
        if (mSearchByArtistAdapter == null) {
            mSearchByArtistAdapter = new SearchByArtistAdapter(this);
        }
        mSearchByArtistAdapter.setListOfArtists(listOfArtists);
        mRecyclerView.setAdapter(mSearchByArtistAdapter);
        showRecyclerView();
    }

    @Override
    public void showAlbums(List<Album> listOfAlbums) {
        if (mSearchByAlbumAdapter == null) {
            mSearchByAlbumAdapter = new SearchByAlbumAdapter(this);
        }
        mSearchByAlbumAdapter.setListOfAlbums(listOfAlbums);
        mRecyclerView.setAdapter(mSearchByAlbumAdapter);
        showRecyclerView();
    }

    @Override
    public void hideList() {
        mRecyclerView.setVisibility(View.GONE);
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
