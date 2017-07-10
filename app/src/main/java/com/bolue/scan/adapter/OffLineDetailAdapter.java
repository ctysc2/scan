package com.bolue.scan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bolue.scan.R;
import com.bolue.scan.greendaohelper.SignHelper;
import com.bolue.scan.listener.OnItemClickListener;
import com.bolue.scan.mvp.entity.LabelEntity;
import com.bolue.scan.mvp.entity.OffLineLessonEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/6/24.
 */

public class OffLineDetailAdapter extends BaseRecyclerAdapter<OffLineDetailAdapter.SimpleAdapterViewHolder> {

    private Context mContext;

    private ArrayList<OffLineLessonEntity.DataEntity.Member> dataSource = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;

    private int id;

    //更新数据
    public void setData(ArrayList<OffLineLessonEntity.DataEntity.Member> dataSource){
        this.dataSource = dataSource;
    }
    //设置监听回调
    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public OffLineDetailAdapter(ArrayList<OffLineLessonEntity.DataEntity.Member> dataSource, Context context,int id){
        this.dataSource = dataSource;
        this.mContext = context;
        this.id = id;
    }
    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return dataSource.size();
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view, false);
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.detail_list, parent, false);
        OffLineDetailAdapter.SimpleAdapterViewHolder vh = new OffLineDetailAdapter.SimpleAdapterViewHolder(v, true);

        return vh;
    }

    @Override
    public void onBindViewHolder(final OffLineDetailAdapter.SimpleAdapterViewHolder holder, final int position,boolean isItem) {
        OffLineLessonEntity.DataEntity.Member data = dataSource.get(position);
        holder.mName.setText(data.getName());

        if(data.getStatus() == 5 || SignHelper.getInstance().getSign(id,data.getCheckcode()) != null){
            holder.mStatus.setText("已参会");
            holder.mStatus.setTextColor(Color.parseColor("#EA9333"));
        }else{
            holder.mStatus.setText("待参会");
            holder.mStatus.setTextColor(Color.parseColor("#769AA2"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });

    }



    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_container)
        RelativeLayout mContainer;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_status)
        TextView mStatus;



        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if(isItem){
                ButterKnife.bind(this, itemView);
            }

        }
    }

    public void insert(OffLineLessonEntity.DataEntity.Member person, int position) {
        insert(dataSource, person, position);
    }

    public void remove(int position) {
        remove(dataSource, position);
    }

    public void clear() {
        clear(dataSource);
    }

    public OffLineLessonEntity.DataEntity.Member getItem(int position) {
        if (position < dataSource.size())
            return dataSource.get(position);
        else
            return null;
    }

}
