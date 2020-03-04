package com.smona.btwriter.bluetoothspp2;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

/**
 * Created by QunChen on 2018/5/2.
 * 消息实体类
 */
public class MsgBeen {
    private byte[] msg;
    private int size;
    private String strMsg;
    private String hexMsg;


    public MsgBeen(byte[] msg, int size) {
        this.msg = msg;
        this.size = size;
        try {
            this.strMsg = new String(msg, "gbk");
        } catch (UnsupportedEncodingException e) {
            this.strMsg = new String(msg);
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < msg.length; i++) {
            String hex = Integer.toHexString(msg[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
            sb.append(" ");
        }
        this.hexMsg = sb.toString();
    }

    public int getLastByte() {
        if(msg == null) {
            return -1;
        }
        if(msg.length ==0) {
            return -1;
        }
        return msg[msg.length - 1];
    }

    public String getStrMsg() {
        return strMsg;
    }

    public String getHexMsg() {
        return hexMsg;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
