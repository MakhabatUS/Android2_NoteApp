package com.makhabatusen.noteapp.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makhabatusen.noteapp.App;
import com.makhabatusen.noteapp.Note;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.databinding.FragmentDashboardBinding;
import com.makhabatusen.noteapp.ui.home.NoteAdapter;
import com.makhabatusen.noteapp.ui.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private NoteAdapter adapter;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_dashboard);
        progressBar = view.findViewById(R.id.progress_bar);
        initList();
        loadDataFromFireStore();
    }

    private void loadDataFromFireStore() {

        FirebaseFirestore.getInstance().collection("notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            List<Note> notes = task.getResult().toObjects(Note.class);
                            adapter.addList(notes);
                        }
                    }
                });
    }


    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClick(int position, Note note, long id) {

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