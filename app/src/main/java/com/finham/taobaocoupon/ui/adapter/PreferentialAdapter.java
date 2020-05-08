package com.finham.taobaocoupon.ui.adapter;

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
import com.finham.taobaocoupon.model.domain.PreferentialContent;
import com.finham.taobaocoupon.utils.ToastUtils;
import com.finham.taobaocoupon.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: Fin
 * Date: 2020/5/6
 * Time: 16:34
 */
public class PreferentialAdapter extends RecyclerView.Adapter<PreferentialAdapter.InnerHolder> {
    private List<PreferentialContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mList = new ArrayList<>();

    public void setData(PreferentialContent content) {
        if (content != null) {
            mList.clear();
            mList.addAll(content.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data());
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preferential, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        PreferentialContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean mapDataBean = mList.get(position);
        holder.setData(mapDataBean);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setMoreData(PreferentialContent moreContent) {
        List<PreferentialContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> map_data =
                moreContent.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        int size = mList.size();
        mList.addAll(map_data);
        notifyItemRangeInserted(size, mList.size());
        ToastUtils.showToast("加载了" + map_data.size() + "条数据");
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.preferential_cover)
        public ImageView cover;

        @BindView(R.id.preferential_content_title_tv)
        public TextView titleTv;

        @BindView(R.id.preferential_origin_prise_tv)
        public TextView originalPriseTv;

        @BindView(R.id.preferential_off_price_tv)
        public TextView offPriceTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(PreferentialContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data) {
            titleTv.setText(data.getTitle());
            // LogUtils.d(this,"pic url --- > " + data.getPict_url());
            String coverPath = UrlUtils.getCoverPath(data.getPict_url());
            Glide.with(cover.getContext()).load(coverPath).into(cover);
            String originalPrise = data.getZk_final_price();
            originalPriseTv.setText("￥" + originalPrise + " ");
            originalPriseTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG); //设置中划线
            int couponAmount = data.getCoupon_amount();
            float originPriseFloat = Float.parseFloat(originalPrise);
            float finalPrise = originPriseFloat - couponAmount;
            offPriceTv.setText("券后价：" + String.format("%.2f", finalPrise));
        }
    }
}
