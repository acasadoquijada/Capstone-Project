package com.example.podcasfy.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.podcasfy.R;
import com.example.podcasfy.databinding.MainFragmentBinding;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainFragment extends Fragment implements PodcastFragment.ItemClickListener {

    private MainFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false);

        SlidingUpPanelLayout slidingUpPanelLayout = binding.slidingLayout;

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {

                    // Sliding layout
                    binding.reproducer.slingindEpisodeImage.setAlpha(1-(4*slideOffset));
                    binding.reproducer.slidingName.setAlpha(1-(4*slideOffset));
                    binding.reproducer.slidingMediaReproducer.setAlpha(1-(4*slideOffset));

                }

                @Override
                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                }
            });

       return binding.getRoot();

    }

    @Override
    public void onItemClick(int clickedItem) {
    }
}
