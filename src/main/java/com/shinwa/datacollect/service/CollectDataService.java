package com.shinwa.datacollect.service;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.entity.CollectData;
import com.shinwa.datacollect.entity.UserInfo;

import javax.sql.DataSource;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CollectDataService {

    public static int saveData(String taskOrder,String originalCountry,String manufacturer,String h3cSN,String originalFactorySN,String customerSN) throws UnknownHostException, SQLException {
        //获取数据源
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }
        LocalDateTime dateTime = LocalDateTime.now();
        UserInfo userInfo = AppCache.loginCache.get("userInfo");

        //使用数据源
        int i = Db.use(ds).insert(Entity.create("collect_data")
                .set("create_time", dateTime)
                .set("creator", userInfo.getName())

                .set("task_order", taskOrder)
                .set("original_country", originalCountry)
                .set("manufacturer", manufacturer)
                .set("h3c_sn", h3cSN)
                .set("original_factory_sn", originalFactorySN)
                .set("customer_sn",customerSN));
        return i;
    }

    public static List<CollectData> queryData(String taskOrder,String customerSN,String creator,String createTime)throws UnknownHostException, SQLException{
        //获取数据源
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }

        String sql = "select * from collect_data where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        if(createTime!=null && !"".equals(createTime)){
            sb.append(" and create_time > ");
            sb.append("'"+createTime+"'");
        }
        if(creator!=null && !"".equals(creator)){
            sb.append(" and creator like '%");
            sb.append(creator);
            sb.append("%'");
        }
        if(taskOrder!=null && !"".equals(taskOrder)){
            sb.append(" and task_order like '%");
            sb.append(taskOrder);
            sb.append("%'");
        }
        if(customerSN!=null && !"".equals(customerSN)){
            sb.append(" and customer_sn like '%");
            sb.append(customerSN);
            sb.append("%'");
        }
        sb.append(" order by create_time ");
        List<Entity> entities = Db.use(ds).query(sb.toString());

        List<CollectData> list = new ArrayList<>();
        for(Entity entity:entities){
            CollectData collectData = new CollectData();
            collectData.setCreateTime(entity.getStr("create_time"));
            collectData.setCreator(entity.getStr("creator"));
            collectData.setCustomerSn(entity.getStr("customer_sn"));
            collectData.setH3cSn(entity.getStr("h3c_sn"));
            collectData.setManufacturer(entity.getStr("manufacturer"));
            collectData.setOriginalCountry(entity.getStr("original_country"));
            collectData.setOriginalFactorySn(entity.getStr("original_factory_sn"));
            collectData.setTaskOrder(entity.getStr("task_order"));
            list.add(collectData);
        }
        return list;
    }


    public static List<Entity> queryDataExist(String taskOrder,String originalCountry,String manufacturer,String h3cSN,String originalFactorySN,String customerSN)throws UnknownHostException, SQLException{
        //获取数据源
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }

        String sql = "select * from collect_data where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        if(taskOrder!=null && !"".equals(taskOrder)){
            sb.append(" and task_order = '");
            sb.append(taskOrder);
            sb.append("'");
        }
        if(originalCountry!=null && !"".equals(originalCountry)){
            sb.append(" and original_country = '");
            sb.append(originalCountry);
            sb.append("'");
        }
        if(manufacturer!=null && !"".equals(manufacturer)){
            sb.append(" and manufacturer = '");
            sb.append(manufacturer);
            sb.append("'");
        }
        if(h3cSN!=null && !"".equals(h3cSN)){
            sb.append(" and h3c_sn = '");
            sb.append(h3cSN);
            sb.append("'");
        }
        if(originalFactorySN!=null && !"".equals(originalFactorySN)){
            sb.append(" and original_factory_sn = '");
            sb.append(originalFactorySN);
            sb.append("'");
        }
        if(customerSN!=null && !"".equals(customerSN)){
            sb.append(" and customer_sn = '");
            sb.append(customerSN);
            sb.append("'");
        }


        return Db.use(ds).query(sb.toString());
    }
}
