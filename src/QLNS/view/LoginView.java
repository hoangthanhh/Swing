package QLNS.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {

    private JTextField tf_username;
    private JPasswordField tf_password;
    private JPanel btn_login;
    private JLabel lbl_login;
    private JLabel lbl_forgot;

    private ActionListener loginListener;

    Color hoverColor = new Color(96, 125, 139);

    public LoginView() {

        setTitle("Đăng nhập hệ thống QLNS");
        setSize(500, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel();
        container.setBackground(Color.WHITE);
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        container.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        container.add(lblTitle);

        // =================== INPUTS =====================
        JPanel pnlInput = new JPanel();
        pnlInput.setPreferredSize(new Dimension(350, 110));
        pnlInput.setLayout(new GridLayout(2, 1, 10, 10));
        pnlInput.setBackground(Color.WHITE);

        // Username
        tf_username = new JTextField();
        makeRounded(tf_username);
        addPlaceholder(tf_username, "Tên đăng nhập");
        pnlInput.add(tf_username);

        // Password
        tf_password = new JPasswordField();
        makeRounded(tf_password);
        addPlaceholder(tf_password, "Mật khẩu");
        pnlInput.add(tf_password);

        container.add(pnlInput);

        // =================== LOGIN BUTTON =====================
        btn_login = new JPanel();
        btn_login.setPreferredSize(new Dimension(330, 45));
        btn_login.setBackground(Color.BLACK);
        btn_login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lbl_login = new JLabel("ĐĂNG NHẬP");
        lbl_login.setForeground(Color.WHITE);
        lbl_login.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn_login.add(lbl_login);

        btn_login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_login.setBackground(hoverColor);
                lbl_login.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_login.setBackground(Color.BLACK);
                lbl_login.setForeground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (loginListener != null) {
                    loginListener.actionPerformed(
                            new ActionEvent(btn_login, ActionEvent.ACTION_PERFORMED, "login")
                    );
                }
            }
        });

        container.add(btn_login);

        // =================== FORGOT =====================
        lbl_forgot = new JLabel("Quên mật khẩu?");
        lbl_forgot.setPreferredSize(new Dimension(330, 40));
        lbl_forgot.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_forgot.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lbl_forgot.setForeground(Color.BLUE);
        lbl_forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        container.add(lbl_forgot);

        add(container, BorderLayout.CENTER);
    }

    private void makeRounded(JComponent comp) {
        comp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
    }

    // ========== PLACEHOLDER ==========
    private void addPlaceholder(JTextField field, String text) {
        field.setForeground(Color.GRAY);
        field.setText(text);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(text);
                }
            }
        });
    }

    public QLNS.model.TaiKhoan getUser() {
        return new QLNS.model.TaiKhoan(
                tf_username.getText(),
                new String(tf_password.getPassword())
        );
    }

    public void addLoginListener(ActionListener listener) {
        this.loginListener = listener;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
