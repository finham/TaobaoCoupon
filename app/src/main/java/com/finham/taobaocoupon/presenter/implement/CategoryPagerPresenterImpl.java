package com.finham.taobaocoupon.presenter.implement;

import com.finham.taobaocoupon.model.Api;
import com.finham.taobaocoupon.model.domain.HomePagerContent;
import com.finham.taobaocoupon.presenter.ICategoryPagerPresenter;
import com.finham.taobaocoupon.utils.RetrofitManager;
import com.finham.taobaocoupon.utils.UrlUtils;
import com.finham.taobaocoupon.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static final int DEFAULT_PAGE = 1;

    //这样写不够完美，我们要将所有实现细节隐藏
    //private static CategoryPagerPresenterImpl sCategoryPagerPresenter = new CategoryPagerPresenterImpl();
    //所以要改成这样：
    private static ICategoryPagerPresenter sCategoryPagerPresenter = new CategoryPagerPresenterImpl();
    private List<HomePagerContent.DataBean> mData;

    private CategoryPagerPresenterImpl() {

    }

    //这边使用的是饿汉式，线程安全。不过由于这边不需要考虑线程的问题，所以用懒汉式的话效率可能会更高一点
    public static ICategoryPagerPresenter getInstance() {
        return sCategoryPagerPresenter;
    }


    @Override
    public void getContentByCategoryId(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {  //少了这个if判断导致每个界面加载了却都变成onLoading！很不细心啊蔡芳汉！
                callback.onLoading();
            }
        }
        //根据分类id去加载内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer targetPage = pagesInfo.get(categoryId);
        if (targetPage == null) {
            targetPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId, targetPage);
        }
        String url = UrlUtils.createHomePagerUrl(categoryId, targetPage);

        Call<HomePagerContent> task = api.getHomePagerContent(url);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    HomePagerContent pagerContent = response.body();
                    //把数据反映到UI上
                    updateUIByContent(pagerContent, categoryId);
                } else {
                    handleError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                handleError(categoryId);
            }
        });
    }

    private void handleError(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId)
                callback.onError();
        }
    }

    /**
     * @param pagerContent
     * @param categoryId   通过categoryId来判断是哪个页面更新UI，这是很重要的区分点
     */
    private void updateUIByContent(HomePagerContent pagerContent, int categoryId) {
        List<HomePagerContent.DataBean> mData = pagerContent.getData();
        //通知UI层更新数据
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                if (callback == null || pagerContent.getData().size() == 0)
                    callback.onEmpty();
                else {
                    int size = mData.size();
                    List<HomePagerContent.DataBean> looperList = mData.subList(size - 5, size); //拿到轮播图的数据，最后五条
                    callback.onLooperListLoaded(looperList); //把数据传递过去~
                    callback.onContentLoaded(mData);
                }
            }
        }
    }

    @Override
    public void loadMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    private List<ICategoryPagerCallback> callbacks = new ArrayList<>();

    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {
        //注册页面的回调时，如果没有的话就把callback加入
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    @Override
    public void unregisterCallback(ICategoryPagerCallback callback) {
        //取消注册时就移除掉
        callbacks.remove(callback);
    }
}
