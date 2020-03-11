package com.smona.btwriter.common;

import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.data.LanuageDataCenter;
import com.smona.http.business.BtBuilder;

/**
 * description:
 *  网络请求建造器。参数是动态APIKEY时使用，参考BusinessHttpService。
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 9/23/19 4:03 PM
 */
public class DynamicBuilder<R> extends BtBuilder<R> {
    public DynamicBuilder(int type, String path) {
        super(type, path);
        addHeader("token", AccountDataCenter.getInstance().getAccountInfo().getToken());
        addHeader("language", LanuageDataCenter.getInstance().getLanuage());
    }
}
