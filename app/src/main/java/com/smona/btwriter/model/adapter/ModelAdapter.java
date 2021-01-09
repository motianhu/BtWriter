package com.smona.btwriter.model.adapter;

import android.text.TextUtils;
import android.widget.Filter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.model.holder.ModelHolder;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

import java.util.ArrayList;
import java.util.List;

public class ModelAdapter extends XBaseAdapter<ModelBean, ModelHolder> {

    private SearchFilter filter;

    public ModelAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(ModelHolder holder, ModelBean item, int pos) {
        holder.bindViews(item);
        holder.itemView.setOnClickListener(v-> ARouterManager.getInstance().sgotoActivitySble(ARouterPath.PATH_TO_MAKE, item));
    }

    class SearchFilter extends Filter {

        private List<ModelBean> original;

        public SearchFilter(List<ModelBean> list) {
            this.original = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                results.values = original;
                results.count = original.size();
            } else {
                List<ModelBean> mList = new ArrayList<>();
                for (ModelBean modelBean : original) {
                    if (modelBean.getPhoneModel().toLowerCase().contains(constraint.toString().trim().toLowerCase())) {
                        mList.add(modelBean);
                    }
                }
                results.values = mList;
                results.count = mList.size();
            }
            // 返回FilterResults对象
            return results;
        }

        /**
         * 该方法用来刷新用户界面，根据过滤后的数据重新展示列表
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // 获取过滤后的数据
            mDataList = (List<ModelBean>) results.values;
            // 刷新数据源显示
            notifyDataSetChanged();
        }
    }

    public Filter getFilter() {
        if(filter == null) {
            filter = new SearchFilter(mDataList);
        }
        return filter;
    }
}
