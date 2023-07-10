package com.makhabatusen.noteapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.makhabatusen.noteapp.MainActivity;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.utils.Prefs;


public class BoardFragment extends Fragment {
    private ViewPager2 viewPager;
    BoardAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager);
        initView();
        TabLayout tabLayout = view.findViewById(R.id.tabDots);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
        }).attach();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });


        view.findViewById(R.id.btn_skip).setOnClickListener(v -> {
                    new Prefs(requireContext()).saveShowStatus();
                    ((MainActivity) requireActivity()).closeFragment();
                }
        );
    }

    public void initView() {
        adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);
        viewPager.fakeDragBy(-10f);
        viewPager.endFakeDrag();


        adapter.setOnButtonListener(() -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_boardFragment_to_navigation_home);

        });

    }
}