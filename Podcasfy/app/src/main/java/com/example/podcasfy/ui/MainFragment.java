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
import androidx.lifecycle.ViewModelProvider;

import com.example.podcasfy.R;
import com.example.podcasfy.databinding.MainFragmentBinding;
import com.example.podcasfy.viewmodel.ReproducerViewModel;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainFragment extends Fragment implements PodcastFragment.ItemClickListener {

    private MainFragmentBinding binding;
    private ReproducerViewModel reproducerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false);

        setupSlidingUpPanel();

       return binding.getRoot();
    }

    /**
     * To setup the SlidingUpPanel, we add a Panel SlideListener that change the alpha value
     * of the sliding elements to have a smooth transition when the panel is slided
     */
    private void setupSlidingUpPanel(){

        binding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                changeAlphaSlidingElements(slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                // We don't want to change this behavior. But it needs to be override
            }
        });
    }

    /**
     * To calculate the new alpha value to guarantees a smooth transition when the panel is slided
     * @param slideOffset needed for the calculation
     * @return the new alpha value
     */
    private float calculateNewAlphaValue(float slideOffset){
        return 1-(4*slideOffset);
    }

    /**
     * To change the alpha value of all the elements of the sliding part of the panel
     * @param slideOffset value to set as alpha
     */
    private void changeAlphaSlidingElements(float slideOffset){

        final float newAlphaValue = calculateNewAlphaValue(slideOffset);

        binding.reproducer.slingindEpisodeImage.setAlpha(newAlphaValue);
        binding.reproducer.slidingName.setAlpha(newAlphaValue);
        binding.reproducer.slidingMediaReproducer.setAlpha(newAlphaValue);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupReproducerViewModel();

    }
    /**
     * To create the ReproducerViewModel and observe podcastName to update the UI.
     */

    private void setupReproducerViewModel(){
        createReproducerViewModel();
        observePodcastName();
    }

    /**
     * To create the ReproducerViewModel
     */
    private void createReproducerViewModel(){
        reproducerViewModel = new ViewModelProvider(requireActivity()).get(ReproducerViewModel.class);
    }

    /**
     * To observe and update the podcast name in the UI
     */
    private void observePodcastName(){
        reproducerViewModel.getName().observe(
                getViewLifecycleOwner(), this::updateSlidingUIName);
    }

    /**
     * To update the UI with the given podcast name
     * @param name of the podcast
     */
    private void updateSlidingUIName(String name){
        binding.reproducer.slidingName.setText(name);
    }

    @Override
    public void onItemClick(int clickedItem) {
    }
}
