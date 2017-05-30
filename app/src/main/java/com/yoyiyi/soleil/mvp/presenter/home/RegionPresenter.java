package com.yoyiyi.soleil.mvp.presenter.home;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yoyiyi.soleil.base.BaseListSubscriber;
import com.yoyiyi.soleil.base.RxPresenter;
import com.yoyiyi.soleil.bean.region.Region;
import com.yoyiyi.soleil.bean.region.RegionType;
import com.yoyiyi.soleil.mvp.contract.home.RegionContract;
import com.yoyiyi.soleil.network.helper.RetrofitHelper;
import com.yoyiyi.soleil.rx.RxUtils;
import com.yoyiyi.soleil.utils.AppUtils;
import com.yoyiyi.soleil.utils.FileUtils;
import com.yoyiyi.soleil.utils.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/23 22:04
 * 描述:首页直播Presenter
 */
public class RegionPresenter extends RxPresenter<RegionContract.View> implements RegionContract.Presenter<RegionContract.View> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RegionPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRegionData() {
        BaseListSubscriber<Region> subscriber = Flowable.just(readJson())
                .flatMap(string -> {
                    Gson gson = new Gson();
                    JsonObject object = new JsonParser().parse(string).getAsJsonObject();
                    JsonArray array = object.getAsJsonArray("data");
                    List<RegionType> regionTypes = new ArrayList<>();
                    for (JsonElement jsonElement : array) {
                        regionTypes.add(gson.fromJson(jsonElement, RegionType.class));
                    }
                    mView.showRegionType(regionTypes);
                    return mRetrofitHelper.getRegion();
                })
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseListSubscriber<Region>(mView) {
                    @Override
                    public void onSuccess(List<Region> regions) {
                        mView.showRegion(regions);
                    }
                });
        addSubscribe(subscriber);
    }

    /**
     * 读取本地Json数据
     */
    private String readJson() {
        InputStream inputStream = FileUtils.openAssetFile(AppUtils.getAppContext(), "region.json");
        String jsonStr = IOUtils.streamToString(inputStream);
        return jsonStr;

    }
}
