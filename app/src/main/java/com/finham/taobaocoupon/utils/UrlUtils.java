package com.finham.taobaocoupon.utils;

/**
 * User: Fin
 * Date: 2020/4/20
 * Time: 11:52
 */
public class UrlUtils {
    public static String createHomePagerUrl(int materialId, int page) {
        return "discovery/" + materialId + "/" + page;
    }

    public static String getCoverPath(String url, int size) {
        return "https:" + url + "_" + size + "x" + size + ".jpg"; //这里是小写的x
    }

    public static String getCoverPath(String url) {
        return "https:" + url;
    }

    public static String getTicketUrl(String url) {
        if (url.startsWith("http") || url.startsWith("https")) {
            return url;
        } else {
            return "https:" + url;
        }
    }

    public static String getSelectedContentUrl(int categoryId) {
        return "/recommend/" + categoryId;
    }
}
