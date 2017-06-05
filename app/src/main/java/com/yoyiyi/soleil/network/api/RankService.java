package com.yoyiyi.soleil.network.api;

import com.yoyiyi.soleil.bean.region.AllRegionRank;
import com.yoyiyi.soleil.network.response.HttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/23 16:25
 * 描述:
 */

public interface RankService {
   /*
    @GET("index/rank/{type}")
    Observable<OriginalRankInfo> getOriginalRanks(@Path("type") String type);

    */


    /**
     * 全区排行
     *
     * @param type
     * @return
     */
    @GET("index/rank/{type}")
    Flowable<HttpResponse<List<AllRegionRank>>> getAllRegionRank(@Path("type") String type);
}
