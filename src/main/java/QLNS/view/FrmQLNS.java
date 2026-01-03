package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmQLNS extends JPanel {

    private JButton btnPhanCong, btnXoa, btnTim, btnXuat, btnReset;
    private JTable table;
    private JTextField txtTim;
    private JTextField txtMaNV, txtHoTen;
    private JComboBox<String> cboPhongBan, cboChucVu;

    public FrmQLNS() {
        initUI();
        txtMaNV.setEnabled(false);
        txtHoTen.setEnabled(false);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblWelcome = new JLabel("QUẢN LÝ NHÂN SỰ", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 4, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Phân công nhân sự"));

        txtMaNV = new JTextField();
        txtMaNV.setDisabledTextColor(Color.BLACK);
        txtHoTen = new JTextField();
        txtHoTen.setDisabledTextColor(Color.BLACK);

        cboPhongBan = new JComboBox<>();
        cboChucVu = new JComboBox<>();

        pnlInput.add(new JLabel("Mã NV:")); pnlInput.add(txtMaNV);
        pnlInput.add(new JLabel("Họ tên:")); pnlInput.add(txtHoTen);
        pnlInput.add(new JLabel("Phòng ban:")); pnlInput.add(cboPhongBan);
        pnlInput.add(new JLabel("Chức vụ:")); pnlInput.add(cboChucVu);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        btnPhanCong = new JButton("Phân công");
        btnXoa = new JButton("Xóa");
        btnXuat = new JButton("Xuất excel");
        btnReset = new JButton("Reset");

        pnlBtn.add(btnPhanCong);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnXuat);
        pnlBtn.add(btnReset);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlBtn, BorderLayout.SOUTH);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(lblWelcome, BorderLayout.NORTH);
        pnlNorth.add(pnlTop, BorderLayout.CENTER);
        add(pnlNorth, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 2));
        pnlSearch.add(new JLabel("Tìm:"));
        txtTim = new JTextField(15);
        pnlSearch.add(txtTim);
        btnTim = new JButton("Tìm");
        pnlSearch.add(btnTim);

        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã NV", "Họ tên", "Phòng ban", "Chức vụ"}
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách nhân sự"));
        pnlCenter.add(scroll, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    public JButton getBtnPhanCong() { return btnPhanCong; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnTim() { return btnTim; }
    public JButton getBtnXuat() { return btnXuat; }
    public JButton getBtnReset() { return btnReset; }

    public JTable getTable() { return table; }
    public JTextField getTxtTim() { return txtTim; }

    public JTextField getTxtMaNV() { return txtMaNV; }
    public JTextField getTxtHoTen() { return txtHoTen; }

    public JComboBox<String> getCboPhongBan() { return cboPhongBan; }
    public JComboBox<String> getCboChucVu() { return cboChucVu; }
}