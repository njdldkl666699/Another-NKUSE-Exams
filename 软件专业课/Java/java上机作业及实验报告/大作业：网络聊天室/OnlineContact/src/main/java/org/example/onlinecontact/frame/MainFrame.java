package org.example.onlinecontact.frame;

import org.example.onlinecontact.client.Client;
import org.example.onlinecontact.dto.MessageDTO;
import org.example.onlinecontact.pojo.User;

import javax.swing.*;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JPanel userPanel;
    private User user;
    private Client client;

    // 聊天界面组件
    private JPanel messagePanel;
    private JTextArea txtInput;
    private JButton btnSend;

    private HashMap<JButton,Integer> buttonId;
    private HashMap<Integer,JButton> idButton;

    private Set<Integer> unreadIds = new HashSet<>();
    private HashMap<Integer, String> originalButtonTexts = new HashMap<>();

    private Integer nowId;
    public MainFrame() {
        super("Online Contact");
        //setDefaultUser();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

                initializeUI();
            }
        });

        //loadAllMessages();
        setVisible(false);
    }

    private void initializeUI() {
        configureWindow();
        createLeftPanel();
        createRightPanel();
        createWelcomeLabel();
    }

    private void configureWindow() {
        setLayout(new BorderLayout());
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, getHeight()));

        userPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(userPanel);
        loadUserList();

        leftPanel.add(scrollPane, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.LINE_START);
    }

    private void createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("聊天区域"));

        JScrollPane messageScroll = createMessageScroll();
        rightPanel.add(messageScroll, BorderLayout.CENTER);
        rightPanel.add(createInputArea(), BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                createWelcomeLabel();
                scrollToBottom();
            }
        });
    }

    private JScrollPane createMessageScroll() {
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(messagePanel);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (!e.getValueIsAdjusting()) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        return scrollPane;
    }

    private JPanel createInputArea() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // 输入区域占整个聊天区的1/4高度
        panel.setPreferredSize(new Dimension(0, getHeight()/4));

        txtInput = new JTextArea();
        txtInput.setLineWrap(true);
        txtInput.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        // 带滚动条的输入框
        JScrollPane inputScroll = new JScrollPane(txtInput);
        inputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 发送按钮面板（保持按钮高度自适应）
        JPanel buttonPanel = new JPanel(new BorderLayout());
        btnSend = new JButton("发送");
        btnSend.addActionListener(e -> sendMessage());
        btnSend.setPreferredSize(new Dimension(80, 0)); // 宽度固定，高度自适应
        buttonPanel.add(btnSend, BorderLayout.CENTER);

        // 组合布局
        panel.add(inputScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel(
                "欢迎来到聊天室：" + user.getUserName(),
                JLabel.CENTER
        );
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(welcomeLabel, BorderLayout.NORTH);
        revalidate();
    }

    private void loadUserList() {
/*        List<String> users = new ArrayList<>();*/
        List<User> user=client.getUser();
        /*for (int i = 0; i < user.size(); i++) {
            users.add(user.get(i).getUserName());
        }*/
        updateUserButtons(user);
    }

    private void updateUserButtons(List<User> users) {
        userPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(3, 3, 3, 3);

        // 初始化映射关系
        buttonId = new HashMap<>();
        idButton = new HashMap<>();
        originalButtonTexts.clear(); // 清空原始文本缓存

        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            gbc.gridy = i;

            // 创建按钮时检查是否有未读消息
            boolean hasUnread = unreadIds.contains(currentUser.getId());
            JButton btn = createUserButton(
                    hasUnread ?
                            addUnreadDot(currentUser.getUserName()) :
                            currentUser.getUserName()
            );

            userPanel.add(btn, gbc);

            // 保存映射关系
            buttonId.put(btn, currentUser.getId());
            idButton.put(currentUser.getId(), btn);
            originalButtonTexts.put(currentUser.getId(), currentUser.getUserName());

            // 按钮点击事件
            btn.addActionListener(e -> {
                nowId = buttonId.get(btn);

                // 清除未读状态
                if (unreadIds.remove(nowId)) {
                    updateButtonUnreadStatus(nowId, false);
                }

                loadAllMessages();
            });

            // 最后一个元素设置垂直权重
            gbc.weighty = (i == users.size() - 1) ? 1.0 : 0.0;
        }

        // 添加顶部对齐的占位符
        userPanel.add(Box.createVerticalGlue(), gbc);

        userPanel.revalidate();
        userPanel.repaint();
    }

    // 辅助方法：为按钮文本添加红点
    private String addUnreadDot(String originalText) {
        return "<html>" + originalText + " <font color='red'>•</font></html>";
    }
    private void updateButtonUnreadStatus(Integer id, boolean hasUnread) {
        JButton button = idButton.get(id);
        if (button != null) {
            String originalText = originalButtonTexts.get(id);
            if (hasUnread) {
                button.setText("<html>" + originalText + " <font color='red'>•</font></html>");
            } else {
                button.setText(originalText);
            }
        }
    }

    private JButton createUserButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return btn;
    }

    private void loadAllMessages() {
        List<ChatMessage> messages = getAllMessages();
        updateMessageDisplay(messages);
    }

    private List<ChatMessage> getAllMessages()  {
        List<ChatMessage> messages = new ArrayList<>();
        List<MessageDTO> messageDTOS=client.getMessage(nowId);
        for (int i = 0; i < messageDTOS.size(); i++) {
            messages.add(new ChatMessage(
                    messageDTOS.get(i).getSendUsername(),
                    messageDTOS.get(i).getSendTime(),
                    messageDTOS.get(i).getMessage()
            ));
        }
        return messages;
    }

    private void updateMessageDisplay(List<ChatMessage> messages) {
        messagePanel.removeAll();
        messages.forEach(msg -> {
            messagePanel.add(createMessagePanel(msg));
            messagePanel.add(Box.createVerticalStrut(8));
        });
        messagePanel.revalidate();
        messagePanel.repaint();
        scrollToBottom();
    }

    private JPanel createMessagePanel(ChatMessage msg) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel sender = new JLabel(msg.getSender());
        sender.setFont(new Font("微软雅黑", Font.BOLD, 12));

        JLabel time = new JLabel("  " + formatTime(msg.getTime()));
        time.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        time.setForeground(Color.GRAY);

        header.add(sender);
        header.add(time);

        JTextPane content = new JTextPane();
        content.setText(msg.getContent());
        content.setEditable(false);
        content.setBackground(UIManager.getColor("Panel.background"));

        panel.add(header);
        panel.add(content);
        return panel;
    }

    private void scrollToBottom() {
        JScrollBar vertical = ((JScrollPane)messagePanel.getParent().getParent())
                .getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private void sendMessage() {
        String content = txtInput.getText().trim();
        if (!content.isEmpty()) {
            // 实际发送消息逻辑
            txtInput.setText("");
            client.sendMessage("sendMessage "+content+" "+nowId);
            loadAllMessages();
        }
    }

    private String formatTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void setDefaultUser() {
        user = User.builder().userName("zhangsan").id(4).build();
        try {
            client = new Client("zhangsan","123456",this);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "连接服务器失败");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginDialog(new MainFrame()));
    }

    static class ChatMessage {
        private final String sender;
        private final LocalDateTime time;
        private final String content;

        public ChatMessage(String sender, LocalDateTime time, String content) {
            this.sender = sender;
            this.time = time;
            this.content = content;
        }

        public String getSender() { return sender; }
        public LocalDateTime getTime() { return time; }
        public String getContent() { return content; }
    }
    void setUser(User user) {
        this.user = user;
    }
    void setCLient(Client client) {
        this.client = client;
    }
    public void getMessage(Integer id)  {
        if (Objects.equals(id, nowId)) {
            new Thread(this::loadAllMessages).start();
        } else {
            // 添加未读提示
            SwingUtilities.invokeLater(() -> {
                if (unreadIds.add(id)) {
                    updateButtonUnreadStatus(id, true);
                }
            });
        }
    }
}
