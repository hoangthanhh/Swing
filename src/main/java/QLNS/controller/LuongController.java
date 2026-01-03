package QLNS.controller;

import QLNS.dao.LuongDAO;
import QLNS.model.Luong;
import QLNS.view.FrmLuong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class LuongController {

    private FrmLuong view;
    private LuongDAO dao;

    public LuongController(FrmLuong view) {
        this.view = view;
        try {
            this.dao = new LuongDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối CSDL: " + e.getMessage());
            return;
        }

        loadTable();
        initEvents();
    }

    // =================================================================
    // LOGIC METHODS (MOVED FROM VIEW)
    // =================================================================

    // 1. Show data on table
    private void showData(List<Luong> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Luong l : list) {
            model.addRow(new Object[]{
                    l.getMaLuong(),
                    l.getLuongCoBan(),
                    l.getGhiChu()
            });
        }
    }

    // 2. Get data from form
    private Luong getFormData() throws NumberFormatException {
        double luong = 0;
        String luongStr = view.getTxtLuongCoBan().getText().trim();
        if (!luongStr.isEmpty()) {
            luong = Double.parseDouble(luongStr);
        }
        return new Luong(
                view.getTxtMaLuong().getText().trim(),
                luong,
                view.getTxtGhiChu().getText().trim()
        );
    }

    // 3. Fill form from table (Lock ID)
    private void fillFormFromTable() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaLuong().setText(table.getValueAt(row, 0).toString());
            view.getTxtLuongCoBan().setText(table.getValueAt(row, 1).toString());

            Object ghiChu = table.getValueAt(row, 2);
            view.getTxtGhiChu().setText(ghiChu != null ? ghiChu.toString() : "");

            // Lock ID field
            view.getTxtMaLuong().setEnabled(false);
        }
    }

    // 4. Get selected ID
    private String getSelectedMaLuong() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            return view.getTable().getValueAt(row, 0).toString();
        }
        return null;
    }

    // 5. Clear form (Unlock ID)
    private void clearForm() {
        view.getTxtMaLuong().setText("");
        view.getTxtLuongCoBan().setText("");
        view.getTxtGhiChu().setText("");

        view.getTable().clearSelection();

        // Unlock ID field
        view.getTxtMaLuong().setEnabled(true);
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
            try {
                Luong l = getFormData();

                if (l.getMaLuong().isEmpty() || l.getGhiChu().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                if (l.getLuongCoBan() <= 0) {
                    JOptionPane.showMessageDialog(view, "Lương cơ bản phải lớn hơn 0!");
                    return;
                }

                for (Luong item : dao.getAll()) {
                    if (item.getMaLuong().equalsIgnoreCase(l.getMaLuong())) {
                        JOptionPane.showMessageDialog(view, "Mã lương đã tồn tại!");
                        return;
                    }
                }

                if (dao.insert(l)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Lương cơ bản phải là số!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnSua().addActionListener(e -> {
            try {
                String maCu = getSelectedMaLuong();
                if (maCu == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mức lương cần sửa!");
                    return;
                }

                Luong lMoi = getFormData();

                if (lMoi.getMaLuong().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Mã lương không được để trống!");
                    return;
                }
                if (lMoi.getLuongCoBan() <= 0) {
                    JOptionPane.showMessageDialog(view, "Lương cơ bản phải lớn hơn 0!");
                    return;
                }

                if (!lMoi.getMaLuong().equalsIgnoreCase(maCu)) {
                    for (Luong item : dao.getAll()) {
                        if (item.getMaLuong().equalsIgnoreCase(lMoi.getMaLuong())) {
                            JOptionPane.showMessageDialog(view, "Mã lương mới bị trùng!");
                            return;
                        }
                    }
                }

                if (dao.update(lMoi, maCu)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Lương cơ bản phải là số!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            try {
                String ma = getSelectedMaLuong();
                if (ma == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mức lương cần xóa!");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view,
                        "Xóa mã lương " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.delete(ma)) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công!");
                        loadTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnTim().addActionListener(e -> {
            String keyword = view.getTxtTim().getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadTable();
            } else {
                List<Luong> list = dao.getAll();
                List<Luong> result = list.stream()
                        .filter(l -> l.getMaLuong().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                showData(result);
            }
        });

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}