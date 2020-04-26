package com.finham.taobaocoupon.ui.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.model.domain.HomePagerContent;
import com.finham.taobaocoupon.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: Fin
 * Date: 2020/4/21
 * Time: 23:25
 */
public class HomePagerRecyclerViewAdapter extends RecyclerView.Adapter<HomePagerRecyclerViewAdapter.ItemHolder> {
    List<HomePagerContent.DataBean> data = new ArrayList<>();
    private onItemClickListener mItemClickListener;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()); //所有的View都有context！
        View view = inflater.inflate(R.layout.item_home_pager, parent, false);
        ButterKnife.bind(this, view);
        return new ItemHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        //holder也要使用ButterKnife！= =第一次用
        HomePagerContent.DataBean dataBean = data.get(position);
        holder.title.setText(dataBean.getTitle());
        ViewGroup.LayoutParams params = holder.cover.getLayoutParams();
        int width = params.width; //淘宝得宽高相同才能显示出来
        int height = params.height;
        int coverSize = (width > height ? width : height) / 2;
        Glide.with(holder.itemView.getContext())
                .load(UrlUtils.getCoverPath(dataBean.getPict_url(), coverSize))
                .into(holder.cover);
        Resources resources = holder.itemView.getContext().getResources();
        float result = Float.parseFloat(dataBean.getZk_final_price()) - (float) (dataBean.getCoupon_amount());

        //holder.offPrice.setText(dataBean.getCoupon_amount()+""); //小技巧：加上字符串后自动变为字符串
        holder.offPrice.setText(String.format(resources.getString(R.string.text_goods_off_price), dataBean.getCoupon_amount()));
        holder.finalPrice.setText(String.format("%.2f", result)); //保留两位小数的意思
        holder.originPrice.setText(String.format(resources.getString(R.string.text_goods_original_price),
                dataBean.getZk_final_price()));
        holder.originPrice.setPaintFlags(Paint.ANTI_ALIAS_FLAG); //添加划掉的效果线
        holder.salesCount.setText(String.format(resources.getString(R.string.text_goods_sell_count), dataBean.getVolume()));
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

    //OOP直接创建
    public void addData(List<HomePagerContent.DataBean> contents) {
        int startPosition = data.size();
        data.addAll(contents);
        //更新UI
        notifyItemRangeChanged(startPosition, contents.size());
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_cover)
        public ImageView cover;
        @BindView(R.id.goods_title)
        public TextView title;
        @BindView(R.id.goods_off_price)
        public TextView offPrice;
        @BindView(R.id.goods_after_off_price)
        public TextView finalPrice;
        @BindView(R.id.goods_original_price)
        public TextView originPrice;
        @BindView(R.id.goods_sell_count)
        public TextView salesCount;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(data.get(getLayoutPosition())); //具体参考我的笔记
                }
            });
        }
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface onItemClickListener{
        //RecyclerView列表内容的点击事件
        void onItemClick(HomePagerContent.DataBean dataBean);
    }
}
