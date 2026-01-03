package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPhongBan extends JPanel {

    private JButton btnThem, btnSua, btnXoa, btnTim, btnReset;
    private JTable table;
    private JTextField txtTim;
    private JTextField txtMaPB, txtTenPB, txtNgayThanhLap, txtGhiChu;

    public FrmPhongBan() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblWelcome = new JLabel("DANH MỤC PHÒNG BAN", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 6, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin phòng ban"));

        txtMaPB = new JTextField();
        txtMaPB.setDisabledTextColor(Color.BLACK);
        txtTenPB = new JTextField();
        txtNgayThanhLap = new JTextField();
        txtGhiChu = new JTextField();

        pnlInput.add(new JLabel("Mã phòng ban:")); pnlInput.add(txtMaPB);
        pnlInput.add(new JLabel("Tên phòng ban:")); pnlInput.add(txtTenPB);
        pnlInput.add(new JLabel("Ngày thành lập:")); pnlInput.add(txtNgayThanhLap);
        pnlInput.add(new JLabel("Ghi chú:")); pnlInput.add(txtGhiChu);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnReset = new JButton("Reset");

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
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
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Mã PB", "Tên PB", "Ngày TL", "Ghi chú"}));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phòng ban"));
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

    public JTextField getTxtMaPB() { return txtMaPB; }
    public JTextField getTxtTenPB() { return txtTenPB; }
    public JTextField getTxtNgayThanhLap() { return txtNgayThanhLap; }
    public JTextField getTxtGhiChu() { return txtGhiChu; }
}