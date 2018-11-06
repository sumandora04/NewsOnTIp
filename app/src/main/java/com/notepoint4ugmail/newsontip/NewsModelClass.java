package com.notepoint4ugmail.newsontip;

/**
 * Created by SUMAN SHEKHAR on 14-Jan-18.
 */

public class NewsModelClass {
    private String author;
    private String heading;
    private String description;
    private String urlToDescription;
    private String urlToImage;
    private String publishedTime;

    public NewsModelClass(
            String author, String heading, String description, String urlToDescription,
            String urlToImage, String publishedTime) {
        this.author = author;
        this.heading = heading;
        this.description = description;
        this.urlToDescription = urlToDescription;
        this.urlToImage = urlToImage;
        this.publishedTime = publishedTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToDescription() {
        return urlToDescription;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedTime() {
        return publishedTime;
    }


}

