package com.example.githubrepos.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.githubrepos.AppExecutors;
import com.example.githubrepos.R;
import com.example.githubrepos.adapter.CommentsAdapter;
import com.example.githubrepos.database.Comment;
import com.example.githubrepos.database.RoomDB;
import com.example.githubrepos.databinding.FragmentDetailBinding;
import com.example.githubrepos.model.ActiveModel;
import com.example.githubrepos.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private View viewDetail;
    private ResponseModel responseModel;
    private List<Comment> userComments = new ArrayList<>();
    CommentsAdapter commentsAdapter;
    FragmentDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false);
        viewDetail = binding.getRoot();
        setDefaults();
        setListeners();
        getComments();
        return viewDetail;
    }

    private void setDefaults() {
        responseModel = ActiveModel.RESPONSE_MODEL;
        Log.i("response", responseModel.getOwner().getAvatar_url());

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewComments.setHasFixedSize(true);
        binding.recyclerviewComments.setLayoutManager(layoutManager);

        commentsAdapter = new CommentsAdapter(getActivity(), userComments);
        binding.recyclerviewComments.setAdapter(commentsAdapter);

        Glide.with(this).load(responseModel.getOwner().getAvatar_url()).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.userImage);

        binding.txtHtmlUrl.setText(responseModel.getHtml_url());
        binding.txtUrl.setText(responseModel.getUrl());
        binding.txtEventsUrl.setText(responseModel.getEvents_url());
        binding.tagsUrl.setText(responseModel.getTags_url());
    }

    private  void setListeners(){
        binding.clearAll.setOnClickListener(view -> {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    RoomDB roomDB = RoomDB.getInstance(getActivity());
                    roomDB.commentsDao().deleteCommentsForRepo(responseModel.getId());
                }
        });
    });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.content_frame, homeFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void getComments() {
        RoomDB roomDB = RoomDB.getInstance(getActivity().getApplicationContext());
        roomDB.commentsDao().getCommentsForRepo(responseModel.getId()).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                if(comments.size() == 0){
                    binding.commentsView.setVisibility(View.GONE);
                }
                userComments.clear();
                userComments.addAll(comments);
                commentsAdapter.notifyDataSetChanged();
            }
        });
    }
}