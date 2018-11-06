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
public class Technology extends Fragment {


    public Technology() {
        // Required empty public constructor
    }
    private static final String NEWS_URL ="https://newsapi.org/v2/top-headlines?sources=the-verge,hacker-news,techcrunch,techradar,the-next-web&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewTech;
    private RecyclerView.Adapter adapterTech;
    private List<NewsModelClass> newsListItemsTech;
    private RequestQueue requestQueueTech;
    private SwipeRefreshLayout swipeRefreshLayoutTech;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_technology,container,false);
        swipeRefreshLayoutTech = view.findViewById(R.id.swipe_to_refresh_technology);
        requestQueueTech = Volley.newRequestQueue(getContext());
        recyclerViewTech =view.findViewById(R.id.recycler_view_technology);
        recyclerViewTech.setHasFixedSize(false);
        recyclerViewTech.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsTech = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewTech,swipeRefreshLayoutTech,NEWS_URL,newsListItemsTech,
                adapterTech,requestQueueTech);

        swipeRefreshLayoutTech.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewTech,swipeRefreshLayoutTech,NEWS_URL,newsListItemsTech,
                        adapterTech,requestQueueTech);
            }
        });

        swipeRefreshLayoutTech.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewTech,swipeRefreshLayoutTech,NEWS_URL,newsListItemsTech,
                        adapterTech,requestQueueTech);
            }
        });
        return view;
    }

}
