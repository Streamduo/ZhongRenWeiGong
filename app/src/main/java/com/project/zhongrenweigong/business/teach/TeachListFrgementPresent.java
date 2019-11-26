package com.project.zhongrenweigong.business.teach;

import com.project.zhongrenweigong.business.bean.TeachListBean;
import com.project.zhongrenweigong.business.bean.TeacherListBean;
import com.project.zhongrenweigong.net.BusinessApi;
import com.project.zhongrenweigong.util.GsonProvider;

import java.util.Map;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Fuduo on 2019/11/14 10:59
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class TeachListFrgementPresent extends XPresent<TeachListFragment> {

    public void getEducationTeach(int currentPage, String name,String address,
                                  String lat, String lng) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("shopCategory", String.valueOf(3));
        stringMap.put("shopCategoryDetail", String.valueOf(0));
        stringMap.put("thisAddr", address);
        stringMap.put("name", name);
        stringMap.put("lat", lat);
        stringMap.put("lng", lng);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getEducationTeach(requestBody)
                .compose(XApi.<TeachListBean>getApiTransformer())
                .compose(XApi.<TeachListBean>getScheduler())
                .compose(getV().<TeachListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<TeachListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(TeachListBean teachListBean) {
                        if (teachListBean.getCode() == 200) {
                            getV().setTeachData(teachListBean);
                        } else {
                            ToastManager.showShort(getV().getContext(), teachListBean.msg);
                        }
                    }
                });
    }

    public void getEducationTeacher(int currentPage, String name,String thisAddr) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("shopCategory", String.valueOf(3));
        stringMap.put("shopCategoryDetail", String.valueOf(1));
        stringMap.put("thisAddr", thisAddr);
        stringMap.put("name", name);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().getEducationTeacher(requestBody)
                .compose(XApi.<TeacherListBean>getApiTransformer())
                .compose(XApi.<TeacherListBean>getScheduler())
                .compose(getV().<TeacherListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<TeacherListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV().getContext(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(TeacherListBean teacherListBean) {
                        if (teacherListBean.getCode() == 200) {
                            getV().setTeacherData(teacherListBean);
                        } else {
                            ToastManager.showShort(getV().getContext(), teacherListBean.msg);
                        }
                    }
                });
    }
}
