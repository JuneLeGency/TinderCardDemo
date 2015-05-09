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
 * ������Ҫ���ڽ�����Ϣ�ʹ���������<br>
 * APP���Բο����࣬ʵ���Լ���Receiver<br>
 * <p/>
 * �����Ĵ����룺<br>
 * 0����ʾ�ɹ�<br>
 * 1��ϵͳ����ָ��Ƿ����ڴ����� <br>
 * 2���Ƿ�����<br>
 * �������ڲ�����<br>
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
     * ע����
     *
     * @param context         APP�����Ķ���
     * @param errorCode       �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
     * @param registerMessage ע��������
     */
    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult registerMessage) {
        if (context == null || registerMessage == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = registerMessage + "ע��ɹ�";
            // ��������token
            String token = registerMessage.getToken();
        } else {
            text = registerMessage + "ע��ʧ�ܣ������룺" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * ��ע����
     *
     * @param context   APP�����Ķ���
     * @param errorCode �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
     */
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "��ע��ɹ�";
        } else {
            text = "��ע��ʧ��" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * ���ñ�ǩ�������
     *
     * @param context   APP�����Ķ���
     * @param errorCode �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
     * @tagName ��ǩ����
     */
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"���óɹ�";
        } else {
            text = "\"" + tagName + "\"����ʧ��,�����룺" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * ɾ����ǩ�������
     *
     * @param context   APP�����Ķ���
     * @param errorCode �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
     * @tagName ��ǩ����
     */
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = null;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"ɾ���ɹ�";
        } else {
            text = "\"" + tagName + "\"ɾ��ʧ��,�����룺" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * �յ���Ϣ<br>
     *
     * @param context APP�����Ķ���
     * @param message �յ�����Ϣ
     */
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        if (context == null || message == null) {
            return;
        }
        String text = "�յ���Ϣ:" + message.toString();
        // ��ȡ�Զ���key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1Ϊǰ̨���õ�key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP����������Ϣ�Ĺ��̡�����
        Log.d(LogTag, text);
        show(context, text);
    }

    /**
     * ֪ͨ���򿪽������
     *
     * @param context APP�����Ķ���
     * @param message ���򿪵���Ϣ����
     */
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "֪ͨ���� :" + message;
        // ��ȡ�Զ���key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1Ϊǰ̨���õ�key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP��������Ĺ��̡�����
        Log.d(LogTag, text);
        // show(context, text);
    }

    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
        String text = "֪ͨ��չʾ ��title:" + notifiShowedRlt.getTitle()
                + ",content:" + notifiShowedRlt.getContent()
                + ",custom_content:" + notifiShowedRlt.getCustomContent();
        Log.d(LogTag, text);
        show(context, text);
    }
}
