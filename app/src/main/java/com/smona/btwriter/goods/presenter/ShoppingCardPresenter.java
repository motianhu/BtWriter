package com.smona.btwriter.goods.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.goods.model.ShoppingCardModel;

public class ShoppingCardPresenter extends BasePresenter<ShoppingCardPresenter.IShoppingCardView> {

    private ShoppingCardModel shoppingCardModel = new ShoppingCardModel();

    public void requestShoppingList() {

    }

    public interface IShoppingCardView extends ICommonView {

    }
}
