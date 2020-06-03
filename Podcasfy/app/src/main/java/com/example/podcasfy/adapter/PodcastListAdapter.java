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
import com.example.podcasfy.model.Podcast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PodcastListAdapter extends  RecyclerView.Adapter<PodcastListAdapter.PodcastHolder> {

    private List<String> mPodcastNames;
    private List<String> mPodcastImages;
    private List<Podcast> mPodcasts;
    private final ItemClickListener mItemClickListener;


    public PodcastListAdapter(List<String> podcastNames, List<String> podcastImages, ItemClickListener itemClickListener) throws Exception {
        if(podcastNames.size() != podcastImages.size()){
            throw new Exception("PodcastNames and PodcastImages MUST have same size");
        }

        this.mPodcastNames = podcastNames;
        this.mPodcastImages = podcastImages;
        this.mItemClickListener = itemClickListener;

    }

    public PodcastListAdapter(List<Podcast> podcast, ItemClickListener itemClickListener){

        this.mPodcasts = podcast;
        this.mItemClickListener = itemClickListener;

    }

    public interface ItemClickListener {
        void onItemClick(int clickedItem);
    }

    public void setPodcastNames(List<String> podcastNames){

        this.mPodcastNames = podcastNames;
    }

    public void setPodcastsImages(List<String> podcastImages){

        this.mPodcastImages = podcastImages;
    }

    public void setPodcasts(List<Podcast> podcasts){

        this.mPodcasts = podcasts;
    }

    @NonNull
    @Override
    public PodcastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.podcast_in_list;

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new PodcastHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastHolder holder, int position) {

        holder.bind (mPodcasts.get(position).getName(), mPodcasts.get(position).getMediaURL());
    }

    @Override
    public int getItemCount() {
        return mPodcasts.size();
    }


    class PodcastHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView itemName;
        private final ImageView itemImage;

        PodcastHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.podcastName);
            itemImage = itemView.findViewById(R.id.podcastImage);

            itemView.setOnClickListener(this);
        }

        void bind(String podcastName, String podcastImageURL){
            itemName.setText(podcastName);
            Picasso.get().load(podcastImageURL).into(itemImage);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos);
        }
    }

}
