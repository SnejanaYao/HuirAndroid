package com.huir.android.message;

import android.util.Log;


import com.huir.android.common.MsgProcessor;
import com.huir.android.entity.Msg;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息分发，根据消息号找到对应的消息协议
 */
public class MsgDispatcher {
    private String TAG = "MsgDispatcher";
    public Map<Integer,MsgProcessor> processorMap = new HashMap<Integer,MsgProcessor>();

    public MsgDispatcher(){
        for (MsgRegistered msgRegistered : MsgRegistered.values()){
            processorMap.put(msgRegistered.getMsgCode(),msgRegistered.getMsgProcessr()); //转存成map
            Log.e(TAG, "MsgDispatcher:[msgCode:"+msgRegistered.getMsgCode()+"]  [Processor:"+msgRegistered.getMsgProcessr().hashCode()+"]" );
        }
    }

    /**
     * 根据消息号取得对应的processor
     * @param msgCode  获取到的消息号
     * @return MsgProcessor
     */
    public MsgProcessor getProcessor(Integer msgCode){
        MsgProcessor msgProcessor = processorMap.get(msgCode);
        return msgProcessor;
    }

    public  void  getMsgDispatcher(ServerRequest request){
        MsgProcessor msgProcessor = getProcessor(request.getMsgCommand());
        msgProcessor.handle(request);
    }
}
