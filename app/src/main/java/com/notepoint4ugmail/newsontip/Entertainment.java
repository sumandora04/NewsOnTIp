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
public class Entertainment extends Fragment {


    public Entertainment() {
        // Required empty public constructor
    }
    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?category=entertainment&country=in&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewEnter;
    private RecyclerView.Adapter adapterEnter;
    private List<NewsModelClass> newsListItemsEnter;
    private RequestQueue requestQueueEnter;
    private SwipeRefreshLayout swipeRefreshLayoutEnter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entertainment,container,false);
        swipeRefreshLayoutEnter = view.findViewById(R.id.swipe_to_refresh_entertainment);
        requestQueueEnter = Volley.newRequestQueue(getContext());
        recyclerViewEnter =view.findViewById(R.id.recycler_view_entertainment);
        recyclerViewEnter.setHasFixedSize(false);
        recyclerViewEnter.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();
        newsListItemsEnter = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewEnter,swipeRefreshLayoutEnter,NEWS_URL,newsListItemsEnter,
                adapterEnter,requestQueueEnter);

        swipeRefreshLayoutEnter.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewEnter,swipeRefreshLayoutEnter,NEWS_URL,newsListItemsEnter,
                        adapterEnter,requestQueueEnter);
            }
        });

        swipeRefreshLayoutEnter.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewEnter,swipeRefreshLayoutEnter,NEWS_URL,newsListItemsEnter,
                        adapterEnter,requestQueueEnter);
            }
        });
        return view;
    }

}
