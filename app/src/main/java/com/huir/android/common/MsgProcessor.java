package com.huir.android.common;

import android.util.Log;

import com.huir.android.message.ServerRequest;

/**
 * 所有消息处理器的父类
 */
public abstract class MsgProcessor {

    private String TAG = "MsgProcessr";

    public void handle(ServerRequest request){
        try {
            process(request);
        }catch (Exception e){
            System.out.print(e.getStackTrace());
            Log.e(TAG, "handle: " + e.getMessage());
        }
    }

    public abstract  void  process(ServerRequest request) throws  Exception;
}
