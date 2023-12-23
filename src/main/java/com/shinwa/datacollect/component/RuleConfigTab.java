package com.shinwa.datacollect.component;

import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.entity.CheckRule;
import com.shinwa.datacollect.entity.CollectData;
import com.shinwa.datacollect.service.CheckRuleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class RuleConfigTab extends JPanel {
    private final JTable jtable;
    private final DefaultTableModel tableModel;
    private final JTextField queryParamRuleNameField;
    private final JTextField ruleIdField;
    private final JTextField ruleNameField;
    private final JTextField ruleContentField;
    
    public RuleConfigTab(){
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        JLabel ruleNameLabel1 = new JLabel("规则名称");
        queryParamRuleNameField = new JTextField(30);
        JButton queryRuleButton = new JButton("查询");
        queryRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryCheckRule();
            }
        });
        northPanel.add(ruleNameLabel1);
        northPanel.add(queryParamRuleNameField);
        northPanel.add(queryRuleButton);
        add(northPanel,BorderLayout.NORTH);

        Vector<String> headers = new Vector<>();
        //headers.add(checkBoxAll.getToolTipText());
        //headers.add("ID");
        headers.add("ID");
        headers.add("规则名称");
        headers.add("规则内容");
        Vector<CollectData> data = new Vector<>();
        tableModel = new DefaultTableModel(data,headers){
            @Override
            public boolean isCellEditable(int row,int column){
//                if(column==0){
//                    return false;
//                }else{
//                    return true;
//                }
                return false;
            }
        };
        jtable = new JTable(tableModel);
        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = jtable.getSelectedRow();
                if(0!=selectedRow){
                    Object ido= tableModel.getValueAt(selectedRow,0);
                    Object nameo = tableModel.getValueAt(selectedRow, 1);
                    Object contento = tableModel.getValueAt(selectedRow, 2);
                    ruleIdField.setText(ido.toString());
                    ruleNameField.setText(nameo.toString());
                    ruleContentField.setText(contento.toString());
                }
            }
        });
        tableFillData();
        JScrollPane centerPane = new JScrollPane(jtable);
        add(centerPane,BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        ruleIdField = new JTextField(15);
        //southPanel2.add(ruleIdField2);
        JLabel ruleNameLabel2 = new JLabel("规则名称");
        southPanel.add(ruleNameLabel2);
        ruleNameField = new JTextField(15);
        southPanel.add(ruleNameField);
        JLabel ruleContentLabel2 = new JLabel("规则内容");
        southPanel.add(ruleContentLabel2);
        ruleContentField = new JTextField(15);
        southPanel.add(ruleContentField);
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(e -> {
            saveCheckRule();
            ruleNameField.setText("");
            ruleContentField.setText("");
            queryCheckRule();
        });
        southPanel.add(saveButton);
        add(southPanel,BorderLayout.SOUTH);
    }


    private void queryCheckRule(){
        String ruleName = queryParamRuleNameField.getText();
        java.util.List<CheckRule> list=null;
        try {
            list = CheckRuleService.queryData(ruleName);
        }catch(UnknownHostException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"查询语句执行失败，请联系管理员!");
        }
        tableModel.setRowCount(0);//清空原有数据
        if(list!=null&&list.size()>0){
            for(CheckRule item:list){
                //加载新数据
                tableModel.addRow(new Object[]{item.getId(),item.getRuleName(),item.getRuleContent()});
            }
        }
        JOptionPane.showMessageDialog(this,"查询成功!");
    }

    private void tableFillData(){
        List<CheckRule> list = AppCache.checkRuleList;
        for(CheckRule rule:list){
            tableModel.addRow(new Object[]{rule.getId(),rule.getRuleName(),rule.getRuleContent()});
        }

    }


    private void saveCheckRule(){
        String id = ruleIdField.getText();
        String ruleName = ruleNameField.getText();
        String ruleContent = ruleContentField.getText();
        int i = 0;
        try {
            i = CheckRuleService.saveData(id,ruleName, ruleContent);
        }catch(UnknownHostException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"保存语句执行失败，请联系管理员!");
        }
        if(i!=1){
            JOptionPane.showMessageDialog(this,"保存数据失败，请联系管理员!");
        }else{
            JOptionPane.showMessageDialog(this,"保存成功!");
        }
    }
}
