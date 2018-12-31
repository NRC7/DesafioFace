package com.desafiolatam.desafioface.views.main;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.adapters.DevelopersAdapter;
import com.desafiolatam.desafioface.networks.users.GetUsers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private DevelopersAdapter adapter;
    //This variable controls when the infinite scroll request more developers.
    private boolean pendingRequest = false;


    public DevelopersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = view.findViewById(R.id.reloadSrl);

        RecyclerView recyclerView = view.findViewById(R.id.developersRv);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DevelopersAdapter();
        recyclerView.setAdapter(adapter);

        //Infinite scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int position = linearLayoutManager.findLastVisibleItemPosition();
                int total = linearLayoutManager.getItemCount();

                if (total - 10 < position) {

                    if (!pendingRequest) {
                        Map<String, String> map = new HashMap<String, String>();
                        String currentPage = String.valueOf((total/10) + 1);
                        map.put("page", currentPage);
                        new ScrollRequest(4, getContext()).execute(map);
                    }
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pendingRequest = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshLayout.setRefreshing(false);
                        adapter.update();
                    }
                },800);
            }
        });
    }

    public void update(String name) {
        pendingRequest = true;
        adapter.find(name);
    }

    //Infinite scroll request http
    private class ScrollRequest extends GetUsers {


        public ScrollRequest(int additionalPages, Context context) {
            super(additionalPages, context);
        }

        @Override
        protected void onPreExecute() {
            pendingRequest = true;
            refreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            pendingRequest = false;
            adapter.update();
            refreshLayout.setRefreshing(false);
        }
    }
}
