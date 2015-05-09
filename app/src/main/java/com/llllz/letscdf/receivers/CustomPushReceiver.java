package com.llllz.letscdf.receivers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 本类主要用于接收消息和处理结果反馈<br>
 * APP可以参考本类，实现自己的Receiver<br>
 * <p/>
 * 常见的错误码：<br>
 * 0：表示成功<br>
 * 1：系统错误，指针非法，内存错误等 <br>
 * 2：非法参数<br>
 * 其它：内部错误<br>
 * <p/>
 * <p/>
 * Copyright (c) 1998-2014 Tencent
 *
 * @author foreachli Email: foreachli@tencent.com
 */
public class CustomPushReceiver extends XGPushBaseReceiver {
    public static final String LogTag = "TPushReceiver";

    private void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册结果
     *
     * @param context         APP上下文对象
     * @param errorCode       错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
     * @param registerMessage 注册结果返回
     */
    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult registerMessage) {
        if (context == null || registerMessage == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = registerMessage + "注册成功";
            // 在这里拿token
            String token = registerMessage.getToken();
        } else {
            text = registerMessage + "注册失败，错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * 反注册结果
     *
     * @param context   APP上下文对象
     * @param errorCode 错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
     */
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * 设置标签操作结果
     *
     * @param context   APP上下文对象
     * @param errorCode 错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
     * @tagName 标签名称
     */
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * 删除标签操作结果
     *
     * @param context   APP上下文对象
     * @param errorCode 错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
     * @tagName 标签名称
     */
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * 收到消息<br>
     *
     * @param context APP上下文对象
     * @param message 收到的消息
     */
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        if (context == null || message == null) {
            return;
        }
        String text = "收到消息:" + message.toString();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程。。。
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * 通知被打开结果反馈
     *
     * @param context APP上下文对象
     * @param message 被打开的消息对象
     */
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "通知被打开 :" + message;
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
        Log.d(LogTag, text);
        // show(context, text);
    }

    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
        String text = "通知被展示 ，title:" + notifiShowedRlt.getTitle()
                + ",content:" + notifiShowedRlt.getContent()
                + ",custom_content:" + notifiShowedRlt.getCustomContent();
        Log.d(LogTag, text);
        show(context, text);
    }
}
