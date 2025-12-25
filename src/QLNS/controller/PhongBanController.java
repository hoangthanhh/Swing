package QLNS.controller;

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
import QLNS.view.FrmPhongBan;

public class PhongBanController {

    private FrmPhongBan view;
    
    public PhongBanController(FrmPhongBan view) {
        this.view = view;
        initEventHandlers();
    }

    private void initEventHandlers() {
        view.addAddListener(new AddListener());
        view.addEditListener(new EditListener());
        view.addDeleteListener(new DeleteListener());
        view.addTableClickListener(new TableClickListener());
    }

    // ====================================
    // VALIDATE NGÀY THÀNH LẬP
    // ====================================
    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateStr);

            if (date.after(new Date())) {
                JOptionPane.showMessageDialog(null, "Ngày thành lập không được trong tương lai!");
                return false;
            }

            int year = Integer.parseInt(dateStr.substring(6));
            if (year < 1900) {
                JOptionPane.showMessageDialog(null, "Năm thành lập phải ≥ 1900!");
                return false;
            }

            return true;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày thành lập phải đúng định dạng dd-MM-yyyy!");
            return false;
        }
    }

    // ====================================
    // ADD PHÒNG BAN
    // ====================================
    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // ----------- LẤY DỮ LIỆU -----------
                String maPB = view.getTxtMaPB().getText().trim();
                String tenPB = view.getTxtTenPB().getText().trim();
                String ngayThanhLap = view.getTxtNgayThanhLap().getText().trim();
                String ghiChu = view.getTxtGhiChu().getText().trim();

                // ----------- KIỂM TRA TRỐNG -----------
                if (maPB.isEmpty() || tenPB.isEmpty() || ngayThanhLap.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không được bỏ trống dữ liệu bắt buộc!");
                    return;
                }

                // ----------- KIỂM TRA NGÀY THÀNH LẬP -----------
                if (!isValidDate(ngayThanhLap)) {
                    return;
                }

                // ----------- GIẢ LẬP ĐỐI TƯỢNG PHÒNG BAN -----------
                Object[] phongBan = new Object[]{maPB, tenPB, ngayThanhLap, ghiChu};

                // ----------- GIẢ LẬP THÊM DỮ LIỆU -----------
                if (insertPhongBan(phongBan)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData(); // Load lại dữ liệu
                    view.clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể thêm! (Trùng mã PB?)");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        }
    }

    // ====================================
    // EDIT PHÒNG BAN
    // ====================================
    class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String maPB = view.getTxtMaPB().getText().trim();
                String tenPB = view.getTxtTenPB().getText().trim();
                String ngayThanhLap = view.getTxtNgayThanhLap().getText().trim();
                String ghiChu = view.getTxtGhiChu().getText().trim();

                if (maPB.isEmpty() || tenPB.isEmpty() || ngayThanhLap.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không được bỏ trống dữ liệu bắt buộc!");
                    return;
                }

                if (!isValidDate(ngayThanhLap)) {
                    return;
                }

                Object[] phongBan = new Object[]{maPB, tenPB, ngayThanhLap, ghiChu};

                if (updatePhongBan(phongBan)) {
                    JOptionPane.showMessageDialog(null, "Sửa thành công!");
                    loadData(); // Load lại dữ liệu
                    view.clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể sửa!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        }
    }

    // ====================================
    // DELETE PHÒNG BAN
    // ====================================
    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String maPB = view.getTxtMaPB().getText();

            if (maPB.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Bạn phải chọn phòng ban để xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null, "Bạn chắc muốn xóa phòng ban " + maPB + "?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (deletePhongBan(maPB)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadData(); // Load lại dữ liệu
                    view.clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể xóa!");
                }
            }
        }
    }

    // ====================================
    // CLICK TABLE → LOAD LÊN FORM
    // ====================================
    class TableClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = view.getTable().getSelectedRow();

            if (row >= 0) {
                view.getTxtMaPB().setText(view.model.getValueAt(row, 0).toString());
                view.getTxtTenPB().setText(view.model.getValueAt(row, 1).toString());
                view.getTxtNgayThanhLap().setText(view.model.getValueAt(row, 2).toString());
                view.getTxtGhiChu().setText(view.model.getValueAt(row, 3).toString());
            }
        }

        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }

    // ====================================
    // CÁC PHƯƠNG THỨC GIẢ LẬP DỮ LIỆU
    // ====================================
    private boolean insertPhongBan(Object[] phongban) {
        // Giả lập thêm dữ liệu vào bảng
        view.model.addRow(phongban);
        return true;
    }

    private boolean updatePhongBan(Object[] phongban) {
        // Giả lập cập nhật dữ liệu trong bảng
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            for (int i = 0; i < phongban.length; i++) {
                view.model.setValueAt(phongban[i], selectedRow, i);
            }
            return true;
        }
        return false;
    }

    private boolean deletePhongBan(String maPB) {
        // Giả lập xóa dữ liệu khỏi bảng
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            view.model.removeRow(selectedRow);
            return true;
        }
        return false;
    }

    private void loadData() {
        // Giả lập load dữ liệu - trong thực tế sẽ lấy từ database
        // Trong phiên bản hoàn chỉnh, phương thức này sẽ load dữ liệu từ DB
    }
}

