package com.example.podcasfy.adapter;

import android.content.Context;
import android.util.Log;
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
import java.util.zip.Inflater;

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
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View root = layoutInflater.inflate(R.layout.subscribed_podcast,parent,false);

        return new SubscribedHolder(root);
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
        return subscriptions.size();
    }

    public class SubscribedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView logo;
        private ImageView provider;
        private TextView name;
        public SubscribedHolder(@NonNull View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.podcastLogo);
            provider = itemView.findViewById(R.id.provider);
            name = itemView.findViewById(R.id.podcastName);

            itemView.setOnClickListener(this);

        }

        void bind(String name, String image, String provider){

            Picasso.get().load(image).into(logo);
            // Check if ivoox or spotify
            if(provider.toLowerCase().equals("ivoox")){
                Picasso.get().load(R.drawable.ivoox_logo).into(this.provider);
            } else if(provider.toLowerCase().equals("spotify")){
                Picasso.get().load(R.drawable.spotify_logo).into(this.provider);
            }
            Log.d("HOLA_", name);
            this.name.setText(name);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            mItemClickListener.onItemClick(pos);
        }
    }
}
