package com.makhabatusen.noteapp.ui.interfaces;

import com.makhabatusen.noteapp.Note;

public interface OnItemClickListener {
    void onClick(int position,  Note note, long id);
    void onLongClick(int position,  Note note);

}
