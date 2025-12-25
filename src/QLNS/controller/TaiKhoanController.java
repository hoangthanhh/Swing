package QLNS.controller;

import QLNS.dao.TaiKhoanDAO;
import QLNS.model.TaiKhoan;
import QLNS.view.FrmQLTK;
import java.util.List;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TaiKhoanController {

    private FrmQLTK view;
    private TaiKhoanDAO dao;

    public TaiKhoanController() {
        try {
            dao = new TaiKhoanDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không kết nối được Database!");
        }

        view = new FrmQLTK();
        loadTable();
        initEventHandlers();
    }

    public TaiKhoanController(FrmQLTK view) {
        try {
            dao = new TaiKhoanDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không kết nối được Database!");
        }
        
        this.view = view;
        loadTable();
        initEventHandlers();
        initEventHandlersForPanel();
    }

    private void loadTable() {
        List<TaiKhoan> list = dao.getAll();
        view.showData(list);
    }

    private void initEventHandlers() {
        view.addAddListener(new AddListener());
        view.addEditListener(new EditListener());
        view.addDeleteListener(new DeleteListener());
        view.addTableClickListener(new TableClickListener());
    }

    private void initEventHandlersForPanel() {
        // Only if this is called from the panel context
    }

    // Add button listener class
    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            view.enableForm(true);
        }
    }

    // Edit button listener class
    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try {
                TaiKhoan tk = view.getFormData();
                String tenTaiKhoan = view.getSelectedTenTK();
                
                if (tenTaiKhoan == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn tài khoản để sửa!");
                    return;
                }
                
                // We need to set the tenTaiKhoan in the object for update
                tk.setTenTaiKhoan(tenTaiKhoan);
                
                if (dao.update(tk)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công");
                    loadTable();
                    view.clearForm();
                    view.enableForm(false);
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi cập nhật: " + ex.getMessage());
            }
        }
    }

    // Delete button listener class
    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try {
                String tenTaiKhoan = view.getSelectedTenTK();
                
                if (tenTaiKhoan == null) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn tài khoản để xóa!");
                    return;
                }
                
                int confirm = JOptionPane.showConfirmDialog(
                    view, 
                    "Bạn có chắc chắn muốn xóa tài khoản " + tenTaiKhoan + "?", 
                    "Xác nhận xóa", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.delete(tenTaiKhoan)) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công");
                        loadTable();
                        view.clearForm();
                        view.enableForm(false);
                    } else {
                        JOptionPane.showMessageDialog(view, "Xóa thất bại");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi xóa: " + ex.getMessage());
            }
        }
    }

    // Table click listener class
    class TableClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.fillFormFromTable();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}

