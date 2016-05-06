package fr.goui.gouitify.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouitify.R;
import fr.goui.gouitify.model.Album;
import fr.goui.gouitify.model.Artist;
import fr.goui.gouitify.model.Track;

/**
 *
 */
public class SearchByAlbumAdapter extends RecyclerView.Adapter<SearchByAlbumAdapter.AlbumViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Album> mListOfAlbums;

    public SearchByAlbumAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListOfAlbums(List<Album> listOfAlbums) {
        mListOfAlbums = listOfAlbums;
        notifyDataSetChanged();
    }

    @Override
    public SearchByAlbumAdapter.AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumViewHolder(mLayoutInflater.inflate(R.layout.layout_item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchByAlbumAdapter.AlbumViewHolder holder, int position) {
        holder.position = position;
        Album album = mListOfAlbums.get(position);
        if (album != null) {
            holder.albumNameTextView.setText(album.getName());
            if (album.getImages() != null && album.getImages().size() > 2) {
                Glide.with(mContext).load(album.getImages().get(2).getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_my_library_music_24dp)
                        .into(holder.albumThumbnail);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListOfAlbums.size();
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        int position;

        @BindView(R.id.album_name)
        TextView albumNameTextView;

        @BindView(R.id.album_thumbnail)
        ImageView albumThumbnail;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.album_layout)
        public void onClick() {
            // TODO go to album details
        }
    }
}
