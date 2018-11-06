package com.notepoint4ugmail.newsontip;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
   // List<NewsModelClass> newsListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NavigationBar.
        mToolbar = findViewById(R.id.navigation_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Creating the object of TabPager.
        TabPager tabPager = new TabPager(getSupportFragmentManager(), MainActivity.this);
        //Linking tabLayout and viewPager to the xml layout.
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(tabPager);
        //setting viewpager to the tabLayout.
        tabLayout.setupWithViewPager(viewPager);

        //For Navigationbar Item Clicks.
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.share_app:
                        shareApp();
                        break;
                    case R.id.rate_app:
                        rateApp();
                        break;
                    case R.id.exit_app:
                        System.exit(1);
                }

                return true;
            }
        });
        }


    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://goo.gl/yNiWCn");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void rateApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.notepoint4ugmail.newsontip"));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //RecyclerView.Adapter adapter;
    public void loadNewsData(final RecyclerView recyclerView, final SwipeRefreshLayout swipeRefreshLayout,
                             String NEWS_URL, final List<NewsModelClass> newsListItems, final RecyclerView.Adapter adapter,
                             RequestQueue requestQueue) {

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                NEWS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar1.setVisibility(View.GONE);
                        //Fetching JSON Response.
                        parseResponse(recyclerView, newsListItems, adapter, response);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressBar1.setVisibility(View.GONE);
                Log.e("Network Error", "" + error.getMessage());
                Toast.makeText(MainActivity.this, "NetworkError", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        requestQueue.add(stringRequest);
    }

    public void parseResponse(RecyclerView recyclerView, List<NewsModelClass> newsListItems,
                              RecyclerView.Adapter adapter, String response) {

        //Fetching JSON Response.
        try {
            if(newsListItems!=null && newsListItems.size()>0)
            {
                newsListItems.clear();
            }

            JSONObject root = new JSONObject(response);
            String status = root.getString("status");

            if (status.equals("ok")) {
                JSONArray array = root.getJSONArray("articles");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    JSONObject source = jsonObject.getJSONObject("source");
                    String name = source.getString("name");

                    NewsModelClass newsModelClass = new NewsModelClass(
                            name,
                            jsonObject.getString("title"),
                            jsonObject.getString("description"),
                            jsonObject.getString("url"),
                            jsonObject.getString("urlToImage"),
                            jsonObject.getString("publishedAt")
                    );


                        newsListItems.add(newsModelClass);

                }

                adapter = new NewsAdapter(newsListItems, MainActivity.this);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }
            if (!status.equals("ok")) {
                Toast.makeText(MainActivity.this, "" + status, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        System.exit(1);
    }

    public void networkState() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (!(activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting())) {
            finish();
            startActivity(new Intent(MainActivity.this, Network.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Checking network state.
        networkState();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        networkState();
    }
}
