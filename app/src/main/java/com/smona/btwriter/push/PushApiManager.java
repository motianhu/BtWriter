package com.smona.btwriter.push;

import android.content.Context;

import com.smona.btwriter.push.bean.TagAliasBean;
import com.smona.btwriter.push.util.TagAliasOperatorHelper;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class PushApiManager {

    private PushApiManager() {
    }

    private static class ParamHolder {
        private static PushApiManager paramCenter = new PushApiManager();
    }

    public static PushApiManager getInstance() {
        return PushApiManager.ParamHolder.paramCenter;
    }

    public void init(Context appContext) {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(appContext);
    }

    public void addTag(Context appContext, String tagBean) {
        Set<String> set = new LinkedHashSet<>();
        set.add(tagBean);

        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        TagAliasOperatorHelper.sequence++;
        tagAliasBean.tags = set;
        tagAliasBean.alias = tagBean;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(appContext.getApplicationContext(),TagAliasOperatorHelper.sequence,tagAliasBean);
    }

    public void removeTag(Context appContext, String tagBean) {
        Set<String> set = new LinkedHashSet<>();
        set.add(tagBean);

        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
        TagAliasOperatorHelper.sequence++;
        tagAliasBean.tags = set;
        tagAliasBean.alias = tagBean;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(appContext.getApplicationContext(),TagAliasOperatorHelper.sequence,tagAliasBean);
    }
}
