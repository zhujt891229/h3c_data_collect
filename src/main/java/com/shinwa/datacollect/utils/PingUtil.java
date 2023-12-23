package com.shinwa.datacollect.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingUtil {
    public static boolean ping(String ipAddress) throws IOException {
        long starttime = System.currentTimeMillis();
        int timeout = 3000;
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeout);
        long endtime = System.currentTimeMillis();
        System.out.println(endtime-starttime);
        return status;
    }

    public static void ping02(String ipAddress){
        String line = null;
        try{
            Process pro = Runtime.getRuntime().exec("ping "+ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(),"GBK"));
            while((line=buf.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean ping(String ipAddress,int pingTimes,int timeOut){
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        String pingCommand = "ping "+ipAddress+" -n "+pingTimes +" -w "+timeOut;
        try{
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if(p==null){
                return false;
            }
            in =new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            int connectedCount = 0;
            String line = null;
            while((line=in.readLine())!=null){
                connectedCount+=getCheckResult(line);
            }
            return connectedCount == pingTimes;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally{
            if(null!=in){
                try{
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static int getCheckResult(String line){
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            return 1;
        }
        return 0;
    }
}
