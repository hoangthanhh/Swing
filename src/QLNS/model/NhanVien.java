package QLNS.model;

public class NhanVien extends Person {

    private String maNhanVien;
    private String email;
    private double diemTongKet;

    public NhanVien(String HoTen, String NgaySinh, String DiaChi,
            String GioiTinh, String maNhanVien, String email, double diemTongKet) {
        super(HoTen, NgaySinh, DiaChi, GioiTinh);
        this.maNhanVien = maNhanVien;
        this.email = email;
        this.diemTongKet = diemTongKet;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public String getEmail() {
        return email;
    }

    public double getDiemTongKet() {
        return diemTongKet;
    }
}
