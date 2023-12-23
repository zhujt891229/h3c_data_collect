package com.shinwa.datacollect.common;

import cn.hutool.db.ds.DSFactory;
import com.shinwa.datacollect.utils.PingUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.InetAddress;

public class DBSource {
    //public final static DataSource ds;
    public static DataSource getDs() throws IOException {
        DataSource ds1 = DSFactory.get("group_db1");
        DataSource ds2 = DSFactory.get("group_db2");
        DataSource ds3 = DSFactory.get("group_db3");
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();
        if(null!=ipAddress){
            if(ipAddress.startsWith("192.168.11")){
                return ds1;
            }else if(ipAddress.startsWith("10.89.34")){
                return ds2;
            }else if(ipAddress.startsWith("10.183.52")){
                return ds3;
            }else if(PingUtil.ping("192.168.11.12")){
                return ds1;
            }else if(PingUtil.ping("10.89.34.56")){
                return ds2;
            }else if(PingUtil.ping("10.183.52.56")){
                return ds3;
            }
        }
        return null;
    }
}
