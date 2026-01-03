package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmThongTinCaNhan extends JPanel {

    private JButton btnThem, btnSua, btnXoa, btnTim, btnReset;
    private JTable table;
    private JTextField txtTim;
    private JTextField txtMaNV, txtHoTen, txtNgaySinh, txtDiaChi, txtSDT;
    private JRadioButton rdoNam, rdoNu;

    public FrmThongTinCaNhan() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblWelcome = new JLabel("THÔNG TIN NGƯỜI DÙNG", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 4, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin cá nhân"));

        txtMaNV = new JTextField();
        txtMaNV.setDisabledTextColor(Color.BLACK);
        txtHoTen = new JTextField();
        txtNgaySinh = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();

        rdoNam = new JRadioButton("Nam");
        rdoNu = new JRadioButton("Nữ");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rdoNam);
        bg.add(rdoNu);
        rdoNam.setSelected(true);

        JPanel pnlGT = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlGT.add(rdoNam);
        pnlGT.add(rdoNu);

        pnlInput.add(new JLabel("Mã NV:")); pnlInput.add(txtMaNV);
        pnlInput.add(new JLabel("Họ tên:")); pnlInput.add(txtHoTen);
        pnlInput.add(new JLabel("Ngày sinh:")); pnlInput.add(txtNgaySinh);
        pnlInput.add(new JLabel("Địa chỉ:")); pnlInput.add(txtDiaChi);
        pnlInput.add(new JLabel("Giới tính:")); pnlInput.add(pnlGT);
        pnlInput.add(new JLabel("SĐT:")); pnlInput.add(txtSDT);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnReset = new JButton("Reset");

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
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
                new String[]{"Mã NV", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ"}
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        pnlCenter.add(scroll, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnTim() { return btnTim; }
    public JButton getBtnReset() { return btnReset; }


    public JTable getTable() { return table; }
    public JTextField getTxtTim() { return txtTim; }

    public JTextField getTxtMaNV() { return txtMaNV; }
    public JTextField getTxtHoTen() { return txtHoTen; }
    public JTextField getTxtNgaySinh() { return txtNgaySinh; }
    public JTextField getTxtDiaChi() { return txtDiaChi; }
    public JTextField getTxtSDT() { return txtSDT; }

    public JRadioButton getRdoNam() { return rdoNam; }
    public JRadioButton getRdoNu() { return rdoNu; }
}