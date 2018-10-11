package com.huir.android.net;

import android.util.Log;

import java.net.Socket;

/**
 * 解码消息
 */
public class Decode {

    public Socket socket;
    public Decode(Socket socket){
            this.socket = socket;
    }
/*
    private void deCode() {
        new Thread() {
            @Override
            public void run() {
                byte[] temp = null;
                turn01:
                while (flagThread) {
                    try {
                        sleep(1000);
                        inPut = socket.getInputStream();
                        int len  = inPut.available();
                        if(len<0) {
                            sleep(3000);
                        }else {
                            Log.e(THR,len+"");
                        }
                        byte[] tempb = new byte[len];
                        inPut.read(tempb, 0, len);
                        if(temp != null) {
                            sleep(1000);
                            Log.e(THR , "《===断包后的处理===》");
                            Log.e(THR , "上次读取的长度为: " + temp.length);
                            Log.e(THR , "本次读取到的长度为: " + tempb.length);
                            byte[] all = new byte[temp.length+tempb.length];
                            System.arraycopy(temp, 0, all, 0, temp.length);
                            System.arraycopy(tempb, 0, all, temp.length, tempb.length);
                            tempb = all;
                            Log.e(THR,"断包 处理完成后的长度为: " + tempb.length);
                        }
                        sleep(1000);
                        turn02:
                        while (flag103) {
                            sleep(1000);
                            Log.e("tempb  " , new String(tempb,"utf-8"));
                            if(tempb[0] ==-103) {
                                sleep(2000);
                                String heart = new String(tempb, "utf-8");
                                Log.e(THR,"《====读到心跳包====》 "+heart);
                                if(len>1) {
                                    byte[] b = new byte[len-1]; //去除心跳包后的长 用来实现复制
                                    byte[] t = tempb; //
                                    System.arraycopy(t,1,b,0,b.length);
                                    tempb = b;
                                    len = len-1;
                                }
                                continue turn01;
                            }
                            Log.e(THR, "《================开始解码================》");
                            command = tempb[0]; //标识位
                            Log.e(THR,"command "  + command);
                            str_length = tempb[1];
                            if(str_length >0) { //过滤掉非法的数据包
                                String str = new String(tempb,2,str_length,"utf-8");
                                int lenlne = Integer.parseInt(str);
                                all_length = 2+str_length+lenlne;
                                //TODO 判断标识符是否是为 对方发送聊天内容  语音消息 视频电话 等 添加一个监听器 给后台传送值 显示推送功能
                                if(command == 103 || command ==104 || command ==105 || command ==106 ){
                                    //TODO  添加是否开始推送程序回调
                                }
                                Log.e(THR,"  command ["+command+"] str_length[" + str_length+"] lenlen[" +lenlne+"]");
                                if(all_length >len) {
                                    sleep(1000);
                                    Log.e(THR,"数据读取不完整.....");
                                    temp = new byte[len];
                                    System.arraycopy(tempb, 0, temp, 0, tempb.length);
                                    Log.e(THR,"第一次读到的长度-----》 " + temp.length);
                                    sleep(500);
                                    continue turn01;
                                }
                                msg = new String(tempb,2+str_length,lenlne,"utf-8");
                                Log.e(THR,"  mgs "+msg);
                                flag103 = false;

                                if(all_length<len) {
                                    sleep(1000);
                                    Log.e(THR ,"粘包了...实际读到的数据就只有["+all_length+"] 读到的字节数组长度为 ["+len+"]");//读多了
                                    byte[] more = new byte[len-all_length]; //原先长度 减去 读取到的所有字节长度 即为 粘包了的包长度
                                    byte[] t = tempb;
                                    System.arraycopy(t,all_length,more,0,more.length); //浅复制
                                    tempb = more;
                                    len = len - all_length;
                                    flag103 = true;
                                    continue turn02;
                                }
                            }else {
                                continue turn01;
                            }
                        }
                    } catch (Exception e) {
                        Log.e(THR,"数据异常");
                    }
                }
            }
        }.start();
    }*/
}
