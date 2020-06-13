package com.example.podcasfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.utils.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodeListAdapter extends  RecyclerView.Adapter<EpisodeListAdapter.PodcastEpisodeHolder> {

    private final ItemClickListener mItemClickListener;
    private List<Episode> episodes;
    private boolean mSwipeListener = false;

    public EpisodeListAdapter(ItemClickListener mItemClickListener, boolean swipeListener) {
        this.mItemClickListener = mItemClickListener;
        this.mSwipeListener = swipeListener;
    }

    @NonNull
    @Override
    public PodcastEpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutIdForListItem = R.layout.podcast_episode_in_list;

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new PodcastEpisodeHolder(view);
    }

    public void setEpisodes(List<Episode> episodes){
        this.episodes = episodes;
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastEpisodeHolder holder, int position) {

        holder.bind(
                episodes.get(position).getName(),
                episodes.get(position).getImageURL());

    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public interface ItemClickListener{
        void onItemClick(int clickedItem);
    }



    class PodcastEpisodeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView podcastEpisodeName;
        private ImageView podcastEpisodeImage;

        public PodcastEpisodeHolder(@NonNull View itemView) {
            super(itemView);

            podcastEpisodeName = itemView.findViewById(R.id.podcastEpisodeName);
            podcastEpisodeImage = itemView.findViewById(R.id.podcastEpisodeImage);


            if(mSwipeListener){
                itemView.setOnTouchListener(new OnSwipeTouchListener(itemView.getContext()) {
                    public void onSwipeTop() {
                        Toast.makeText(itemView.getContext(), "top", Toast.LENGTH_SHORT).show();
                    }
                    public void onSwipeRight() {
                        Toast.makeText(itemView.getContext(), "right", Toast.LENGTH_SHORT).show();
                    }
                    public void onSwipeLeft() {
                        Toast.makeText(itemView.getContext(), "left", Toast.LENGTH_SHORT).show();
                    }
                    public void onSwipeBottom() {
                        Toast.makeText(itemView.getContext(), "bottom", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            itemView.setOnClickListener(this);
        }

        void bind(String name, String image){
            podcastEpisodeName.setText(name);
            Picasso.get().load(image).into(podcastEpisodeImage);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos);
        }
    }
}
