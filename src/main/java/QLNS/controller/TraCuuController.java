package QLNS.controller;

import QLNS.dao.TraCuuDAO;
import QLNS.view.FrmTraCuu;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TraCuuController {

    private FrmTraCuu view;
    private TraCuuDAO dao;

    public TraCuuController(FrmTraCuu view) {
        this.view = view;
        this.dao = new TraCuuDAO();

        loadTable("");
        initEvents();
    }

    private void showData(List<Object[]> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    private void loadTable(String keyword) {
        List<Object[]> list = dao.search(keyword);
        showData(list);
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
            String keyword = view.getTxtTimKiem().getText().trim();
            List<Object[]> result = dao.search(keyword);
            showData(result);
        });

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