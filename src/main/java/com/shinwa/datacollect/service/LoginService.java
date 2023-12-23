package com.shinwa.datacollect.service;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.common.DBSource;
import com.shinwa.datacollect.entity.UserInfo;

import javax.sql.DataSource;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LoginService {
    public static UserInfo login(String username, String password) throws UnknownHostException, SQLException {
        //获取数据源
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }
        //使用数据源
        List<Entity> entities = Db.use(ds).find(Entity.create("user_info")
                .set("username", username)
                .set("password",password));
        if(null==entities||entities.size()==0){
            return null;
        }else{
            Entity entity =  entities.get(0);
            String username1 = entity.getStr("username");
            String name = entity.getStr("name");
            String dept = entity.getStr("dept");
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username1);
            userInfo.setName(name);
            userInfo.setDept(dept);
            return userInfo;
        }
    }
}
