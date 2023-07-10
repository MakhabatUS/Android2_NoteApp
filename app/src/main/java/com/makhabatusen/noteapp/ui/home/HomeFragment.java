package com.makhabatusen.noteapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.App;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.ui.form.FormFragment;
import com.makhabatusen.noteapp.ui.interfaces.OnItemClickListener;
import com.makhabatusen.noteapp.Note;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private Note note;
    private int pos;
    private boolean toAdd;
    public static final String KEY_SAVED_CONTACT = "saved contact";
    public static final String KEY_SEND = "send data to FormFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new NoteAdapter();
        List<Note>  list = App.dataBase.noteDao().getAll();
        adapter.addList(list);

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        initList();

        view.findViewById(R.id.fb).setOnClickListener(v -> {
            toAdd = true;
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_home_to_formFragment);

        });
        setFragmentListener();


    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(FormFragment.KEY_ADD,
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        note = (Note) result.getSerializable(FormFragment.KEY_NEW_CONTACT);
                        if (toAdd)
                            adapter.addItem(note);
                        else  adapter.editItem(note,pos);
                    }
                });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClick(int position,  Note note, long id) {
                pos = position;
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_SAVED_CONTACT, note);
                getParentFragmentManager().setFragmentResult(KEY_SEND, bundle);
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_formFragment);
                toAdd = false;
            }


            @Override
            public void onLongClick(int position, Note note) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle(R.string.delete_element);
                alert.setMessage(R.string.sure);
                alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.deleteItem(position);
                        App.dataBase.noteDao().delete(note);
                    }
                });
                alert.setNegativeButton(R.string.no, (dialogInterface, i) -> {
                });
                alert.create().show();
            }

        });
    }
}