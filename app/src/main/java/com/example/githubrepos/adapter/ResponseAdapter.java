package com.example.githubrepos.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.githubrepos.AppExecutors;
import com.example.githubrepos.R;
import com.example.githubrepos.database.Comment;
import com.example.githubrepos.database.RoomDB;
import com.example.githubrepos.interfaces.Showdetails;
import com.example.githubrepos.model.ResponseModel;
import com.example.githubrepos.utils.CommentUtil;

import java.util.ArrayList;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.NewsHolder> {

    private final ArrayList<ResponseModel> newsModels;
    private final Activity activity;
    private final Showdetails getDetails;

    public ResponseAdapter(Activity activity, ArrayList<ResponseModel> list, Showdetails getDetails) {
        this.newsModels = list;
        this.activity = activity;
        this.getDetails = getDetails;
    }

    @NonNull
    @Override
    public ResponseAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_list, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseAdapter.NewsHolder holder, int position) {

        final ResponseModel newsModel = newsModels.get(position);
        String path = newsModel.getOwner().getAvatar_url();
        Glide.with(activity).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.user_image);


        holder.txt_user_name.setText(newsModel.getName());
        holder.txt_user_full_name.setText(newsModel.getFull_name());
        holder.txt_description.setText(newsModel.getDescription());

        holder.itemView.setOnClickListener(view -> getDetails.showDetails(newsModels.get(position)));
        holder.btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentUtil util = new CommentUtil(activity);
                util.show_comment_view(newsModel.getName());
                util.getCommentData().observe((LifecycleOwner) activity, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        RoomDB roomDB = RoomDB.getInstance(activity);
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                roomDB.commentsDao().addComment(new Comment(newsModel.getId(),s,"madhu"));

                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "Comment added", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void addNullData() {
        newsModels.add(null);
        notifyItemInserted(newsModels.size() - 1);
    }

    public void removeNull() {
        newsModels.remove(newsModels.size() - 1);
        notifyItemRemoved(newsModels.size());
    }



    public void addData(ArrayList<ResponseModel> integersList) {
        newsModels.addAll(integersList);
        notifyDataSetChanged();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        private final TextView txt_user_name;
        private final TextView txt_user_full_name;
        private final TextView txt_description;
        private final ImageView user_image;
        private final Button btn_add_comment;


        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            txt_user_name = itemView.findViewById(R.id.txt_user_name);
            txt_user_full_name = itemView.findViewById(R.id.txt_user_full_name);
            txt_description = itemView.findViewById(R.id.txt_description);
            user_image = itemView.findViewById(R.id.user_image);
            btn_add_comment = itemView.findViewById(R.id.add_comment);
        }
    }
}
