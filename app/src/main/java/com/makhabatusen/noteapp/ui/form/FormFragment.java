package com.makhabatusen.noteapp.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.makhabatusen.noteapp.App;
import com.makhabatusen.noteapp.MainActivity;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.Note;
import com.makhabatusen.noteapp.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormFragment extends Fragment {
    public static final String KEY_NEW_CONTACT = "contact";
    public static final String KEY_ADD = "insert a new Contact";
    private EditText editText;
    private Note note;
    private String savedItem;
    private String text;
    private String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.et);

        view.findViewById(R.id.btn_save).setOnClickListener(v -> {
            save();
        });
        setFragmentListener();

    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(HomeFragment.KEY_SEND,
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                        note = (Note) result.getSerializable(HomeFragment.KEY_SAVED_CONTACT);
                        savedItem = note.getTitle();
                        date = note.getCreatedAt();
                        editText.setText(savedItem);
                    }
                });

    }

    private void save() {
        text = editText.getText().toString();

        Log.e("FormFragment", "text =  " + text);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm yyyy/MM/dd", Locale.ROOT);
        String dateString = dateFormat.format(System.currentTimeMillis());

        if (note == null) {
            note= new Note(text,dateString);
            //saving to Data Base
            App.dataBase.noteDao().insert(note);
        } else {
            note.setTitle(text);
            App.dataBase.noteDao().updateItem(note);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_NEW_CONTACT, note);
        getParentFragmentManager().setFragmentResult(KEY_ADD, bundle);

        ((MainActivity) requireActivity()).closeFragment();

    }
}