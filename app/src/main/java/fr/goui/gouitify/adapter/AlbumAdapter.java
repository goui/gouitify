package fr.goui.gouitify.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouitify.R;
import fr.goui.gouitify.listener.OnAlbumClickListener;
import fr.goui.gouitify.model.Album;

/**
 *
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Album> mListOfAlbums;

    private OnAlbumClickListener mOnAlbumClickListener;

    public AlbumAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListOfAlbums(List<Album> listOfAlbums) {
        mListOfAlbums = listOfAlbums;
        notifyDataSetChanged();
    }

    public void setOnAlbumClickListener(OnAlbumClickListener onAlbumClickListener) {
        mOnAlbumClickListener = onAlbumClickListener;
    }

    @Override
    public AlbumAdapter.AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumViewHolder(mLayoutInflater.inflate(R.layout.layout_item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.AlbumViewHolder holder, int position) {
        holder.position = position;
        final Album album = mListOfAlbums.get(position);
        if (album != null) {
            holder.albumNameTextView.setText(album.getName());
            if (album.getImages() != null && album.getImages().size() > 2) {
                Glide.with(mContext).load(album.getImages().get(2).getUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_my_library_music_24dp)
                        .into(holder.albumThumbnail);
            }
            holder.albumLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnAlbumClickListener != null) {
                        mOnAlbumClickListener.onAlbumClick(album.getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListOfAlbums.size();
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        int position;

        @BindView(R.id.album_layout)
        LinearLayout albumLayout;

        @BindView(R.id.album_name)
        TextView albumNameTextView;

        @BindView(R.id.album_thumbnail)
        ImageView albumThumbnail;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
