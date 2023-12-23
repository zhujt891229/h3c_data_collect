package com.shinwa.datacollect.component;

import cn.hutool.poi.excel.ExcelWriter;
import com.eltima.components.ui.DatePicker;
import com.shinwa.datacollect.entity.CollectData;
import com.shinwa.datacollect.service.CollectDataService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class DataOperateTab extends JPanel {
    private final JTextField taskOrderField2;
    private final JTextField customerSNField2;
    private final JTextField creatorField;
    private final DatePicker datePicker;
    private final JTable jtable;
    private final DefaultTableModel tableModel;

    public DataOperateTab(){

        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2,7,0,0));
        JLabel createTimeLabel = new JLabel("创建时间：");
        datePicker = getDatePicker();
        northPanel.add(createTimeLabel);
        northPanel.add(datePicker);
        northPanel.add(new JLabel(""));
        JLabel taskOrderLabel2 = new JLabel("任务令：");
        taskOrderField2 = new JTextField(20);
        northPanel.add(taskOrderLabel2);
        northPanel.add(taskOrderField2);
        northPanel.add(new JLabel(""));
        JButton queryButton = new JButton("查询");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryData();
            }
        });
        northPanel.add(queryButton);
        JLabel customerSNLabel2 = new JLabel("客户SN：");
        customerSNField2 = new JTextField(20);
        northPanel.add(customerSNLabel2);
        northPanel.add(customerSNField2);
        northPanel.add(new JLabel(""));
        JLabel creatorLabel = new JLabel("创建人：");
        creatorField = new JTextField(20);
        northPanel.add(creatorLabel);
        northPanel.add(creatorField);
        northPanel.add(new JLabel(""));
        JButton exportButton = new JButton("导出");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportData();
            }
        });
        northPanel.add(exportButton);


//        centerPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        centerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
        JCheckBox checkBoxAll = new JCheckBox("全选");
        Vector<String> headers = new Vector<>();
        //headers.add(checkBoxAll.getToolTipText());
        //headers.add("ID");
        headers.add("任务令");
        headers.add("原产国");
        headers.add("厂家代码");
        headers.add("H3C SN");
        headers.add("原厂SN");
        headers.add("客户SN");
        headers.add("创建人");
        headers.add("创建时间");
        Vector<CollectData> data = new Vector<>();
//        data.add(new CollectData());
//        data.add(new CollectData());
//        Vector<Vector> v = new Vector<>();
//        v.add(data);
        tableModel = new DefaultTableModel(data,headers){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };

        jtable = new JTable(tableModel);

        //jtable.setSize(800,600);
        JScrollPane centerPane = new JScrollPane(jtable);


        add(northPanel,BorderLayout.NORTH);
        add(centerPane,BorderLayout.CENTER);

    }

    private static DatePicker getDatePicker(){
        final DatePicker datePicker;
        String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date();
        Font font = new Font("Times New Roman",Font.BOLD,14);
        Dimension dimension = new Dimension(177,24);
        int[] hilightDays = {1,3,5,7};
        int[] disabledDays = {4,6,5,9};
        datePicker = new DatePicker(date,DefaultFormat,font,dimension);

        datePicker.setLocation(137,83);
        datePicker.setBounds(137,83,177,24);
        datePicker.setHightlightdays(hilightDays,Color.red);
        datePicker.setDisableddays(disabledDays);
        datePicker.setLocale(Locale.CHINA);
        datePicker.setTimePanleVisible(true);
        return datePicker;
    }

    private void queryData(){
        String taskOrder2 = taskOrderField2.getText();
//        String originalCountry2 = originalCountryField.getText();
//        String manufacturer = manufacturerField.getText();
//        String h3cSN = h3cSNField.getText();
//        String originalFactorySN = originalFactorySNField.getText();
        String customerSN2 = customerSNField2.getText();
        String createTime = datePicker.getText();
        String creator = creatorField.getText();
        java.util.List<CollectData> list=null;
        try {
            list = CollectDataService.queryData(taskOrder2, customerSN2,creator,createTime);
        }catch(UnknownHostException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"查询语句执行失败，请联系管理员!");
        }
        tableModel.setRowCount(0);//清空原有数据
        if(list!=null&&list.size()>0){
            for(CollectData item:list){
                //加载新数据
                tableModel.addRow(new Object[]{item.getTaskOrder(),item.getOriginalCountry(),item.getManufacturer(),item.getH3cSn(),item.getOriginalFactorySn(),item.getCustomerSn(),item.getCreator(),item.getCreateTime()});
            }

        }
        JOptionPane.showMessageDialog(this,"查询成功!");
    }
    private void exportData(){
        String taskOrder2 = taskOrderField2.getText();
        String customerSN2 = customerSNField2.getText();
        String createTime = datePicker.getText();
        String creator = creatorField.getText();
        java.util.List<CollectData> list=null;
        try {
            list = CollectDataService.queryData(taskOrder2, customerSN2,creator,createTime);
        }catch(UnknownHostException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"查询语句执行失败，请联系管理员!");
        }
        String s = exportExcel(list);
        JOptionPane.showMessageDialog(this,"导出完成!"+s);
    }

    private String exportExcel(java.util.List<CollectData> list){
        String desktopPath = System.getProperty("user.dir");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeStr = localDateTime.format(formatter);
//        ClassLoader classLoader = getClass().getClassLoader();
//        URL resource=classLoader.getResource("template/data_collect.xlsx");
        File sourceFile = new File(desktopPath+File.separator+"template/data_collect.xlsx");
        if(!sourceFile.exists()){
            JOptionPane.showMessageDialog(this,"文件模板不存在,请联系管理员!");
        }
        File file = new File(desktopPath+File.separator+dateTimeStr+ "data_collect.xlsx");
        if(!file.exists()){
            try {
                //file.createNewFile();
                Files.copy(sourceFile.toPath(),file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,"文件创建失败,请联系管理员!");
            }
        }
        ExcelWriter excelWriter = new ExcelWriter(file);
        //写入标题行
        excelWriter.setSheet(0).writeCellValue(0,0,"任务令");
        excelWriter.setSheet(0).writeCellValue(1,0,"原产国");
        excelWriter.setSheet(0).writeCellValue(2,0,"厂家代码");
        excelWriter.setSheet(0).writeCellValue(3,0,"H3C SN");
        excelWriter.setSheet(0).writeCellValue(4,0,"原厂SN");
        excelWriter.setSheet(0).writeCellValue(5,0,"客户SN");
        excelWriter.setSheet(0).writeCellValue(6,0,"创建人");
        excelWriter.setSheet(0).writeCellValue(7,0,"创建时间");
        //写入数据
        for(int y = 1;y<=list.size();y++){
            excelWriter.setSheet(0).writeCellValue(0,y,list.get(y-1).getTaskOrder());
            excelWriter.setSheet(0).writeCellValue(1,y,list.get(y-1).getOriginalCountry());
            excelWriter.setSheet(0).writeCellValue(2,y,list.get(y-1).getManufacturer());
            excelWriter.setSheet(0).writeCellValue(3,y,list.get(y-1).getH3cSn());
            excelWriter.setSheet(0).writeCellValue(4,y,list.get(y-1).getOriginalFactorySn());
            excelWriter.setSheet(0).writeCellValue(5,y,list.get(y-1).getCustomerSn());
            excelWriter.setSheet(0).writeCellValue(6,y,list.get(y-1).getCreator());
            excelWriter.setSheet(0).writeCellValue(7,y,list.get(y-1).getCreateTime());
        }

        excelWriter.close();
        return file.getPath();
    }
}
