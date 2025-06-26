package org.example.onlinecontact.frame;

import org.example.onlinecontact.client.Client;
import org.example.onlinecontact.client.login.Login;
import org.example.onlinecontact.exception.LoginErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class LoginDialog extends JDialog {


    Integer id=null;
    public LoginDialog(MainFrame frame) {
        super(frame, "Login",true);

        setSize(350,200);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //container = getContentPane();

        JPanel panel = new JPanel();
        // 添加面板
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(id==null)
                {
                    Window window1 = SwingUtilities.getWindowAncestor(LoginDialog.this);
                    window1.dispose();
                }
            }
        });
        placeComponents(panel);
        add(panel);
        setResizable(false);

        setVisible(true);
    }
    private void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("User:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(e -> {
            String user = userText.getText();
            String password = passwordText.getText();
            if(user!=null && password!=null) {
                try{
                    MainFrame mainFrame = (MainFrame) getParent();
                    Client client=new Client(user,password,mainFrame);
                    Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                    Window window1 = SwingUtilities.getWindowAncestor(LoginDialog.this);
                    mainFrame.setUser(client.user);
                    mainFrame.setCLient(client);
                    window1.setVisible(true);
                    window.dispose();


                }
                catch(LoginErrorException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(loginButton);

    }
    /*public Integer getId()
    {
        return id;
    }*/
}
