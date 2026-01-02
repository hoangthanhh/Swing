package QLNS.controller;

import QLNS.view.*;
import QLNS.controller.TaiKhoanController;
import QLNS.controller.NhanVienController;

import javax.swing.*;

public class MainController {

    private FrmMain view;

    public MainController(FrmMain view) {
        this.view = view;
        updateMenuByRole();
        initEvents();
    }

    private void updateMenuByRole() {
        if (view.getLoaiTK().equalsIgnoreCase("Nhân viên")) {
            view.getMnuQuanLy().setEnabled(false);
            view.getmniQuanLyTaiKhoan().setEnabled(false);
            view.getMniBaoCao().setEnabled(false);
            // ... các phân quyền khác giữ nguyên
            view.getMniPhongBan().setEnabled(false);
            view.getMniChucVu().setEnabled(false);
            view.getMniPhuCap().setEnabled(false);
            view.getMniLuong().setEnabled(false);
            view.getMniThuong().setEnabled(false);
        }
    }

    private void initEvents() {
        view.getMniDangXuat().addActionListener(e -> {
            view.dispose();
            FrmLogin loginView = new FrmLogin();
            new LoginController(loginView);
            loginView.setVisible(true);
        });

        view.getmniQuanLyTaiKhoan().addActionListener(e -> {
            FrmQLTK viewTK = new FrmQLTK();
            new TaiKhoanController(viewTK);
            new NhanVienController(viewTK);
            openPanel(viewTK);
        });

        view.getMniThongTinCaNhan().addActionListener(e -> {
            FrmThongTinCaNhan viewNV = new FrmThongTinCaNhan();
            new NhanVienController(viewNV);
            openPanel(viewNV);
        });

        view.getMniPhongBan().addActionListener(e -> {
            FrmPhongBan viewPB = new FrmPhongBan();
            new PhongBanController(viewPB);
            openPanel(viewPB);
        });

        view.getMniChucVu().addActionListener(e -> {
            FrmChucVu viewCV = new FrmChucVu();
            new ChucVuController(viewCV); 
            openPanel(viewCV);
        });

        view.getMniLuong().addActionListener(e -> {
            FrmLuong viewLuong = new FrmLuong();
            new LuongController(viewLuong); 
            openPanel(viewLuong);
        });

        view.getMniThuong().addActionListener(e -> {
            FrmThuong viewThuong = new FrmThuong();
            new ThuongController(viewThuong); 
            openPanel(viewThuong);
        });

        view.getMniPhuCap().addActionListener(e -> {
            FrmPhuCap viewPC = new FrmPhuCap();
            new PhuCapController(viewPC); 
            openPanel(viewPC);
        });

        view.getMniQLNS().addActionListener(e -> {
            FrmQLNS viewQLNS = new FrmQLNS();
            new QLNSController(viewQLNS); 
            openPanel(viewQLNS);
        });

        view.getMniBangLuong().addActionListener(e -> {
            FrmBangLuong viewBL = new FrmBangLuong();
            new BangLuongController(viewBL);
            openPanel(viewBL);
        });

        view.getMniTraCuu().addActionListener(e -> {
            FrmTraCuu viewTraCuu = new FrmTraCuu();
            new TraCuuController(viewTraCuu);
            openPanel(viewTraCuu);
        });

        view.getMniTroGiup().addActionListener(e -> {
            FrmTroGiup viewTroGiup = new FrmTroGiup();
            openPanel(viewTroGiup);
        });

        view.getMniBaoCao().addActionListener(e -> {
            FrmBaoCao viewBaoCao = new FrmBaoCao();
            new BaoCaoController(viewBaoCao);
            openPanel(viewBaoCao);
        });
    }

    private void openPanel(JPanel panel) {
        view.setContentPanel(panel);
    }
}
