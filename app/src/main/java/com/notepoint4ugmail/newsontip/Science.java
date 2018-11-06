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
public class Science extends Fragment {


    public Science() {
        // Required empty public constructor
    }

    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?category=science-and-nature&country=in&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewScience;
    private RecyclerView.Adapter adapterScience;
    private List<NewsModelClass> newsListItemsScience;
    private RequestQueue requestQueueScience;
    private SwipeRefreshLayout swipeRefreshLayoutScience;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_science,container,false);
        swipeRefreshLayoutScience = view.findViewById(R.id.swipe_to_refresh_science);
        requestQueueScience = Volley.newRequestQueue(getContext());
        recyclerViewScience =view.findViewById(R.id.recycler_view_science);
        recyclerViewScience.setHasFixedSize(false);
        recyclerViewScience.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsScience = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewScience,swipeRefreshLayoutScience,NEWS_URL,newsListItemsScience,
                adapterScience,requestQueueScience);

        swipeRefreshLayoutScience.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewScience,swipeRefreshLayoutScience,NEWS_URL,newsListItemsScience,
                        adapterScience,requestQueueScience);
            }
        });

        swipeRefreshLayoutScience.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewScience,swipeRefreshLayoutScience,NEWS_URL,newsListItemsScience,
                        adapterScience,requestQueueScience);
            }
        });
        return view;
    }
}
