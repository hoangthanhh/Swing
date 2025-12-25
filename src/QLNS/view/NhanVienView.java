package QLNS.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NhanVienView extends JFrame {

    public JTextField txtHoTen, txtDiaChi, txtNgaySinh, txtMaNhanVien, txtEmail, txtDiemTongKet;
    public JRadioButton rdNam, rdNu;
    private ButtonGroup groupSex;
    public JButton btnThem, btnSua, btnXoa, btnLuu;
    public JTable table;
    public DefaultTableModel model;

    public NhanVienView() {
        setTitle("Quản Lý Nhân Viên");
        setSize(720, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lbHoTen = new JLabel("Họ Tên ");
        lbHoTen.setBounds(150, 30, 200, 30);
        add(lbHoTen);

        JLabel lbNgaySinh = new JLabel("Ngày Sinh");
        lbNgaySinh.setBounds(150, 70, 200, 30);
        add(lbNgaySinh);

        JLabel lbDiaChi = new JLabel("Địa Chỉ");
        lbDiaChi.setBounds(150, 110, 200, 30);
        add(lbDiaChi);

        JLabel lbGioiTinh = new JLabel("Giới Tính");
        lbGioiTinh.setBounds(150, 150, 200, 30);
        add(lbGioiTinh);

        JLabel lbMaNhanVien = new JLabel("Mã nhân viên");
        lbMaNhanVien.setBounds(150, 190, 200, 30);
        add(lbMaNhanVien);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setBounds(150, 230, 200, 30);
        add(lbEmail);

        JLabel lbDiemTongKet = new JLabel("Điểm tổng kết");
        lbDiemTongKet.setBounds(150, 270, 200, 30);
        add(lbDiemTongKet);

        txtHoTen = new JTextField();
        txtHoTen.setBounds(300, 30, 200, 30);
        add(txtHoTen);

        txtNgaySinh = new JTextField();
        txtNgaySinh.setBounds(300, 70, 200, 30);
        add(txtNgaySinh);

        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(300, 110, 200, 30);
        add(txtDiaChi);

        rdNam = new JRadioButton("Nam");
        rdNam.setBounds(300, 150, 70, 30);

        rdNu = new JRadioButton("Nữ");
        rdNu.setBounds(400, 150, 70, 30);

        groupSex = new ButtonGroup();
        groupSex.add(rdNam);
        groupSex.add(rdNu);

        rdNam.setSelected(true);

        add(rdNam);
        add(rdNu);

        txtMaNhanVien = new JTextField();
        txtMaNhanVien.setBounds(300, 190, 200, 30);
        add(txtMaNhanVien);

        txtEmail = new JTextField();
        txtEmail.setBounds(300, 230, 200, 30);
        add(txtEmail);

        txtDiemTongKet = new JTextField();
        txtDiemTongKet.setBounds(300, 270, 200, 30);
        add(txtDiemTongKet);

        btnThem = new JButton("Thêm");
        btnThem.setBounds(100, 350, 110, 30);
        add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(250, 350, 110, 30);
        add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(400, 350, 110, 30);
        add(btnXoa);

        btnLuu = new JButton("Lưu");
        btnLuu.setBounds(550, 350, 110, 30);
        add(btnLuu);

        String[] columnNames = {
            "Họ tên", "Ngày sinh", "Địa chỉ", "Giới tính", "Mã nhân viên", "Email", "Điểm Tổng Kết"
        };

        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);

        // ăn hết chiều ngang Frame
        sp.setBounds(20, 390, getWidth() - 40, 200);
        add(sp);

        // Tự giãn khi resize cửa sổ
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                sp.setBounds(20, 390, getWidth() - 40, getHeight() - 430);
            }
        });

        // --- THÊM DÒNG NÀY ĐỂ CỬA SỔ HIỆN GIỮA MÀN HÌNH ---
        setLocationRelativeTo(null);
    }

    public void addTableClickListener(MouseListener ml) {
        table.addMouseListener(ml);
    }

    public void addAddListener(ActionListener a) {
        btnThem.addActionListener(a);
    }

    public void addEditListener(ActionListener a) {
        btnSua.addActionListener(a);
    }

    public void addDeleteListener(ActionListener a) {
        btnXoa.addActionListener(a);
    }

    public void addSaveListener(ActionListener a) {
        btnLuu.addActionListener(a);
    }

    public void resetForm() {
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtDiaChi.setText("");
        txtMaNhanVien.setText("");
        txtEmail.setText("");
        txtDiemTongKet.setText("");
        rdNam.setSelected(true);
    }

}

