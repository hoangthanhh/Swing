package QLNS.controller;

import QLNS.dao.BaoCaoDAO;
import QLNS.view.FrmBaoCao;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class BaoCaoController {

    private FrmBaoCao view;
    private BaoCaoDAO dao;

    public BaoCaoController(FrmBaoCao view) {
        this.view = view;
        this.dao = new BaoCaoDAO();

        loadTable("");
        initEvents();
    }

    private void loadTable(String keyword) {
        List<Object[]> list = dao.search(keyword);
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    private void fillFormFromTable() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaNV().setText(getValue(row, 0));
            view.getTxtHoTen().setText(getValue(row, 1));
            view.getTxtNgaySinh().setText(getValue(row, 2));
            view.getTxtDiaChi().setText(getValue(row, 3));

            String gioitinh = getValue(row, 4);
            if (gioitinh.equalsIgnoreCase("Nam")) view.getRdoNam().setSelected(true);
            else view.getRdoNu().setSelected(true);

            view.getTxtSDT().setText(getValue(row, 5));
            view.getTxtPhongBan().setText(getValue(row, 6));
            view.getTxtChucVu().setText(getValue(row, 7));
            view.getTxtLuongCB().setText(getValue(row, 8));
            view.getTxtPhuCap().setText(getValue(row, 9));
            view.getTxtThuong().setText(getValue(row, 10));
            view.getTxtThucLinh().setText(getValue(row, 11));
        }
    }

    private String getValue(int row, int col) {
        Object val = view.getTable().getValueAt(row, col);
        return val != null ? val.toString() : "";
    }

    private void clearForm() {
        view.getTxtMaNV().setText("");
        view.getTxtHoTen().setText("");
        view.getTxtNgaySinh().setText("");
        view.getTxtDiaChi().setText("");
        view.getTxtSDT().setText("");
        view.getRdoNam().setSelected(true);
        view.getTxtPhongBan().setText("");
        view.getTxtChucVu().setText("");
        view.getTxtLuongCB().setText("");
        view.getTxtPhuCap().setText("");
        view.getTxtThuong().setText("");
        view.getTxtThucLinh().setText("");
        view.getTable().clearSelection();
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
        view.getBtnTim().addActionListener(e -> {
            String keyword = view.getTxtTimKiem().getText().trim();
            loadTable(keyword);
        });

        view.getBtnXuat().addActionListener(e -> exportExcel());

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fillFormFromTable();
            }
        });

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}