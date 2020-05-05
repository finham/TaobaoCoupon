package com.finham.taobaocoupon.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finham.taobaocoupon.R;
import com.finham.taobaocoupon.model.domain.SelectedCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fin
 * Date: 2020/5/4
 * Time: 16:47
 */
public class SelectedCategoryAdapter extends RecyclerView.Adapter<SelectedCategoryAdapter.InnerHolder> {

    private List<SelectedCategory.DataBean> mDataBeanList = new ArrayList<>();
    private int mCurrentSelectedPosition;
    private onLeftCategoryClickListener mOnLeftClickListener;

    /**
     * 设置数据（我个人不会是这种写法= =，我会放到构造方法里，不过也不一定，我最近也有在使用普通方法 2020.05.04）
     *
     * @param category
     */
    public void setData(SelectedCategory category) {
        List<SelectedCategory.DataBean> data = category.getData();
        if (data != null) {
            mDataBeanList.clear();
            mDataBeanList.addAll(data);
            notifyDataSetChanged();
        }
        if (mDataBeanList.size() > 0) {
            mOnLeftClickListener.onLeftCategoryClick(mDataBeanList.get(mCurrentSelectedPosition));
        }
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_selected_category, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TextView tv = holder.itemView.findViewById(R.id.left_category_tv);
        if (mCurrentSelectedPosition == position) {
            holder.itemView.setBackgroundColor(tv.getContext().getResources().getColor(R.color.colorEFEEEE));
        } else {
            holder.itemView.setBackgroundColor(tv.getContext().getResources().getColor(R.color.white));
        }
        SelectedCategory.DataBean dataBean = mDataBeanList.get(position);
        tv.setText(dataBean.getFavorites_title());
        Log.d("SelectedCategoryAdapter", dataBean.getFavorites_title());
        holder.itemView.setOnClickListener(view -> {
            if (mOnLeftClickListener != null && mCurrentSelectedPosition != position) {
                //修改SelectedPosition的位置
                mCurrentSelectedPosition = position;
                mOnLeftClickListener.onLeftCategoryClick(dataBean);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size();
    }

    class InnerHolder extends RecyclerView.ViewHolder {
        InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setLeftCategoryClickListener(onLeftCategoryClickListener listener) {
        this.mOnLeftClickListener = listener;
//        if (mDataBeanList.size() > 0) {
//            mOnLeftClickListener.onLeftCategoryClick(mDataBeanList.get(mCurrentSelectedPosition));
//        }
    }

    /**
     * 创建接口来实现点击事件的响应
     */
    public interface onLeftCategoryClickListener {
        void onLeftCategoryClick(SelectedCategory.DataBean bean);
    }
}
