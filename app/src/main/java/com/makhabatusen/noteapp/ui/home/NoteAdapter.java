package com.makhabatusen.noteapp.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.ui.interfaces.OnItemClickListener;
import com.makhabatusen.noteapp.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final ArrayList<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    public NoteAdapter() {
    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notes.get(position));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addList(List<Note> list) {
        this.notes.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        /*
        Adding new item to bottom of the list
        list.add(text);
        notifyItemInserted(list.size() - 1);
         */

        // adding item to top of the list
        notes.add(0, note);
        notifyItemInserted(0);
    }


    public void editItem(Note note, int pos) {
        notes.set(pos, note);
        notifyItemChanged(pos);
    }

    public void deleteItem(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTask;
        private final TextView tvDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition(), notes.get(getAdapterPosition()), getItemId());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClick(getAdapterPosition(), notes.get(getAdapterPosition()));
                    return true;
                }
            });
            tvTask = itemView.findViewById(R.id.tv_task);
            tvDate = itemView.findViewById(R.id.tv_date);



        }

        public void bind(Note note) {
            tvTask.setText(note.getTitle());
            tvDate.setText(note.getCreatedAt());

            if (getAdapterPosition() % 2 == 0) {
                itemView.setBackgroundResource(R.color.orange_light);
            } else {
                itemView.setBackgroundResource(R.color.blue_light);

            }
        }
    }
}
