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
public class India extends Fragment {


    public India() {
        // Required empty public constructor
    }
    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?country=in&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewIndia;
    private RecyclerView.Adapter adapterIndia;
    private List<NewsModelClass> newsListItemsIndia;
    private RequestQueue requestQueueIndia;
    private SwipeRefreshLayout swipeRefreshLayoutIndia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_india,container,false);
        swipeRefreshLayoutIndia = view.findViewById(R.id.swipe_to_refresh_india);
        requestQueueIndia = Volley.newRequestQueue(getContext());
        recyclerViewIndia =view.findViewById(R.id.recycler_view_india);
        recyclerViewIndia.setHasFixedSize(false);
        recyclerViewIndia.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsIndia = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewIndia,swipeRefreshLayoutIndia,NEWS_URL,newsListItemsIndia,
                adapterIndia,requestQueueIndia);

        swipeRefreshLayoutIndia.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewIndia,swipeRefreshLayoutIndia,NEWS_URL,newsListItemsIndia,
                        adapterIndia,requestQueueIndia);
            }
        });

        swipeRefreshLayoutIndia.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewIndia,swipeRefreshLayoutIndia,NEWS_URL,newsListItemsIndia,
                        adapterIndia,requestQueueIndia);
            }
        });
        return view;
    }


}
