package QLNS.controller;

import QLNS.dao.NhanVienDAO;
import QLNS.model.NhanVien;
import QLNS.view.FrmQLTK;
import QLNS.view.FrmThongTinCaNhan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NhanVienController {

    private FrmThongTinCaNhan viewNV;
    private FrmQLTK viewTK;
    private NhanVienDAO dao;

    public NhanVienController(FrmThongTinCaNhan view) {
        this.viewNV = view;
        try {
            this.dao = new NhanVienDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối: " + e.getMessage());
            return;
        }

        loadTable();
        initEvents();
    }

    public NhanVienController(FrmQLTK view) {
        this.viewTK = view;
        try { this.dao = new NhanVienDAO(); }
        catch (Exception e) { return; }
        loadComboBoxMaNV();
    }

    private void showData(List<NhanVien> list) {
        String[] columns = {"Mã NV", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getNgaySinh(),
                    nv.getGioiTinh(),
                    nv.getSdt(),
                    nv.getDiaChi()
            });
        }
        viewNV.getTable().setModel(model);
    }

    private NhanVien getFormData() {
        String ma = viewNV.getTxtMaNV().getText().trim();
        String ten = viewNV.getTxtHoTen().getText().trim();
        String ngaySinh = viewNV.getTxtNgaySinh().getText().trim();
        String diaChi = viewNV.getTxtDiaChi().getText().trim();
        String sdt = viewNV.getTxtSDT().getText().trim();
        String gioiTinh = viewNV.getRdoNam().isSelected() ? "Nam" : "Nữ";

        return new NhanVien(ma, ten, ngaySinh, diaChi, gioiTinh, sdt);
    }

    private void fillFormFromTable() {
        JTable table = viewNV.getTable();
        int row = table.getSelectedRow();
        if (row >= 0) {
            viewNV.getTxtMaNV().setText(table.getValueAt(row, 0).toString());
            viewNV.getTxtHoTen().setText(table.getValueAt(row, 1).toString());
            viewNV.getTxtNgaySinh().setText(table.getValueAt(row, 2).toString());

            String gioitinh = table.getValueAt(row, 3).toString();
            if (gioitinh.equalsIgnoreCase("Nam")) {
                viewNV.getRdoNam().setSelected(true);
            } else {
                viewNV.getRdoNu().setSelected(true);
            }

            viewNV.getTxtSDT().setText(table.getValueAt(row, 4).toString());
            viewNV.getTxtDiaChi().setText(table.getValueAt(row, 5).toString());

            viewNV.getTxtMaNV().setEnabled(false);
        }
    }

    private String getSelectedMaNV() {
        int row = viewNV.getTable().getSelectedRow();
        if (row >= 0) {
            return viewNV.getTable().getValueAt(row, 0).toString();
        }
        return null;
    }

    private void clearForm() {
        viewNV.getTxtMaNV().setText("");
        viewNV.getTxtHoTen().setText("");
        viewNV.getTxtNgaySinh().setText("");
        viewNV.getTxtDiaChi().setText("");
        viewNV.getTxtSDT().setText("");
        viewNV.getRdoNam().setSelected(true);
        viewNV.getTable().clearSelection();

        viewNV.getTxtMaNV().setEnabled(true);
    }

    private boolean checkNgaySinh(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateStr.trim());
            Date now = new Date();

            if (date.after(now)) {
                JOptionPane.showMessageDialog(viewNV, "Ngày sinh không được lớn hơn ngày hiện tại!");
                return false;
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);

            if (year < 1900) {
                JOptionPane.showMessageDialog(viewNV, "Năm sinh phải lớn hơn hoặc bằng 1900!");
                return false;
            }

            return true;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(viewNV, "Ngày sinh không hợp lệ!\nVui lòng nhập đúng định dạng");
            return false;
        }
    }

    private boolean checkSDT(String sdt) {
        if (!sdt.matches("\\d+")) {
            JOptionPane.showMessageDialog(viewNV, "Số điện thoại chỉ được chứa ký tự số!");
            return false;
        }
        if (sdt.length() > 10) {
            JOptionPane.showMessageDialog(viewNV, "Số điện thoại không được quá 10 số!");
            return false;
        }
        return true;
    }

    private void loadTable() {
        if (viewNV != null) {
            List<NhanVien> list = dao.getAll();
            showData(list);
        }
    }

    private void initEvents() {
        viewNV.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fillFormFromTable();
            }
        });


        viewNV.getBtnThem().addActionListener(e -> {
            try {
                NhanVien nv = getFormData();

                if (nv.getMaNV().isEmpty() || nv.getHoTen().isEmpty() || nv.getNgaySinh().isEmpty() || nv.getSdt().isEmpty()) {
                    JOptionPane.showMessageDialog(viewNV, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                if (!checkNgaySinh(nv.getNgaySinh())) return;

                if (!checkSDT(nv.getSdt())) return;

                List<NhanVien> list = dao.getAll();
                for (NhanVien existing : list) {
                    if (existing.getMaNV().equalsIgnoreCase(nv.getMaNV())) {
                        JOptionPane.showMessageDialog(viewNV, "Mã nhân viên đã tồn tại!");
                        return;
                    }
                }

                if (dao.insert(nv)) {
                    JOptionPane.showMessageDialog(viewNV, "Thêm thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(viewNV, "Thêm thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewNV, "Lỗi thêm: " + ex.getMessage());
            }
        });

        viewNV.getBtnSua().addActionListener(e -> {
            try {
                String maNV_Cu = getSelectedMaNV();
                if (maNV_Cu == null) {
                    JOptionPane.showMessageDialog(viewNV, "Vui lòng chọn nhân viên cần sửa!");
                    return;
                }

                NhanVien nv_Moi = getFormData();

                if (nv_Moi.getMaNV().isEmpty() || nv_Moi.getHoTen().isEmpty()) {
                    JOptionPane.showMessageDialog(viewNV, "Thông tin chính không được để trống!");
                    return;
                }

                if (!checkNgaySinh(nv_Moi.getNgaySinh())) return;
                if (!checkSDT(nv_Moi.getSdt())) return;

                if (!nv_Moi.getMaNV().equalsIgnoreCase(maNV_Cu)) {
                    List<NhanVien> list = dao.getAll();
                    for (NhanVien existing : list) {
                        if (existing.getMaNV().equalsIgnoreCase(nv_Moi.getMaNV())) {
                            JOptionPane.showMessageDialog(viewNV, "Mã nhân viên mới đã tồn tại!");
                            return;
                        }
                    }
                }

                if (dao.update(nv_Moi, maNV_Cu)) {
                    JOptionPane.showMessageDialog(viewNV, "Cập nhật thành công!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(viewNV, "Cập nhật thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewNV, "Lỗi sửa: " + ex.getMessage());
            }
        });

        viewNV.getBtnXoa().addActionListener(e -> {
            try {
                String maNV = getSelectedMaNV();
                if (maNV == null) {
                    JOptionPane.showMessageDialog(viewNV, "Vui lòng chọn nhân viên cần xóa!");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(viewNV, "Xóa nhân viên: " + maNV + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.delete(maNV)) {
                        JOptionPane.showMessageDialog(viewNV, "Xóa thành công!");
                        loadTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(viewNV, "Xóa thất bại!");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewNV, "Lỗi xóa: " + ex.getMessage());
            }
        });

        viewNV.getBtnTim().addActionListener(e -> {
            String keyword = viewNV.getTxtTim().getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadTable();
            } else {
                List<NhanVien> all = dao.getAll();
                List<NhanVien> res = all.stream()
                        .filter(nv -> nv.getHoTen().toLowerCase().contains(keyword) || nv.getMaNV().toLowerCase().contains(keyword))
                        .collect(Collectors.toList());
                showData(res);
            }
        });

        viewNV.getBtnReset().addActionListener(e -> {
            clearForm();
        });
    }

    public void loadComboBoxMaNV() {
        if (viewTK != null) {
            try {
                List<String> listMa = dao.getAllMaNV();
                viewTK.getCboMaNV().removeAllItems();
                for (String ma : listMa) viewTK.getCboMaNV().addItem(ma);
            } catch (Exception e) {}
        }
    }
}