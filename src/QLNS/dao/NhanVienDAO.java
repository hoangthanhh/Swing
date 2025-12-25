package QLNS.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import QLNS.model.NhanVien;
import QLNS.util.DBConnection;

public class NhanVienDAO {

    private final Connection con;

    public NhanVienDAO() throws Exception {
        con = DBConnection.getConnection();

        if (con == null) {

            throw new Exception("Không thể kết nối đến cơ sở dữ liệu!");
        }
    }

    public ArrayList<NhanVien> getAll() {
        ArrayList<NhanVien> list = new ArrayList<>();

        try (
                Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM NhanVien");) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("hoten"),
                        rs.getString("ngaysinh"),
                        rs.getString("diachi"),
                        rs.getString("gioitinh"),
                        rs.getString("manhanvien"),
                        rs.getString("email"),
                        rs.getDouble("diemtongket")
                );
                list.add(nv);
            }

        } catch (SQLException e) {
            // In lỗi SQL ra console để debug khi thất bại
            System.err.println("Lỗi SQL khi lấy danh sách nhân viên:");
            e.printStackTrace();
            // KHÔNG NÊN BỎ TRỐNG catch block
        }
        return list;
    }

    public boolean insert(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (hoten, ngaysinh, diachi, gioitinh, manhanvien, email, diemtongket) VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getNgaySinh());
            ps.setString(3, nv.getDiaChi());
            ps.setString(4, nv.getGioiTinh());
            ps.setString(5, nv.getMaNhanVien());
            ps.setString(6, nv.getEmail());
            ps.setDouble(7, nv.getDiemTongKet());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thêm nhân viên:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhanVien nv) {
        String sql = "UPDATE NhanVien SET hoten=?, ngaysinh=?, diachi=?, gioitinh=?, email=?, diemtongket=? WHERE manhanvien=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getNgaySinh());
            ps.setString(3, nv.getDiaChi());
            ps.setString(4, nv.getGioiTinh());
            ps.setString(5, nv.getEmail());
            ps.setDouble(6, nv.getDiemTongKet());
            ps.setString(7, nv.getMaNhanVien());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật nhân viên:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE manhanvien=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNhanVien);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi xóa nhân viên:");
            e.printStackTrace();
            return false;
        }
    }
}

