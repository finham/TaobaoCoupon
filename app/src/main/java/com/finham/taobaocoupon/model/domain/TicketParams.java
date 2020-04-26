package com.finham.taobaocoupon.model.domain;

/**
 * User: Fin
 * Date: 2020/4/26
 * Time: 23:08
 */
public class TicketParams {
    private String url;

    public TicketParams(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
}
