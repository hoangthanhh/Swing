package QLNS.view;

import java.awt.*;
import javax.swing.*;

public class FrmLogin extends JFrame {

    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JButton btnLogin;

    public FrmLogin() {
        setTitle("Đăng nhập hệ thống QLNS");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("LOGIN SYSTEM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Tài khoản:"), gbc);

        txtTaiKhoan = new JTextField(20);
        gbc.gridx = 1;
        add(txtTaiKhoan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Mật khẩu:"), gbc);

        txtMatKhau = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtMatKhau, gbc);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnLogin, gbc);
    }

    // ===== GETTER =====
    public JTextField getTxtTaiKhoan() {
        return txtTaiKhoan;
    }

    public JPasswordField getTxtMatKhau() {
        return txtMatKhau;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }
}
