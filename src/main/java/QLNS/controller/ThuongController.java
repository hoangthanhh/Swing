package QLNS.controller;

import QLNS.dao.ThuongDAO;
import QLNS.model.Thuong;
import QLNS.view.FrmThuong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ThuongController {

    private FrmThuong view;
    private ThuongDAO dao;

    public ThuongController(FrmThuong view) {
        this.view = view;
        try {
            this.dao = new ThuongDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối CSDL: " + e.getMessage());
            return;
        }

        loadTable();
        initEvents();
    }

    private void showData(List<Thuong> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Thuong t : list) {
            model.addRow(new Object[]{
                    t.getMaThuong(),
                    t.getSoTien(),
                    t.getLyDo()
            });
        }
    }

    private Thuong getFormData() throws NumberFormatException {
        double soTien = 0;
        String tienStr = view.getTxtSoTien().getText().trim();
        if (!tienStr.isEmpty()) {
            soTien = Double.parseDouble(tienStr);
        }
        return new Thuong(
                view.getTxtMaThuong().getText().trim(),
                soTien,
                view.getTxtLyDo().getText().trim()
        );
    }

    private void fillFormFromTable() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaThuong().setText(table.getValueAt(row, 0).toString());
            view.getTxtSoTien().setText(table.getValueAt(row, 1).toString());

            Object lyDo = table.getValueAt(row, 2);
            view.getTxtLyDo().setText(lyDo != null ? lyDo.toString() : "");

            view.getTxtMaThuong().setEnabled(false);
        }
    }

    private String getSelectedMaThuong() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            return view.getTable().getValueAt(row, 0).toString();
        }
        return null;
    }

    private void clearForm() {
        view.getTxtMaThuong().setText("");
        view.getTxtSoTien().setText("");
        view.getTxtLyDo().setText("");

        view.getTable().clearSelection();

        view.getTxtMaThuong().setEnabled(true);
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
                Thuong t = getFormData();

                if (t.getMaThuong().isEmpty() || t.getLyDo().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                if (t.getSoTien() <= 0) {
                    JOptionPane.showMessageDialog(view, "Số tiền thưởng phải lớn hơn 0!");
                    return;
                }

                for (Thuong item : dao.getAll()) {
                    if (item.getMaThuong().equalsIgnoreCase(t.getMaThuong())) {
                        JOptionPane.showMessageDialog(view, "Mã thưởng đã tồn tại!");
                        return;
                    }
                }

                if (dao.insert(t)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Số tiền phải nhập số!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnSua().addActionListener(e -> {
            try {
                String maCu = getSelectedMaThuong();
                if (maCu == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mục thưởng cần sửa!");
                    return;
                }

                Thuong tMoi = getFormData();
                if (tMoi.getMaThuong().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Mã thưởng không được để trống!");
                    return;
                }
                if (tMoi.getSoTien() <= 0) {
                    JOptionPane.showMessageDialog(view, "Số tiền thưởng phải lớn hơn 0!");
                    return;
                }

                if (!tMoi.getMaThuong().equalsIgnoreCase(maCu)) {
                    for (Thuong item : dao.getAll()) {
                        if (item.getMaThuong().equalsIgnoreCase(tMoi.getMaThuong())) {
                            JOptionPane.showMessageDialog(view, "Mã thưởng mới bị trùng!");
                            return;
                        }
                    }
                }

                if (dao.update(tMoi, maCu)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Số tiền phải nhập số!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            try {
                String ma = getSelectedMaThuong();
                if (ma == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn mục thưởng cần xóa!");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view,
                        "Xóa mã thưởng " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);

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
                List<Thuong> list = dao.getAll();
                List<Thuong> result = list.stream()
                        .filter(t -> t.getMaThuong().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                showData(result);
            }
        });

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}