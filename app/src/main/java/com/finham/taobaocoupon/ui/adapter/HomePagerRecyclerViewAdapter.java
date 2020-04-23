package com.finham.taobaocoupon.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finham.taobaocoupon.model.domain.HomePagerContent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fin
 * Date: 2020/4/21
 * Time: 23:25
 */
public class HomePagerRecyclerViewAdapter extends RecyclerView.Adapter<HomePagerRecyclerViewAdapter.ItemHolder> {
    List<HomePagerContent.DataBean> data = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //还得创建一个集合来保存数据，这样即使外面数据为空也不会崩溃？（其实我不太懂）
    public void setData(List<HomePagerContent.DataBean> contents) {
        data.clear();
        data.addAll(contents);
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
