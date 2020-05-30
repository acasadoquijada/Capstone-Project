package com.example.podcasfy.UI;

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

import java.util.List;

public class PodcastListAdapter extends  RecyclerView.Adapter<PodcastListAdapter.PodcastHolder> {

    private List<String> mPodcastNames;
    private List<String> mPodcastImages;

    public PodcastListAdapter(List<String> podcastNames, List<String> podcastImages) throws Exception {
        if(podcastNames.size() != podcastImages.size()){
            throw new Exception("PodcastNames and PodcastImages MUST have same size");
        }

        this.mPodcastNames = podcastNames;
        this.mPodcastImages = podcastImages;
    }

    public void setPodcastNames(List<String> podcastNames){

        this.mPodcastNames = podcastNames;
    }

    public void setPodcastsImages(List<String> podcastImages){

        this.mPodcastImages = podcastImages;
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

        holder.bind (mPodcastNames.get(position), mPodcastImages.get(position));
    }

    @Override
    public int getItemCount() {
        if(mPodcastImages != null && mPodcastNames != null){
            return (mPodcastImages.size()+ mPodcastNames.size())/2;
        }
        return 0;
    }


    static class PodcastHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
           // mItemClickListener.onItemClick(pos);
        }
    }

}
