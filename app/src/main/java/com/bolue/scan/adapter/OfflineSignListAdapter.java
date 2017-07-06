package com.bolue.scan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolue.scan.R;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.OffLineSignedEntity;
import com.bolue.scan.utils.DimenUtil;
import com.bolue.scan.widget.SwipeMenuLayout;
import com.codbking.calendar.entity.CalendarData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/7/4.
 */

public class OfflineSignListAdapter extends RecyclerView.Adapter<OfflineSignListAdapter.SimpleAdapterViewHolder> {

    private List<OffLineSignedEntity> list;

    private OnItemClickListener mOnItemClickListener;

    private OnItemClickListener mOnSelectListener;

    private OnItemClickListener mOnDeleteListener;

    private Context mContext;

    private boolean isEdit = false;

    public OfflineSignListAdapter(List<OffLineSignedEntity> list, Context context) {
        this.list = list;
        this.mContext = context;
    }


    @Override
    public OfflineSignListAdapter.SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_sign_list, parent, false);
        OfflineSignListAdapter.SimpleAdapterViewHolder vh = new OfflineSignListAdapter.SimpleAdapterViewHolder(v, true);
        return vh;
    }

    @Override
    public void onBindViewHolder(SimpleAdapterViewHolder holder, final int position) {

        OffLineSignedEntity data = list.get(position);
        holder.mBrief.setImageURI(data.getBrief_image());
        holder.mTitle.setText(data.getTitle());
        holder.mPart.setText("共"+data.getJoin_num()+"人参会");

        switch (data.getStatus()){
            case 0:
                holder.mStatus.setText("已结束");
                holder.mStatus.setTextColor(Color.parseColor("#06B5C8"));
                break;
            case 1:
                holder.mStatus.setText("举办中");
                holder.mStatus.setTextColor(Color.parseColor("#21B7C4"));
                break;
            case 2:
            default:
                holder.mStatus.setText("未开始");
                holder.mStatus.setTextColor(Color.parseColor("#F15114"));
                break;

        }


        holder.mRlDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteListener.onItemClick(position);
            }
        });

        if(this.isEdit == true){
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mLLContainer.setPadding((int)DimenUtil.dp2px(5),(int)DimenUtil.dp2px(15),(int)DimenUtil.dp2px(10),(int)DimenUtil.dp2px(10));
            if(data.isSelected()){
                if(!holder.mCheckBox.isSelected()){
                    holder.mCheckBox.setSelected(true);
                    final ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(50);
                    holder.mCheckBox.startAnimation(animation);
                }
            }else{
                holder.mCheckBox.setSelected(false);
            }
            //holder.mSwipe.setSwipeEnable(false);
            holder.mLLContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnSelectListener.onItemClick(position);
                }
            });
        }else{
            holder.mCheckBox.setVisibility(View.GONE);
            //holder.mSwipe.setSwipeEnable(true);
            holder.mLLContainer.setPadding((int)DimenUtil.dp2px(15),(int)DimenUtil.dp2px(15),(int)DimenUtil.dp2px(10),(int)DimenUtil.dp2px(10));
            holder.mLLContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }
    public void setOnSelectClickListener(OnItemClickListener listener){
        mOnSelectListener = listener;
    }
    public void setOnDeleteClickListener(OnItemClickListener listener){
        mOnDeleteListener = listener;
    }

    public void setData(List<OffLineSignedEntity> list,boolean isEdit) {
        this.list = list;
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void delete(int position) {
        if(position>=list.size())
            return;

        list.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position,getItemCount()-position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.card_view)
        View rootView;

        @BindView(R.id.iv_breif)
        SimpleDraweeView mBrief;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.tv_part)
        TextView mPart;

        @BindView(R.id.tv_status)
        TextView mStatus;


        @BindView(R.id.iv_checkbox)
        ImageView mCheckBox;

        @BindView(R.id.swipe)
        SwipeMenuLayout mSwipe;

        @BindView(R.id.ll_container)
        LinearLayout mLLContainer;

        @BindView(R.id.rl_del)
        RelativeLayout mRlDel;


        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
