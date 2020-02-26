package com.smona.btwriter.goods.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.goods.bean.ResShoppingCardList;
import com.smona.btwriter.goods.bean.RespGoodsList;
import com.smona.btwriter.goods.model.ShoppingCardModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class ShoppingCardPresenter extends BasePresenter<ShoppingCardPresenter.IShoppingCardView> {

    private ShoppingCardModel shoppingCardModel = new ShoppingCardModel();

    public void requestShoppingList() {
        shoppingCardModel.requestList(new OnResultListener<BaseResponse<ResShoppingCardList>>() {
            @Override
            public void onSuccess(BaseResponse<ResShoppingCardList> response) {
                if(mView != null) {
                    mView.onList(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IShoppingCardView extends ICommonView {
        void onList(ResShoppingCardList shoppingCardList);
    }
}
