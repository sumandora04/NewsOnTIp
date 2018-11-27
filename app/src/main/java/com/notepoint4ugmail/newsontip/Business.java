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
public class Business extends Fragment {


    public Business() {
        // Required empty public constructor
    }


    private static final String NEWS_URL = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=4f36ccfa5f074a3b8b56eeed0a29634d";
    private RecyclerView recyclerViewBusiness;
    private RecyclerView.Adapter adapterBusiness;
    private List<NewsModelClass> newsListItemsBusiness;
    private RequestQueue requestQueueBusiness;
    private SwipeRefreshLayout swipeRefreshLayoutBusiness;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business,container,false);
        swipeRefreshLayoutBusiness = view.findViewById(R.id.swipe_to_refresh_business);
        requestQueueBusiness = Volley.newRequestQueue(getContext());
        recyclerViewBusiness =view.findViewById(R.id.recycler_view_business);
        recyclerViewBusiness.setHasFixedSize(false);
        recyclerViewBusiness.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity)getActivity()).networkState();

        newsListItemsBusiness = new ArrayList<>();

        ((MainActivity)getActivity()).loadNewsData(recyclerViewBusiness,swipeRefreshLayoutBusiness,NEWS_URL,newsListItemsBusiness,
                adapterBusiness,requestQueueBusiness);

        swipeRefreshLayoutBusiness.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).loadNewsData(recyclerViewBusiness,swipeRefreshLayoutBusiness,NEWS_URL,newsListItemsBusiness,
                        adapterBusiness,requestQueueBusiness);
            }
        });

        swipeRefreshLayoutBusiness.post(new Runnable() {
            @Override
            public void run() {

                ((MainActivity)getActivity()).loadNewsData(recyclerViewBusiness,swipeRefreshLayoutBusiness,NEWS_URL,newsListItemsBusiness,
                        adapterBusiness,requestQueueBusiness);
            }
        });
        return view;
    }

}
