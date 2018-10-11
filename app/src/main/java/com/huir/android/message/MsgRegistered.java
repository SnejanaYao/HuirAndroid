package com.huir.android.message;

import com.huir.android.common.MsgProcessor;
import com.huir.android.process.chat.AudioChatProcessor;
import com.huir.android.process.chat.MsgChatProcessor;

/**
 * 注册所有消息事件
 */
public enum MsgRegistered {
    first(1,new MsgChatProcessor()),
    second(2,new AudioChatProcessor());

    private int msgCode;
    private MsgProcessor msgProcessr;
    private MsgRegistered(int msgCode,MsgProcessor msgProcessr){
        this.msgCode = msgCode;
        this.msgProcessr = msgProcessr;
    }


    public int getMsgCode(){
        return this.msgCode;
    }
    public MsgProcessor getMsgProcessr() {
        return msgProcessr;
    }
}
