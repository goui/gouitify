package fr.goui.gouitify.adapter;

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
import fr.goui.gouitify.R;
import fr.goui.gouitify.listener.OnTrackClickListener;
import fr.goui.gouitify.model.Track;

/**
 *
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Track> mListOfTracks;

    private OnTrackClickListener mOnTrackClickListener;

    public TrackAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListOfTracks(List<Track> listOfTracks) {
        mListOfTracks = listOfTracks;
        notifyDataSetChanged();
    }

    public void setOnTrackClickListener(OnTrackClickListener onTrackClickListener) {
        mOnTrackClickListener = onTrackClickListener;
    }

    @Override
    public TrackAdapter.TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrackViewHolder(mLayoutInflater.inflate(R.layout.layout_item_track, parent, false));
    }

    @Override
    public void onBindViewHolder(TrackAdapter.TrackViewHolder holder, final int position) {
        holder.position = position;
        final Track track = mListOfTracks.get(position);
        if (track != null) {
            holder.trackNameTextView.setText(track.getName());
            if (track.getArtists() != null && track.getArtists().size() > 0) {
                holder.artistNameTextView.setText(track.getArtists().get(0).getName());
            }
            if (track.getAlbum() != null) {
                holder.albumNameTextView.setText(track.getAlbum().getName());
            }
            holder.trackLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnTrackClickListener != null) {
                        mOnTrackClickListener.onTrackClick(track.getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListOfTracks.size();
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {
        int position;

        @BindView(R.id.track_layout)
        LinearLayout trackLayout;

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
    }
}
