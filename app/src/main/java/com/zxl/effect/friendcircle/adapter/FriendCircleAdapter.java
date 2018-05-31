package com.zxl.effect.friendcircle.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zxl.effect.R;
import com.zxl.effect.friendcircle.beans.FriendCircleBean;
import com.zxl.effect.friendcircle.widget.NineGridView;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * Description
 * Created by zxl on 2018/5/29 下午3:01.
 * Email:444288256@qq.com
 */
public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.CircleViewHolder> {

    private List<FriendCircleBean> mFriendCircle = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private ImageWatcher mImageWatcher;

    private LinearLayoutManager mLayoutManager;
    private RequestOptions mRequestOptions;

    private DrawableTransitionOptions mDrawableTransitionOptions;

    public FriendCircleAdapter(Context mContext, RecyclerView recyclerView,ImageWatcher mImageWatcher) {
        this.mContext = mContext;
        this.mImageWatcher = mImageWatcher;
        this.mLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mRequestOptions = new RequestOptions().centerCrop();
        this.mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
    }

    public void setData(List<FriendCircleBean> data){
        mFriendCircle.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public CircleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CircleViewHolder(mLayoutInflater.inflate(R.layout.friend_circle_item,parent,false));
    }


    @Override
    public void onBindViewHolder(final CircleViewHolder holder, int position) {
        holder.nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, View view) {
                mImageWatcher.show((ImageView) view, holder.nineGridView.getImageViews(),
                        mFriendCircle.get(position).imageUrls);
            }
        });
        holder.nineGridView.setAdapter(new NineImageAdapter(mContext,mRequestOptions,
                mDrawableTransitionOptions,mFriendCircle.get(position).imageUrls));
    }

    @Override
    public int getItemCount() {
        return mFriendCircle.size();
    }

    static class CircleViewHolder extends RecyclerView.ViewHolder{
        NineGridView nineGridView;

        public CircleViewHolder(View itemView) {
            super(itemView);
            nineGridView = itemView.findViewById(R.id.nine_grid_view);
        }
    }
}
