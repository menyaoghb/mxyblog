package com.mxy.system.info.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

/**
 * 执行系统命令工具类
 *
 * @author mengyao
 */
public class CmdUtil {

    public static void main(String[] args) {
        System.out.println(isAlive("WeChat.exe"));
    }

    /**
     * 通过进程名称获取进程是否活着
     *
     * @param processName 进程名称
     * @return 活着返回true否则false
     */
    public static boolean isAlive(String processName) {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String command = "ps -ef|grep " + processName + " |grep -v grep";
        if (osName.contains("Win")) {
            command = "tasklist /fi \"" + "imagename eq " + processName + "\"";
        }
        String execResult = exec(command);
        if (execResult == null || !execResult.contains(processName)) {
            return false;
        }
        return true;
    }

    /**
     * 执行命令
     *
     * @param command 命令
     * @return 执行命令后返回的内容
     */
    public static String exec(String command) {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            Properties props = System.getProperties();
            String osName = props.getProperty("os.name");
            if (osName.contains("Win")) {
                p = r.exec(command);
            } else {
                p = r.exec(new String[]{"/bin/sh", "-c", command});
            }
        } catch (IOException e) {
            return null;
        }
        if (p == null) {
            return null;
        }

        try (
                InputStream inputStream = p.getInputStream();
                InputStreamReader in = new InputStreamReader(inputStream, "gbk");
                BufferedReader br = new BufferedReader(in);
        ) {
            StringBuilder sb = new StringBuilder();
            String line = null;
            boolean firstFlag = true;
            while ((line = br.readLine()) != null) {
                if (Objects.equals(line, "")) {
                    continue;
                }
                if (!firstFlag) {
                    sb.append("\n");
                }
                sb.append(line);
                firstFlag = false;
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

}
