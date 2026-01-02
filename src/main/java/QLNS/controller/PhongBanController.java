package QLNS.controller;

import QLNS.dao.PhongBanDAO;
import QLNS.model.PhongBan;
import QLNS.view.FrmPhongBan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PhongBanController {

    private FrmPhongBan view;
    private PhongBanDAO dao;

    public PhongBanController(FrmPhongBan view) {
        this.view = view;
        // Khởi tạo DAO
        try {
            this.dao = new PhongBanDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối CSDL: " + e.getMessage());
            return;
        }

        loadTable();
        initEvents();
    }

    private void showData(List<PhongBan> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (PhongBan pb : list) {
            model.addRow(new Object[]{
                    pb.getMaPB(),
                    pb.getTenPB(),
                    pb.getNgayThanhLap(),
                    pb.getGhiChu()
            });
        }
    }

    private PhongBan getFormData() {
        return new PhongBan(
                view.getTxtMaPB().getText().trim(),
                view.getTxtTenPB().getText().trim(),
                view.getTxtNgayThanhLap().getText().trim(),
                view.getTxtGhiChu().getText().trim()
        );
    }

    private void fillFormFromTable() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaPB().setText(table.getValueAt(row, 0).toString());
            view.getTxtTenPB().setText(table.getValueAt(row, 1).toString());
            view.getTxtNgayThanhLap().setText(table.getValueAt(row, 2).toString());

            Object ghiChu = table.getValueAt(row, 3);
            view.getTxtGhiChu().setText(ghiChu != null ? ghiChu.toString() : "");

            view.getTxtMaPB().setEnabled(false);
        }
    }

    private String getSelectedMaPB() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            return view.getTable().getValueAt(row, 0).toString();
        }
        return null;
    }

    private void clearForm() {
        view.getTxtMaPB().setText("");
        view.getTxtTenPB().setText("");
        view.getTxtNgayThanhLap().setText("");
        view.getTxtGhiChu().setText("");

        view.getTable().clearSelection();

        view.getTxtMaPB().setEnabled(true);
    }

    private boolean checkNgay(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateStr.trim());
            Date now = new Date();

            if (date.after(now)) {
                JOptionPane.showMessageDialog(view, "Ngày thành lập không được lớn hơn ngày hiện tại!");
                return false;
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);

            if (year < 1900) {
                JOptionPane.showMessageDialog(view, "Năm thành lập phải lớn hơn hoặc bằng 1900!");
                return false;
            }

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Ngày thành lập không hợp lệ!\nVui lòng nhập đúng định dạng");
            return false;
        }
    }

    private void loadTable() {
        showData(dao.getAll());
    }

    private void initEvents() {
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fillFormFromTable();
            }
        });


        view.getBtnThem().addActionListener(e -> {
            PhongBan pb = getFormData();

            if (pb.getMaPB().isEmpty() || pb.getTenPB().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập Mã và Tên phòng ban!");
                return;
            }
            if (!checkNgay(pb.getNgayThanhLap())) return;

            for (PhongBan item : dao.getAll()) {
                if (item.getMaPB().equalsIgnoreCase(pb.getMaPB())) {
                    JOptionPane.showMessageDialog(view, "Mã phòng ban đã tồn tại!");
                    return;
                }
            }

            if (dao.insert(pb)) {
                JOptionPane.showMessageDialog(view, "Thêm thành công!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!");
            }
        });

        view.getBtnSua().addActionListener(e -> {
            String maCu = getSelectedMaPB();
            if (maCu == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn phòng ban cần sửa!");
                return;
            }

            PhongBan pb = getFormData();
            if (pb.getMaPB().isEmpty() || pb.getTenPB().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không được để trống thông tin!");
                return;
            }
            if (!checkNgay(pb.getNgayThanhLap())) return;

            if (dao.update(pb, maCu)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            String ma = getSelectedMaPB();
            if (ma == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn phòng ban cần xóa!");
                return;
            }

            int cf = JOptionPane.showConfirmDialog(view, "Bạn chắc chắn muốn xóa phòng ban " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (cf == JOptionPane.YES_OPTION) {
                if (dao.delete(ma)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại (Có thể do ràng buộc dữ liệu)!");
                }
            }
        });

        view.getBtnTim().addActionListener(e -> {
            String keyword = view.getTxtTim().getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadTable();
            } else {
                List<PhongBan> list = dao.getAll();
                List<PhongBan> result = list.stream()
                        .filter(pb -> pb.getTenPB().toLowerCase().contains(keyword) ||
                                pb.getMaPB().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                showData(result);
            }
        });
    }
}