package com.example.a65667.road.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a65667.road.R;
import com.example.a65667.road.bean.SwipeBean;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.VideoItemVH> {

    private Context mContext;
    private LayoutInflater mInfalter;
    private List<SwipeBean> mDatas;

    public VideoItemAdapter(Context context, List<SwipeBean> mDatas){
        mContext = context;
        mInfalter = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public VideoItemVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoItemVH(mInfalter.inflate(R.layout.item_video_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoItemVH holder, final int position) {
        ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(position % 2 == 0 ? true : false);//这句话关掉IOS阻塞式交互效果

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnSwipeListener){
                    mOnSwipeListener.onDel(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);
    }

    private onSwipeListener mOnSwipeListener;

    public onSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

    class VideoItemVH extends RecyclerView.ViewHolder{
        Button btnDelete;
        public VideoItemVH (View itemView){
            super(itemView);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }

}
