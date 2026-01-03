package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmQLTK extends JPanel {

    private JButton btnThem, btnSua, btnXoa, btnTim, btnXuat, btnReset;
    private JTable table;
    private JTextField txtTenTK, txtTim;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboLoaiTK, cboMaNV;

    public FrmQLTK() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblWelcome = new JLabel("QUẢN LÝ TÀI KHOẢN", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 20, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

        txtTenTK = new JTextField();
        txtTenTK.setDisabledTextColor(Color.BLACK);

        txtMatKhau = new JPasswordField();
        cboLoaiTK = new JComboBox<>();
        cboMaNV = new JComboBox<>();

        pnlInput.add(new JLabel("Tên TK:")); pnlInput.add(txtTenTK);
        pnlInput.add(new JLabel("Mật khẩu:")); pnlInput.add(txtMatKhau);
        pnlInput.add(new JLabel("Loại TK:")); pnlInput.add(cboLoaiTK);
        pnlInput.add(new JLabel("Mã NV:")); pnlInput.add(cboMaNV);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXuat = new JButton("Xuất excel");
        btnReset = new JButton("Reset");

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnXuat);
        pnlBtn.add(btnReset);

        JPanel pnlTop = new JPanel(new BorderLayout(5, 5));
        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlBtn, BorderLayout.SOUTH);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(lblWelcome, BorderLayout.NORTH);
        pnlNorth.add(pnlTop, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        pnlSearch.add(new JLabel("Tìm:"));
        txtTim = new JTextField(15);
        pnlSearch.add(txtTim);
        btnTim = new JButton("Tìm");
        pnlSearch.add(btnTim);

        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Tên TK", "Mật khẩu", "Loại TK", "Mã NV"}
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách tài khoản"));

        pnlCenter.add(scroll, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnTim() { return btnTim; }
    public JButton getBtnXuat() { return btnXuat; }
    public JButton getBtnReset() { return btnReset; }

    public JTable getTable() { return table; }
    public JTextField getTxtTim() { return txtTim; }

    public JTextField getTxtTenTK() { return txtTenTK; }
    public JPasswordField getTxtMatKhau() { return txtMatKhau; }
    public JComboBox<String> getCboLoaiTK() { return cboLoaiTK; }
    public JComboBox<String> getCboMaNV() { return cboMaNV; }
}