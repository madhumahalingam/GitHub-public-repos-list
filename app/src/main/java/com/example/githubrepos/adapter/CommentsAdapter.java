package com.example.githubrepos.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubrepos.AppExecutors;
import com.example.githubrepos.R;
import com.example.githubrepos.database.Comment;
import com.example.githubrepos.database.RoomDB;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.NewsHolder> {

    private final List<Comment> comments;
    private final Activity activity;

    public CommentsAdapter(Activity activity, List<Comment> comments) {
        this.comments = comments;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CommentsAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.NewsHolder holder, int position) {

        final Comment comment = comments.get(position);
        holder.txt_comment.setText(comment.getComment());

        holder.img_delete.setOnClickListener(view -> {
            RoomDB roomDB = RoomDB.getInstance(activity);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    roomDB.commentsDao().deleteSingleComment(comment.id);
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        private final TextView txt_comment;
        private final ImageView img_delete;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            txt_comment = itemView.findViewById(R.id.txt_comment);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }
}
