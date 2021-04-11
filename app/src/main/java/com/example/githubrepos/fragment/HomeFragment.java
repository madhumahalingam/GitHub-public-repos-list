package com.example.githubrepos.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.githubrepos.R;
import com.example.githubrepos.adapter.ResponseAdapter;
import com.example.githubrepos.databinding.FragmentHomeBinding;
import com.example.githubrepos.interfaces.Showdetails;
import com.example.githubrepos.interfaces.ViewResponseInterface;
import com.example.githubrepos.model.ActiveModel;
import com.example.githubrepos.model.ResponseModel;
import com.example.githubrepos.pojo.ErrorBody;
import com.example.githubrepos.pojo.jsonresponsemodel.JsonResponseModel;
import com.example.githubrepos.viewmodel.ResponseViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ViewResponseInterface, Showdetails {

    private View homeView;
    private ResponseAdapter responseAdapter;
        private ArrayList<ResponseModel> reposList;
    private ResponseViewModel responseViewModel;
    private Context context;
    private Showdetails getDetails;
    LinearLayoutManager layoutManager;

    FragmentHomeBinding binding;
    boolean isLoading = false;
    int itemsCount = 100;
    boolean isLastPage = false;
    public static final int PAGE_SIZE = 100;
    int repo_id = 0;
    int current_page_size = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        homeView = binding.getRoot();
        setDefaults();
        return homeView;
    }

    private void setDefaults() {
        context = getActivity();
        getDetails = (Showdetails) this;
        reposList = new ArrayList<>();
        responseViewModel = new ResponseViewModel(context, this);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(layoutManager);

        responseAdapter = new ResponseAdapter(getActivity(), reposList, getDetails);
        binding.recyclerview.setAdapter(responseAdapter);
        binding.recyclerview.addOnScrollListener(recyclerViewOnScrollListener);
        binding.progressCircular.setVisibility(View.VISIBLE);
        responseViewModel.getRepos(repo_id);
    }

    RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    int repo_id = reposList.get(current_page_size-1).getId();
                    binding.progressCircular.setVisibility(View.VISIBLE);
                    responseViewModel.getRepos(repo_id);
                }
            }
        }
    };

    @Override
    public void onGetAcademy(List<JsonResponseModel> jsonResponseModel) {
        for (int i = 0; i < jsonResponseModel.size(); i++) {
            reposList.add(new ResponseModel(jsonResponseModel.get(i).getId(), jsonResponseModel.get(i).getNode_id(), jsonResponseModel.get(i).getName(), jsonResponseModel.get(i).getFull_name(), jsonResponseModel.get(i).getOwner(),
                    jsonResponseModel.get(i).getHtml_url(), jsonResponseModel.get(i).getDescription(), jsonResponseModel.get(i).getFork(), jsonResponseModel.get(i).getUrl(), jsonResponseModel.get(i).getForks_url(), jsonResponseModel.get(i).getKeys_url(),
                    jsonResponseModel.get(i).getCollaborators_url(), jsonResponseModel.get(i).getTeams_url(), jsonResponseModel.get(i).getHooks_url(), jsonResponseModel.get(i).getIssue_events_url(), jsonResponseModel.get(i).getEvents_url(), jsonResponseModel.get(i).getAssignees_url(),
                    jsonResponseModel.get(i).getBranches_url(), jsonResponseModel.get(i).getTags_url(), jsonResponseModel.get(i).getBlobs_url(), jsonResponseModel.get(i).getGit_tags_url(), jsonResponseModel.get(i).getGit_refs_url(), jsonResponseModel.get(i).getTrees_url(),
                    jsonResponseModel.get(i).getStatuses_url(), jsonResponseModel.get(i).getLanguages_url(), jsonResponseModel.get(i).getStargazers_url(), jsonResponseModel.get(i).getContributors_url(), jsonResponseModel.get(i).getSubscribers_url(), jsonResponseModel.get(i).getSubscription_url(),
                    jsonResponseModel.get(i).getCommits_url(), jsonResponseModel.get(i).getGit_commits_url(), jsonResponseModel.get(i).getComments_url(), jsonResponseModel.get(i).getIssue_comment_url(), jsonResponseModel.get(i).getContents_url(), jsonResponseModel.get(i).getCompare_url(),
                    jsonResponseModel.get(i).getMerges_url(), jsonResponseModel.get(i).getArchive_url(), jsonResponseModel.get(i).getDownloads_url(), jsonResponseModel.get(i).getIssues_url(), jsonResponseModel.get(i).getPulls_url(), jsonResponseModel.get(i).getMilestones_url(), jsonResponseModel.get(i).getNotifications_url(),
                    jsonResponseModel.get(i).getLabels_url(), jsonResponseModel.get(i).getReleases_url(), jsonResponseModel.get(i).getDeployments_url()));
        }
        current_page_size = current_page_size+100;
        responseAdapter.notifyDataSetChanged();
        binding.progressCircular.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(ErrorBody errorBody, int statusCode) {
       // Toast.makeText(context, errorBody.Message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTokenExpired(String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDetails(ResponseModel responseModel) {
        Log.i("responseModel", responseModel.getArchive_url());

        ActiveModel.RESPONSE_MODEL = responseModel;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailFragment detailFragment = new DetailFragment();
        fragmentTransaction.replace(R.id.content_frame, detailFragment,"detailpage");
        fragmentTransaction.commit();
    }
}