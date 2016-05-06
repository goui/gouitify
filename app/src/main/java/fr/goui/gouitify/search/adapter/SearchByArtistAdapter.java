package fr.goui.gouitify.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouitify.R;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.model.Track;

/**
 *
 */
public class SearchByArtistAdapter extends RecyclerView.Adapter<SearchByArtistAdapter.ArtistViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Artist> mListOfArtists;

    public SearchByArtistAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListOfArtists(List<Artist> listOfArtists) {
        mListOfArtists = listOfArtists;
        notifyDataSetChanged();
    }

    @Override
    public SearchByArtistAdapter.ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistViewHolder(mLayoutInflater.inflate(R.layout.layout_item_artist, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchByArtistAdapter.ArtistViewHolder holder, int position) {
        holder.position = position;
        Artist artist = mListOfArtists.get(position);
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
        }
    }

    @Override
    public int getItemCount() {
        return mListOfArtists.size();
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {
        int position;

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

        @OnClick(R.id.artist_layout)
        public void onClick() {
            // TODO go to artist details
        }
    }
}
