package com.example.podcasfy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.api.Provider;
import com.example.podcasfy.databinding.SubscribedPodcastInListBinding;
import com.example.podcasfy.model.Podcast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubscribedPodcastsListAdapter extends RecyclerView.Adapter<SubscribedPodcastsListAdapter.SubscribedHolder> {

    private List<Podcast> subscriptions;
    private final ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void onItemClick(int clickedItem);
    }

    public SubscribedPodcastsListAdapter(ItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public void setSubscriptions(List<Podcast> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @NonNull
    @Override
    public SubscribedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        SubscribedPodcastInListBinding binding =
                SubscribedPodcastInListBinding.inflate(inflater, parent, false);


        return new SubscribedHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribedHolder holder, int position) {

        holder.bind(
                subscriptions.get(position).getName(),
                subscriptions.get(position).getMediaURL(),
                subscriptions.get(position).getProvider());
    }

    @Override
    public int getItemCount() {
        if(subscriptions != null){
            return subscriptions.size();
        }

        return 0;
    }

    public class SubscribedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SubscribedPodcastInListBinding binding;

        SubscribedHolder(@NonNull SubscribedPodcastInListBinding binding) {
            super(binding.getRoot());


            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        /**
         * To bind the SubscribedPodcast information to the ViewHolder
         * @param name of the podcast
         * @param image of the podcast
         * @param provider of the podcast
         */

        void bind(String name, String image, String provider){

            binding.podcastName.setText(name);

            Picasso.get().load(image).into(binding.podcastLogo);

            setupProviderLogo(provider);
        }

        /**
         * To setup the corresponding logo image to identify the Podcast's provider
         * @param provider String identifier
         */

        private void setupProviderLogo(String provider){
            if(provider.equals(Provider.SPAIN)){
                binding.provider.setImageResource(R.drawable.spain_logo);
            } else if(provider.equals(Provider.UK)){
                binding.provider.setImageResource(R.drawable.uk_logo);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos);
        }
    }
}
