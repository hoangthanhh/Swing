package QLNS.controller;

import QLNS.dao.BangLuongDAO;
import QLNS.dao.LuongDAO;
import QLNS.dao.PhuCapDAO;
import QLNS.dao.ThuongDAO;
import QLNS.model.ChucVu;
import QLNS.model.Luong;
import QLNS.model.PhuCap;
import QLNS.model.Thuong;
import QLNS.view.FrmBangLuong;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class BangLuongController {

    private FrmBangLuong view;
    private BangLuongDAO daoBL;
    private LuongDAO daoLuong;
    private ThuongDAO daoThuong;
    private PhuCapDAO daoPC;

    // Cache lists for lookup
    private List<Luong> listLuongGoc;
    private List<Thuong> listThuongGoc;
    private List<PhuCap> listPhuCapGoc;

    public BangLuongController(FrmBangLuong view) {
        this.view = view;
        try {
            daoBL = new BangLuongDAO();
            daoLuong = new LuongDAO();
            daoThuong = new ThuongDAO();
            daoPC = new PhuCapDAO();
        } catch (Exception e) { e.printStackTrace(); }

        loadData();
        initEvents();
    }

    private void loadData() {
        listLuongGoc = daoLuong.getAll();
        listThuongGoc = daoThuong.getAll();
        listPhuCapGoc = daoPC.getAll();

        loadCombos(listLuongGoc, listThuongGoc, listPhuCapGoc);

        // Load Table
        loadTable();
    }

    private void loadCombos(List<Luong> listLuong, List<Thuong> listThuong, List<PhuCap> listPC) {
        view.getCboLuongCoBan().removeAllItems();
        view.getCboLuongCoBan().addItem("0");
        for (Luong l : listLuong) {
            view.getCboLuongCoBan().addItem(String.valueOf(l.getLuongCoBan()));
        }

        view.getCboTienThuong().removeAllItems();
        view.getCboTienThuong().addItem("0");
        for (Thuong t : listThuong) {
            view.getCboTienThuong().addItem(String.valueOf(t.getSoTien()));
        }

        view.getCboTienPhuCap().removeAllItems();
        view.getCboTienPhuCap().addItem("0");
        for (PhuCap p : listPC) {
            view.getCboTienPhuCap().addItem(String.valueOf(p.getTienPC()));
        }
    }

    private void showData(List<Object[]> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        for (Object[] row : list) {
            double lcb = (row[2] != null) ? Double.parseDouble(row[2].toString()) : 0;
            double thuong = (row[3] != null) ? Double.parseDouble(row[3].toString()) : 0;
            double phucap = (row[4] != null) ? Double.parseDouble(row[4].toString()) : 0;
            double thucLinh = lcb + thuong + phucap;

            model.addRow(new Object[]{
                    row[0],
                    row[1],
                    lcb,
                    thuong,
                    phucap,
                    thucLinh
            });
        }
    }

    private void fillForm() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            view.getTxtMaNV().setText(table.getValueAt(row, 0).toString());
            view.getTxtHoTen().setText(table.getValueAt(row, 1).toString());

            setSelectedComboValue(view.getCboLuongCoBan(), table.getValueAt(row, 2).toString());
            setSelectedComboValue(view.getCboTienThuong(), table.getValueAt(row, 3).toString());
            setSelectedComboValue(view.getCboTienPhuCap(), table.getValueAt(row, 4).toString());

            view.getTxtLuongThucLinh().setText(table.getValueAt(row, 5).toString());

            view.getTxtMaNV().setEnabled(false);
            view.getTxtHoTen().setEnabled(false);
        }
    }

    private void setSelectedComboValue(JComboBox<String> combo, String value) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equals(value)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(0);
    }

    private void updateThucLinhDisplay() {
        double lcb = parseMoney(view.getCboLuongCoBan());
        double thuong = parseMoney(view.getCboTienThuong());
        double pc = parseMoney(view.getCboTienPhuCap());
        view.getTxtLuongThucLinh().setText(String.valueOf(lcb + thuong + pc));
    }

    private double parseMoney(JComboBox<String> combo) {
        try {
            if (combo.getSelectedItem() != null) {
                return Double.parseDouble(combo.getSelectedItem().toString());
            }
        } catch (Exception e) {}
        return 0;
    }

    // 5. Get Selected IDs
    private String getSelectedMaLuong() {
        int index = view.getCboLuongCoBan().getSelectedIndex();
        if (index > 0 && index - 1 < listLuongGoc.size()) {
            return listLuongGoc.get(index - 1).getMaLuong();
        }
        return null;
    }

    private String getSelectedMaThuong() {
        int index = view.getCboTienThuong().getSelectedIndex();
        if (index > 0 && index - 1 < listThuongGoc.size()) {
            return listThuongGoc.get(index - 1).getMaThuong();
        }
        return null;
    }

    private String getSelectedMaPhuCap() {
        int index = view.getCboTienPhuCap().getSelectedIndex();
        if (index > 0 && index - 1 < listPhuCapGoc.size()) {
            return listPhuCapGoc.get(index - 1).getMaPC();
        }
        return null;
    }

    private String getSelectedMaNV() {
        return view.getTxtMaNV().getText();
    }

    private void clearForm() {
        view.getTxtMaNV().setText("");
        view.getTxtHoTen().setText("");
        view.getCboLuongCoBan().setSelectedIndex(0);
        view.getCboTienThuong().setSelectedIndex(0);
        view.getCboTienPhuCap().setSelectedIndex(0);
        view.getTxtLuongThucLinh().setText("");
        view.getTable().clearSelection();


    }

    private void loadTable() {
        List<Object[]> list = daoBL.getAllBangLuong();
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
                updateThucLinhDisplay();
            }
        });

        ActionListener comboAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateThucLinhDisplay();
            }
        };
        view.getCboLuongCoBan().addActionListener(comboAction);
        view.getCboTienThuong().addActionListener(comboAction);
        view.getCboTienPhuCap().addActionListener(comboAction);

        view.getBtnCapNhat().addActionListener(e -> {
            String maNV = getSelectedMaNV();
            if (maNV.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên để tính lương!");
                return;
            }

            String maL = getSelectedMaLuong();
            String maT = getSelectedMaThuong();
            String maP = getSelectedMaPhuCap();

            if (daoBL.updateBangLuong(maNV, maL, maT, maP)) {
                JOptionPane.showMessageDialog(view, "Cập nhật bảng lương thành công!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            String maNV = getSelectedMaNV();
            if (maNV.isEmpty()) return;

            int cf = JOptionPane.showConfirmDialog(view, "Xóa thông tin lương của nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (cf == JOptionPane.YES_OPTION) {
                if (daoBL.updateBangLuong(maNV, null, null, null)) {
                    JOptionPane.showMessageDialog(view, "Đã xóa thông tin lương!");
                    loadTable();
                    clearForm();
                }
            }
        });

        view.getBtnTim().addActionListener(e -> {
            String keyword = view.getTxtTim().getText().trim().toLowerCase();

            if (keyword.isEmpty()) {
                loadTable();
            } else {
                List<Object[]> list = daoBL.getAllBangLuong();

                List<Object[]> listTimKiem = list.stream()
                        .filter(row -> {
                            String maNV = row[0] != null ? row[0].toString().toLowerCase() : "";
                            String hoTen = row[1] != null ? row[1].toString().toLowerCase() : "";
                            return maNV.contains(keyword) || hoTen.contains(keyword);
                        })
                        .collect(Collectors.toList());

                showData(listTimKiem);
            }
        });

        view.getBtnXuat().addActionListener(e -> exportExcel());

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}