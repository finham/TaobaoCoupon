package com.finham.taobaocoupon.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.model.domain.SelectedContent;
import com.finham.taobaocoupon.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: Fin
 * Date: 2020/5/5
 * Time: 11:35
 */
public class SelectedContentAdapter extends RecyclerView.Adapter<SelectedContentAdapter.InnerHolder> {
    List<SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> mList = new ArrayList<>();

    public void setData(SelectedContent content) {
        if (content.getCode() == Constants.SUCCESS_CODE) {
            List<SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> uatm_tbk_item
                    = content.getData().getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
            mList.clear();
            mList.addAll(uatm_tbk_item);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_content,parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean uatmTbkItemBean = mList.get(position);
        holder.setData(uatmTbkItemBean);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.selected_cover)
        public ImageView cover;

        @BindView(R.id.selected_off_prise)
        public TextView offPriseTv;


        @BindView(R.id.selected_title)
        public TextView title;

        @BindView(R.id.selected_buy_btn)
        public TextView buyBtn;

        @BindView(R.id.selected_original_prise)
        public TextView originalPriseTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(SelectedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean itemData) {
            title.setText(itemData.getTitle());
            String pict_url = itemData.getPict_url();
            //LogUtils.d(this,"url -- > " + pict_url);
            Glide.with(itemView.getContext()).load(pict_url).into(cover);
            if(TextUtils.isEmpty(itemData.getCoupon_click_url())) {
                originalPriseTv.setText("晚啦，没有优惠券了");
                buyBtn.setVisibility(View.GONE);
            } else {
                originalPriseTv.setText("原价：" + itemData.getZk_final_price());
                buyBtn.setVisibility(View.GONE);
            }

            if(TextUtils.isEmpty(itemData.getCoupon_info())) {
                offPriseTv.setVisibility(View.GONE);
            } else {
                offPriseTv.setVisibility(View.VISIBLE);
                offPriseTv.setText(itemData.getCoupon_info());
            }
        }
    }
}
