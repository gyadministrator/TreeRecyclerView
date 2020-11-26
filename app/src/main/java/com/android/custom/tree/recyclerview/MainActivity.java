package com.android.custom.tree.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.custom.tree.treeview.adapter.TreeAdapter;
import com.android.custom.tree.treeview.entity.TreeEntity;
import com.android.custom.tree.treeview.event.SelectItemEvent;
import com.android.custom.tree.treeview.util.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        String json = JsonUtil.getJson("department.json", this);
        try {
            JSONObject jsonObject = new JSONObject(json);
            TreeEntity treeEntity = new TreeEntity(jsonObject.getString("id"), jsonObject.getString("deptName"), false, jsonObject.getJSONArray("children").length() > 0, jsonObject.getJSONArray("children").toString());
            List<TreeEntity> allValues = new ArrayList<>();
            allValues.add(treeEntity);
            TreeAdapter treeAdapter = new TreeAdapter(allValues, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(treeAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Object o) {
        if (o instanceof SelectItemEvent) {
            SelectItemEvent selectItemEvent = (SelectItemEvent) o;
            TreeEntity treeEntity = selectItemEvent.treeEntity;
            Toast.makeText(this, treeEntity.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
