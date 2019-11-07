package com.project.zhongrenweigong.business.manager;

import com.project.zhongrenweigong.base.BaseModel;
import com.project.zhongrenweigong.business.bean.WorkerListBean;
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
 * 作者：Fuduo on 2019/11/2 11:23
 * 邮箱：duoendeavor@163.com
 * 意图：
 */
public class WorkerManagerPresent extends XPresent<WorkerManagerActivity> {

    public void addWorker(String mcId, String employeesId, String employeesName) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("employeesName", employeesName);
        stringMap.put("employeesId", employeesId);
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().addWorker(requestBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                            getV().getFirstPageData();
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }

    public void deleteWorker(String mcId, String employeesId, final int position) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("employeesId", employeesId);
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().deleteWorker(requestBody)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                           getV().deleteItem(position);
                        } else {
                            ToastManager.showShort(getV(), baseModel.msg);
                        }
                    }
                });
    }

    public void findEmployees(int currentPage,String mcId) {
        Map<String, String> stringMap = BusinessApi.getBasicParamsUidAndToken();
        stringMap.put("pageNum", String.valueOf(10));
        stringMap.put("currentPage", String.valueOf(currentPage));
        stringMap.put("mcId", mcId);
        String body = GsonProvider.gson.toJson(stringMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                body);
        BusinessApi.businessNetManager().findEmployees(requestBody)
                .compose(XApi.<WorkerListBean>getApiTransformer())
                .compose(XApi.<WorkerListBean>getScheduler())
                .compose(getV().<WorkerListBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<WorkerListBean>() {

                    @Override
                    protected void onFail(NetError error) {
                        ToastManager.showShort(getV(), "网络连接失败，请检查网络设置");
                        getV().getDataError();
                    }

                    @Override
                    public void onNext(WorkerListBean workerListBean) {
                        if (workerListBean.getCode() == 200) {
                            getV().setData(workerListBean);
                        }else {
                            ToastManager.showShort(getV(),workerListBean.msg);
                        }
                    }
                });
    }

}
