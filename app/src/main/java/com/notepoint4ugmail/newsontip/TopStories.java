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
public class TopStories extends Fragment {


    public TopStories() {
        // Required empty public constructor
    }

    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?sources=google-news-in,the-times-of-india,the-hindu&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewTop;
    private RecyclerView.Adapter adapterTop;
    private List<NewsModelClass> newsListItemsTop;
    private RequestQueue requestQueueOne;
    private SwipeRefreshLayout swipeRefreshLayoutTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_stories,container,false);
        swipeRefreshLayoutTop = view.findViewById(R.id.swipe_to_refresh_top);
        requestQueueOne = Volley.newRequestQueue(getContext());
        recyclerViewTop =view.findViewById(R.id.recycler_view_top);
        recyclerViewTop.setHasFixedSize(false);
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsTop = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewTop,swipeRefreshLayoutTop,NEWS_URL,newsListItemsTop,
                adapterTop,requestQueueOne);

        swipeRefreshLayoutTop.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewTop,swipeRefreshLayoutTop,NEWS_URL,newsListItemsTop,
                        adapterTop,requestQueueOne);

            }
        });

        swipeRefreshLayoutTop.post(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewTop,swipeRefreshLayoutTop,NEWS_URL,newsListItemsTop,
                        adapterTop,requestQueueOne);

            }
        });
        return view;
    }

}
