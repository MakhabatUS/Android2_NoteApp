package com.makhabatusen.noteapp.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.makhabatusen.noteapp.MainActivity;
import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.ui.model.Note;

public class FormFragment extends Fragment {
    public static final String REQUEST_KEY = "task_key";
    public static final String REQUEST_KEY_NOTE = "task_key_note";

    private EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.et);

        view.findViewById(R.id.btn_save).setOnClickListener(view1 ->
                save());
    }

    private void save() {

        String text = editText.getText().toString();
        Log.e("FormFragment", "text = " + text);
        Note note = new Note(text, System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putSerializable(REQUEST_KEY_NOTE, note);
        getParentFragmentManager().setFragmentResult(REQUEST_KEY, bundle);
        ((MainActivity)  requireActivity()).closeFragment();
    }
}