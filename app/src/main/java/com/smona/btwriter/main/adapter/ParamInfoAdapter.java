package com.smona.btwriter.main.adapter;


import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.main.holder.ParamInfoHolder;

public class ParamInfoAdapter extends XBaseAdapter<ParamInfo, ParamInfoHolder> {

    private OnParamListener onParamListener;

    public ParamInfoAdapter(int resId) {
        super(resId);
    }

    public void setOnParamListener(OnParamListener onParamListener) {
        this.onParamListener = onParamListener;
    }

    @Override
    protected void convert(ParamInfoHolder holder, ParamInfo item, int pos) {
        holder.bindViews(item);
        holder.editView.setOnClickListener(v-> clickEdit(item));
        holder.deleteView.setOnClickListener(v-> clickDelete(item));
        holder.useView.setOnClickListener(v-> clickUse(item));
    }

    private void clickEdit(ParamInfo item) {
        if(onParamListener != null) {
            onParamListener.onEdit(item);
        }
    }

    private void clickDelete(ParamInfo item) {
        if(onParamListener != null) {
            onParamListener.onDelete(item);
        }
    }

    private void clickUse(ParamInfo item) {
        if(onParamListener != null) {
            onParamListener.onUse(item);
        }
    }

    public void delParam(int id) {
        for(ParamInfo paramInfo: mDataList) {
            if(paramInfo.getId() == id) {
                mDataList.remove(paramInfo);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public interface OnParamListener {
        void onEdit(ParamInfo item);
        void onDelete(ParamInfo item);
        void onUse(ParamInfo item);
    }
}