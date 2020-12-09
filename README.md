废话不多说，先上图！！！！！：

![image](https://github.com/gyadministrator/TreeRecyclerView/blob/master/images/spot.png)

怎样使用?

工程的Gradle引入方式：

    repositories {
            google()
            jcenter()
            mavenCentral()
        }

    allprojects {
        repositories {
            google()
            jcenter()
            maven { url 'https://jitpack.io' }
            mavenCentral()
        }
    }

  dependencies {
		 implementation 'com.github.gyadministrator:TreeRecyclerView:1.1'
	}


在activity使用，非常简单。
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


department.json数据结构如下:
{
    "deptName": "组织结构",
    "children": [
        {
            "deptName": "公开司",
            "children": [
                {
                    "deptName": "公开司营运部",
                    "children": [
                        {
                            "deptName": "铜仁中心",
                            "children": [
                                {
                                    "deptName": "铜仁服务区",
                                    "children": [
                                        {
                                            "deptName": "保洁部",
                                            "id": 18
                                        },
                                        {
                                            "deptName": "治安部",
                                            "id": 19
                                        },
                                        {
                                            "deptName": "测试部1",
                                            "id": 65
                                        }
                                    ],
                                    "id": 5
                                },
                                {
                                    "deptName": "服务区1号",
                                    "children": [
                                        {
                                            "deptName": "保洁部",
                                            "id": 62
                                        }
                                    ],
                                    "id": 9
                                },
                                {
                                    "deptName": "A停车区",
                                    "id": 39
                                }
                            ],
                            "id": 2
                        },
                        {
                            "deptName": "遵义中心",
                            "children": [
                                {
                                    "deptName": "虾子服务区",
                                    "children": [
                                        {
                                            "deptName": "保洁部",
                                            "id": 13
                                        },
                                        {
                                            "deptName": "测试",
                                            "id": 59
                                        },
                                        {
                                            "deptName": "管理部",
                                            "id": 15
                                        },
                                        {
                                            "deptName": "餐饮部",
                                            "id": 16
                                        },
                                        {
                                            "deptName": "加油站部",
                                            "id": 17
                                        },
                                        {
                                            "deptName": "商超部门",
                                            "id": 21
                                        },
                                        {
                                            "deptName": "特产销售部",
                                            "id": 14
                                        }
                                    ],
                                    "id": 4
                                },
                                {
                                    "deptName": "AA服务区",
                                    "children": [
                                        {
                                            "deptName": "保安部",
                                            "id": 61
                                        }
                                    ],
                                    "id": 48
                                },
                                {
                                    "deptName": "服务区2号",
                                    "id": 11
                                }
                            ],
                            "id": 3
                        },
                        {
                            "deptName": "兴义中心",
                            "children": [
                                {
                                    "deptName": "长耳营服务区",
                                    "id": 52
                                }
                            ],
                            "id": 50
                        }
                    ],
                    "id": 1
                },
                {
                    "deptName": "亨达总公司",
                    "children": [
                        {
                            "deptName": "黔南事业部",
                            "children": [
                                {
                                    "deptName": "铜仁服务区",
                                    "children": [
                                        {
                                            "deptName": "保洁部",
                                            "id": 18
                                        },
                                        {
                                            "deptName": "治安部",
                                            "id": 19
                                        },
                                        {
                                            "deptName": "测试部1",
                                            "id": 65
                                        }
                                    ],
                                    "id": 5
                                },
                                {
                                    "deptName": "服务区1号",
                                    "children": [
                                        {
                                            "deptName": "保洁部",
                                            "id": 62
                                        }
                                    ],
                                    "id": 9
                                },
                                {
                                    "deptName": "B停车区",
                                    "id": 40
                                },
                                {
                                    "deptName": "A停车区",
                                    "id": 39
                                }
                            ],
                            "id": 8
                        },
                        {
                            "deptName": "黔北事业部",
                            "children": [
                                {
                                    "deptName": "虾子服务区",
                                    "children": [
                                        {
                                            "deptName": "保洁部",
                                            "id": 13
                                        },
                                        {
                                            "deptName": "测试",
                                            "id": 59
                                        },
                                        {
                                            "deptName": "管理部",
                                            "id": 15
                                        },
                                        {
                                            "deptName": "餐饮部",
                                            "id": 16
                                        },
                                        {
                                            "deptName": "加油站部",
                                            "id": 17
                                        },
                                        {
                                            "deptName": "商超部门",
                                            "id": 21
                                        },
                                        {
                                            "deptName": "特产销售部",
                                            "id": 14
                                        }
                                    ],
                                    "id": 4
                                },
                                {
                                    "deptName": "AA服务区",
                                    "children": [
                                        {
                                            "deptName": "保安部",
                                            "id": 61
                                        }
                                    ],
                                    "id": 48
                                },
                                {
                                    "deptName": "服务区2号",
                                    "id": 11
                                }
                            ],
                            "id": 10
                        },
                        {
                            "deptName": "黔西南事业部",
                            "children": [
                                {
                                    "deptName": "长耳营服务区",
                                    "id": 52
                                }
                            ],
                            "id": 51
                        }
                    ],
                    "id": 7
                }
            ],
            "id": 6
        }
    ],
    "id": -1
}