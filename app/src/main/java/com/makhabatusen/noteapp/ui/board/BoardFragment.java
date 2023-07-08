package com.makhabatusen.noteapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makhabatusen.noteapp.MainActivity;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.utils.Preferences;


public class BoardFragment extends Fragment {

    private ViewPager2 viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.view_pager);

        initView();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });


        view.findViewById(R.id.tv_skip).setOnClickListener(view1 -> {
            new Preferences(requireContext()).saveShowStatus();
            ((MainActivity) requireActivity()).closeFragment();
        });

    }

    private void initView() {
        BoardAdapter boardAdapter = new BoardAdapter();
        viewPager.setAdapter(boardAdapter);
    }
}