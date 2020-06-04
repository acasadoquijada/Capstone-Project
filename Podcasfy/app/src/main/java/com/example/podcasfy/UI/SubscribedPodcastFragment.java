package com.example.podcasfy.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.SubscribedPodcastsListAdapter;
import com.example.podcasfy.model.Podcast;

import java.util.ArrayList;
import java.util.List;

public class SubscribedPodcastFragment extends Fragment implements SubscribedPodcastsListAdapter.ItemClickListener{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.subscribed_podcasts_fragment,container, false);


        SubscribedPodcastsListAdapter subscribedPodcastsListAdapter = new SubscribedPodcastsListAdapter(this);

        RecyclerView recyclerView = rootView.findViewById(R.id.subscribedRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);

        Podcast p = new Podcast();
        p.setName("Podcast name 1");
        p.setProvider("ivoox");
        p.setMediaURL("https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg");

        Podcast p2 = new Podcast();
        p2.setName("Podcast name 2");
        p2.setProvider("spotify");
        p2.setMediaURL("https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg");

        List<Podcast> subs= new ArrayList<>();

        subs.add(p);
        subs.add(p2);

        subscribedPodcastsListAdapter.setSubscriptions(subs);
        recyclerView.setAdapter(subscribedPodcastsListAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        // HERE I USE MY VIEW MODEL
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(int clickedItem) {

        NavDirections action =
                SubscribedPodcastFragmentDirections
                        .actionSubscribedPodcastFragmentToPodcastFragment("id");
        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(action);
    }
}
