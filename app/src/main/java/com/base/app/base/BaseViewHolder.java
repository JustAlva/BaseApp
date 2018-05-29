package com.base.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * describe: RecyclerView  ViewHolder 基类
 * creator: keding.zhou
 * date: 2017/12/7 15:27
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private  OnItemClickListener onItemClickListener;
    public BaseViewHolder(View itemView ) {
        super(itemView);
    }

    public BaseViewHolder(View itemView, OnItemClickListener onClickListener) {
        super(itemView);
        onItemClickListener = onClickListener;
        //给itemView设置点击事件监听
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, getAdapterPosition());
    }

    /**
     * 自定义点击接口
     */
    public interface OnItemClickListener {
          void onItemClick(View view, int position);
    }
}
