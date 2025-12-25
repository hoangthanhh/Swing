package QLNS.model;

public class TaiKhoan {

    private int id;
    private String tenTaiKhoan;
    private String matKhau;
    private String loaiTaiKhoan;
    private String maNhanVien;

    public TaiKhoan() {
    }

    // public TaiKhoan(String maNhanVien, String loaiTaiKhoan) {
    //     this.maNhanVien = maNhanVien;
    //     this.loaiTaiKhoan = loaiTaiKhoan;
    // }

    public TaiKhoan(String tenTaiKhoan, String matKhau,
            String loaiTaiKhoan, String maNhanVien) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.maNhanVien = maNhanVien;
    }

    public TaiKhoan(String tenTaiKhoan, String matKhau) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

}

