package com.makhabatusen.noteapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.databinding.FragmentHomeBinding;
import com.makhabatusen.noteapp.ui.form.FormFragment;
import com.makhabatusen.noteapp.ui.interfaces.OnItemClickListener;
import com.makhabatusen.noteapp.ui.model.Note;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    private FragmentHomeBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter =  new TaskAdapter();


        ArrayList<Note> list = new ArrayList<>();

        list.add(new Note("Germany", System.currentTimeMillis()));
        list.add(new Note("Kyrgyzstan", System.currentTimeMillis()));
        list.add(new Note("Thailand", System.currentTimeMillis()));
        list.add(new Note("Australia", System.currentTimeMillis()));
        list.add(new Note("Vietnam", System.currentTimeMillis()));
        adapter.addList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        recyclerView = view.findViewById(R.id.recycler_view);

        initList();
        view.findViewById(R.id.fb).setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_home_to_formFragment);
        });

        setFragmentListener();
    }

    private void setFragmentListener() {

        getParentFragmentManager().setFragmentResultListener(FormFragment.REQUEST_KEY,
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = (Note) result.getSerializable(FormFragment.REQUEST_KEY_NOTE);
                        adapter.addItem(note);
                    }
                });
    }


    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),  DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onCLick(int position) {

            }

            @Override
            public void onLongCLick(int position) {

            }
        });


    }
}