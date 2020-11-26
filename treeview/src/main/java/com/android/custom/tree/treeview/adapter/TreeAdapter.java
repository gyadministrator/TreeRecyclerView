package com.android.custom.tree.treeview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custom.tree.treeview.R;
import com.android.custom.tree.treeview.entity.TreeEntity;
import com.android.custom.tree.treeview.event.PositionEvent;
import com.android.custom.tree.treeview.event.SelectItemEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @ProjectName: TreeRecyclerView
 * @Package: com.android.custom.tree.treeview.adapter
 * @ClassName: TreeAdapter
 * @Author: 1984629668@qq.com
 * @CreateDate: 2020/11/26 9:33
 */
public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {
    private List<TreeEntity> allValues;
    private Context context;

    public TreeAdapter(List<TreeEntity> allValues, Context context) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.allValues = allValues;
        this.context = context;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Object o) {
        if (o instanceof PositionEvent) {
            PositionEvent positionEvent = (PositionEvent) o;
            int position = positionEvent.position;
            for (int i = 0; i < allValues.size(); i++) {
                TreeEntity treeEntity = allValues.get(i);
                if (i != position) {
                    treeEntity.setCheck(false);
                }
            }
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("InflateParams")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final TreeEntity treeEntity = allValues.get(position);
        RecyclerView childView;
        if (treeEntity == null) return;
        holder.tvTitle.setText(treeEntity.getTitle());
        if (!treeEntity.isExpand()) {
            holder.ivLeft.setVisibility(GONE);
        }
        if (treeEntity.isCheck()) {
            holder.ivRight.setImageResource(R.mipmap.checked);
            holder.ivRight.setTag(R.mipmap.checked);
        } else {
            holder.ivRight.setImageResource(R.mipmap.check);
            holder.ivRight.setTag(null);
        }
        String children = treeEntity.getChildren();
        try {
            JSONArray jsonArray = new JSONArray(children);
            if (jsonArray.length() > 0) {
                holder.ivRight.setVisibility(GONE);
                childView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.common_recyclerview, null);
                List<TreeEntity> allValues = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    if (object.has("children")) {
                        allValues.add(new TreeEntity(object.getString("id"), object.getString("deptName"), false, object.getJSONArray("children").length() > 0, object.getJSONArray("children").toString()));
                    } else {
                        allValues.add(new TreeEntity(object.getString("id"), object.getString("deptName"), false, false, ""));
                    }
                }
                TreeAdapter treeAdapter = new TreeAdapter(allValues, context);
                childView.setLayoutManager(new LinearLayoutManager(context));
                childView.setAdapter(treeAdapter);

                childView.setVisibility(GONE);
                holder.item.setTag(childView);
                if (holder.llContent.getChildCount() > 1) {
                    holder.llContent.removeViewAt(holder.llContent.getChildCount() - 1);
                }
                holder.llContent.addView(childView);
            } else {
                holder.ivLeft.setVisibility(GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ivRight.getTag() == null) {
                    holder.ivRight.setImageResource(R.mipmap.checked);
                    holder.ivRight.setTag(R.mipmap.checked);
                    treeEntity.setCheck(true);
                    SelectItemEvent selectItemEvent = new SelectItemEvent();
                    selectItemEvent.treeEntity = treeEntity;
                    EventBus.getDefault().postSticky(selectItemEvent);
                } else {
                    treeEntity.setCheck(false);
                    holder.ivRight.setImageResource(R.mipmap.check);
                    holder.ivRight.setTag(null);
                }
                //PositionEvent positionEvent = new PositionEvent();
                //positionEvent.position = position;
                //EventBus.getDefault().postSticky(positionEvent);
            }
        });
        final RecyclerView finalChildView = (RecyclerView) holder.item.getTag();
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalChildView != null) {
                    if (finalChildView.getVisibility() == VISIBLE) {
                        finalChildView.setVisibility(GONE);
                        holder.ivLeft.setImageResource(R.mipmap.close);
                    } else {
                        finalChildView.setVisibility(VISIBLE);
                        holder.ivLeft.setImageResource(R.mipmap.open);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allValues == null ? 0 : allValues.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLeft;
        TextView tvTitle;
        ImageView ivRight;
        View item;
        LinearLayout llContent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView;
            ivLeft = itemView.findViewById(R.id.iv_left);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivRight = itemView.findViewById(R.id.iv_right);
            llContent = itemView.findViewById(R.id.ll_content);
        }
    }
}
