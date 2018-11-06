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
public class Politics extends Fragment {


    public Politics() {
        // Required empty public constructor
    }
    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?category=politics&country=in&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewPolitics;
    private RecyclerView.Adapter adapterPolitics;
    private List<NewsModelClass> newsListItemsPolitics;
    private RequestQueue requestQueuePolitics;
    private SwipeRefreshLayout swipeRefreshLayoutPolitics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_politics,container,false);
        swipeRefreshLayoutPolitics = view.findViewById(R.id.swipe_to_refresh_politics);
        requestQueuePolitics = Volley.newRequestQueue(getContext());
        recyclerViewPolitics =view.findViewById(R.id.recycler_view_politics);
        recyclerViewPolitics.setHasFixedSize(false);
        recyclerViewPolitics.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsPolitics = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewPolitics,swipeRefreshLayoutPolitics,NEWS_URL,newsListItemsPolitics,
                adapterPolitics,requestQueuePolitics);

        swipeRefreshLayoutPolitics.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewPolitics,swipeRefreshLayoutPolitics,NEWS_URL,newsListItemsPolitics,
                        adapterPolitics,requestQueuePolitics);
            }
        });

        swipeRefreshLayoutPolitics.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewPolitics,swipeRefreshLayoutPolitics,NEWS_URL,newsListItemsPolitics,
                        adapterPolitics,requestQueuePolitics);
            }
        });
        return view;
    }
}
