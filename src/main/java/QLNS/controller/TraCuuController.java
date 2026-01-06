package QLNS.controller;

import QLNS.dao.TraCuuDAO;
import QLNS.view.FrmTraCuu;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TraCuuController {

    private FrmTraCuu view;
    private TraCuuDAO dao;
    private String currentMaNV; // Store the current user's ID

    public TraCuuController(FrmTraCuu view) {
        this(view, null);
    }

    public TraCuuController(FrmTraCuu view, String currentMaNV) {
        this.view = view;
        this.currentMaNV = currentMaNV;
        this.dao = new TraCuuDAO();

        loadTable("");
        initEvents();

        // If it's for a specific employee, disable search and show only their info
        if (currentMaNV != null && !currentMaNV.isEmpty()) {
            view.getTxtTimKiem().setEnabled(false);
            view.getBtnTim().setEnabled(false);
            // Load only the current user's information
            loadCurrentUserDetails();
        }
    }

    private void showData(List<Object[]> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    private void loadTable(String keyword) {
        List<Object[]> list;
        if (currentMaNV != null && !currentMaNV.isEmpty()) {
            // For employee view, only show their own data
            Object[] employee = dao.getByMaNV(currentMaNV);
            if (employee != null) {
                list = new ArrayList<>();
                list.add(employee);
            } else {
                list = new ArrayList<>(); // Empty list if user not found
            }
        } else {
            // For admin/manager view, use the search
            list = dao.search(keyword);
        }
        showData(list);
    }

    private void loadCurrentUserDetails() {
        if (currentMaNV != null && !currentMaNV.isEmpty()) {
            Object[] employee = dao.getByMaNV(currentMaNV);
            if (employee != null) {
                List<Object[]> list = new ArrayList<>();
                list.add(employee);
                showData(list);

                // Auto-fill the detailed information panel with the user's data
                view.getTxtMaNV().setText(employee[0] != null ? employee[0].toString() : "");
                view.getTxtHoTen().setText(employee[1] != null ? employee[1].toString() : "");
                view.getTxtNgaySinh().setText(employee[2] != null ? employee[2].toString() : "");
                view.getTxtDiaChi().setText(employee[3] != null ? employee[3].toString() : "");

                String gioiTinh = employee[4] != null ? employee[4].toString() : "";
                if ("Nam".equalsIgnoreCase(gioiTinh)) {
                    view.getRdoNam().setSelected(true);
                } else {
                    view.getRdoNu().setSelected(true);
                }

                view.getTxtSDT().setText(employee[5] != null ? employee[5].toString() : "");
                view.getTxtPhongBan().setText(employee[6] != null ? employee[6].toString() : "");
                view.getTxtChucVu().setText(employee[7] != null ? employee[7].toString() : "");

                view.getTxtLuongCB().setText(employee[8] != null ? employee[8].toString() : "");
                view.getTxtPhuCap().setText(employee[9] != null ? employee[9].toString() : "");
                view.getTxtThuong().setText(employee[10] != null ? employee[10].toString() : "");
                view.getTxtThucLinh().setText(employee[11] != null ? employee[11].toString() : "");
            }
        }
    }

    private void fillFormFromTable() {
        int row = view.getTable().getSelectedRow();
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

    private void initEvents() {
        view.getBtnTim().addActionListener(e -> {
            // Only allow search if not in employee-only mode
            if (currentMaNV == null || currentMaNV.isEmpty()) {
                String keyword = view.getTxtTimKiem().getText().trim();
                List<Object[]> result;

                // If the search field contains only an employee ID (no spaces), use exact match
                if (!keyword.contains(" ") && keyword.matches("^[a-zA-Z0-9]+$")) {
                    Object[] employee = dao.getByMaNV(keyword);
                    if (employee != null) {
                        result = new ArrayList<>();
                        result.add(employee);
                    } else {
                        result = new ArrayList<>(); // Return empty list if employee not found
                    }
                } else {
                    // Use the general search for partial matches or name searches
                    result = dao.search(keyword);
                }

                showData(result);
            }
        });

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Only allow table selection if not in employee-only mode
                if (currentMaNV == null || currentMaNV.isEmpty()) {
                    fillFormFromTable();
                } else {
                    // For employee view, the detailed info is already shown
                    // so we don't need to fill from table
                }
            }
        });

        view.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }
}