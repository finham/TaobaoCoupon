package com.finham.taobaocoupon.model.domain;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/5/11
 * Time: 10:30
 */
public class SearchHistory {
    public List<String> getHistories() {
        return histories;
    }

    public void setHistories(List<String> histories) {
        this.histories = histories;
    }

    private List<String> histories;
}
