package com.bolue.scan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolue.scan.R;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;
import com.bolue.scan.utils.DimenUtil;
import com.bumptech.glide.Glide;
import com.codbking.calendar.entity.CalendarData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalRecyclerAdapter extends RecyclerView.Adapter<NormalRecyclerAdapter.SimpleAdapterViewHolder> {
    private List<CalendarData> list;

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;

    public NormalRecyclerAdapter(List<CalendarData> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }
    @Override
    public void onBindViewHolder(SimpleAdapterViewHolder holder,final  int position) {
        CalendarData data = list.get(position);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<CalendarData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recycleview, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }

    public void insert(CalendarData entity, int position) {
        list.add(position, entity);
        notifyItemInserted(position);
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

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public CalendarData getItem(int position) {
        if (position < list.size())
            return list.get(position);
        else
            return null;
    }

}