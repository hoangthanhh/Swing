package QLNS.controller;

import QLNS.dao.PhuCapDAO;
import QLNS.model.PhuCap;
import QLNS.view.FrmPhuCap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PhuCapController {

    private FrmPhuCap view;
    private PhuCapDAO dao;

    public PhuCapController(FrmPhuCap view) {
        this.view = view;
        try {
            this.dao = new PhuCapDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối CSDL: " + e.getMessage());
            return;
        }

        loadTable();
        initEvents();
    }

    private void showData(List<PhuCap> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (PhuCap pc : list) {
            model.addRow(new Object[]{
                    pc.getMaPC(),
                    pc.getTenPC(),
                    pc.getTienPC(),
                    pc.getNgayHieuLuc()
            });
        }
    }

    private PhuCap getFormData() throws NumberFormatException {
        double tien = 0;
        String tienStr = view.getTxtTienPC().getText().trim();
        if (!tienStr.isEmpty()) {
            tien = Double.parseDouble(tienStr);
        }
        return new PhuCap(
                view.getTxtMaPC().getText().trim(),
                view.getTxtTenPC().getText().trim(),
                tien,
                view.getTxtNgayHL().getText().trim()
        );
    }

    private void fillFormFromTable() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaPC().setText(table.getValueAt(row, 0).toString());
            view.getTxtTenPC().setText(table.getValueAt(row, 1).toString());
            view.getTxtTienPC().setText(table.getValueAt(row, 2).toString());
            view.getTxtNgayHL().setText(table.getValueAt(row, 3).toString());

            view.getTxtMaPC().setEnabled(false);
        }
    }

    private String getSelectedMaPC() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            return view.getTable().getValueAt(row, 0).toString();
        }
        return null;
    }

    private void clearForm() {
        view.getTxtMaPC().setText("");
        view.getTxtTenPC().setText("");
        view.getTxtTienPC().setText("");
        view.getTxtNgayHL().setText("");

        view.getTable().clearSelection();

        view.getTxtMaPC().setEnabled(true);
    }

    private boolean checkNgay(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateStr.trim());
            Date now = new Date();

            if (date.after(now)) {
                JOptionPane.showMessageDialog(view, "Ngày hiệu lực không được lớn hơn ngày hiện tại!");
                return false;
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);

            if (year < 1900) {
                JOptionPane.showMessageDialog(view, "Năm sinh phải lớn hơn hoặc bằng 1900!");
                return false;
            }

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Ngày hiệu lực không hợp lệ!\nVui lòng nhập đúng định dạng");
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
            try {
                PhuCap pc = getFormData();

                if (pc.getMaPC().isEmpty() || pc.getTenPC().isEmpty() || pc.getNgayHieuLuc().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                if (pc.getTienPC() <= 0) {
                    JOptionPane.showMessageDialog(view, "Tiền phụ cấp phải lớn hơn 0!");
                    return;
                }

                if (!checkNgay(pc.getNgayHieuLuc())) return;

                // Check duplicate ID
                for (PhuCap item : dao.getAll()) {
                    if (item.getMaPC().equalsIgnoreCase(pc.getMaPC())) {
                        JOptionPane.showMessageDialog(view, "Mã phụ cấp đã tồn tại!");
                        return;
                    }
                }

                if (dao.insert(pc)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Tiền phụ cấp phải nhập số!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnSua().addActionListener(e -> {
            try {
                String maCu = getSelectedMaPC();
                if (maCu == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn phụ cấp cần sửa!");
                    return;
                }

                PhuCap pcMoi = getFormData();
                if (pcMoi.getMaPC().isEmpty() || pcMoi.getTenPC().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Thông tin chính không được để trống!");
                    return;
                }
                if (pcMoi.getTienPC() <= 0) {
                    JOptionPane.showMessageDialog(view, "Tiền phụ cấp phải lớn hơn 0!");
                    return;
                }
                if (!checkNgay(pcMoi.getNgayHieuLuc())) return;

                if (!pcMoi.getMaPC().equalsIgnoreCase(maCu)) {
                    for (PhuCap item : dao.getAll()) {
                        if (item.getMaPC().equalsIgnoreCase(pcMoi.getMaPC())) {
                            JOptionPane.showMessageDialog(view, "Mã phụ cấp mới bị trùng!");
                            return;
                        }
                    }
                }

                if (dao.update(pcMoi, maCu)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Tiền phụ cấp phải nhập số!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            try {
                String ma = getSelectedMaPC();
                if (ma == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn phụ cấp cần xóa!");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view,
                        "Xóa phụ cấp " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);

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
                List<PhuCap> list = dao.getAll();
                List<PhuCap> result = list.stream()
                        .filter(pc -> pc.getMaPC().toLowerCase().contains(keyword) ||
                                pc.getTenPC().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                showData(result);
            }
        });

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}