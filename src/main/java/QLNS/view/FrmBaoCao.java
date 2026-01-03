package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmBaoCao extends JPanel {

    private JButton btnTim, btnXuat, btnReset;
    private JTable table;
    private JTextField txtTimKiem;

    private JTextField txtMaNV, txtHoTen, txtNgaySinh, txtDiaChi, txtSDT;
    private JTextField txtPhongBan, txtChucVu;
    private JTextField txtLuongCB, txtPhuCap, txtThuong, txtThucLinh;
    private JRadioButton rdoNam, rdoNu;

    public FrmBaoCao() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("TRA CỨU THÔNG TIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JPanel pnlInfo = new JPanel(new GridLayout(4, 6, 10, 15));
        pnlInfo.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));


        txtMaNV = new JTextField();
        txtMaNV.setEnabled(false);
        txtMaNV.setDisabledTextColor(Color.BLACK);

        txtDiaChi = new JTextField();
        txtDiaChi.setEnabled(false);
        txtDiaChi.setDisabledTextColor(Color.BLACK);

        txtLuongCB = new JTextField();
        txtLuongCB.setEnabled(false);
        txtLuongCB.setDisabledTextColor(Color.BLACK);

        txtHoTen = new JTextField();
        txtHoTen.setEnabled(false);
        txtHoTen.setDisabledTextColor(Color.BLACK);

        txtSDT = new JTextField();
        txtSDT.setEnabled(false);
        txtSDT.setDisabledTextColor(Color.BLACK);

        txtPhuCap = new JTextField();
        txtPhuCap.setEnabled(false);
        txtPhuCap.setDisabledTextColor(Color.BLACK);

        txtNgaySinh = new JTextField();
        txtNgaySinh.setEnabled(false);
        txtNgaySinh.setDisabledTextColor(Color.BLACK);

        txtPhongBan = new JTextField();
        txtPhongBan.setEnabled(false);
        txtPhongBan.setDisabledTextColor(Color.BLACK);

        txtThuong = new JTextField();
        txtThuong.setEnabled(false);
        txtThuong.setDisabledTextColor(Color.BLACK);

        txtChucVu = new JTextField();
        txtChucVu.setEnabled(false);
        txtChucVu.setDisabledTextColor(Color.BLACK);

        txtThucLinh = new JTextField();
        txtThucLinh.setEnabled(false);
        txtThucLinh.setFont(new Font("Arial", Font.BOLD, 12));
        txtThucLinh.setDisabledTextColor(Color.RED);

        rdoNam = new JRadioButton("Nam");
        rdoNu = new JRadioButton("Nữ");
        rdoNam.setEnabled(false);
        rdoNu.setEnabled(false);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rdoNam);
        bg.add(rdoNu);

        JPanel pnlGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlGioiTinh.add(rdoNam);
        pnlGioiTinh.add(rdoNu);

        pnlInfo.add(new JLabel("Mã nhân viên:")); pnlInfo.add(txtMaNV);
        pnlInfo.add(new JLabel("Địa chỉ:")); pnlInfo.add(txtDiaChi);
        pnlInfo.add(new JLabel("Lương cơ bản:")); pnlInfo.add(txtLuongCB);

        pnlInfo.add(new JLabel("Họ tên:")); pnlInfo.add(txtHoTen);
        pnlInfo.add(new JLabel("SĐT:")); pnlInfo.add(txtSDT);
        pnlInfo.add(new JLabel("Tiền phụ cấp:")); pnlInfo.add(txtPhuCap);

        pnlInfo.add(new JLabel("Ngày sinh:")); pnlInfo.add(txtNgaySinh);
        pnlInfo.add(new JLabel("Phòng ban:")); pnlInfo.add(txtPhongBan);
        pnlInfo.add(new JLabel("Tiền thưởng:")); pnlInfo.add(txtThuong);

        pnlInfo.add(new JLabel("Giới tính:")); pnlInfo.add(pnlGioiTinh);
        pnlInfo.add(new JLabel("Chức vụ:")); pnlInfo.add(txtChucVu);
        pnlInfo.add(new JLabel("Lương thực lĩnh:")); pnlInfo.add(txtThucLinh);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlSearch.add(new JLabel("Tra cứu theo mã nhân viên hoặc họ tên:"));

        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        pnlSearch.add(txtTimKiem);

        btnTim = new JButton("Tra cứu");
        pnlSearch.add(btnTim);

        btnXuat = new JButton("Xuất excel");
        pnlSearch.add(btnXuat);

        btnReset = new JButton("Reset");
        pnlSearch.add(btnReset);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Mã NV", "Họ tên", "Ngày sinh", "Địa chỉ", "Giới tính", "SĐT",
                        "Phòng ban", "Chức vụ", "Lương CB", "Phụ cấp", "Thưởng", "Thực lĩnh"
                }
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Kết quả tìm kiếm"));

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(lblTitle, BorderLayout.NORTH);
        pnlTop.add(pnlInfo, BorderLayout.CENTER);
        pnlTop.add(pnlSearch, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public JButton getBtnTim() { return btnTim; }
    public JButton getBtnXuat() { return btnXuat; }
    public JButton getBtnReset() { return btnReset; }
    public JTextField getTxtTimKiem() { return txtTimKiem; }
    public JTable getTable() { return table; }

    public JTextField getTxtMaNV() { return txtMaNV; }
    public JTextField getTxtHoTen() { return txtHoTen; }
    public JTextField getTxtNgaySinh() { return txtNgaySinh; }
    public JTextField getTxtDiaChi() { return txtDiaChi; }
    public JTextField getTxtSDT() { return txtSDT; }
    public JTextField getTxtPhongBan() { return txtPhongBan; }
    public JTextField getTxtChucVu() { return txtChucVu; }
    public JTextField getTxtLuongCB() { return txtLuongCB; }
    public JTextField getTxtPhuCap() { return txtPhuCap; }
    public JTextField getTxtThuong() { return txtThuong; }
    public JTextField getTxtThucLinh() { return txtThucLinh; }
    public JRadioButton getRdoNam() { return rdoNam; }
    public JRadioButton getRdoNu() { return rdoNu; }
}