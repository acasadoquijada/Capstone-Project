package com.example.podcasfy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.databinding.PodcastEpisodeInListBinding;
import com.example.podcasfy.model.Episode;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.rambler.libs.swipe_layout.SwipeLayout;

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

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        PodcastEpisodeInListBinding binding =
                PodcastEpisodeInListBinding.inflate(inflater, parent, false);

        return new PodcastEpisodeHolder(binding);
    }

    public void setEpisodes(List<Episode> episodes){
        this.episodes = episodes;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastEpisodeHolder holder, int position) {

        holder.bind(
                episodes.get(position).getName(),
                episodes.get(position).getImageURL());
    }

    @Override
    public int getItemCount() {
        if(episodes != null)
            return episodes.size();
        return 0;
    }

    public interface ItemClickListener{
        void onItemClick(int clickedItem, boolean delete);
    }

    class PodcastEpisodeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private PodcastEpisodeInListBinding binding;

        PodcastEpisodeHolder(@NonNull PodcastEpisodeInListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            setupSwipeListener();

            itemView.setOnClickListener(this);

        }

        private void setupSwipeListener(){
            if(mSwipeListener){
                binding.swipeLayout.setRightSwipeEnabled(true);
                binding.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
                    @Override
                    public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {

                    }

                    @Override
                    public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
                        if(!moveToRight){
                            customOnClick(binding.getRoot());
                        }
                    }

                    @Override
                    public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {

                    }

                    @Override
                    public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {

                    }
                });

            }
        }

        void bind(String name, String image){
            binding.podcastEpisodeName.setText(name);
            Picasso.get().load(image).into(binding.podcastEpisodeImage);
        }

        private void customOnClick(View v){
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos, true);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mItemClickListener.onItemClick(pos,false);
        }
    }
}
