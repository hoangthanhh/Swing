package QLNS.dao;

import QLNS.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BangLuongDAO {

    public List<Object[]> getAllBangLuong() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT nv.MaNhanVien, nv.HoTen, " +
                     "COALESCE(l.LuongCoBan, 0), " +
                     "COALESCE(t.SoTien, 0), " +
                     "COALESCE(pc.TienPhuCap, 0) " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN Luong l ON nv.MaLuong = l.MaLuong " +
                     "LEFT JOIN Thuong t ON nv.MaThuong = t.MaThuong " +
                     "LEFT JOIN PhuCap pc ON nv.MaPhuCap = pc.MaPhuCap";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString(1), // MaNV
                    rs.getString(2), // HoTen
                    rs.getDouble(3), // LuongCB
                    rs.getDouble(4), // Thuong
                    rs.getDouble(5)  // PhuCap
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateBangLuong(String maNV, String maLuong, String maThuong, String maPC) {
        String sql = "UPDATE NhanVien SET MaLuong=?, MaThuong=?, MaPhuCap=? WHERE MaNhanVien=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            if (maLuong == null) ps.setNull(1, java.sql.Types.VARCHAR);
            else ps.setString(1, maLuong);
            
            if (maThuong == null) ps.setNull(2, java.sql.Types.VARCHAR);
            else ps.setString(2, maThuong);
            
            if (maPC == null) ps.setNull(3, java.sql.Types.VARCHAR);
            else ps.setString(3, maPC);
            
            ps.setString(4, maNV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public List<Object[]> search(String keyword) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT nv.MaNhanVien, nv.HoTen, nv.NgaySinh, nv.DiaChi, nv.GioiTinh, nv.SDT, " +
                "pb.TenPhongBan, cv.TenChucVu, " +
                "COALESCE(l.LuongCoBan, 0), " +
                "COALESCE(pc.TienPhuCap, 0), " +
                "COALESCE(t.SoTien, 0), " +
                "(COALESCE(l.LuongCoBan, 0) + COALESCE(pc.TienPhuCap, 0) + COALESCE(t.SoTien, 0)) AS ThucLinh " +
                "FROM NhanVien nv " +
                "LEFT JOIN PhongBan pb ON nv.MaPhongBan = pb.MaPhongBan " +
                "LEFT JOIN ChucVu cv ON nv.MaChucVu = cv.MaChucVu " +
                "LEFT JOIN Luong l ON nv.MaLuong = l.MaLuong " +
                "LEFT JOIN PhuCap pc ON nv.MaPhuCap = pc.MaPhuCap " +
                "LEFT JOIN Thuong t ON nv.MaThuong = t.MaThuong " +
                "WHERE nv.MaNhanVien LIKE ? OR nv.HoTen LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String query = "%" + keyword + "%";
            ps.setString(1, query);
            ps.setString(2, query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Object[]{
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getString("SDT"),
                        rs.getString("TenPhongBan"),
                        rs.getString("TenChucVu"),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getDouble("ThucLinh")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}