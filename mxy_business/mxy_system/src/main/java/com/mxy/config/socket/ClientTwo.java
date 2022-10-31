package com.mxy.config.socket;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ClientTwo {


    public static void main(String[] args) {
        String smsContent = "客户端02测试";
        String destNum = "10086";
        int contentLength = destNum.getBytes(StandardCharsets.UTF_8).length;
        Socket socket = null;
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String message = null;
        try {
            socket = new Socket("127.0.0.1", 8001);
            System.out.println("socket" + JSONObject.toJSONString(socket));
            os = socket.getOutputStream();
            // 服务端接收GBK编码数据
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os, "GBK")), false);
            // 获取输出流向服务端写入数据
            String content = "NMC-SMS VER 1.0\r\nCommand: Send\r\nSrcNum: 91\r\nDestNum: " + destNum + "\r\nContent-Length: " + contentLength + "\r\n\r\n" + smsContent + "";
            System.out.println("向服务端写入数据：" + content);
            pw.write(content);
            pw.flush();
            socket.shutdownOutput();
            // 获取输入流接受服务端返回的信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((message = br.readLine()) != null) {
                System.out.println("服务器说：" + message);
            }
            socket.shutdownInput();
            System.out.print("=======发送成功====" + new Date() + "=======\n");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (pw != null) {
                pw.close();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


