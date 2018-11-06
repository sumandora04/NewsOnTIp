package com.notepoint4ugmail.newsontip;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Health extends Fragment {


    public Health() {
        // Required empty public constructor
    }
    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?category=health-and-medical&country=in&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewHealth;
    private RecyclerView.Adapter adapterHealth;
    private List<NewsModelClass> newsListItemsHealth;
    private RequestQueue requestQueueHealth;
    private SwipeRefreshLayout swipeRefreshLayoutHealth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health,container,false);
        swipeRefreshLayoutHealth = view.findViewById(R.id.swipe_to_refresh_health);
        requestQueueHealth = Volley.newRequestQueue(getContext());
        recyclerViewHealth =view.findViewById(R.id.recycler_view_health);
        recyclerViewHealth.setHasFixedSize(false);
        recyclerViewHealth.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsHealth = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewHealth,swipeRefreshLayoutHealth,NEWS_URL,newsListItemsHealth,
                adapterHealth,requestQueueHealth);

        swipeRefreshLayoutHealth.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewHealth,swipeRefreshLayoutHealth,NEWS_URL,newsListItemsHealth,
                        adapterHealth,requestQueueHealth);
            }
        });

        swipeRefreshLayoutHealth.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewHealth,swipeRefreshLayoutHealth,NEWS_URL,newsListItemsHealth,
                        adapterHealth,requestQueueHealth);
            }
        });
        return view;
    }

}
