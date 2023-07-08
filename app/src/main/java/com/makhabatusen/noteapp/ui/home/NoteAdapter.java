package com.makhabatusen.noteapp.ui.home;

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

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Note> list = new ArrayList<>();
    private OnItemClickListener listener;

    public TaskAdapter() {
    }


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
        // if we put 2, but it's only 1 item in the list, it will through an error (Array out of bound exception)
        return list.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addList(List<Note> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        /*
        Adding new item to bottom of the list
        list.add(text);
        notifyItemInserted(list.size() - 1);
         */

        // adding item to top of the list
        list.add(0, note);
        notifyItemInserted(0);
    }


    public void editItem(Note note, int pos) {
        list.set(pos, note);
        notifyItemChanged(pos);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTask;
        private TextView tvDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // new Interface OnItemClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition(), list.get(getAdapterPosition()), getItemId());
                }
            });

            // setting LongClickListener
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClick(getAdapterPosition(), list.get(getAdapterPosition()));
                    return true;
                }
            });
            tvTask = itemView.findViewById(R.id.tv_task);
            tvDate = itemView.findViewById(R.id.tv_date);



        }

        public void bind(Note note) {
            tvTask.setText(note.getTitle());
            // tvDate.setText(String.valueOf(note.getCreatedAt()));
            tvDate.setText(note.getCreatedAt());

            //setting Background Color for Items (orange if position is even, blue if odd)
            if (getAdapterPosition() % 2 == 0) {
                itemView.setBackgroundResource(R.color.orange_light);
            } else {
                itemView.setBackgroundResource(R.color.blue_light);

            }
        }
    }
}
