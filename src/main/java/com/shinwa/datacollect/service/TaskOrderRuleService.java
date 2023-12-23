package com.shinwa.datacollect.service;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.entity.TaskOrderRule;
import com.shinwa.datacollect.entity.UserInfo;

import javax.sql.DataSource;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskOrderRuleService {

    public static List<TaskOrderRule> queryData(String taskOrder) throws UnknownHostException, SQLException {
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }
//        String sql = "select * from task_order_rule where 1=1 ";
//        StringBuilder sb = new StringBuilder(sql);
//
//        if(taskOrder!=null && !"".equals(taskOrder)){
//            sb.append(" and task_order = '");
//            sb.append(taskOrder);
//            sb.append("'");
//        }
//        sb.append(" order by create_time ");
//        List<Entity> entities = Db.use(ds).query(sb.toString());


        List<Entity> entities = Db.use(ds).findBy("task_order_rule", "task_order", taskOrder);

        List<TaskOrderRule> list = new ArrayList<>();
        for(Entity entity:entities){
            TaskOrderRule taskOrderRule = new TaskOrderRule();
            taskOrderRule.setTaskOrder(entity.getStr("task_order"));
            taskOrderRule.setOriginalCountryShow(entity.getStr("original_country_show"));
            taskOrderRule.setOriginalCountryRule(entity.getStr("original_country_rule"));
            taskOrderRule.setManufacturerShow(entity.getStr("manufacturer_show"));
            taskOrderRule.setManufacturerRule(entity.getStr("manufacturer_rule"));
            taskOrderRule.setH3cSnShow(entity.getStr("h3c_sn_show"));
            taskOrderRule.setH3cSnRule(entity.getStr("h3c_sn_rule"));
            taskOrderRule.setOriginalFactorySnShow(entity.getStr("original_factory_sn_show"));
            taskOrderRule.setOriginalFactorySnRule(entity.getStr("original_factory_sn_rule"));
            taskOrderRule.setCustomerSnShow(entity.getStr("customer_sn_show"));
            taskOrderRule.setCustomerSnRule(entity.getStr("customer_sn_rule"));
            taskOrderRule.setCreateTime(entity.getStr("create_time"));
            taskOrderRule.setCreator(entity.getStr("creator"));
            list.add(taskOrderRule);
        }
        return list;
    }

    public static int saveData(TaskOrderRule taskOrderRule) throws UnknownHostException, SQLException {
        DataSource ds = AppCache.dataSource;
        if(ds==null){
            throw new UnknownHostException();
        }
        LocalDateTime dateTime = LocalDateTime.now();
        UserInfo userInfo = AppCache.loginCache.get("userInfo");
        String taskOrder = taskOrderRule.getTaskOrder();
        List<Entity> entities = Db.use(ds).find(Entity.create("task_order_rule")
                .set("task_order", taskOrder));

        int i=0;
        //id为空时插入数据，id不为空时修改数据
        if(null==entities||entities.size()<1){

            i = Db.use(ds).insert(Entity.create("task_order_rule")
                    .set("create_time", dateTime)
                    .set("creator", userInfo.getName())
                    .set("update_time", dateTime)
                    .set("updater", userInfo.getName())

                    .set("task_order", taskOrder)
                    .set("original_country_show", taskOrderRule.getOriginalCountryShow())
                    .set("original_country_rule", taskOrderRule.getOriginalCountryRule())
                    .set("manufacturer_show",taskOrderRule.getManufacturerShow())
                    .set("manufacturer_rule",taskOrderRule.getManufacturerRule())
                    .set("h3c_sn_show",taskOrderRule.getH3cSnShow())
                    .set("h3c_sn_rule",taskOrderRule.getH3cSnRule())
                    .set("original_factory_sn_show",taskOrderRule.getOriginalFactorySnShow())
                    .set("original_factory_sn_rule",taskOrderRule.getOriginalFactorySnRule())
                    .set("customer_sn_show",taskOrderRule.getCustomerSnShow())
                    .set("customer_sn_rule",taskOrderRule.getCustomerSnRule())
            );
        }else {
            i=Db.use(ds).update(Entity.create()
                            .set("original_country_show", taskOrderRule.getOriginalCountryShow())
                            .set("original_country_rule", taskOrderRule.getOriginalCountryRule())
                            .set("manufacturer_show",taskOrderRule.getManufacturerShow())
                            .set("manufacturer_rule",taskOrderRule.getManufacturerRule())
                            .set("h3c_sn_show",taskOrderRule.getH3cSnShow())
                            .set("h3c_sn_rule",taskOrderRule.getH3cSnRule())
                            .set("original_factory_sn_show",taskOrderRule.getOriginalFactorySnShow())
                            .set("original_factory_sn_rule",taskOrderRule.getOriginalFactorySnRule())
                            .set("customer_sn_show",taskOrderRule.getCustomerSnShow())
                            .set("customer_sn_rule",taskOrderRule.getCustomerSnRule())
                            .set("update_time",dateTime)
                            .set("updater",userInfo.getUsername()),
                    Entity.create("task_order_rule").set("task_order",taskOrder));
        }
        return i;
    }

    public static void deleteData(){

    }
}
