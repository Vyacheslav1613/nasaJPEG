package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

class JsonInfo {
    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private String url;

    @JsonProperty("copyright")
    public String getCopyright() {
        return copyright;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("explanation")
    public String getExplanation() {
        return explanation;
    }

    @JsonProperty("hdurl")
    public String getHdurl() {
        return hdurl;
    }

    @JsonProperty("media_type")
    public String getMedia_type() {
        return media_type;
    }

    @JsonProperty("service_version")
    public String getService_version() {
        return service_version;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "copyright: " + copyright +
                ", \ndate: " + date +
                ", \nexplanation: " + explanation +
                ", \nhdurl: " + hdurl +
                ", \nmedia_type: " + media_type +
                ", \nservice_version: " + service_version +
                ", \ntitle: " + title +
                ", \nurl: " + url;
    }
}