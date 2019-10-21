package com.project.zhongrenweigong.base;

import java.util.List;

public interface PressionListener {
    //授权成功的接口
    void onGranted();
    //授权失败的接口
    void onFailure(List<String> failurePression);
}
