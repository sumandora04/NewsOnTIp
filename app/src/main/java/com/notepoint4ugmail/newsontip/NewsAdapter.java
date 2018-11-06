package com.notepoint4ugmail.newsontip;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by SUMAN SHEKHAR on 14-Jan-18.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsModelClass> newsModelClassList;
    private Context context;

    public NewsAdapter(List<NewsModelClass> newsModelClassList, Context context) {
        this.newsModelClassList = newsModelClassList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_news_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Getting current items from the list.
        final NewsModelClass nmc = newsModelClassList.get(position);

        if (nmc.getHeading().length()>90) {
            holder.heading.setText(nmc.getHeading().substring(0, 90) + "...");
        }else {
            holder.heading.setText(nmc.getHeading());
        }
        holder.author.setText(nmc.getAuthor());

        /*
        *  Setting the Published time of the article.
        * */
        String originalPublishedAt = nmc.getPublishedTime();
        long millisSinceEpoch = new DateTime( originalPublishedAt ).getMillis();

        Date dateObject = new Date(millisSinceEpoch);

        String publicationDate = formatDate(dateObject);
        String publicationTime = formatTime(dateObject);
        holder.publishedTime.setText(publicationDate+", "+publicationTime);

        final String descriptionUrl = nmc.getUrlToDescription();
        holder.shareContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ""+descriptionUrl);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });

        if (nmc.getDescription().equals("null")|| nmc.getDescription().equals(" ")){
            holder.description.setVisibility(View.GONE);
        }else{
            if (nmc.getDescription().length()>100) {
                holder.description.setText(nmc.getDescription().substring(0,100)+"...");
            }else {
                holder.description.setText(nmc.getDescription());
            }
        }

        if (nmc.getUrlToImage().equals("null")){
            holder.newsImage.setVisibility(View.GONE);
        }else{
            Picasso.with(context)
                    .load(nmc.getUrlToImage())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.newsImage);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FullNewsDescription.class);
                intent.putExtra("url",nmc.getUrlToDescription());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
       // Log.e("ListSize",""+newsModelClassList.size());
        return newsModelClassList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView heading,author,publishedTime,description;
        private ImageView newsImage,shareContent;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout_single_news);
            heading = itemView.findViewById(R.id.text_View_Header);
            author = itemView.findViewById(R.id.text_View_Author);
            publishedTime = itemView.findViewById(R.id.text_View_Published_time);
            description = itemView.findViewById(R.id.text_View_Description);
            newsImage = itemView.findViewById(R.id.image_news);
            shareContent = itemView.findViewById(R.id.image_share);
        }
    }


    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
