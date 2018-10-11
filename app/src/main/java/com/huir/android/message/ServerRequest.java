package com.huir.android.message;

/**
 * 处理客户端接收消息
 */
public class ServerRequest {
    private String deStr;
    private int command;

    public  ServerRequest(int command,String deStr){
        //TODO 解密
        //
        deStr = "解密出来的从字段";
    }


    /**
     * 获取消息协议号
     * @return  消息协议号
     */
    public int getMsgCommand(){
        return command;
    }

    /**
     * 获取消息协议号
     * @return  消息协议号
     */
    public String getMsgDecode(){
        return deStr;
    }
}
