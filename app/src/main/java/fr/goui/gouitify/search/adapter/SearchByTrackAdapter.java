package fr.goui.gouitify.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouitify.R;
import fr.goui.gouitify.model.Track;

/**
 *
 */
public class SearchByTrackAdapter extends RecyclerView.Adapter<SearchByTrackAdapter.TrackViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Track> mListOfTracks;

    public SearchByTrackAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListOfTracks(List<Track> listOfTracks) {
        mListOfTracks = listOfTracks;
        notifyDataSetChanged();
    }

    @Override
    public SearchByTrackAdapter.TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrackViewHolder(mLayoutInflater.inflate(R.layout.layout_item_track, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchByTrackAdapter.TrackViewHolder holder, int position) {
        holder.position = position;
        Track track = mListOfTracks.get(position);
        if (track != null) {
            holder.trackNameTextView.setText(track.getName());
            if (track.getArtists() != null && track.getArtists().size() > 0) {
                holder.artistNameTextView.setText(track.getArtists().get(0).getName());
            }
            if (track.getAlbum() != null) {
                holder.albumNameTextView.setText(track.getAlbum().getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListOfTracks.size();
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {
        int position;

        @BindView(R.id.track_name)
        TextView trackNameTextView;

        @BindView(R.id.track_artist)
        TextView artistNameTextView;

        @BindView(R.id.track_album)
        TextView albumNameTextView;

        public TrackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.track_layout)
        public void onClick() {
            // TODO go to track details
        }
    }
}
