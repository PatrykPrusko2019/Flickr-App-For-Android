package com.patrykprusko.flickrapp;

import java.io.Serializable;


/**
 *  photo record
 */
public class Photo implements Serializable {
    private String title;
    private String author;
    private String author_id;
    private String linkLargeImage;
    private String urlImage;
    private String tags;

    public Photo(String title, String author, String author_id, String linkLargeImage, String urlImage, String tags) {
        this.title = title;
        this.author = author;
        this.author_id = author_id;
        this.linkLargeImage = linkLargeImage;
        this.urlImage = urlImage;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLinkLargeImage() {
        return linkLargeImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", author_id='" + author_id + '\'' +
                ", linkLargeImage='" + linkLargeImage + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
