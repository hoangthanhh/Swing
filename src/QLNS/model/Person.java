package QLNS.model;

public class Person {

    protected String HoTen;
    protected String NgaySinh;
    protected String DiaChi;
    protected String GioiTinh;

    public Person(String HoTen, String NgaySinh, String DiaChi, String GioiTinh) {
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.DiaChi = DiaChi;
        this.GioiTinh = GioiTinh;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }
}
