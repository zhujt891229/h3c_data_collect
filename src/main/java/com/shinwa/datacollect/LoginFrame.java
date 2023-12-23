package com.shinwa.datacollect;

import com.shinwa.datacollect.common.AppCache;
import com.shinwa.datacollect.entity.UserInfo;
import com.shinwa.datacollect.service.LoginService;
import com.shinwa.datacollect.utils.ScreenUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class LoginFrame extends JFrame implements ActionListener{
    private final JLabel usernameLabel;
    private final JTextField usernameField;
    private final JLabel passwordLabel;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    public LoginFrame(){
        setTitle("用户登录");
        setSize(500,300);
        //get the window's size
        Dimension screenSize = ScreenUtil.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        //show the window on center
        int x = (screenWidth-this.getWidth())/2;
        int y = (screenHeight-this.getHeight())/2;
        setLocation(x,y);
        //initial components
        setLayout(new BorderLayout());
        usernameLabel = new JLabel("账号:");
        usernameField = new JTextField();
        passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField();
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);
        passwordField.addActionListener(this);
//        passwordField.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
//                    //log.info("回车按钮触发登录事件");
//                    doLogin();
//                }
//            }
//        });

        //add components 1
//        JPanel centerPanel = new JPanel(new GridLayout(2,1));
//        centerPanel.add(usernameLabel);
//        centerPanel.add(usernameField);
//        add(centerPanel,BorderLayout.NORTH);
//
//        JPanel centerPanel2 = new JPanel(new GridLayout(2,2));
//        centerPanel2.add(passwordLabel);
//        centerPanel2.add(passwordField);
//        add(centerPanel2,BorderLayout.CENTER);
//
//        JPanel southPanel = new JPanel();
//        southPanel.add(loginButton);
//        add(southPanel,BorderLayout.SOUTH);

        //add components 2
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints usernameLabelConstraints = new GridBagConstraints();
        usernameLabelConstraints.gridx = 0;
        usernameLabelConstraints.gridy = 0;
        usernameLabelConstraints.gridwidth = 1;
        usernameLabelConstraints.gridheight = 1;
        usernameLabelConstraints.anchor = GridBagConstraints.CENTER;
        usernameLabelConstraints.fill = GridBagConstraints.VERTICAL;
        usernameLabelConstraints.insets = new Insets(5,5,5,25);
        usernameLabelConstraints.ipadx = 10;
        usernameLabelConstraints.ipady = 10;
        usernameLabelConstraints.weightx = 30;
        usernameLabelConstraints.weighty = 50;
        centerPanel.add(usernameLabel,usernameLabelConstraints);

        GridBagConstraints usernameFieldConstraints = new GridBagConstraints();
        usernameFieldConstraints.gridx = 1;
        usernameFieldConstraints.gridy = 0;
        usernameFieldConstraints.gridwidth = 2;
        usernameFieldConstraints.gridheight = 1;
        usernameFieldConstraints.anchor = GridBagConstraints.CENTER;
        usernameFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        usernameFieldConstraints.insets = new Insets(5,5,5,25);
        usernameFieldConstraints.ipadx = 10;
        usernameFieldConstraints.ipady = 10;
        usernameFieldConstraints.weightx = 70;
        //usernameFieldConstraints.weighty = 50;
        centerPanel.add(usernameField,usernameFieldConstraints);

        GridBagConstraints passwordLabelConstraints = new GridBagConstraints();
        passwordLabelConstraints.gridx = 0;
        passwordLabelConstraints.gridy = 1;
        passwordLabelConstraints.gridwidth = 1;
        passwordLabelConstraints.gridheight = 1;
        passwordLabelConstraints.anchor = GridBagConstraints.CENTER;
        passwordLabelConstraints.fill = GridBagConstraints.VERTICAL;
        passwordLabelConstraints.insets = new Insets(5,5,5,25);
        passwordLabelConstraints.ipadx = 10;
        passwordLabelConstraints.ipady = 10;
//        passwordLabelConstraints.weightx = 30;
        passwordLabelConstraints.weighty = 50;
        centerPanel.add(passwordLabel,passwordLabelConstraints);

        GridBagConstraints passwordFieldConstraints = new GridBagConstraints();
        passwordFieldConstraints.gridx = 1;
        passwordFieldConstraints.gridy = 1;
        passwordFieldConstraints.gridwidth = 2;
        passwordFieldConstraints.gridheight = 1;
        passwordFieldConstraints.anchor = GridBagConstraints.CENTER;
        passwordFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        passwordFieldConstraints.insets = new Insets(5,5,5,25);
        passwordFieldConstraints.ipadx = 10;
        passwordFieldConstraints.ipady = 10;
        centerPanel.add(passwordField,passwordFieldConstraints);
        add(centerPanel,BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.add(loginButton);
        add(southPanel,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
        UserInfo userInfo = AppCache.loginCache.get(username);
        if(null==userInfo){
            //如果缓存中不存在用户信息，则调用后端登录接口查询
            userInfo = doLogin();
        }
        if(null != userInfo){
            JOptionPane.showMessageDialog(this,"登录成功!");
            //将登录用户存入缓存中
            AppCache.loginCache.put("userInfo",userInfo);
            //登录成功后跳转到收集数据页面，并关闭登录框
            DesktopFrame desktopFrame = new DesktopFrame();
            desktopFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            desktopFrame.setVisible(true);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this,"用户名或密码错误!");
        }
    }

    public UserInfo doLogin(){
        //获取页面上的用户名和密码
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            return LoginService.login(username, password);
        }catch(UnknownHostException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据库连接失败，请联系管理员!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"用户数据查询失败，请联系管理员!");
        }
        return null;
    }

    public static void main(String[] args){
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

}
