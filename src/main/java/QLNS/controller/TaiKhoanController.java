package QLNS.controller;

import QLNS.dao.TaiKhoanDAO;
import QLNS.model.TaiKhoan;
import QLNS.view.FrmQLTK;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TaiKhoanController {

    private FrmQLTK view;
    private TaiKhoanDAO dao;

    public TaiKhoanController(FrmQLTK view) {
        this.view = view;

        try {
            dao = new TaiKhoanDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Không kết nối được Database: " + e.getMessage());
            return;
        }

        new LoaiTaiKhoanController(view);
        new NhanVienController(view);

        loadTable();
        initEvents();
    }

    private void showData(List<TaiKhoan> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        for (TaiKhoan tk : list) {
            model.addRow(new Object[]{
                    tk.getTenTaiKhoan(),
                    tk.getMatKhau(),
                    tk.getLoaiTaiKhoan(),
                    tk.getMaNhanVien()
            });
        }
    }

    private TaiKhoan getFormData() {
        String loaiTK = (view.getCboLoaiTK().getSelectedItem() != null) ?
                view.getCboLoaiTK().getSelectedItem().toString() : "";
        String maNV = (view.getCboMaNV().getSelectedItem() != null) ?
                view.getCboMaNV().getSelectedItem().toString() : "";

        return new TaiKhoan(
                view.getTxtTenTK().getText().trim(),
                new String(view.getTxtMatKhau().getPassword()),
                loaiTK,
                maNV
        );
    }

    private void fillFormFromTable() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtTenTK().setText(table.getValueAt(row, 0).toString());
            view.getTxtMatKhau().setText(table.getValueAt(row, 1).toString());

            view.getCboLoaiTK().setSelectedItem(table.getValueAt(row, 2).toString());
            view.getCboMaNV().setSelectedItem(table.getValueAt(row, 3).toString());

            view.getTxtTenTK().setEnabled(false);
        }
    }

    private String getSelectedTenTK() {
        int row = view.getTable().getSelectedRow();
        if (row >= 0) {
            return view.getTable().getValueAt(row, 0).toString();
        }
        return null;
    }

    private void clearForm() {
        view.getTxtTenTK().setText("");
        view.getTxtMatKhau().setText("");
        if (view.getCboLoaiTK().getItemCount() > 0) view.getCboLoaiTK().setSelectedIndex(0);
        if (view.getCboMaNV().getItemCount() > 0) view.getCboMaNV().setSelectedIndex(0);
        view.getTable().clearSelection();

        view.getTxtTenTK().setEnabled(true);
    }

    private void loadTable() {
        List<TaiKhoan> list = dao.getAll();
        showData(list);
    }

    private void exportExcel() {
        JTable table = view.getTable();
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Không có dữ liệu để xuất!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Báo cáo lương");

                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(table.getColumnName(i));

                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    cell.setCellStyle(style);
                }

                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Object val = table.getValueAt(i, j);
                        if (val != null) {
                            row.createCell(j).setCellValue(val.toString());
                        }
                    }
                }

                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                }

                JOptionPane.showMessageDialog(view, "Xuất Excel thành công!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xuất file: " + e.getMessage());
                e.printStackTrace();
            }
        }
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
                TaiKhoan tk = getFormData();

                if (tk.getTenTaiKhoan().isEmpty() || tk.getMatKhau().isEmpty() ||
                        tk.getLoaiTaiKhoan().isEmpty() || tk.getMaNhanVien().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                for (TaiKhoan existingTK : dao.getAll()) {
                    if (existingTK.getTenTaiKhoan().equalsIgnoreCase(tk.getTenTaiKhoan())) {
                        JOptionPane.showMessageDialog(view, "Tên tài khoản '" + tk.getTenTaiKhoan() + "' đã tồn tại!");
                        return;
                    }
                }

                if (dao.insert(tk)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi thêm: " + ex.getMessage());
            }
        });

        view.getBtnSua().addActionListener(e -> {
            try {
                String tenTK_Cu = getSelectedTenTK();

                if (tenTK_Cu == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn tài khoản để sửa!");
                    return;
                }

                TaiKhoan tk_Moi = getFormData();

                if (tk_Moi.getTenTaiKhoan().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Tên tài khoản không được để trống!");
                    return;
                }

                if (!tk_Moi.getTenTaiKhoan().equalsIgnoreCase(tenTK_Cu)) {
                    for (TaiKhoan t : dao.getAll()) {
                        if (t.getTenTaiKhoan().equalsIgnoreCase(tk_Moi.getTenTaiKhoan())) {
                            JOptionPane.showMessageDialog(view, "Tên tài khoản mới đã tồn tại!");
                            return;
                        }
                    }
                }

                if (dao.update(tk_Moi, tenTK_Cu)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi cập nhật: " + ex.getMessage());
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            try {
                String tenTK = getSelectedTenTK();
                if (tenTK == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn tài khoản để xóa!");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(view,
                        "Xóa tài khoản: " + tenTK + "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.delete(tenTK)) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công");
                        loadTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(view, "Xóa thất bại");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi xóa: " + ex.getMessage());
            }
        });

        view.getBtnTim().addActionListener(e -> {
            String keyword = view.getTxtTim().getText().trim().toLowerCase();

            if (keyword.isEmpty()) {
                loadTable();
            } else {
                List<TaiKhoan> allList = dao.getAll();
                List<TaiKhoan> searchResult = allList.stream()
                        .filter(tk -> tk.getTenTaiKhoan().toLowerCase().contains(keyword) ||
                                tk.getMaNhanVien().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                showData(searchResult);
            }
        });

        view.getBtnXuat().addActionListener(e -> exportExcel());

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}