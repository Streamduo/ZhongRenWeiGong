package com.project.zhongrenweigong.net;


import cn.droidlover.xdroidmvp.log.XLog;


/**
 * API 结果处理
 */
public class ApiHelper {

    public static void showCode(int code, String message) {
        XLog.e("error code :" + code);
//        switch (code) {
//            case -1:
//                BaseApplication.softApplication.showToast("当前网络环境不稳定，请稍后重试");
//                break;
//            case 4000:
//            case 4009:
//                BaseApplication.softApplication.showToast("登陆信息过期，请重新登陆");
//                goGuide();
//                break;
//            case 4007:
//            case 4020:
//                BaseApplication.softApplication.showToast("用户状态异常，请致电400-166-8393");
//                goGuide();
//                break;
//            case 4002:
//                BaseApplication.softApplication.showToast("验证码发送失败");
//                break;
//            case 4003:
//                BaseApplication.softApplication.showToast("该手机号已注册");
//                break;
//            case 4004:
//                BaseApplication.softApplication.showToast("该手机号还没注册哦");
//                break;
//            case 4005:
//                BaseApplication.softApplication.showToast("注册失败，请尝试重新注册");
//                break;
//            case 4006:
//                BaseApplication.softApplication.showToast("该手机号还没注册哦");
//                break;
//            case 4008:
//                BaseApplication.softApplication.showToast("密码错误，请重新输入");
//                break;
//
//            case 4010: // 其他设备登录
//                BaseApplication.softApplication.showToast(message);
//                goGuide();
//                break;
//            case 4011:
//                BaseApplication.softApplication.showToast("验证码错误，请重新输入");
//                break;
//            case 4012:
//                BaseApplication.softApplication.showToast("修改密码失败");
//                break;
//            case 4013:
//                BaseApplication.softApplication.showToast("修改手机号失败");
//                break;
//            case 4014:
//                BaseApplication.softApplication.showToast("内容有敏感词，请重新编辑后发布");
//                break;
//            case 4015:
//                BaseApplication.softApplication.showToast("更新失败，请重新操作");
//                break;
//            case 4016:
//                BaseApplication.softApplication.showToast("抱歉，动态发送失败，请重新操作");
//                break;
//            case 4017:
//                BaseApplication.softApplication.showToast("抱歉，动态删除失败，请重新操作");
//                break;
//            case 4018:
//                BaseApplication.softApplication.showToast("该手机号验证码发送次数超过限制，请24小时后再试");
//                break;
//            case 4019:
//                BaseApplication.softApplication.showToast("抱歉，评论发送失败，请重新操作");
//                break;
//            case 4021:
//                BaseApplication.softApplication.showToast("抱歉，课程添加失败，请重新操作");
//                break;
//            case 4027:
//                BaseApplication.softApplication.showToast("信息过期，请尝试回到上一页面刷新页面");
//                break;
//            case 4028:
//                BaseApplication.softApplication.showToast("IAP校验失败");
//                break;
//            case 4029:
//                BaseApplication.softApplication.showToast("创建订单失败");
//                break;
//            case 4030:
//                BaseApplication.softApplication.showToast("该手机号验证码发送次数超过限制，请30分钟后再试");
//                break;
//            case 4031:
//                BaseApplication.softApplication.showToast("该手机号验证码发送次数超过限制，请60分钟后再试");
//                break;
//            case 4032:
//                BaseApplication.softApplication.showToast("二维码已失效");
//                break;
//            case 4033:
//                BaseApplication.softApplication.showToast("该手机号验证码发送次数超过限制，请10分钟后再试");
//                break;
//            case 4034:
//                BaseApplication.softApplication.showToast("更新订单失败");
//                break;
//            case 4035:
//                BaseApplication.softApplication.showToast("兑换券无效");
//                break;
//            case 4036:
//                BaseApplication.softApplication.showToast("请先开通超级VIP");
//                break;
//            case 4037: // 手机号变更
//            case 4038: // 注册多少分钟后才可以发言
//            case 4039: // 被禁言，测试 1 分钟 3 次
//            case 4040: // 禁言
//            case 4051: // 提现金额不能少于……
//                BaseApplication.softApplication.showToast(message);
//                break;
//            case 4041:
//                BaseApplication.softApplication.showToast("创建分销链接失败");
//                break;
//            case 4042:
//                BaseApplication.softApplication.showToast("授权失败");
//                break;
//            case 4043:
//                BaseApplication.softApplication.showToast("二维码已过期");
//                break;
//            case 4044:
//                BaseApplication.softApplication.showToast("二维码生成失败");
//                break;
//            case 4045:
//                BaseApplication.softApplication.showToast("第三方账号绑定失败");
//                break;
//            case 4046:
//                BaseApplication.softApplication.showToast("余额不足");
//                break;
//            case 4047:
//                BaseApplication.softApplication.showToast("提现金额超过限制");
//                break;
//            case 4048:
//                BaseApplication.softApplication.showToast("更新失败");
//                break;
//            case 4049:
//                BaseApplication.softApplication.showToast("提现密码错误");
//                break;
//            case 4050:
//                BaseApplication.softApplication.showToast("提现失败，请完善用户信息");
//                break;
//            case 4056:
//                BaseApplication.softApplication.showToast("订单删除失败");
//                break;
//            case 4057:
//                BaseApplication.softApplication.showToast("订单已过期");
//                break;
//            case 4058:
//                BaseApplication.softApplication.showToast("申请退款失败");
//                break;
//            case 4059:
//                BaseApplication.softApplication.showToast("发起拼团失败");
//                break;
//            case 4060:
//                BaseApplication.softApplication.showToast("已绑定该第三方平台账号");
//                break;
//            case 4061:
//                BaseApplication.softApplication.showToast("该第三方平台账号已绑定其他账户");
//                break;
//            case 4062:
//                BaseApplication.softApplication.showToast("解绑第三方平台账号失败");
//                break;
//            case 4063:
//                BaseApplication.softApplication.showToast("该手机号验证码发送次数超过限制，请1分钟后再试");
//                break;
//            case 4113:
//                BaseApplication.softApplication.showToast("访问异常，请设置正确的本地时间");
//                break;
//            case 4101:
//            case 4102:
//            case 4103:
//            case 4104:
//            case 4105:
//            case 4106:
//            case 4108:
//            case 4109:
//            case 4110:
//            case 4111:
//            case 4112:
//            case 4115:
//            case 4116:
//            case 5000:
//                BaseApplication.softApplication.showToast("访问异常，请尝试重新打开或重新登录");
//                break;
//            case 10007:
//                BaseApplication.softApplication.showToast("线下活动已在审核中");
//                break;
//            case 10008:
//                BaseApplication.softApplication.showToast("线下活动已通过审核");
//                break;
//            case 10009:
//                BaseApplication.softApplication.showToast("活动已过期");
//                break;
//            case 4052:
//                BaseApplication.softApplication.showToast("当前时间已失效，请重新设置");
//                break;
//            case 4053:
//                BaseApplication.softApplication.showToast("设置计划开始时间失败，请重新设置");
//                break;
//            case 5003://新修改域名接口 重新定义的错误码
//                BaseApplication.softApplication.showToast(message);
//                goGuide();
//                break;
//            default://新修改域名接口后 提示内容均来自后台
////                BaseApplication.softApplication.showToast("未知错误，请致电400-166-8393");
//                BaseApplication.softApplication.showToast(message);
//        }

    }



//    public static void goGuide() {
//        BaseApplication.jumpDetail = "0";
//        //设置激光推送别名
//        JPushInterface.setAlias(BaseApplication.softApplication, "", new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//            }
//        });
//        //code退出，删除用户数据
//        BaseApplication.softApplication.exit();
//        Intent intent = new Intent(BaseApplication.softApplication, GuideActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        BaseApplication.softApplication.startActivity(intent);
//    }
}
