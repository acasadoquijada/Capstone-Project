package com.example.podcasfy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.podcasfy.R;
import com.example.podcasfy.api.Provider;
import com.example.podcasfy.databinding.PodcastInListBinding;
import com.example.podcasfy.model.Podcast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PodcastListAdapter extends  RecyclerView.Adapter<PodcastListAdapter.PodcastHolder> {

    private List<Podcast> mPodcastList;
    private final ItemClickListener mItemClickListener;

    private String provider;


    public PodcastListAdapter(ItemClickListener itemClickListener, String provider){
        this.mItemClickListener = itemClickListener;
        this.provider = provider;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItem, String provider);
    }

    public void setPodcasts(List<Podcast> podcasts){

        this.mPodcastList = podcasts;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PodcastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        PodcastInListBinding binding =
                PodcastInListBinding.inflate(inflater, parent, false);

        return new PodcastHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastHolder holder, int position) {

        holder.bind (mPodcastList.get(position).getName(), mPodcastList.get(position).getMediaURL());
    }

    @Override
    public int getItemCount() {
        if(mPodcastList != null)
            return mPodcastList.size();
        return 0;
    }


    class PodcastHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private PodcastInListBinding binding;

        PodcastHolder(@NonNull PodcastInListBinding binding) {
            super(binding.getRoot());

            if(provider.equals(Provider.SUBSCRIBED)){
                binding.providerLogo.setImageResource(R.drawable.ic_bookmark_black);
            } else if(provider.equals(Provider.SPAIN)){
                binding.providerLogo.setImageResource(R.drawable.spain_logo);
            } else if(provider.equals(Provider.UK)){
                binding.providerLogo.setImageResource(R.drawable.uk_logo);
            }

            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        void bind(String podcastName, String podcastImageURL){

            binding.podcastName.setText(podcastName);

            Picasso.get().load(podcastImageURL).into(binding.podcastImage);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos, provider);
        }
    }
}
