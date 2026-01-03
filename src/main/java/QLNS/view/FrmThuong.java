package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmThuong extends JPanel {

    private JButton btnThem, btnSua, btnXoa, btnTim, btnReset;
    private JTable table;
    private JTextField txtTim;
    private JTextField txtMaThuong, txtSoTien, txtLyDo;

    public FrmThuong() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblWelcome = new JLabel("DANH MỤC THƯỞNG", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 4, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin thưởng"));

        txtMaThuong = new JTextField();
        txtMaThuong.setDisabledTextColor(Color.BLACK);
        txtSoTien = new JTextField();
        txtLyDo = new JTextField();

        pnlInput.add(new JLabel("Mã thưởng:")); pnlInput.add(txtMaThuong);
        pnlInput.add(new JLabel("Số tiền:")); pnlInput.add(txtSoTien);
        pnlInput.add(new JLabel("Lý do:")); pnlInput.add(txtLyDo);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
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
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 2));

        pnlSearch.add(new JLabel("Tìm:"));
        txtTim = new JTextField(15);
        pnlSearch.add(txtTim);
        btnTim = new JButton("Tìm");
        pnlSearch.add(btnTim);

        pnlCenter.add(pnlSearch, BorderLayout.NORTH);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Mã thưởng", "Số tiền", "Lý do"}));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách thưởng"));
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

    public JTextField getTxtMaThuong() { return txtMaThuong; }
    public JTextField getTxtSoTien() { return txtSoTien; }
    public JTextField getTxtLyDo() { return txtLyDo; }
}