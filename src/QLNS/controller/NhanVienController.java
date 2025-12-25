package QLNS.controller;

import QLNS.dao.NhanVienDAO;
import QLNS.model.NhanVien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import QLNS.view.NhanVienView;

public class NhanVienController {

    private NhanVienView view;
    private NhanVienDAO dao;

    public NhanVienController() {
        try {
            dao = new NhanVienDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không kết nối được Database!");
        }

        view = new NhanVienView();

        view.addAddListener(new AddListener());
        view.addEditListener(new EditListener());
        view.addDeleteListener(new DeleteListener());
        view.addSaveListener(new SaveListener());
        view.addTableClickListener(new TableClick());

        loadTable();
        view.setVisible(true);
    }

    // ====================================
    // VALIDATE NGÀY SINH
    // ====================================
    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateStr);

            if (date.after(new Date())) {
                JOptionPane.showMessageDialog(null, "Ngày sinh không được trong tương lai!");
                return false;
            }

            int year = Integer.parseInt(dateStr.substring(6));
            if (year < 1900) {
                JOptionPane.showMessageDialog(null, "Năm sinh phải ≥ 1900!");
                return false;
            }

            return true;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày sinh phải đúng định dạng dd-MM-yyyy!");
            return false;
        }
    }

    // ====================================
    // VALIDATE EMAIL GMAIL
    // ====================================
    private boolean isValidGmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$");
    }

    // ====================================
    // LOAD TABLE
    // ====================================
    private void loadTable() {
        view.model.setRowCount(0);
        ArrayList<NhanVien> list = dao.getAll();

        for (NhanVien nv : list) {
            view.model.addRow(new Object[]{
                nv.getHoTen(),
                nv.getNgaySinh(),
                nv.getDiaChi(),
                nv.getGioiTinh(),
                nv.getMaNhanVien(),
                nv.getEmail(),
                nv.getDiemTongKet()
            });
        }
    }

    // ====================================
    // ADD EMPLOYEE
    // ====================================
    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // ----------- LẤY DỮ LIỆU -----------
                String hoTen = view.txtHoTen.getText().trim();
                String ngaySinh = view.txtNgaySinh.getText().trim();
                String diaChi = view.txtDiaChi.getText().trim();
                String gt = view.rdNam.isSelected() ? "Nam" : "Nữ";
                String maNhanVien = view.txtMaNhanVien.getText().trim();
                String email = view.txtEmail.getText().trim();
                String diemTongKetStr = view.txtDiemTongKet.getText().trim();

                // ----------- KIỂM TRA TRỐNG -----------
                if (hoTen.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty()
                        || maNhanVien.isEmpty() || email.isEmpty() || diemTongKetStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không được bỏ trống dữ liệu!");
                    return;
                }

                // ----------- KIỂM TRA NGÀY SINH -----------
                if (!isValidDate(ngaySinh)) {
                    return;
                }

                // ----------- KIỂM TRA EMAIL -----------
                if (!isValidGmail(email)) {
                    JOptionPane.showMessageDialog(null, "Email phải có dạng: example@gmail.com");
                    return;
                }

                // ----------- KIỂM TRA ĐIỂM SỐ -----------
                double diemTongKet;
                try {
                    diemTongKet = Double.parseDouble(diemTongKetStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Điểm tổng kết phải là số!");
                    return;
                }

                // ----------- ĐỐI TƯỢNG NHAN VIEN -----------
                NhanVien nv = new NhanVien(hoTen, ngaySinh, diaChi, gt, maNhanVien, email, diemTongKet);

                if (dao.insert(nv)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadTable();
                    view.resetForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể thêm! (Trùng mã nhân viên?)");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        }
    }

    // ====================================
    // EDIT EMPLOYEE
    // ====================================
    class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String hoTen = view.txtHoTen.getText().trim();
                String ngaySinh = view.txtNgaySinh.getText().trim();
                String diaChi = view.txtDiaChi.getText().trim();
                String gt = view.rdNam.isSelected() ? "Nam" : "Nữ";
                String maNhanVien = view.txtMaNhanVien.getText().trim();
                String email = view.txtEmail.getText().trim();
                String diemTongKetStr = view.txtDiemTongKet.getText().trim();

                if (hoTen.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty()
                        || maNhanVien.isEmpty() || email.isEmpty() || diemTongKetStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không được bỏ trống dữ liệu!");
                    return;
                }

                if (!isValidDate(ngaySinh)) {
                    return;
                }

                if (!isValidGmail(email)) {
                    JOptionPane.showMessageDialog(null, "Email phải có dạng: example@gmail.com");
                    return;
                }

                double diemTongKet;
                try {
                    diemTongKet = Double.parseDouble(diemTongKetStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Điểm tổng kết phải là số!");
                    return;
                }

                NhanVien nv = new NhanVien(hoTen, ngaySinh, diaChi, gt, maNhanVien, email, diemTongKet);

                if (dao.update(nv)) {
                    JOptionPane.showMessageDialog(null, "Sửa thành công!");
                    loadTable();
                    view.resetForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể sửa!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        }
    }

    // ====================================
    // DELETE EMPLOYEE
    // ====================================
    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String maNhanVien = view.txtMaNhanVien.getText();

            if (maNhanVien.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Bạn phải nhập mã nhân viên!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null, "Bạn chắc muốn xóa?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.delete(maNhanVien)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadTable();
                    view.resetForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể xóa!");
                }
            }
        }
    }

    // ====================================
    // CLICK TABLE → LOAD LÊN FORM
    // ====================================
    class TableClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = view.table.getSelectedRow();

            view.txtHoTen.setText(view.model.getValueAt(row, 0).toString());
            view.txtNgaySinh.setText(view.model.getValueAt(row, 1).toString());
            view.txtDiaChi.setText(view.model.getValueAt(row, 2).toString());

            String gt = view.model.getValueAt(row, 3).toString();
            if (gt.equals("Nam")) {
                view.rdNam.setSelected(true);
            } else {
                view.rdNu.setSelected(true);
            }

            view.txtMaNhanVien.setText(view.model.getValueAt(row, 4).toString());
            view.txtEmail.setText(view.model.getValueAt(row, 5).toString());
            view.txtDiemTongKet.setText(view.model.getValueAt(row, 6).toString());
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try (FileWriter fw = new FileWriter("nhanvien_data.txt")) {

                ArrayList<NhanVien> list = dao.getAll();

                for (NhanVien nv : list) {
                    fw.write(
                            nv.getHoTen() + " | "
                            + nv.getNgaySinh() + " | "
                            + nv.getDiaChi() + " | "
                            + nv.getGioiTinh() + " | "
                            + nv.getMaNhanVien() + " | "
                            + nv.getEmail() + " | "
                            + nv.getDiemTongKet() + "\n"
                    );
                }

                JOptionPane.showMessageDialog(null, "Đã lưu file nhanvien_data.txt!");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi lưu file!");
            }
        }
    }
}

