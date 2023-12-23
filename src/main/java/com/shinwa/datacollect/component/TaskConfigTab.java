package com.shinwa.datacollect.component;

import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.entity.CheckRule;
import com.shinwa.datacollect.entity.TaskOrderRule;
import com.shinwa.datacollect.service.CollectDataService;
import com.shinwa.datacollect.service.TaskOrderRuleService;
import com.shinwa.datacollect.utils.LoggerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TaskConfigTab extends JPanel {
    Logger logger = LoggerUtil.getLogger(TaskConfigTab.class.getName());
    private final JTextField taskOrderField;
    private final JTextField originalCountryField;
    private final JTextField manufacturerField;
    private final JTextField h3cSNField;
    private final JTextField originalFactorySNField;
    private final JTextField customerSNField;
    private final JCheckBox checkBox1;
    private final JCheckBox checkBox2;
    private final JCheckBox checkBox3;
    private final JCheckBox checkBox4;
    private final JCheckBox checkBox5;
    private final JComboBox<CheckRule> checkRuleBox1;
    private final JComboBox<CheckRule> checkRuleBox2;
    private final JComboBox<CheckRule> checkRuleBox3;
    private final JComboBox<CheckRule> checkRuleBox4;
    private final JComboBox<CheckRule> checkRuleBox5;
    private final JTextField creatorField;
    private final JTextField createTimeField;


    public TaskConfigTab(){
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();
        //头部
        JLabel taskOrderLabel = new JLabel("任务令:");
        taskOrderField = new JTextField(30);
        taskOrderField.setSize(30,15);
        taskOrderField.addActionListener(e -> queryConfig());
        //center
        JLabel fieldConfigLabel = new JLabel("采集数据字段配置:");

        checkBox1 = new JCheckBox();
        JLabel showLabel1 = new JLabel("展示");
        originalCountryField = new JTextField(30);
        originalCountryField.setText("原产国");
        originalCountryField.setEditable(false);
        checkRuleBox1 = new JComboBox<>();
        comboBoxFillData(checkRuleBox1);

        checkBox2 = new JCheckBox();
        JLabel showLabel2 = new JLabel("展示");
        manufacturerField = new JTextField(30);
        manufacturerField.setText("生产商");
        manufacturerField.setEditable(false);
        checkRuleBox2 = new JComboBox<>();
        comboBoxFillData(checkRuleBox2);

        checkBox3 = new JCheckBox();
        JLabel showLabel3 = new JLabel("展示");
        h3cSNField = new JTextField(30);
        h3cSNField.setText("H3C SN");
        h3cSNField.setEditable(false);
        checkRuleBox3 = new JComboBox<>();
        comboBoxFillData(checkRuleBox3);

        checkBox4 = new JCheckBox();
        JLabel showLabel4 = new JLabel("展示");
        originalFactorySNField =new JTextField(30);
        originalFactorySNField.setText("原厂SN");
        originalFactorySNField.setEditable(false);
        checkRuleBox4 = new JComboBox<>();
        comboBoxFillData(checkRuleBox4);

        checkBox5 = new JCheckBox();
        JLabel showLabel5 = new JLabel("展示");
        customerSNField = new JTextField(30);
        customerSNField.setText("客户SN");
        customerSNField.setEditable(false);
        checkRuleBox5 = new JComboBox<>();
        comboBoxFillData(checkRuleBox5);

        JLabel creatorLabel = new JLabel("创建人");
        creatorField = new JTextField(30);
        creatorField.setEditable(false);
        JLabel createTimeLabel = new JLabel("创建时间");
        createTimeField = new JTextField(30);
        createTimeField.setEditable(false);

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        northPanel.add(taskOrderLabel);
        northPanel.add(taskOrderField);
        add(northPanel,BorderLayout.NORTH);

        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints fieldConfigLabelConstraints = new GridBagConstraints();
        fieldConfigLabelConstraints.gridx = 2;
        fieldConfigLabelConstraints.gridy = 0;
        fieldConfigLabelConstraints.gridwidth=1;
        fieldConfigLabelConstraints.gridheight=1;
        fieldConfigLabelConstraints.weightx=0.6;
        fieldConfigLabelConstraints.weighty = 0.1;
        fieldConfigLabelConstraints.anchor=GridBagConstraints.CENTER;
        fieldConfigLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        fieldConfigLabelConstraints.insets = new Insets(0,0,0,0);
        fieldConfigLabelConstraints.ipadx = 5;
        fieldConfigLabelConstraints.ipady = 5;
        centerPanel.add(fieldConfigLabel,fieldConfigLabelConstraints);

        GridBagConstraints checkBox1Constraints = new GridBagConstraints();
        checkBox1Constraints.gridx = 0;
        checkBox1Constraints.gridy = 1;
        checkBox1Constraints.gridwidth=1;
        checkBox1Constraints.gridheight=1;
        checkBox1Constraints.weightx=0.1;
        checkBox1Constraints.weighty = 0.1;
        checkBox1Constraints.anchor=GridBagConstraints.EAST;
//        checkBox1Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkBox1Constraints.insets = new Insets(0,30,0,0);
        checkBox1Constraints.ipadx = 5;
        checkBox1Constraints.ipady = 1;
        centerPanel.add(checkBox1,checkBox1Constraints);

        GridBagConstraints showLabel1Constraints = new GridBagConstraints();
        showLabel1Constraints.gridx = 1;
        showLabel1Constraints.gridy = 1;
        showLabel1Constraints.gridwidth=1;
        showLabel1Constraints.gridheight=1;
        showLabel1Constraints.weightx=0.1;
//        showLabel1Constraints.weighty = 0.1;
        showLabel1Constraints.anchor=GridBagConstraints.WEST;
//        showLabel1Constraints.fill=GridBagConstraints.HORIZONTAL;
        showLabel1Constraints.insets = new Insets(0,0,0,0);
        showLabel1Constraints.ipadx = 5;
        showLabel1Constraints.ipady = 1;
        centerPanel.add(showLabel1,showLabel1Constraints);

        GridBagConstraints originalCountryFieldConstraints = new GridBagConstraints();
        originalCountryFieldConstraints.gridx = 2;
        originalCountryFieldConstraints.gridy = 1;
        originalCountryFieldConstraints.gridwidth=1;
        originalCountryFieldConstraints.gridheight=1;
        originalCountryFieldConstraints.weightx=0.6;
//        originalCountryFieldConstraints.weighty = 0.1;
        originalCountryFieldConstraints.anchor=GridBagConstraints.CENTER;
        originalCountryFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        originalCountryFieldConstraints.insets = new Insets(0,15,0,0);
        originalCountryFieldConstraints.ipadx = 5;
        originalCountryFieldConstraints.ipady = 1;
        centerPanel.add(originalCountryField,originalCountryFieldConstraints);

        GridBagConstraints checkRuleBox1Constraints = new GridBagConstraints();
        checkRuleBox1Constraints.gridx = 3;
        checkRuleBox1Constraints.gridy = 1;
        checkRuleBox1Constraints.gridwidth=1;
        checkRuleBox1Constraints.gridheight=1;
        checkRuleBox1Constraints.weightx=0.4;
//        checkRuleBox1Constraints.weighty = 0.1;
        checkRuleBox1Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox1Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox1Constraints.insets = new Insets(0,15,0,30);
        checkRuleBox1Constraints.ipadx = 5;
        checkRuleBox1Constraints.ipady = 1;
        centerPanel.add(checkRuleBox1,checkRuleBox1Constraints);

        GridBagConstraints checkBox2Constraints = new GridBagConstraints();
        checkBox2Constraints.gridx = 0;
        checkBox2Constraints.gridy = 2;
        checkBox2Constraints.gridwidth=1;
        checkBox2Constraints.gridheight=1;
//        checkBox2Constraints.weightx=0.1;
        checkBox2Constraints.weighty = 0.1;
        checkBox2Constraints.anchor=GridBagConstraints.EAST;
//        checkBox2Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkBox2Constraints.insets = new Insets(0,30,0,0);
        checkBox2Constraints.ipadx = 5;
        checkBox2Constraints.ipady = 1;
        centerPanel.add(checkBox2,checkBox2Constraints);

        GridBagConstraints showLabel2Constraints = new GridBagConstraints();
        showLabel2Constraints.gridx = 1;
        showLabel2Constraints.gridy = 2;
        showLabel2Constraints.gridwidth=1;
        showLabel2Constraints.gridheight=1;
//        showLabel2Constraints.weightx=0.1;
//        showLabel2Constraints.weighty = 0.1;
        showLabel2Constraints.anchor=GridBagConstraints.WEST;
//        showLabel2Constraints.fill=GridBagConstraints.HORIZONTAL;
        showLabel2Constraints.insets = new Insets(0,0,0,0);
        showLabel2Constraints.ipadx = 5;
        showLabel2Constraints.ipady = 1;
        centerPanel.add(showLabel2,showLabel2Constraints);

        GridBagConstraints manufacturerFieldConstraints = new GridBagConstraints();
        manufacturerFieldConstraints.gridx = 2;
        manufacturerFieldConstraints.gridy = 2;
        manufacturerFieldConstraints.gridwidth=1;
        manufacturerFieldConstraints.gridheight=1;
//        manufacturerFieldConstraints.weightx=0.2;
//        manufacturerFieldConstraints.weighty = 0.1;
        manufacturerFieldConstraints.anchor=GridBagConstraints.CENTER;
        manufacturerFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        manufacturerFieldConstraints.insets = new Insets(0,15,0,0);
        manufacturerFieldConstraints.ipadx = 5;
        manufacturerFieldConstraints.ipady = 1;
        centerPanel.add(manufacturerField,manufacturerFieldConstraints);

        GridBagConstraints checkRuleBox2Constraints = new GridBagConstraints();
        checkRuleBox2Constraints.gridx = 3;
        checkRuleBox2Constraints.gridy = 2;
        checkRuleBox2Constraints.gridwidth=1;
        checkRuleBox2Constraints.gridheight=1;
//        checkRuleBox2Constraints.weightx=0.2;
//        checkRuleBox2Constraints.weighty = 0.1;
        checkRuleBox2Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox2Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox2Constraints.insets = new Insets(0,15,0,30);
        checkRuleBox2Constraints.ipadx = 5;
        checkRuleBox2Constraints.ipady = 1;
        centerPanel.add(checkRuleBox2,checkRuleBox2Constraints);

        GridBagConstraints checkBox3Constraints = new GridBagConstraints();
        checkBox3Constraints.gridx = 0;
        checkBox3Constraints.gridy = 3;
        checkBox3Constraints.gridwidth=1;
        checkBox3Constraints.gridheight=1;
//        checkBox3Constraints.weightx=0.2;
        checkBox3Constraints.weighty = 0.1;
        checkBox3Constraints.anchor=GridBagConstraints.EAST;
//        checkBox3Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkBox3Constraints.insets = new Insets(0,30,0,0);
        checkBox3Constraints.ipadx = 5;
        checkBox3Constraints.ipady = 1;
        centerPanel.add(checkBox3,checkBox3Constraints);

        GridBagConstraints showLabel3Constraints = new GridBagConstraints();
        showLabel3Constraints.gridx = 1;
        showLabel3Constraints.gridy = 3;
        showLabel3Constraints.gridwidth=1;
        showLabel3Constraints.gridheight=1;
//        showLabel3Constraints.weightx=0.2;
//        showLabel3Constraints.weighty = 0.1;
        showLabel3Constraints.anchor=GridBagConstraints.WEST;
//        showLabel3Constraints.fill=GridBagConstraints.HORIZONTAL;
        showLabel3Constraints.insets = new Insets(0,0,0,0);
        showLabel3Constraints.ipadx = 5;
        showLabel3Constraints.ipady = 1;
        centerPanel.add(showLabel3,showLabel3Constraints);

        GridBagConstraints h3cSNFieldConstraints = new GridBagConstraints();
        h3cSNFieldConstraints.gridx = 2;
        h3cSNFieldConstraints.gridy = 3;
        h3cSNFieldConstraints.gridwidth=1;
        h3cSNFieldConstraints.gridheight=1;
//        h3cSNFieldConstraints.weightx=0.2;
//        h3cSNFieldConstraints.weighty = 0.1;
        h3cSNFieldConstraints.anchor=GridBagConstraints.CENTER;
        h3cSNFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        h3cSNFieldConstraints.insets = new Insets(0,15,0,0);
        h3cSNFieldConstraints.ipadx = 5;
        h3cSNFieldConstraints.ipady = 1;
        centerPanel.add(h3cSNField,h3cSNFieldConstraints);

        GridBagConstraints checkRuleBox3Constraints = new GridBagConstraints();
        checkRuleBox3Constraints.gridx = 3;
        checkRuleBox3Constraints.gridy = 3;
        checkRuleBox3Constraints.gridwidth=1;
        checkRuleBox3Constraints.gridheight=1;
//        checkRuleBox3Constraints.weightx=0.2;
//        checkRuleBox3Constraints.weighty = 0.1;
        checkRuleBox3Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox3Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox3Constraints.insets = new Insets(0,15,0,30);
        checkRuleBox3Constraints.ipadx = 5;
        checkRuleBox3Constraints.ipady = 1;
        centerPanel.add(checkRuleBox3,checkRuleBox3Constraints);

        GridBagConstraints checkBox4Constraints = new GridBagConstraints();
        checkBox4Constraints.gridx = 0;
        checkBox4Constraints.gridy = 4;
        checkBox4Constraints.gridwidth=1;
        checkBox4Constraints.gridheight=1;
//        checkBox4Constraints.weightx=0.2;
        checkBox4Constraints.weighty = 0.1;
        checkBox4Constraints.anchor=GridBagConstraints.EAST;
//        checkBox4Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkBox4Constraints.insets = new Insets(0,30,0,0);
        checkBox4Constraints.ipadx = 5;
        checkBox4Constraints.ipady = 1;
        centerPanel.add(checkBox4,checkBox4Constraints);

        GridBagConstraints showLabel4Constraints = new GridBagConstraints();
        showLabel4Constraints.gridx = 1;
        showLabel4Constraints.gridy = 4;
        showLabel4Constraints.gridwidth=1;
        showLabel4Constraints.gridheight=1;
//        showLabel4Constraints.weightx=0.2;
//        showLabel4Constraints.weighty = 0.1;
        showLabel4Constraints.anchor=GridBagConstraints.WEST;
//        showLabel4Constraints.fill=GridBagConstraints.HORIZONTAL;
        showLabel4Constraints.insets = new Insets(0,0,0,0);
        showLabel4Constraints.ipadx = 5;
        showLabel4Constraints.ipady = 1;
        centerPanel.add(showLabel4,showLabel4Constraints);

        GridBagConstraints originalFactorySNFieldConstraints = new GridBagConstraints();
        originalFactorySNFieldConstraints.gridx = 2;
        originalFactorySNFieldConstraints.gridy = 4;
        originalFactorySNFieldConstraints.gridwidth=1;
        originalFactorySNFieldConstraints.gridheight=1;
//        originalFactorySNFieldConstraints.weightx=0.2;
//        originalFactorySNFieldConstraints.weighty = 0.1;
        originalFactorySNFieldConstraints.anchor=GridBagConstraints.CENTER;
        originalFactorySNFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        originalFactorySNFieldConstraints.insets = new Insets(0,15,0,0);
        originalFactorySNFieldConstraints.ipadx = 5;
        originalFactorySNFieldConstraints.ipady = 1;
        centerPanel.add(originalFactorySNField,originalFactorySNFieldConstraints);

        GridBagConstraints checkRuleBox4Constraints = new GridBagConstraints();
        checkRuleBox4Constraints.gridx = 3;
        checkRuleBox4Constraints.gridy = 4;
        checkRuleBox4Constraints.gridwidth=1;
        checkRuleBox4Constraints.gridheight=1;
//        checkRuleBox4Constraints.weightx=0.2;
//        checkRuleBox4Constraints.weighty = 0.1;
        checkRuleBox4Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox4Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox4Constraints.insets = new Insets(0,15,0,30);
        checkRuleBox4Constraints.ipadx = 5;
        checkRuleBox4Constraints.ipady = 1;
        centerPanel.add(checkRuleBox4,checkRuleBox4Constraints);

        GridBagConstraints checkBox5Constraints = new GridBagConstraints();
        checkBox5Constraints.gridx = 0;
        checkBox5Constraints.gridy = 5;
        checkBox5Constraints.gridwidth=1;
        checkBox5Constraints.gridheight=1;
//        checkBox5Constraints.weightx=0.2;
        checkBox5Constraints.weighty = 0.1;
        checkBox5Constraints.anchor=GridBagConstraints.EAST;
//        checkBox5Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkBox5Constraints.insets = new Insets(0,30,0,0);
        checkBox5Constraints.ipadx = 5;
        checkBox5Constraints.ipady = 1;
        centerPanel.add(checkBox5,checkBox5Constraints);

        GridBagConstraints showLabel5Constraints = new GridBagConstraints();
        showLabel5Constraints.gridx = 1;
        showLabel5Constraints.gridy = 5;
        showLabel5Constraints.gridwidth=1;
        showLabel5Constraints.gridheight=1;
//        showLabel5Constraints.weightx=0.2;
//        showLabel5Constraints.weighty = 0.1;
        showLabel5Constraints.anchor=GridBagConstraints.WEST;
//        showLabel5Constraints.fill=GridBagConstraints.HORIZONTAL;
        showLabel5Constraints.insets = new Insets(0,0,0,0);
        showLabel5Constraints.ipadx = 5;
        showLabel5Constraints.ipady = 1;
        centerPanel.add(showLabel5,showLabel5Constraints);

        GridBagConstraints customerSNFieldConstraints = new GridBagConstraints();
        customerSNFieldConstraints.gridx = 2;
        customerSNFieldConstraints.gridy = 5;
        customerSNFieldConstraints.gridwidth=1;
        customerSNFieldConstraints.gridheight=1;
//        customerSNFieldConstraints.weightx=0.2;
//        customerSNFieldConstraints.weighty = 0.1;
        customerSNFieldConstraints.anchor=GridBagConstraints.CENTER;
        customerSNFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        customerSNFieldConstraints.insets = new Insets(0,15,0,0);
        customerSNFieldConstraints.ipadx = 5;
        customerSNFieldConstraints.ipady = 1;
        centerPanel.add(customerSNField,customerSNFieldConstraints);

        GridBagConstraints checkRuleBox5Constraints = new GridBagConstraints();
        checkRuleBox5Constraints.gridx = 3;
        checkRuleBox5Constraints.gridy = 5;
        checkRuleBox5Constraints.gridwidth=1;
        checkRuleBox5Constraints.gridheight=1;
//        checkRuleBox5Constraints.weightx=0.2;
//        checkRuleBox5Constraints.weighty = 0.1;
        checkRuleBox5Constraints.anchor=GridBagConstraints.CENTER;
        checkRuleBox5Constraints.fill=GridBagConstraints.HORIZONTAL;
        checkRuleBox5Constraints.insets = new Insets(0,15,0,30);
        checkRuleBox5Constraints.ipadx = 5;
        checkRuleBox5Constraints.ipady = 1;
        centerPanel.add(checkRuleBox5,checkRuleBox5Constraints);

        GridBagConstraints creatorLabelConstraints = new GridBagConstraints();
        creatorLabelConstraints.gridx = 0;
        creatorLabelConstraints.gridy = 6;
        creatorLabelConstraints.gridwidth=2;
        creatorLabelConstraints.gridheight=1;
//        creatorLabelConstraints.weightx=0.2;
        creatorLabelConstraints.weighty = 0.1;
        creatorLabelConstraints.anchor=GridBagConstraints.EAST;
//        creatorLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        creatorLabelConstraints.insets = new Insets(0,30,0,0);
        creatorLabelConstraints.ipadx = 5;
        creatorLabelConstraints.ipady = 1;
        centerPanel.add(creatorLabel,creatorLabelConstraints);

        GridBagConstraints creatorFieldConstraints = new GridBagConstraints();
        creatorFieldConstraints.gridx = 2;
        creatorFieldConstraints.gridy = 6;
        creatorFieldConstraints.gridwidth=1;
        creatorFieldConstraints.gridheight=1;
//        creatorFieldConstraints.weightx=0.2;
//        creatorFieldConstraints.weighty = 0.1;
        creatorFieldConstraints.anchor=GridBagConstraints.CENTER;
        creatorFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        creatorFieldConstraints.insets = new Insets(0,20,0,0);
        creatorFieldConstraints.ipadx = 5;
        creatorFieldConstraints.ipady = 1;
        centerPanel.add(creatorField,creatorFieldConstraints);

        GridBagConstraints createTimeLabelConstraints = new GridBagConstraints();
        createTimeLabelConstraints.gridx = 0;
        createTimeLabelConstraints.gridy = 7;
        createTimeLabelConstraints.gridwidth=2;
        createTimeLabelConstraints.gridheight=1;
//        createTimeLabelConstraints.weightx=0.2;
        createTimeLabelConstraints.weighty = 0.1;
        createTimeLabelConstraints.anchor=GridBagConstraints.EAST;
//        createTimeLabelConstraints.fill=GridBagConstraints.HORIZONTAL;
        createTimeLabelConstraints.insets = new Insets(0,30,0,0);
        createTimeLabelConstraints.ipadx = 5;
        createTimeLabelConstraints.ipady = 1;
        centerPanel.add(createTimeLabel,createTimeLabelConstraints);

        GridBagConstraints createTimeFieldConstraints = new GridBagConstraints();
        createTimeFieldConstraints.gridx = 2;
        createTimeFieldConstraints.gridy = 7;
        createTimeFieldConstraints.gridwidth=1;
        createTimeFieldConstraints.gridheight=1;
//        createTimeFieldConstraints.weightx=0.2;
//        createTimeFieldConstraints.weighty = 0.1;
        createTimeFieldConstraints.anchor=GridBagConstraints.CENTER;
        createTimeFieldConstraints.fill=GridBagConstraints.HORIZONTAL;
        createTimeFieldConstraints.insets = new Insets(0,20,0,0);
        createTimeFieldConstraints.ipadx = 5;
        createTimeFieldConstraints.ipady = 1;
        centerPanel.add(createTimeField,createTimeFieldConstraints);

        add(centerPanel,BorderLayout.CENTER);

        southPanel.add(saveButton);
        southPanel.add(deleteButton);
        add(southPanel, BorderLayout.SOUTH);
        
    }

    private void comboBoxFillData(JComboBox<CheckRule> comboBox){
        List<CheckRule> checkRuleList = AppCache.checkRuleList;
        for(CheckRule checkRule:checkRuleList){
            comboBox.addItem(checkRule);
        }
    }

    private void queryConfig(){
        String taskOrder = taskOrderField.getText();
        java.util.List<TaskOrderRule> list = null;
        try {
            list = TaskOrderRuleService.queryData(taskOrder);
            if(null!=list&&list.size()>0){
                TaskOrderRule taskOrderRule = list.get(0);
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
                String creator = taskOrderRule.getCreator();
                String createTime = taskOrderRule.getCreateTime();
                creatorField.setText(creator);
                createTimeField.setText(createTime);
                if("Y".equals(originalCountryShow)){
                    checkBox1.setSelected(true);
                    checkRuleBox1.setSelectedIndex(getComboBoxIndex(originalCountryRule));
                }
                if("Y".equals(manufacturerShow)){
                    checkBox2.setSelected(true);
                    checkRuleBox2.setSelectedIndex(getComboBoxIndex(manufacturerRule));
                }
                if("Y".equals(h3cSnShow)){
                    checkBox3.setSelected(true);
                    checkRuleBox3.setSelectedIndex(getComboBoxIndex(h3cSnRule));
                }
                if("Y".equals(originalFactorySnShow)){
                    checkBox4.setSelected(true);
                    checkRuleBox4.setSelectedIndex(getComboBoxIndex(originalFactorySnRule));
                }
                if("Y".equals(customerSnShow)){
                    checkBox5.setSelected(true);
                    checkRuleBox5.setSelectedIndex(getComboBoxIndex(customerSnRule));
                }

            }else{
                checkBox1.setSelected(false);
                checkBox2.setSelected(false);
                checkBox3.setSelected(false);
                checkBox4.setSelected(false);
                checkBox5.setSelected(false);
                checkRuleBox1.setSelectedIndex(0);
                checkRuleBox2.setSelectedIndex(0);
                checkRuleBox3.setSelectedIndex(0);
                checkRuleBox4.setSelectedIndex(0);
                checkRuleBox5.setSelectedIndex(0);
                creatorField.setText("");
                createTimeField.setText("");
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"查询语句执行失败，请联系管理员!");
        }
    }

    private void saveData(){
        String taskOrder = taskOrderField.getText();
//        String originalCountry = originalCountryField.getText();
//        String manufacturer = manufacturerField.getText();
//        String h3cSN = h3cSNField.getText();
//        String originalFactorySN = originalFactorySNField.getText();
//        String customerSN = customerSNField.getText();
        boolean selected1 = checkBox1.isSelected();
        boolean selected2 = checkBox2.isSelected();
        boolean selected3 = checkBox3.isSelected();
        boolean selected4 = checkBox4.isSelected();
        boolean selected5 = checkBox5.isSelected();
        Object selectedItem1 = checkRuleBox1.getSelectedItem();
        Object selectedItem2 = checkRuleBox2.getSelectedItem();
        Object selectedItem3 = checkRuleBox3.getSelectedItem();
        Object selectedItem4 = checkRuleBox4.getSelectedItem();
        Object selectedItem5 = checkRuleBox5.getSelectedItem();
        TaskOrderRule taskOrderRule = new TaskOrderRule();
        taskOrderRule.setTaskOrder(taskOrder);
        if(selected1){
            taskOrderRule.setOriginalCountryShow("Y");
            assert selectedItem1 != null;
            taskOrderRule.setOriginalCountryRule(((CheckRule)selectedItem1).getRuleContent());
        }
        if(selected2){
            taskOrderRule.setManufacturerShow("Y");
            assert selectedItem2 != null;
            taskOrderRule.setManufacturerRule(((CheckRule)selectedItem2).getRuleContent());
        }
        if(selected3){
            taskOrderRule.setH3cSnShow("Y");
            assert selectedItem3 != null;
            taskOrderRule.setH3cSnRule(((CheckRule)selectedItem3).getRuleContent());
        }
        if(selected4){
            taskOrderRule.setOriginalFactorySnShow("Y");
            assert selectedItem4 != null;
            taskOrderRule.setOriginalFactorySnRule(((CheckRule)selectedItem4).getRuleContent());
        }
        if(selected5){
            taskOrderRule.setCustomerSnShow("Y");
            assert selectedItem5 != null;
            taskOrderRule.setCustomerSnRule(((CheckRule)selectedItem5).getRuleContent());
        }

        int i=0;
        try {
            i = TaskOrderRuleService.saveData(taskOrderRule);
        }catch(UnknownHostException e){
            logger.info(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"sql语句执行失败，请联系管理员!");
        }
        if(i!=1){
            JOptionPane.showMessageDialog(this,"保存数据失败，请联系管理员!");
        }else{
            JOptionPane.showMessageDialog(this,"保存成功!");
        }
    }

    private void deleteData(){

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
}
