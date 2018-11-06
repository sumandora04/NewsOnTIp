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
public class Sports extends Fragment {


    public Sports() {
        // Required empty public constructor
    }

    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?category=sport&country=in&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewSports;
    private RecyclerView.Adapter adapterSports;
    private List<NewsModelClass> newsListItemsSports;
    private RequestQueue requestQueueSports;
    private SwipeRefreshLayout swipeRefreshLayoutSports;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports,container,false);
        swipeRefreshLayoutSports = view.findViewById(R.id.swipe_to_refresh_sports);
        requestQueueSports = Volley.newRequestQueue(getContext());
        recyclerViewSports =view.findViewById(R.id.recycler_view_sports);
        recyclerViewSports.setHasFixedSize(false);
        recyclerViewSports.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsSports = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewSports,swipeRefreshLayoutSports,NEWS_URL,newsListItemsSports,
                adapterSports,requestQueueSports);

        swipeRefreshLayoutSports.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewSports,swipeRefreshLayoutSports,NEWS_URL,newsListItemsSports,
                        adapterSports,requestQueueSports);
            }
        });

        swipeRefreshLayoutSports.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewSports,swipeRefreshLayoutSports,NEWS_URL,newsListItemsSports,
                        adapterSports,requestQueueSports);
            }
        });
        return view;
    }
}
