package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmBangLuong extends JPanel {

    private JButton btnCapNhat, btnXoa, btnTim, btnXuat, btnReset;
    private JTable table;
    private JTextField txtTim;
    private JTextField txtMaNV, txtHoTen, txtLuongThucLinh;
    private JComboBox<String> cboLuongCoBan, cboTienThuong, cboTienPhuCap;

    public FrmBangLuong() {
        initUI();
        txtMaNV.setEnabled(false);
        txtHoTen.setEnabled(false);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblWelcome = new JLabel("BẢNG LƯƠNG NHÂN SỰ", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 8));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin bảng lương"));

        txtMaNV = new JTextField();
        txtMaNV.setDisabledTextColor(Color.BLACK);

        txtHoTen = new JTextField();
        txtHoTen.setDisabledTextColor(Color.BLACK);

        txtLuongThucLinh = new JTextField();
        txtLuongThucLinh.setEditable(false);
        txtLuongThucLinh.setForeground(Color.RED);
        txtLuongThucLinh.setFont(new Font("Arial", Font.BOLD, 12));

        cboLuongCoBan = new JComboBox<>();
        cboTienThuong = new JComboBox<>();
        cboTienPhuCap = new JComboBox<>();

        pnlInput.add(new JLabel("Mã nhân viên:")); pnlInput.add(txtMaNV);
        pnlInput.add(new JLabel("Lương cơ bản:")); pnlInput.add(cboLuongCoBan);
        pnlInput.add(new JLabel("Họ tên:")); pnlInput.add(txtHoTen);
        pnlInput.add(new JLabel("Tiền phụ cấp:")); pnlInput.add(cboTienPhuCap);
        pnlInput.add(new JLabel("Tiền thưởng:")); pnlInput.add(cboTienThuong);
        pnlInput.add(new JLabel("Lương thực lĩnh:")); pnlInput.add(txtLuongThucLinh);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        btnCapNhat = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa");
        btnXuat = new JButton("Xuất excel");
        btnReset = new JButton("Reset");

        pnlBtn.add(btnCapNhat);
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

        pnlSearch.add(new JLabel("Tìm kiếm:"));
        txtTim = new JTextField(15);
        pnlSearch.add(txtTim);
        btnTim = new JButton("Tìm");
        pnlSearch.add(btnTim);

        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã NV", "Họ tên", "Lương CB", "Thưởng", "Phụ cấp", "Thực Lĩnh"}
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách bảng lương"));
        pnlCenter.add(scroll, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }


    public JButton getBtnCapNhat() { return btnCapNhat; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnTim() { return btnTim; }
    public JButton getBtnXuat() { return btnXuat; }
    public JButton getBtnReset() { return btnReset; }


    public JTable getTable() { return table; }
    public JTextField getTxtTim() { return txtTim; }

    public JTextField getTxtMaNV() { return txtMaNV; }
    public JTextField getTxtHoTen() { return txtHoTen; }
    public JTextField getTxtLuongThucLinh() { return txtLuongThucLinh; }

    public JComboBox<String> getCboLuongCoBan() { return cboLuongCoBan; }
    public JComboBox<String> getCboTienThuong() { return cboTienThuong; }
    public JComboBox<String> getCboTienPhuCap() { return cboTienPhuCap; }
}