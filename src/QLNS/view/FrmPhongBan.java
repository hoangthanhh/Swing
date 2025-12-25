package QLNS.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPhongBan extends JPanel {

    // ===== FIELD =====
    public JTextField txtMaPB, txtTenPB, txtTim, txtNgayThanhLap;
    public JTextArea txtGhiChu;
    public JButton btnThem, btnLuu, btnXoa, btnTim;
    public JTable table;
    public DefaultTableModel model;

    public FrmPhongBan() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== PANEL THÔNG TIN =====
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBorder(BorderFactory.createTitledBorder("Thông tin phòng ban"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== ROW 1 =====
        gbc.gridx = 0; gbc.gridy = 0;
        pnlInfo.add(new JLabel("Mã phòng ban:"), gbc);

        gbc.gridx = 1;
        txtMaPB = new JTextField(15);
        pnlInfo.add(txtMaPB, gbc);

        gbc.gridx = 2;
        pnlInfo.add(new JLabel("Tên phòng ban:"), gbc);

        gbc.gridx = 3;
        txtTenPB = new JTextField(15);
        pnlInfo.add(txtTenPB, gbc);

        // ===== ROW 2 =====
        gbc.gridx = 0; gbc.gridy = 1;
        pnlInfo.add(new JLabel("Ngày thành lập:"), gbc);

        gbc.gridx = 1;
        txtNgayThanhLap = new JTextField(15);
        pnlInfo.add(txtNgayThanhLap, gbc);

        gbc.gridx = 2;
        pnlInfo.add(new JLabel("Ghi chú:"), gbc);

        gbc.gridx = 3;
        txtGhiChu = new JTextArea(3, 15);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        pnlInfo.add(new JScrollPane(txtGhiChu), gbc);

        add(pnlInfo, BorderLayout.NORTH);

        // ===== PANEL CENTER =====
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));

        // ===== BUTTON + SEARCH =====
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

        // ===== TABLE =====
        String[] columnNames = {
            "Mã PB", "Tên phòng ban", "Ngày thành lập", "Ghi chú"
        };
        
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phòng ban"));

        pnlCenter.add(scroll, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        enableForm(false);
    }

    // ===== FORM UTILS =====
    public void enableForm(boolean b) {
        txtMaPB.setEnabled(b);
        txtTenPB.setEnabled(b);
        txtNgayThanhLap.setEnabled(b);
        txtGhiChu.setEnabled(b);
    }

    public void clearForm() {
        txtMaPB.setText("");
        txtTenPB.setText("");
        txtNgayThanhLap.setText("");
        txtGhiChu.setText("");
    }

    public String getKeyword() {
        return txtTim.getText().trim();
    }

    // ===== GETTER =====
    public JTable getTable() { return table; }
    public JTextField getTxtMaPB() { return txtMaPB; }
    public JTextField getTxtTenPB() { return txtTenPB; }
    public JTextField getTxtNgayThanhLap() { return txtNgayThanhLap; }
    public JTextArea getTxtGhiChu() { return txtGhiChu; }
    
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

