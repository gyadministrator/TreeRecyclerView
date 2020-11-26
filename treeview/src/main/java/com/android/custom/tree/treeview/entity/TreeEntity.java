package com.android.custom.tree.treeview.entity;

/**
 * @ProjectName: TreeRecyclerView
 * @Package: com.android.custom.tree.treeview.entity
 * @ClassName: TreeEntity
 * @Author: 1984629668@qq.com
 * @CreateDate: 2020/11/26 9:04
 */
public class TreeEntity {
    private String id;
    private String title;
    private boolean check;
    private boolean expand;

    private String children;

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public TreeEntity(String id, String title, boolean check, boolean expand, String children) {
        this.id = id;
        this.title = title;
        this.check = check;
        this.expand = expand;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
