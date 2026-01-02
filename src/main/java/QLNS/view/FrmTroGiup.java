package QLNS.view;

import javax.swing.*;
import java.awt.*;

public class FrmTroGiup extends JPanel {

    public FrmTroGiup() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 60, 75)); // Màu nền xám xanh đậm

        JPanel pnlWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        pnlWrapper.setOpaque(false);

        JPanel pnlContent = new JPanel(new GridLayout(0, 1, 0, 10));
        pnlContent.setOpaque(false);

        addLabel(pnlContent, "Mọi thắc mắc xin liên hệ với trưởng phòng nhân sự", 22, true);
        addLabel(pnlContent, "Nguyễn Quốc Bảo - Trần Hà Linh", 20, false);
        addLabel(pnlContent, "Số hotline: 0389617834 - 01284924563", 20, false);
        addLabel(pnlContent, "Địa chỉ: The Pizza Company cơ sở chính tại Linh Đàm, Số 3 dãy a, KDT Tây Nam, Hoàng Mai, Hà Nội", 20, false);

        pnlContent.add(new JLabel(" "));
        pnlContent.add(new JLabel(" "));

        addLabel(pnlContent, "Phần mềm trên được phát triển bởi các thành viên CTY 74DCTT27_UTT", 20, false);
        addLabel(pnlContent, "Rất mong quý khách hàng sử dụng hài lòng và đưa ra lời nhận xét góp ý, thay mặt đội ngũ, Tôi Đào Văn Vinh trưởng nhóm xin trân trọng và cảm ơn!", 20, false);
        addLabel(pnlContent, "Hotline: 0389781614 – http://muabanwebsite.vn", 20, false);

        pnlWrapper.add(pnlContent);
        add(pnlWrapper, BorderLayout.CENTER);
    }

    private void addLabel(JPanel panel, String text, int fontSize, boolean isBold) {
        JLabel lbl = new JLabel("<html><div style='text-align: center; width: 900px;'>" + text + "</div></html>");

        lbl.setForeground(Color.WHITE); // Chữ màu trắng
        lbl.setFont(new Font("Arial", isBold ? Font.BOLD : Font.PLAIN, fontSize));
        lbl.setHorizontalAlignment(SwingConstants.CENTER); // Căn chữ ra giữa ô lưới

        panel.add(lbl);
    }
}