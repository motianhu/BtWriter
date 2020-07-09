package com.smona.btwriter.goods.bean;

import android.content.Context;
import android.text.TextUtils;

import com.smona.btwriter.R;

import java.util.HashMap;
import java.util.Map;

public class CategoryContract {
    private   Map<String, String> typeMap = new HashMap<>();
    private String[] types;

    public CategoryContract(Context context) {
        types = context.getResources().getStringArray(R.array.type_array);
        for (int i = 0; i < types.length; i++) {
            typeMap.put((i + 1) + "", types[i]);
        }
    }

    public String getCategoryName(String index) {
        String type = typeMap.get(index);
        if (TextUtils.isEmpty(type)) {
            type = types[types.length - 1];
        }
        return type;
    }
}
