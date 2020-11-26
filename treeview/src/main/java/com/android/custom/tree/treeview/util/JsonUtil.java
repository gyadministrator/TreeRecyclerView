package com.android.custom.tree.treeview.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ProjectName: TreeRecyclerView
 * @Package: com.android.custom.tree.treeview.util
 * @ClassName: JsonUtil
 * @Author: 1984629668@qq.com
 * @CreateDate: 2020/11/26 10:20
 */
public class JsonUtil {
    public static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
