package com.patrykprusko.flickrapp;

public class Photo {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getLinkLargeImage() {
        return linkLargeImage;
    }

    public void setLinkLargeImage(String linkLargeImage) {
        this.linkLargeImage = linkLargeImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
