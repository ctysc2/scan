package com.bolue.scan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolue.scan.R;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.codbking.calendar.entity.CalendarData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/6/22.
 */

public class CitysRecycleAdapter extends RecyclerView.Adapter<CitysRecycleAdapter.SimpleAdapterViewHolder>{

    private Context mContext;

    private ArrayList<LabelEntity.DataEntity.City> dataSource = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;

    //更新数据
    public void setData(ArrayList<LabelEntity.DataEntity.City> dataSource){
        this.dataSource = dataSource;
    }
    //设置监听回调
    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public CitysRecycleAdapter(ArrayList<LabelEntity.DataEntity.City> dataSource,Context context){
        this.dataSource = dataSource;
        this.mContext = context;
    }
    @Override
    public CitysRecycleAdapter.SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);
        CitysRecycleAdapter.SimpleAdapterViewHolder vh = new CitysRecycleAdapter.SimpleAdapterViewHolder(v, true);
        vh.text.setTextSize(14);
        vh.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.layout_bac));
        return vh;
    }

    @Override
    public void onBindViewHolder(final CitysRecycleAdapter.SimpleAdapterViewHolder holder, final int position) {
        LabelEntity.DataEntity.City data = dataSource.get(position);
        holder.text.setText(data.getCity_name());
        if(data.isSelected()){
            holder.text.setTextColor(Color.parseColor("#21B7C4"));
        }else{
            holder.text.setTextColor(Color.parseColor("#333333"));
        }
        holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOnItemClickListener.onItemClick(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView text;


        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
