package com.finham.taobaocoupon.presenter.implement;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.HomePagerContent;
import com.finham.taobaocoupon.presenter.ICategoryPagerPresenter;
import com.finham.taobaocoupon.utils.RetrofitManager;
import com.finham.taobaocoupon.utils.UrlUtils;
import com.finham.taobaocoupon.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * User: Fin
 * Date: 2020/4/20
 * Time: 10:18
 */
public class CategoryPagerPresenterImpl implements ICategoryPagerPresenter {
    //使用Map来管理分类页面，id为int，page也为int
    private Map<Integer, Integer> pagesInfo = new HashMap<>();
    private static final int DEFAULT_PAGE =1;

    //这样写不够完美，我们要将所有实现细节隐藏
    //private static CategoryPagerPresenterImpl sCategoryPagerPresenter = new CategoryPagerPresenterImpl();
    //所以要改成这样：
    private static ICategoryPagerPresenter sCategoryPagerPresenter = new CategoryPagerPresenterImpl();
    private CategoryPagerPresenterImpl(){

    }

    //这边使用的是饿汉式，线程安全。不过由于这边不需要考虑线程的问题，所以用懒汉式的话效率可能会更高一点
    public static ICategoryPagerPresenter getInstance(){
        return sCategoryPagerPresenter;
    }


    @Override
    public void getContentByCategoryId(int categoryId) {
        //根据分类id去加载内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage == null) {
            targetPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId, targetPage);
        }
        String url = UrlUtils.createHomePagerUrl(categoryId,targetPage);

        Call<HomePagerContent> task = api.getHomePagerContent(url);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                if(code == HttpURLConnection.HTTP_OK){
                    HomePagerContent pagerContent = response.body();
                }else {
                    //TODO：错误处理
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                //TODO
            }
        });
    }

    @Override
    public void loadMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {

    }

    @Override
    public void unregisterCallback(ICategoryPagerCallback callback) {

    }
}
