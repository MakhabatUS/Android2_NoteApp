package com.makhabatusen.noteapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makhabatusen.noteapp.R;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private BoardsButtonsListener boardListener;
    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<String> desc = new ArrayList<>();
    private final ArrayList<Integer> photos = new ArrayList<>();


    public BoardAdapter() {

        titles.add("EXPLORE");
        titles.add("DISCOVERY");
        titles.add("CREATE");

        desc.add("EXPLORE THE UNKNOWN WORLD");
        desc.add("DISCOVER THE BEAUTY AROUND");
        desc.add("CREATING WIRELESS POSSIBILITIES");

        photos.add(R.drawable.explore);
        photos.add(R.drawable.discovery);
        photos.add(R.drawable.create);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textDesc;
        private ImageView imageView;
        private Button btnLogin;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);

        }

        private void init(View itemView) {

            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            imageView = itemView.findViewById(R.id.imageView);
            btnLogin = itemView.findViewById(R.id.btn_login);


            btnLogin.setOnClickListener(v->{
                boardListener.openMainPage();
            });
//
        }

        public void bind(int position) {
            textTitle.setText(titles.get(position));
            imageView.setImageResource(photos.get(position));
            textDesc.setText(desc.get(position));
            btnLoginSetUP(position);

        }

        private void btnLoginSetUP(int position) {
            if (position == 2)
                btnLogin.setVisibility(View.VISIBLE);
            else
                btnLogin.setVisibility(View.GONE);
        }

    }
    public  interface BoardsButtonsListener {
        void openMainPage();
    }

    public void setOnButtonListener( BoardsButtonsListener listener) {
        boardListener = listener;
    }
}
