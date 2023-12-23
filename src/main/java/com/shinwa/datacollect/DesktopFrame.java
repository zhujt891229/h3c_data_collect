package com.shinwa.datacollect;

import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.component.CollectDataTab;
import com.shinwa.datacollect.component.DataOperateTab;
import com.shinwa.datacollect.component.RuleConfigTab;
import com.shinwa.datacollect.component.TaskConfigTab;
import com.shinwa.datacollect.entity.UserInfo;
import com.shinwa.datacollect.utils.LoggerUtil;
import com.shinwa.datacollect.utils.ScreenUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.*;

public class DesktopFrame extends JFrame {
    private final JDialog notationDialog;
    private static final Logger logger = LoggerUtil.getLogger(DesktopFrame.class.getName());

    public DesktopFrame(){

        setTitle("数据采集系统");
        setSize(800,600);
        Dimension screenSize = ScreenUtil.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        //show the window on center
        int x = (screenWidth-this.getWidth())/2;
        int y = (screenHeight-this.getHeight())/2;
        setLocation(x,y);
        //initial components
        setLayout(new BorderLayout());
        //初始化头部
        JLabel nameLabel = new JLabel("当前登录用户:");
        JTextField nameField = new JTextField(30);
        UserInfo loginUserInfo = AppCache.loginCache.get("userInfo");
        nameField.setText(loginUserInfo.getDept()+ loginUserInfo.getName());
        nameField.setEditable(false);
        notationDialog = new JDialog(this);
        notationDialog.setLayout(null);
        notationDialog.setTitle("提示");
        JLabel showMessage = new JLabel("确定要退出登录吗?");
        showMessage.setBounds(60,20,120,25);
        JButton confirmButton = new JButton("确认");
        confirmButton.setBounds(60,70,60,25);
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(140,70,60,25);
        notationDialog.add(showMessage);
        notationDialog.add(confirmButton);
        notationDialog.getContentPane().add(cancelButton);
        notationDialog.pack();
        JButton exitButton = new JButton("退出登录");
        exitButton.addActionListener(e -> {
            notationDialog.setSize(270,155);
            Dimension screenSize1 = ScreenUtil.getScreenSize();
            int screenWidth1 = screenSize1.width;
            int screenHeight1 = screenSize1.height;
            int x1 = (screenWidth1 -300)/2;
            int y1 = (screenHeight1 -200)/2;
            notationDialog.setLocation(x1, y1);
            notationDialog.setVisible(true);
        });
        confirmButton.addActionListener(e -> {
            dispose();
            AppCache.loginCache.clear();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setVisible(true);
        });
        cancelButton.addActionListener(e -> notationDialog.dispose());
        JPanel topPanel = new JPanel();
        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(exitButton);

        CollectDataTab collectDataTab = new CollectDataTab();
        DataOperateTab dataOperateTab = new DataOperateTab();
        RuleConfigTab ruleConfigTab = new RuleConfigTab();
        TaskConfigTab taskConfigTab = new TaskConfigTab();

        //页签面板
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            String title = tabbedPane.getTitleAt(selectedIndex);
            logger.info(title+" has been selected!");
        });
        tabbedPane.addTab("数据采集", collectDataTab);
        tabbedPane.addTab("数据操作", dataOperateTab);
        if("admin".equals(AppCache.loginCache.get("userInfo").getUsername())){
            tabbedPane.addTab("规则配置", ruleConfigTab);
            tabbedPane.addTab("任务令配置",taskConfigTab);
        }

        //主面板
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(0.15);
        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(tabbedPane);

        add(splitPane);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showDialog();
            }
        });
    }

    public void showDialog() {
        Object[] options = { "确定", "取消" };
        JOptionPane pane2 = new JOptionPane("确定要退出系统吗?", JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION, null, options, options[1]);
        JDialog dialog = pane2.createDialog(this, "提示");
        dialog.setVisible(true);
        Object selectedValue = pane2.getValue();
        if (selectedValue == null || selectedValue == options[1]) {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 这个是关键
        } else if (selectedValue == options[0]) {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
}
