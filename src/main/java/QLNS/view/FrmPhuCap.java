package QLNS.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPhuCap extends JPanel {

    private JButton btnThem, btnSua, btnXoa, btnTim, btnReset;
    private JTable table;
    private JTextField txtTim;
    private JTextField txtMaPC, txtTenPC, txtTienPC, txtNgayHL;

    public FrmPhuCap() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblWelcome = new JLabel("DANH MỤC PHỤ CẤP", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));

        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 4, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin phụ cấp"));

        txtMaPC = new JTextField();
        txtMaPC.setDisabledTextColor(Color.BLACK);
        txtTenPC = new JTextField();
        txtTienPC = new JTextField();
        txtNgayHL = new JTextField();

        pnlInput.add(new JLabel("Mã phụ cấp:")); pnlInput.add(txtMaPC);
        pnlInput.add(new JLabel("Tên phụ cấp:")); pnlInput.add(txtTenPC);
        pnlInput.add(new JLabel("Tiền phụ cấp:")); pnlInput.add(txtTienPC);
        pnlInput.add(new JLabel("Ngày hiệu lực:")); pnlInput.add(txtNgayHL);

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
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Mã PC", "Tên phụ cấp", "Tiền", "Ngày hiệu lực"}));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách phụ cấp"));
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

    public JTextField getTxtMaPC() { return txtMaPC; }
    public JTextField getTxtTenPC() { return txtTenPC; }
    public JTextField getTxtTienPC() { return txtTienPC; }
    public JTextField getTxtNgayHL() { return txtNgayHL; }
}