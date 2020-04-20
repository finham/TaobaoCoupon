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
}
