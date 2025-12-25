package QLNS.view;

import QLNS.controller.TaiKhoanController;
import QLNS.model.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class FrmQLTK extends JPanel {

    // ===== BUTTON =====
    public JButton btnThem, btnLuu, btnXoa, btnTim;

    // ===== TABLE =====
    private JTable table;

    // ===== FIELD =====
    private JTextField txtTenTK, txtTim;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboLoaiTK, cboMaNV;

    public FrmQLTK() {
        initUI();
        new TaiKhoanController(this);
    }

    private void initUI() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // ===== PANEL THÔNG TIN =====
    JPanel pnlInfo = new JPanel(new GridBagLayout());
    pnlInfo.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 5, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // ROW 1
    gbc.gridx = 0; gbc.gridy = 0;
    pnlInfo.add(new JLabel("Tên TK:"), gbc);

    gbc.gridx = 1;
    txtTenTK = new JTextField(15);
    pnlInfo.add(txtTenTK, gbc);

    gbc.gridx = 2;
    pnlInfo.add(new JLabel("Loại TK:"), gbc);

    gbc.gridx = 3;
    cboLoaiTK = new JComboBox<>();
    pnlInfo.add(cboLoaiTK, gbc);

    // ROW 2
    gbc.gridx = 0; gbc.gridy = 1;
    pnlInfo.add(new JLabel("Mật khẩu:"), gbc);

    gbc.gridx = 1;
    txtMatKhau = new JPasswordField(15);
    pnlInfo.add(txtMatKhau, gbc);

    gbc.gridx = 2;
    pnlInfo.add(new JLabel("Mã NV:"), gbc);

    gbc.gridx = 3;
    cboMaNV = new JComboBox<>();
    pnlInfo.add(cboMaNV, gbc);

    add(pnlInfo, BorderLayout.NORTH);

    // ===== PANEL CENTER =====
    JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));

    // BUTTON + SEARCH
    JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

    btnThem = new JButton("Thêm");
    btnLuu = new JButton("Sửa");
    btnXoa = new JButton("Xóa");

    pnlAction.add(btnThem);
    pnlAction.add(btnLuu);
    pnlAction.add(btnXoa);

    pnlAction.add(Box.createHorizontalStrut(30));
    pnlAction.add(new JLabel("Tìm:"));

    txtTim = new JTextField(15);
    pnlAction.add(txtTim);

    btnTim = new JButton("Tìm");
    pnlAction.add(btnTim);

    pnlCenter.add(pnlAction, BorderLayout.NORTH);

    // TABLE
    table = new JTable();
    JScrollPane scroll = new JScrollPane(table);
    scroll.setBorder(BorderFactory.createTitledBorder("Danh sách tài khoản"));

    pnlCenter.add(scroll, BorderLayout.CENTER);

    add(pnlCenter, BorderLayout.CENTER);

    enableForm(false);
}


    // ===== HIỂN THỊ DỮ LIỆU =====
    public void showData(List<TaiKhoan> list) {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Tên TK", "Mật khẩu", "Loại TK", "Mã NV"}, 0
        );

        for (TaiKhoan tk : list) {
            model.addRow(new Object[]{
                    tk.getTenTaiKhoan(),
                    tk.getMatKhau(),
                    tk.getLoaiTaiKhoan(),
                    tk.getMaNhanVien()
            });
        }
        table.setModel(model);
    }

    // ===== LẤY DATA FORM =====
    public TaiKhoan getFormData() {
        return new TaiKhoan(
                txtTenTK.getText().trim(),
                new String(txtMatKhau.getPassword()),
                cboLoaiTK.getSelectedItem().toString(),
                cboMaNV.getSelectedItem().toString()
        );
    }

    public void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtTenTK.setText(table.getValueAt(row, 0).toString());
        txtMatKhau.setText(table.getValueAt(row, 1).toString());
        cboLoaiTK.setSelectedItem(table.getValueAt(row, 2).toString());
        cboMaNV.setSelectedItem(table.getValueAt(row, 3).toString());

        enableForm(true);
    }

    public void clearForm() {
        txtTenTK.setText("");
        txtMatKhau.setText("");
        cboLoaiTK.setSelectedIndex(0);
        cboMaNV.setSelectedIndex(0);
    }

    public String getKeyword() {
        return txtTim.getText().trim();
    }

    public String getSelectedTenTK() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return table.getValueAt(row, 0).toString();
    }

    public void enableForm(boolean b) {
        txtTenTK.setEnabled(b);
        txtMatKhau.setEnabled(b);
        cboLoaiTK.setEnabled(b);
        cboMaNV.setEnabled(b);
    }

    // ===== GETTER =====
    public JTable getTable() { return table; }
    public JComboBox<String> getCboLoaiTK() { return cboLoaiTK; }
    public JComboBox<String> getCboMaNV() { return cboMaNV; }
    
    // ===== NEW METHODS FOR MVC PATTERN =====
    public void addAddListener(ActionListener listener) {
        btnThem.addActionListener(listener);
    }
    
    public void addEditListener(ActionListener listener) {
        btnLuu.addActionListener(listener);
    }
    
    public void addDeleteListener(ActionListener listener) {
        btnXoa.addActionListener(listener);
    }
    
    public void addTableClickListener(MouseListener listener) {
        table.addMouseListener(listener);
    }
}

