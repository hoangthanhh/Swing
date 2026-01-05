package QLNS.view;

import QLNS.controller.MainController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class FrmMain extends JFrame {

    private final Color PRIMARY_COLOR = new Color(41, 128, 185); // Xanh dương
    private final Color TEXT_COLOR = new Color(255, 255, 255);   // Trắng
    private final Color HOVER_COLOR = new Color(52, 152, 219);   // Xanh nhạt hơn
    private final Font MENU_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font ITEM_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    JMenuBar menuBar = new JMenuBar();

    JMenu mnuHeThong;
    JMenuItem mniQuanLyTaiKhoan;
    JMenuItem mniDangXuat;

    JMenu mnuDanhMuc;
    JMenuItem mniThongTinCaNhan;
    JMenuItem mniPhongBan;
    JMenuItem mniChucVu;
    JMenuItem mniLuong;
    JMenuItem mniThuong;
    JMenuItem mniPhuCap;

    JMenu mnuQuanLy;
    JMenuItem mniQLNS;
    JMenuItem mniBangLuong;

    JMenu mnuChucNang;
    JMenuItem mniTraCuu;
    JMenuItem mniTroGiup;
    JMenuItem mniVeTrangChu;

    JMenuItem mniBaoCao;

    private JPanel pnlContent;
    private JLabel lblStatus;

    private String maNhanVien;
    private String loaiTK;

    public FrmMain(String maNhanVien, String loaiTK) {
        this.maNhanVien = maNhanVien;
        this.loaiTK = loaiTK;

        initComponents();
        initUI();

        new MainController(this);
    }

    private void initComponents() {


        mnuHeThong = createMenu("Hệ thống");
        mniQuanLyTaiKhoan = createMenuItem("Quản lý tài khoản");
        mniDangXuat = createMenuItem("Đăng xuất");

        mnuDanhMuc = createMenu("Danh mục");
        mniThongTinCaNhan = createMenuItem("Thông tin cá nhân");
        mniPhongBan = createMenuItem("Phòng ban");
        mniChucVu = createMenuItem("Chức vụ");
        mniLuong = createMenuItem("Lương");
        mniThuong = createMenuItem("Thưởng");
        mniPhuCap = createMenuItem("Phụ cấp");

        mnuQuanLy = createMenu("Quản lý");
        mniQLNS = createMenuItem("Quản lý nhân sự");
        mniBangLuong = createMenuItem("Bảng lương nhân viên");

        mnuChucNang = createMenu("Chức năng");
        mniTraCuu = createMenuItem("Tra cứu");
        mniTroGiup = createMenuItem("Trợ giúp");
        mniVeTrangChu = createMenuItem("Về trang chủ");

        mniBaoCao = new JMenuItem("Báo cáo");
        mniBaoCao.setFont(MENU_FONT);
        mniBaoCao.setForeground(TEXT_COLOR);
        mniBaoCao.setBackground(PRIMARY_COLOR);
    }

    private void initUI() {
        setTitle("HỆ THỐNG QUẢN LÝ NHÂN SỰ - QLNS");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuBar.setBackground(PRIMARY_COLOR);
        menuBar.setBorder(new EmptyBorder(5, 5, 5, 5));

        UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder());
        mnuHeThong.add(mniVeTrangChu);
        mnuHeThong.addSeparator();
        mnuHeThong.add(mniQuanLyTaiKhoan);
        mnuHeThong.addSeparator();
        mnuHeThong.add(mniDangXuat);

        mnuDanhMuc.add(mniThongTinCaNhan);
        mnuDanhMuc.addSeparator();
        mnuDanhMuc.add(mniPhongBan);
        mnuDanhMuc.add(mniChucVu);
        mnuDanhMuc.addSeparator();
        mnuDanhMuc.add(mniLuong);
        mnuDanhMuc.add(mniThuong);
        mnuDanhMuc.add(mniPhuCap);

        mnuQuanLy.add(mniQLNS);
        mnuDanhMuc.addSeparator();
        mnuQuanLy.add(mniBangLuong);

        mnuChucNang.add(mniTraCuu);
        mnuDanhMuc.addSeparator();
        mnuChucNang.add(mniTroGiup);


        menuBar.add(mnuHeThong);
        menuBar.add(mnuDanhMuc);
        menuBar.add(mnuQuanLy);
        menuBar.add(mnuChucNang);
        menuBar.add(mniBaoCao);

        setJMenuBar(menuBar);

        pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlWelcome = new JPanel();
        pnlWelcome.setLayout(new BoxLayout(pnlWelcome, BoxLayout.Y_AXIS));
        pnlWelcome.setBackground(Color.WHITE);
        pnlWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblWelcome = new JLabel("CHÀO MỪNG ĐẾN VỚI HỆ THỐNG QUẢN LÝ NHÂN SỰ");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblWelcome.setForeground(PRIMARY_COLOR);
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Phần mềm hỗ trợ quản lý hồ sơ, lương thưởng hiệu quả");
        lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlWelcome.add(Box.createVerticalGlue()); // Đẩy nội dung vào giữa theo chiều dọc
        pnlWelcome.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlWelcome.add(lblWelcome);
        pnlWelcome.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlWelcome.add(lblSub);
        pnlWelcome.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("Phát triển bởi Vinh - Thanh - Quang Anh © 2025", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBorder(new EmptyBorder(10, 0, 0, 0));

        pnlContent.add(pnlWelcome, BorderLayout.CENTER);
        pnlContent.add(lblFooter, BorderLayout.SOUTH);

        add(pnlContent, BorderLayout.CENTER);

        // --- CẤU HÌNH STATUS BAR ---
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                new EmptyBorder(5, 10, 5, 10)
        ));
        statusPanel.setBackground(new Color(245, 245, 245));

        lblStatus = new JLabel();
        lblStatus.setText("<html>Xin chào: <b>" + maNhanVien + "</b> | Quyền hạn: <font color='blue'>" + loaiTK + "</font></html>");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblTime = new JLabel(java.time.LocalDate.now().toString());

        statusPanel.add(lblStatus, BorderLayout.WEST);
        statusPanel.add(lblTime, BorderLayout.EAST);

        add(statusPanel, BorderLayout.SOUTH);
    }

    private JMenu createMenu(String title) {
        JMenu menu = new JMenu(title);
        menu.setFont(MENU_FONT);
        menu.setForeground(TEXT_COLOR);
        menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return menu;
    }

    private JMenuItem createMenuItem(String title) {
        JMenuItem item = new JMenuItem(title);
        item.setFont(ITEM_FONT);
        item.setBackground(Color.WHITE);
        return item;
    }

    public void setContentPanel(JPanel panel) {
        pnlContent.removeAll();
        pnlContent.add(panel, BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    public void resetToWelcomeScreen() {
        pnlContent.removeAll();

        JPanel pnlWelcome = new JPanel();
        pnlWelcome.setLayout(new BoxLayout(pnlWelcome, BoxLayout.Y_AXIS));
        pnlWelcome.setBackground(Color.WHITE);
        pnlWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblWelcome = new JLabel("CHÀO MỪNG ĐẾN VỚI HỆ THỐNG QUẢN LÝ NHÂN SỰ");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblWelcome.setForeground(PRIMARY_COLOR);
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Phần mềm hỗ trợ quản lý hồ sơ, lương thưởng hiệu quả");
        lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlWelcome.add(Box.createVerticalGlue()); // Đẩy nội dung vào giữa theo chiều dọc
        pnlWelcome.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlWelcome.add(lblWelcome);
        pnlWelcome.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlWelcome.add(lblSub);
        pnlWelcome.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("Phát triển bởi Vinh - Thanh - Quang Anh © 2025", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBorder(new EmptyBorder(10, 0, 0, 0));

        pnlContent.add(pnlWelcome, BorderLayout.CENTER);
        pnlContent.add(lblFooter, BorderLayout.SOUTH);

        pnlContent.revalidate();
        pnlContent.repaint();
    }

    public JMenuItem getMniDangXuat() { return mniDangXuat; }
    public JMenuItem getmniQuanLyTaiKhoan() { return mniQuanLyTaiKhoan; }
    public JMenuItem getMniQLNS() { return mniQLNS; }
    public JMenuItem getMniPhongBan() { return mniPhongBan; }
    public JMenuItem getMniChucVu() { return mniChucVu; }
    public JMenuItem getMniPhuCap() { return mniPhuCap; }
    public JMenuItem getMniLuong() { return mniLuong; }
    public JMenuItem getMniThuong() { return mniThuong; }
    public JMenuItem getMniThongTinCaNhan() { return mniThongTinCaNhan; }
    public JMenuItem getMniBangLuong() { return mniBangLuong; }
    public JMenuItem getMniTraCuu() { return mniTraCuu; }
    public JMenuItem getMniTroGiup() { return mniTroGiup; }
    public JMenu getMnuQuanLy() { return mnuQuanLy; }
    public JMenuItem getMniBaoCao() { return mniBaoCao; }
    public JMenuItem getMniVeTrangChu() { return mniVeTrangChu; }
    public String getLoaiTK() { return loaiTK; }
    public String getMaNhanVien() { return maNhanVien; }
}