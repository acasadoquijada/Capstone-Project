package com.example.podcasfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.squareup.picasso.Picasso;

public class PodcastEpisodeListAdapter extends  RecyclerView.Adapter<PodcastEpisodeListAdapter.PodcastEpisodeHolder> {

    private final ItemClickListener mItemClickListener;

    public PodcastEpisodeListAdapter(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
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

    @Override
    public void onBindViewHolder(@NonNull PodcastEpisodeHolder holder, int position) {

        holder.bind( "Planos y Centellas 3x19 - El Silencio de los Corderos (1991)",
                "https://static-1.ivoox.com/audios/2/3/9/1/261590341932_MD.jpg");
    }

    @Override
    public int getItemCount() {
        return 20;
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

            itemView.setOnClickListener(this);
        }

        public void bind(String name, String image){
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
