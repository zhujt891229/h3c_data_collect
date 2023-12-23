package com.shinwa.datacollect.component;

import cn.hutool.db.Entity;
import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.entity.CheckRule;
import com.shinwa.datacollect.entity.CollectData;
import com.shinwa.datacollect.entity.TaskOrderRule;
import com.shinwa.datacollect.service.CollectDataService;
import com.shinwa.datacollect.service.TaskOrderRuleService;
import com.shinwa.datacollect.utils.LoggerUtil;

import javax.swing.*;
import java.awt.*;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CollectDataTab extends JPanel {
    Logger logger = LoggerUtil.getLogger(CollectDataTab.class.getName());

    private final TaskOrderRule currentTaskOrderRule;

    private final JTextField taskOrderField;
    JComboBox<CheckRule> checkRuleBox0;
    private final JTextField originalCountryField;
    JComboBox<CheckRule> checkRuleBox1;
    private final JTextField manufacturerField;
    JComboBox<CheckRule> checkRuleBox2;
    private final JTextField h3cSNField;
    JComboBox<CheckRule> checkRuleBox3;
    private final JTextField originalFactorySNField;
    JComboBox<CheckRule> checkRuleBox4;
    private final JTextField customerSNField;
    JComboBox<CheckRule> checkRuleBox5;

    public CollectDataTab(){
        setLayout(new GridBagLayout());

        currentTaskOrderRule = new TaskOrderRule();
        JLabel taskOrderLabel = new JLabel("指定任务令:");
        taskOrderField = new JTextField(65);
        checkRuleBox0 = new JComboBox<>();
        comboBoxFillData(checkRuleBox0);
        JLabel originalCountryLabel = new JLabel("指定原产国:");
        originalCountryField = new JTextField(65);
        checkRuleBox1 = new JComboBox<>();
        comboBoxFillData(checkRuleBox1);
        checkRuleBox1.setEnabled(false);
        JLabel manufacturerLabel = new JLabel("指定厂家代码:");
        manufacturerField = new JTextField(65);
        checkRuleBox2 = new JComboBox<>();
        comboBoxFillData(checkRuleBox2);
        checkRuleBox2.setEnabled(false);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        JLabel h3cSNLabel = new JLabel("H3C SN:");
        h3cSNField = new JTextField(65);
        checkRuleBox3 = new JComboBox<>();
        comboBoxFillData(checkRuleBox3);
        checkRuleBox3.setEnabled(false);
        JLabel originalFactorySNLabel = new JLabel("原厂SN:");
        originalFactorySNField = new JTextField(65);
        checkRuleBox4 = new JComboBox<>();
        comboBoxFillData(checkRuleBox4);
        checkRuleBox4.setEnabled(false);
        JLabel customerSNLabel = new JLabel("客户SN:");
        customerSNField = new JTextField(65);
        checkRuleBox5 = new JComboBox<>();
        comboBoxFillData(checkRuleBox5);
        checkRuleBox5.setEnabled(false);
        JTextArea recordsArea = new JTextArea();
        recordsArea.setLineWrap(true);
        recordsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(recordsArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JButton cleanButton = new JButton("清空");

        taskOrderField.addActionListener(e -> {
            checkTaskOrder();
//            Object selectedItem = checkRuleBox1.getSelectedItem();
//            if(null!=selectedItem){
//                String regex = ((CheckRule)(selectedItem)).getRuleContent();
//                if(taskOrderField.getText().matches(regex)||"".equals(regex)){
//                    originalCountryField.requestFocus();
//                }else{
//                    taskOrderFiledNotation();
//                }
//            }else{
//                originalCountryField.requestFocus();
//            }
            //originalCountryField.requestFocus();
            //getNextFocusableComponent();
            getNextFocus(0);
        });
        originalCountryField.addActionListener(e -> {
            Object selectedItem = checkRuleBox1.getSelectedItem();
            if(null!=selectedItem){
                String regex = ((CheckRule)(selectedItem)).getRuleContent();
                if(originalCountryField.getText().matches(regex)||"".equals(regex)){
                    manufacturerField.requestFocus();
                }else{
                    originalCountryFieldNotation();
                }
            }else{
                manufacturerField.requestFocus();
            }
            getNextFocus(1);
        });
        manufacturerField.addActionListener(e -> {
            Object selectedItem = checkRuleBox2.getSelectedItem();
            if(null!=selectedItem){
                String regex = ((CheckRule)(selectedItem)).getRuleContent();
                if(manufacturerField.getText().matches(regex)||"".equals(regex)){
                    h3cSNField.requestFocus();
                }else{
                    manufacturerFieldNotation();
                }
            }else{
                h3cSNField.requestFocus();
            }
            getNextFocus(2);
        });

        h3cSNField.addActionListener(e -> {
            Object selectedItem = checkRuleBox3.getSelectedItem();
            if(null!=selectedItem){
                String regex = ((CheckRule)(selectedItem)).getRuleContent();
                if(h3cSNField.getText().matches(regex)||"".equals(regex)){

                    originalFactorySNField.requestFocus();
                    recordsArea.append(taskOrderField.getText());
                    recordsArea.append(",");
                    recordsArea.append(manufacturerField.getText());
                    recordsArea.append(",");
                    recordsArea.append(h3cSNField.getText());
                    recordsArea.append(",");
                }else{
                    h3cSNFieldNotation();
                }
            }else{
                originalFactorySNField.requestFocus();
                recordsArea.append(taskOrderField.getText());
                recordsArea.append(",");
                recordsArea.append(manufacturerField.getText());
                recordsArea.append(",");
                recordsArea.append(h3cSNField.getText());
                recordsArea.append(",");
            }
//            recordsArea.append(taskOrderField.getText());
//            recordsArea.append(",");
//            recordsArea.append(originalCountryField.getText());
//            recordsArea.append(",");
//            recordsArea.append(manufacturerField.getText());
//            recordsArea.append(",");
//            recordsArea.append(h3cSNField.getText());
//            recordsArea.append(",");
            getNextFocus(3);
        });
        originalFactorySNField.addActionListener(e -> {
            Object selectedItem = checkRuleBox4.getSelectedItem();
            if(null!=selectedItem){
                String regex = ((CheckRule)(selectedItem)).getRuleContent();
                if(originalFactorySNField.getText().matches(regex)||"".equals(regex)){
                    customerSNField.requestFocus();
                    recordsArea.append(originalFactorySNField.getText());
                    recordsArea.append(",");
                }else{
                    originalFactorySNFieldNotation();
                }
            }else{
                customerSNField.requestFocus();
                recordsArea.append(originalFactorySNField.getText());
                recordsArea.append(",");
            }
//            recordsArea.append(originalFactorySNField.getText());
//            recordsArea.append(",");
            getNextFocus(4);
        });
        customerSNField.addActionListener(e -> {
            Object selectedItem = checkRuleBox5.getSelectedItem();
            if(null!=selectedItem){
                String regex = ((CheckRule)(selectedItem)).getRuleContent();
                if(customerSNField.getText().matches(regex)||"".equals(regex)){
                    h3cSNField.requestFocus();
                    recordsArea.append(customerSNField.getText());
                    recordsArea.append("\n");
                }else{
                    customerSNFieldNotation();
                }
            }else{
                h3cSNField.requestFocus();
                recordsArea.append(customerSNField.getText());
                recordsArea.append("\n");
            }
//            recordsArea.append(customerSNField.getText());
//            recordsArea.append("\n");
            //存储到数据库
            saveData();
            //清空页面三个框的数据
            h3cSNField.setText("");
            originalFactorySNField.setText("");
            customerSNField.setText("");
            getNextFocus(5);
        });

        cleanButton.addActionListener(e -> {
            taskOrderField.setText("");
            originalCountryField.setText("");
            manufacturerField.setText("");
            h3cSNField.setText("");
            originalFactorySNField.setText("");
            customerSNField.setText("");
            recordsArea.setText("");
            checkRuleBox1.setSelectedIndex(0);
            checkRuleBox2.setSelectedIndex(0);
            checkRuleBox3.setSelectedIndex(0);
            checkRuleBox4.setSelectedIndex(0);
            checkRuleBox5.setSelectedIndex(0);
            currentTaskOrderRule.setTaskOrder(null);
        });

        GridBagConstraints taskOrderLabelConstraints = new GridBagConstraints();
        taskOrderLabelConstraints.gridx=0;
        taskOrderLabelConstraints.gridy=0;
        taskOrderLabelConstraints.gridwidth=1;
        taskOrderLabelConstraints.gridheight=1;
        taskOrderLabelConstraints.weightx=0.2;
        taskOrderLabelConstraints.weighty = 0.1;
        taskOrderLabelConstraints.anchor=GridBagConstraints.CENTER;
        taskOrderLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        taskOrderLabelConstraints.insets = new Insets(0,30,0,0);
        taskOrderLabelConstraints.ipadx = 5;
        taskOrderLabelConstraints.ipady = 5;
        add(taskOrderLabel,taskOrderLabelConstraints);
        GridBagConstraints taskOrderFieldConstraints = new GridBagConstraints();
        taskOrderFieldConstraints.gridx=1;
        taskOrderFieldConstraints.gridy=0;
        taskOrderFieldConstraints.gridwidth=1;
        taskOrderFieldConstraints.gridheight=1;
        taskOrderFieldConstraints.weightx=0.7;
        taskOrderFieldConstraints.anchor=GridBagConstraints.CENTER;
        taskOrderFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        taskOrderFieldConstraints.insets = new Insets(0,10,0,20);
        taskOrderFieldConstraints.ipadx = 5;
        taskOrderFieldConstraints.ipady = 5;
        add(taskOrderField,taskOrderFieldConstraints);

//        GridBagConstraints checkRuleBox0Constraints = new GridBagConstraints();
//        checkRuleBox0Constraints.gridx=2;
//        checkRuleBox0Constraints.gridy=0;
//        checkRuleBox0Constraints.gridwidth=1;
//        checkRuleBox0Constraints.gridheight=1;
//        checkRuleBox0Constraints.weightx=0.1;
//        checkRuleBox0Constraints.anchor=GridBagConstraints.CENTER;
//        checkRuleBox0Constraints.fill=GridBagConstraints.HORIZONTAL;
//        checkRuleBox0Constraints.insets = new Insets(0,10,0,35);
//        checkRuleBox0Constraints.ipadx = 5;
//        checkRuleBox0Constraints.ipady = 5;
//        add(checkRuleBox0,checkRuleBox0Constraints);

        GridBagConstraints originalCountryLabelConstraints = new GridBagConstraints();
        originalCountryLabelConstraints.gridx=0;
        originalCountryLabelConstraints.gridy=1;
        originalCountryLabelConstraints.gridwidth=1;
        originalCountryLabelConstraints.gridheight=1;
        originalCountryLabelConstraints.weighty = 0.1;
        originalCountryLabelConstraints.anchor=GridBagConstraints.CENTER;
        originalCountryLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        originalCountryLabelConstraints.insets = new Insets(0,30,0,0);
        originalCountryLabelConstraints.ipadx = 5;
        originalCountryLabelConstraints.ipady = 5;
        add(originalCountryLabel,originalCountryLabelConstraints);

        GridBagConstraints originalCountryFieldConstraints = new GridBagConstraints();
        originalCountryFieldConstraints.gridx=1;
        originalCountryFieldConstraints.gridy=1;
        originalCountryFieldConstraints.gridwidth=1;
        originalCountryFieldConstraints.gridheight=1;
        originalCountryFieldConstraints.anchor=GridBagConstraints.CENTER;
        originalCountryFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        originalCountryFieldConstraints.insets = new Insets(0,10,0,20);
        originalCountryFieldConstraints.ipadx = 5;
        originalCountryFieldConstraints.ipady = 5;
        add(originalCountryField,originalCountryFieldConstraints);

        GridBagConstraints checkRuleBox1Constraints = new GridBagConstraints();
        checkRuleBox1Constraints.gridx=2;
        checkRuleBox1Constraints.gridy=1;
        checkRuleBox1Constraints.gridwidth=1;
        checkRuleBox1Constraints.gridheight=1;
        checkRuleBox1Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox1Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox1Constraints.insets = new Insets(0,10,0,35);
        checkRuleBox1Constraints.ipadx = 5;
        checkRuleBox1Constraints.ipady = 5;
        add(checkRuleBox1,checkRuleBox1Constraints);

        GridBagConstraints manufacturerLabelConstraints = new GridBagConstraints();
        manufacturerLabelConstraints.gridx=0;
        manufacturerLabelConstraints.gridy=2;
        manufacturerLabelConstraints.gridwidth=1;
        manufacturerLabelConstraints.gridheight=1;
        manufacturerLabelConstraints.weighty = 0.1;
        manufacturerLabelConstraints.anchor=GridBagConstraints.CENTER;
        manufacturerLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        manufacturerLabelConstraints.insets = new Insets(0,30,0,0);
        manufacturerLabelConstraints.ipadx = 5;
        manufacturerLabelConstraints.ipady = 5;
        add(manufacturerLabel,manufacturerLabelConstraints);

        GridBagConstraints manufacturerFieldConstraints = new GridBagConstraints();
        manufacturerFieldConstraints.gridx=1;
        manufacturerFieldConstraints.gridy=2;
        manufacturerFieldConstraints.gridwidth=1;
        manufacturerFieldConstraints.gridheight=1;
        manufacturerFieldConstraints.anchor=GridBagConstraints.CENTER;
        manufacturerFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        manufacturerFieldConstraints.insets = new Insets(0,10,0,20);
        manufacturerFieldConstraints.ipadx = 5;
        manufacturerFieldConstraints.ipady = 5;
        add(manufacturerField,manufacturerFieldConstraints);

        GridBagConstraints checkRuleBox2Constraints = new GridBagConstraints();
        checkRuleBox2Constraints.gridx=2;
        checkRuleBox2Constraints.gridy=2;
        checkRuleBox2Constraints.gridwidth=1;
        checkRuleBox2Constraints.gridheight=1;
        checkRuleBox2Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox2Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox2Constraints.insets = new Insets(0,10,0,35);
        checkRuleBox2Constraints.ipadx = 5;
        checkRuleBox2Constraints.ipady = 5;
        add(checkRuleBox2,checkRuleBox2Constraints);

        GridBagConstraints separatorConstraints = new GridBagConstraints();
        separatorConstraints.gridx=1;
        separatorConstraints.gridy=3;
        separatorConstraints.gridwidth=1;
        separatorConstraints.gridheight=1;
        separatorConstraints.anchor=GridBagConstraints.CENTER;
        separatorConstraints.fill=GridBagConstraints.HORIZONTAL;
        separatorConstraints.insets = new Insets(0,0,0,0);
        separatorConstraints.ipadx = 5;
        separatorConstraints.ipady = 5;
        add(separator,separatorConstraints);

        GridBagConstraints h3cSNLabelConstraints = new GridBagConstraints();
        h3cSNLabelConstraints.gridx=0;
        h3cSNLabelConstraints.gridy=4;
        h3cSNLabelConstraints.gridwidth=1;
        h3cSNLabelConstraints.gridheight=1;
        h3cSNLabelConstraints.weighty = 0.1;
        h3cSNLabelConstraints.anchor=GridBagConstraints.CENTER;
        h3cSNLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        h3cSNLabelConstraints.insets = new Insets(0,30,0,0);
        h3cSNLabelConstraints.ipadx = 5;
        h3cSNLabelConstraints.ipady = 5;
        add(h3cSNLabel,h3cSNLabelConstraints);

        GridBagConstraints h3cSNFieldConstraints = new GridBagConstraints();
        h3cSNFieldConstraints.gridx=1;
        h3cSNFieldConstraints.gridy=4;
        h3cSNFieldConstraints.gridwidth=1;
        h3cSNFieldConstraints.gridheight=1;
        h3cSNFieldConstraints.anchor=GridBagConstraints.CENTER;
        h3cSNFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        h3cSNFieldConstraints.insets = new Insets(0,10,0,20);
        h3cSNFieldConstraints.ipadx = 5;
        h3cSNFieldConstraints.ipady = 5;
        add(h3cSNField,h3cSNFieldConstraints);

        GridBagConstraints checkRuleBox3Constraints = new GridBagConstraints();
        checkRuleBox3Constraints.gridx=2;
        checkRuleBox3Constraints.gridy=4;
        checkRuleBox3Constraints.gridwidth=1;
        checkRuleBox3Constraints.gridheight=1;
        checkRuleBox3Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox3Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox3Constraints.insets = new Insets(0,10,0,35);
        checkRuleBox3Constraints.ipadx = 5;
        checkRuleBox3Constraints.ipady = 5;
        add(checkRuleBox3,checkRuleBox3Constraints);

        GridBagConstraints originalFactorySNLabelConstraints = new GridBagConstraints();
        originalFactorySNLabelConstraints.gridx=0;
        originalFactorySNLabelConstraints.gridy=5;
        originalFactorySNLabelConstraints.gridwidth=1;
        originalFactorySNLabelConstraints.gridheight=1;
        originalFactorySNLabelConstraints.weighty = 0.1;
        originalFactorySNLabelConstraints.anchor=GridBagConstraints.CENTER;
        originalFactorySNLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        originalFactorySNLabelConstraints.insets = new Insets(0,30,0,0);
        originalFactorySNLabelConstraints.ipadx = 5;
        originalFactorySNLabelConstraints.ipady = 5;
        add(originalFactorySNLabel,originalFactorySNLabelConstraints);

        GridBagConstraints originalFactorySNFieldConstraints = new GridBagConstraints();
        originalFactorySNFieldConstraints.gridx=1;
        originalFactorySNFieldConstraints.gridy=5;
        originalFactorySNFieldConstraints.gridwidth=1;
        originalFactorySNFieldConstraints.gridheight=1;
        originalFactorySNFieldConstraints.anchor=GridBagConstraints.CENTER;
        originalFactorySNFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        originalFactorySNFieldConstraints.insets = new Insets(0,10,0,20);
        originalFactorySNFieldConstraints.ipadx = 5;
        originalFactorySNFieldConstraints.ipady = 5;
        add(originalFactorySNField,originalFactorySNFieldConstraints);

        GridBagConstraints checkRuleBox4Constraints = new GridBagConstraints();
        checkRuleBox4Constraints.gridx=2;
        checkRuleBox4Constraints.gridy=5;
        checkRuleBox4Constraints.gridwidth=1;
        checkRuleBox4Constraints.gridheight=1;
        checkRuleBox4Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox4Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox4Constraints.insets = new Insets(0,10,0,35);
        checkRuleBox4Constraints.ipadx = 5;
        checkRuleBox4Constraints.ipady = 5;
        add(checkRuleBox4,checkRuleBox4Constraints);

        GridBagConstraints customerSNLabelConstraints = new GridBagConstraints();
        customerSNLabelConstraints.gridx=0;
        customerSNLabelConstraints.gridy=6;
        customerSNLabelConstraints.gridwidth=1;
        customerSNLabelConstraints.gridheight=1;
        customerSNLabelConstraints.weighty = 0.1;
        customerSNLabelConstraints.anchor=GridBagConstraints.CENTER;
        customerSNLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        customerSNLabelConstraints.insets = new Insets(0,30,0,0);
        customerSNLabelConstraints.ipadx = 5;
        customerSNLabelConstraints.ipady = 5;
        add(customerSNLabel,customerSNLabelConstraints);

        GridBagConstraints customerSNFieldConstraints = new GridBagConstraints();
        customerSNFieldConstraints.gridx=1;
        customerSNFieldConstraints.gridy=6;
        customerSNFieldConstraints.gridwidth=1;
        customerSNFieldConstraints.gridheight=1;
        customerSNFieldConstraints.anchor=GridBagConstraints.CENTER;
        customerSNFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        customerSNFieldConstraints.insets = new Insets(0,10,0,20);
        customerSNFieldConstraints.ipadx = 5;
        customerSNFieldConstraints.ipady = 5;
        add(customerSNField,customerSNFieldConstraints);

        GridBagConstraints checkRuleBox5Constraints = new GridBagConstraints();
        checkRuleBox5Constraints.gridx=2;
        checkRuleBox5Constraints.gridy=6;
        checkRuleBox5Constraints.gridwidth=1;
        checkRuleBox5Constraints.gridheight=1;
        checkRuleBox5Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox5Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox5Constraints.insets = new Insets(0,10,0,35);
        checkRuleBox5Constraints.ipadx = 5;
        checkRuleBox5Constraints.ipady = 5;
        add(checkRuleBox5,checkRuleBox5Constraints);

        GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
        scrollPaneConstraints.gridx=1;
        scrollPaneConstraints.gridy=7;
        scrollPaneConstraints.gridwidth=1;
        scrollPaneConstraints.gridheight=3;
        scrollPaneConstraints.weighty = 0.4;
        scrollPaneConstraints.anchor=GridBagConstraints.CENTER;
        scrollPaneConstraints.fill=GridBagConstraints.BOTH;
        scrollPaneConstraints.insets = new Insets(5,0,35,0);
        scrollPaneConstraints.ipadx = 5;
        scrollPaneConstraints.ipady = 5;
        add(scrollPane,scrollPaneConstraints);

        GridBagConstraints cleanButtonConstraints = new GridBagConstraints();
        cleanButtonConstraints.gridx=2;
        cleanButtonConstraints.gridy=9;
        cleanButtonConstraints.gridwidth=1;
        cleanButtonConstraints.gridheight=1;
        cleanButtonConstraints.anchor=GridBagConstraints.CENTER;
        cleanButtonConstraints.fill=GridBagConstraints.NONE;
        cleanButtonConstraints.insets = new Insets(5,0,35,0);
        cleanButtonConstraints.ipadx = 5;
        cleanButtonConstraints.ipady = 5;
        add(cleanButton,cleanButtonConstraints);

    }

    private void comboBoxFillData(JComboBox<CheckRule> comboBox){
        List<CheckRule> checkRuleList = AppCache.checkRuleList;
        for(CheckRule checkRule:checkRuleList){
            comboBox.addItem(checkRule);
        }
    }

//    public void taskOrderFiledNotation(){
//        JOptionPane.showMessageDialog(this,"指定任务令输入不合法!");
//    }
    public void originalCountryFieldNotation(){
        JOptionPane.showMessageDialog(this,"指定原产国输入不合法!");
    }
    public void manufacturerFieldNotation(){
        JOptionPane.showMessageDialog(this,"指定厂家代码输入不合法!");
    }
    public void h3cSNFieldNotation(){
        JOptionPane.showMessageDialog(this,"H3C SN 输入不合法!");
    }
    public void originalFactorySNFieldNotation(){
        JOptionPane.showMessageDialog(this,"原厂SN输入不合法!");
    }
    public void customerSNFieldNotation(){
        JOptionPane.showMessageDialog(this,"客户SN输入不合法!");
    }

    private void saveData(){
        String taskOrder = taskOrderField.getText();
        if(null==taskOrder||"".equals(taskOrder)){
            JOptionPane.showMessageDialog(this,"任务令不能为空!");
            return;
        }
        String originalCountry = originalCountryField.getText();
        String manufacturer = manufacturerField.getText();
        String h3cSN = h3cSNField.getText();
        String originalFactorySN = originalFactorySNField.getText();
        String customerSN = customerSNField.getText();
        //校验其他字段
        String originalCountryShow = currentTaskOrderRule.getOriginalCountryShow();
        String manufacturerShow = currentTaskOrderRule.getManufacturerShow();
        String h3cSnShow = currentTaskOrderRule.getH3cSnShow();
        String originalFactorySnShow = currentTaskOrderRule.getOriginalFactorySnShow();
        String customerSnShow = currentTaskOrderRule.getCustomerSnShow();
        if("Y".equals(originalCountryShow)&&"".equals(originalCountry)){
            JOptionPane.showMessageDialog(this,"原产国不能为空!");
            return;
        }
        if("Y".equals(manufacturerShow)&&"".equals(manufacturer)){
            JOptionPane.showMessageDialog(this,"生产商不能为空!");
            return;
        }
        if("Y".equals(h3cSnShow)&&"".equals(h3cSN)){
            JOptionPane.showMessageDialog(this,"H3C_SN不能为空!");
            return;
        }
        if("Y".equals(originalFactorySnShow)&&"".equals(originalFactorySN)){
            JOptionPane.showMessageDialog(this,"原厂SN不能为空!");
            return;
        }
        if("Y".equals(customerSnShow)&&"".equals(customerSN)){
            JOptionPane.showMessageDialog(this,"客户SN不能为空!");
            return;
        }
        int i=0;
        try {
            //先查询是否存在
            List<Entity> existDataList = CollectDataService.queryDataExist(taskOrder,originalCountry,manufacturer,h3cSN,originalFactorySN,customerSN);
            if(existDataList!=null&&existDataList.size()>0){
                JOptionPane.showMessageDialog(this,"当前数据已存在，请勿重复录入!");
                return;
            }
            i = CollectDataService.saveData(taskOrder, originalCountry,manufacturer,h3cSN,originalFactorySN,customerSN);
        }catch(UnknownHostException e){
            logger.info(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
            return;
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"sql语句执行失败，请联系管理员!");
            return;
        }
        if(i!=1){
            JOptionPane.showMessageDialog(this,"保存数据失败，请联系管理员!");
        }
    }

    private void checkTaskOrder(){
        String taskOrderFieldText = taskOrderField.getText();
        List<TaskOrderRule> taskOrderRules=null;
        try {
            taskOrderRules = TaskOrderRuleService.queryData(taskOrderFieldText);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"sql语句执行失败，请联系管理员!");
        }
        if(null==taskOrderRules||taskOrderRules.size()<1){
            currentTaskOrderRule.setTaskOrder(taskOrderField.getText());
            currentTaskOrderRule.setCustomerSnShow("Y");
            currentTaskOrderRule.setCustomerSnRule("");
            currentTaskOrderRule.setOriginalFactorySnShow("Y");
            currentTaskOrderRule.setOriginalFactorySnRule("");
            currentTaskOrderRule.setOriginalCountryShow("Y");
            currentTaskOrderRule.setOriginalCountryRule("");
            currentTaskOrderRule.setH3cSnShow("Y");
            currentTaskOrderRule.setH3cSnRule("");
            currentTaskOrderRule.setManufacturerShow("Y");
            currentTaskOrderRule.setManufacturerRule("");
            JOptionPane.showMessageDialog(this,"当前任务令没有配置校验规则，是否继续?");
        }else{
            TaskOrderRule taskOrderRule = taskOrderRules.get(0);
            String taskOrder = taskOrderRule.getTaskOrder();
            String originalCountryShow = taskOrderRule.getOriginalCountryShow();
            String originalCountryRule = taskOrderRule.getOriginalCountryRule();
            String manufacturerShow = taskOrderRule.getManufacturerShow();
            String manufacturerRule = taskOrderRule.getManufacturerRule();
            String h3cSnShow = taskOrderRule.getH3cSnShow();
            String h3cSnRule = taskOrderRule.getH3cSnRule();
            String originalFactorySnShow = taskOrderRule.getOriginalFactorySnShow();
            String originalFactorySnRule = taskOrderRule.getOriginalFactorySnRule();
            String customerSnShow = taskOrderRule.getCustomerSnShow();
            String customerSnRule = taskOrderRule.getCustomerSnRule();
            currentTaskOrderRule.setTaskOrder(taskOrder);
            currentTaskOrderRule.setOriginalCountryShow(originalCountryShow);
            currentTaskOrderRule.setOriginalCountryRule(originalCountryRule);
            currentTaskOrderRule.setManufacturerShow(manufacturerShow);
            currentTaskOrderRule.setManufacturerRule(manufacturerRule);
            currentTaskOrderRule.setH3cSnShow(h3cSnShow);
            currentTaskOrderRule.setH3cSnRule(h3cSnRule);
            currentTaskOrderRule.setOriginalFactorySnShow(originalFactorySnShow);
            currentTaskOrderRule.setOriginalFactorySnRule(originalFactorySnRule);
            currentTaskOrderRule.setCustomerSnShow(customerSnShow);
            currentTaskOrderRule.setCustomerSnRule(customerSnRule);

            if(!"Y".equals(originalCountryShow)){
                originalCountryField.setEditable(false);
                checkRuleBox1.setSelectedIndex(0);
            }else{
                originalCountryField.setEditable(true);
                checkRuleBox1.setSelectedIndex(getComboBoxIndex(originalCountryRule));
            }

            if(!"Y".equals(manufacturerShow)){
                manufacturerField.setEditable(false);
                checkRuleBox2.setSelectedIndex(0);
            }else{
                manufacturerField.setEditable(true);
                checkRuleBox2.setSelectedIndex(getComboBoxIndex(manufacturerRule));
            }

            if(!"Y".equals(h3cSnShow)){
                h3cSNField.setEditable(false);
                checkRuleBox3.setSelectedIndex(0);
            }else{
                h3cSNField.setEditable(true);
                checkRuleBox3.setSelectedIndex(getComboBoxIndex(h3cSnRule));
            }

            if(!"Y".equals(originalFactorySnShow)){
                originalFactorySNField.setEditable(false);
                checkRuleBox4.setSelectedIndex(0);
            }else{
                originalFactorySNField.setEditable(true);
                checkRuleBox4.setSelectedIndex(getComboBoxIndex(originalFactorySnRule));
            }

            if(!"Y".equals(customerSnShow)){
                customerSNField.setEditable(false);
                checkRuleBox5.setSelectedIndex(0);
            }else{
                customerSNField.setEditable(true);
                checkRuleBox5.setSelectedIndex(getComboBoxIndex(customerSnRule));
            }



        }
    }

    private int getComboBoxIndex(String ruleContent){
        if(null==ruleContent||"".equals(ruleContent)){
            return 0;
        }
        List<CheckRule> checkRuleList = AppCache.checkRuleList;
        for(int i=0;i<checkRuleList.size();i++){
            if(ruleContent.equals(checkRuleList.get(i).getRuleContent())){
                return i;
            }
        }
        return 0;
    }

    private void getNextFocus(int i){
        String originalCountryShow = currentTaskOrderRule.getOriginalCountryShow();
        String manufacturerShow = currentTaskOrderRule.getManufacturerShow();
        String h3cSnShow = currentTaskOrderRule.getH3cSnShow();
        String originalFactorySnShow = currentTaskOrderRule.getOriginalFactorySnShow();
        String customerSnShow = currentTaskOrderRule.getCustomerSnShow();
        if(i==0){
            if("Y".equals(originalCountryShow)){
                originalCountryField.requestFocus();
            }else if("Y".equals(manufacturerShow)){
                manufacturerField.requestFocus();
            }else if("Y".equals(h3cSnShow)){
                h3cSNField.requestFocus();
            }else if("Y".equals(originalFactorySnShow)){
                originalFactorySNField.requestFocus();
            }else if("Y".equals(customerSnShow)){
                customerSNField.requestFocus();
            }
        }else if(i==1){
            if("Y".equals(manufacturerShow)){
                manufacturerField.requestFocus();
            }else if("Y".equals(h3cSnShow)){
                h3cSNField.requestFocus();
            }else if("Y".equals(originalFactorySnShow)){
                originalFactorySNField.requestFocus();
            }else if("Y".equals(customerSnShow)){
                customerSNField.requestFocus();
            }
        }else if(i==2){
            if("Y".equals(h3cSnShow)){
                h3cSNField.requestFocus();
            }else if("Y".equals(originalFactorySnShow)){
                originalFactorySNField.requestFocus();
            }else if("Y".equals(customerSnShow)){
                customerSNField.requestFocus();
            }
        }else if(i==3){
            if("Y".equals(originalFactorySnShow)){
                originalFactorySNField.requestFocus();
            }else if("Y".equals(customerSnShow)){
                customerSNField.requestFocus();
            }
        }else if(i==4){
            if("Y".equals(customerSnShow)){
                customerSNField.requestFocus();
            }
        }else if(i==5){
            if("Y".equals(h3cSnShow)){
                h3cSNField.requestFocus();
            }else if("Y".equals(originalFactorySnShow)){
                originalFactorySNField.requestFocus();
            }else if("Y".equals(customerSnShow)){
                customerSNField.requestFocus();
            }
        }
    }

}
