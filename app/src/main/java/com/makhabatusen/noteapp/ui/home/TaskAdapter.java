package com.makhabatusen.noteapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.R;
import com.makhabatusen.noteapp.ui.interfaces.OnItemClickListener;
import com.makhabatusen.noteapp.ui.model.Note;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Note> list =  new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public TaskAdapter() {
     }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addList(ArrayList<Note> list) {

        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        this.list.add(0,note);
        notifyItemInserted(list.indexOf(0));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                onItemClickListener.onCLick(getAdapterPosition());
            });

            itemView.setOnLongClickListener(view -> {
                onItemClickListener.onLongCLick(getAdapterPosition());
                return true;
            });

            textView = itemView.findViewById(R.id.tv_task);
        }

        public void bind(Note note) {
            textView.setText(note.getTitle());
        }
    }
}
