package QLNS.controller;

import QLNS.dao.TaiKhoanDAO;
import QLNS.model.TaiKhoan;
import QLNS.view.LoginView;

public class LoginController {

    private LoginView view;
    private TaiKhoanDAO dao;

    public LoginController() {
        view = new LoginView();
        view.addLoginListener(e -> login());
        view.setVisible(true);

        try {
            dao = new TaiKhoanDAO();
        } catch (Exception ex) {
            view.showMessage("Không thể kết nối MySQL!");
        }
    }

    private void login() {
        TaiKhoan user = view.getUser();

        if (dao.checkLogin(user.getTenTaiKhoan(), user.getMatKhau())) {
            view.showMessage("Đăng nhập thành công!");
            view.dispose();

            new NhanVienController();  // Start the employee management interface after login
        } else {
            view.showMessage("Sai tài khoản hoặc mật khẩu!");
        }
    }

}

