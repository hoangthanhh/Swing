package QLNS.controller;

import QLNS.dao.ChucVuDAO;
import QLNS.dao.NhanVienDAO;
import QLNS.dao.PhongBanDAO;
import QLNS.model.ChucVu;
import QLNS.model.PhongBan;
import QLNS.view.FrmQLNS;
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

public class QLNSController {

    private FrmQLNS view;
    private NhanVienDAO daoNV;
    private PhongBanDAO daoPB;
    private ChucVuDAO daoCV;

    private List<PhongBan> listPB;
    private List<ChucVu> listCV;

    public QLNSController(FrmQLNS view) {
        this.view = view;
        try {
            daoNV = new NhanVienDAO();
            daoPB = new PhongBanDAO();
            daoCV = new ChucVuDAO();
        } catch (Exception e) { e.printStackTrace(); }

        loadData();
        initEvents();
    }

    private void loadData() {
        listPB = daoPB.getAll();
        listCV = daoCV.getAll();
        loadCombos(listPB, listCV);

        loadTable();
    }

    private void loadCombos(List<PhongBan> listPB, List<ChucVu> listCV) {
        view.getCboPhongBan().removeAllItems();
        view.getCboPhongBan().addItem("--- Chọn phòng ban ---");
        for (PhongBan pb : listPB) {
            view.getCboPhongBan().addItem(pb.getTenPB());
        }

        view.getCboChucVu().removeAllItems();
        view.getCboChucVu().addItem("--- Chọn chức vụ ---");
        for (ChucVu cv : listCV) {
            view.getCboChucVu().addItem(cv.getTenCV());
        }
    }

    private void showData(List<Object[]> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Object[] r : list) {
            model.addRow(r);
        }
    }

    private void fillForm() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaNV().setText(table.getValueAt(row, 0).toString());
            view.getTxtHoTen().setText(table.getValueAt(row, 1).toString());

            Object pbValue = table.getValueAt(row, 2);
            if (pbValue == null || pbValue.toString().equals("Chưa có")) {
                view.getCboPhongBan().setSelectedIndex(0);
            } else {
                view.getCboPhongBan().setSelectedItem(pbValue.toString());
            }

            Object cvValue = table.getValueAt(row, 3);
            if (cvValue == null || cvValue.toString().equals("Chưa có")) {
                view.getCboChucVu().setSelectedIndex(0);
            } else {
                view.getCboChucVu().setSelectedItem(cvValue.toString());
            }
        }
    }

    private void clearForm() {
        view.getTxtMaNV().setText("");
        view.getTxtHoTen().setText("");
        view.getCboPhongBan().setSelectedIndex(0);
        view.getCboChucVu().setSelectedIndex(0);
        view.getTable().clearSelection();
    }

    private String getSelectedTenPB() {
        if (view.getCboPhongBan().getSelectedIndex() <= 0) return null;
        return view.getCboPhongBan().getSelectedItem().toString();
    }

    private String getSelectedTenCV() {
        if (view.getCboChucVu().getSelectedIndex() <= 0) return null;
        return view.getCboChucVu().getSelectedItem().toString();
    }

    private String getSelectedMaNV() {
        return view.getTxtMaNV().getText();
    }

    private String findMaPB(String tenPB) {
        if (tenPB == null) return null;
        for (PhongBan pb : listPB) {
            if (pb.getTenPB().equals(tenPB)) return pb.getMaPB();
        }
        return null;
    }

    private String findMaCV(String tenCV) {
        if (tenCV == null) return null;
        for (ChucVu cv : listCV) {
            if (cv.getTenCV().equals(tenCV)) return cv.getMaCV();
        }
        return null;
    }

    private void loadTable() {
        List<Object[]> list = daoNV.getAllHienThi();
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
                fillForm();
            }
        });

        view.getBtnPhanCong().addActionListener(e -> {
            String maNV = getSelectedMaNV();
            if (maNV.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên để phân công!");
                return;
            }

            String tenPB = getSelectedTenPB();
            String tenCV = getSelectedTenCV();

            String maPB = findMaPB(tenPB);
            String maCV = findMaCV(tenCV);

            if (daoNV.phanCong(maNV, maPB, maCV)) {
                JOptionPane.showMessageDialog(view, "Phân công thành công!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Phân công thất bại!");
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            String maNV = getSelectedMaNV();
            if (maNV.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Chọn nhân viên cần xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Xóa nhân viên " + maNV + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (daoNV.delete(maNV)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                }
            }
        });


        view.getBtnTim().addActionListener(e -> {
            String tuKhoa = view.getTxtTim().getText().trim().toLowerCase();
            if (tuKhoa.isEmpty()) {
                loadTable();
            } else {
                List<Object[]> list = daoNV.getAllHienThi();
                List<Object[]> ketQua = list.stream()
                        .filter(row -> row[0].toString().toLowerCase().contains(tuKhoa) ||
                                row[1].toString().toLowerCase().contains(tuKhoa))
                        .collect(Collectors.toList());
                showData(ketQua);
            }
        });

        view.getBtnXuat().addActionListener(e -> exportExcel());

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}