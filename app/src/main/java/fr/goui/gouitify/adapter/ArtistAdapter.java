package fr.goui.gouitify.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouitify.R;
import fr.goui.gouitify.listener.OnArtistClickListener;
import fr.goui.gouitify.model.Artist;

/**
 *
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Artist> mListOfArtists;

    private OnArtistClickListener mOnArtistClickListener;

    public ArtistAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListOfArtists(List<Artist> listOfArtists) {
        mListOfArtists = listOfArtists;
        notifyDataSetChanged();
    }

    public void setOnArtistClickListener(OnArtistClickListener onArtistClickListener) {
        mOnArtistClickListener = onArtistClickListener;
    }

    @Override
    public ArtistAdapter.ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistViewHolder(mLayoutInflater.inflate(R.layout.layout_item_artist, parent, false));
    }

    @Override
    public void onBindViewHolder(ArtistAdapter.ArtistViewHolder holder, int position) {
        holder.position = position;
        final Artist artist = mListOfArtists.get(position);
        if (artist != null) {
            holder.artistNameTextView.setText(artist.getName());
            int rating = holder.artistPopularityRatingBar.getNumStars();
            holder.artistPopularityRatingBar.setRating(artist.getPopularity() * rating / 100);
            if (artist.getImages() != null && artist.getImages().size() > 2) {
                Glide.with(mContext).load(artist.getImages().get(2).getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_person_24dp)
                        .into(holder.artistThumbnail);
            }
            holder.artistLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnArtistClickListener != null) {
                        mOnArtistClickListener.onArtistClick(artist.getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListOfArtists.size();
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {
        int position;

        @BindView(R.id.artist_layout)
        LinearLayout artistLayout;

        @BindView(R.id.artist_name)
        TextView artistNameTextView;

        @BindView(R.id.artist_thumbnail)
        ImageView artistThumbnail;

        @BindView(R.id.artist_popularity)
        RatingBar artistPopularityRatingBar;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
