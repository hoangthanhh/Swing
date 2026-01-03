package QLNS.controller;

import QLNS.dao.ChucVuDAO;
import QLNS.model.ChucVu;
import QLNS.view.FrmChucVu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ChucVuController {

    private FrmChucVu view;
    private ChucVuDAO dao;

    public ChucVuController(FrmChucVu view) {
        this.view = view;
        try {
            this.dao = new ChucVuDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối CSDL: " + e.getMessage());
            return;
        }

        loadTable();
        initEvents();
    }

    private void showData(List<ChucVu> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (ChucVu cv : list) {
            model.addRow(new Object[]{
                    cv.getMaCV(),
                    cv.getTenCV(),
                    cv.getMoTa()
            });
        }
    }

    private ChucVu getFormData() {
        return new ChucVu(
                view.getTxtMaCV().getText().trim(),
                view.getTxtTenCV().getText().trim(),
                view.getTxtMoTa().getText().trim()
        );
    }

    private void fillFormFromTable() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaCV().setText(table.getValueAt(row, 0).toString());
            view.getTxtTenCV().setText(table.getValueAt(row, 1).toString());

            Object moTa = table.getValueAt(row, 2);
            view.getTxtMoTa().setText(moTa != null ? moTa.toString() : "");

            view.getTxtMaCV().setEnabled(false);
        }
    }

    private String getSelectedMaCV() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            return view.getTable().getValueAt(row, 0).toString();
        }
        return null;
    }

    private void clearForm() {
        view.getTxtMaCV().setText("");
        view.getTxtTenCV().setText("");
        view.getTxtMoTa().setText("");

        view.getTable().clearSelection();

        // Unlock ID field
        view.getTxtMaCV().setEnabled(true);
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
            ChucVu cv = getFormData();

            if (cv.getMaCV().isEmpty() || cv.getTenCV().isEmpty() || cv.getMoTa().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            for (ChucVu item : dao.getAll()) {
                if (item.getMaCV().equalsIgnoreCase(cv.getMaCV())) {
                    JOptionPane.showMessageDialog(view, "Mã chức vụ đã tồn tại!");
                    return;
                }
            }

            if (dao.insert(cv)) {
                JOptionPane.showMessageDialog(view, "Thêm thành công!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!");
            }
        });

        view.getBtnSua().addActionListener(e -> {
            String maCu = getSelectedMaCV();
            if (maCu == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn chức vụ cần sửa!");
                return;
            }

            ChucVu cvMoi = getFormData();
            if (cvMoi.getMaCV().isEmpty() || cvMoi.getTenCV().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Thông tin không được để trống!");
                return;
            }


            if (dao.update(cvMoi, maCu)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            String ma = getSelectedMaCV();
            if (ma == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn chức vụ cần xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Xóa chức vụ " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.delete(ma)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                }
            }
        });

        view.getBtnTim().addActionListener(e -> {
            String keyword = view.getTxtTim().getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadTable();
            } else {
                List<ChucVu> list = dao.getAll();
                List<ChucVu> result = list.stream()
                        .filter(cv -> cv.getTenCV().toLowerCase().contains(keyword) ||
                                cv.getMaCV().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                showData(result);
            }
        });

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}