package Views;

import Controll.ChangeRolemanager;
import Controll.Membersmanager;
import Model.SeedData;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainScreen extends JFrame {

    private JTable memberTable;
    private DefaultTableModel memberModel;
    
    private JTable requestTable;
    private DefaultTableModel requestModel;

    private Font thaiFont = new Font("Tahoma", Font.PLAIN, 13);
    private Font thaiFontBold = new Font("Tahoma", Font.BOLD, 13);

    private JButton btnLoadData;
    private JButton btnCreateRequest;
    private JButton btnVoteApprove;
    private JButton btnVoteReject;
    private JButton btnCancelRequest;

    public MainScreen() {
        setTitle("ระบบจัดการคำขอเปลี่ยนบทบาทสมาชิก - Friends Forever");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel memberPanel = new JPanel(new BorderLayout(5, 5));
        String[] memberCols = {"รหัสสมาชิก (ID)", "ชื่อ", "บทบาทปัจจุบัน", "สถานะ"};
        memberModel = new DefaultTableModel(memberCols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        memberTable = new JTable(memberModel);
        memberTable.setFont(thaiFont);
        memberTable.getTableHeader().setFont(thaiFontBold);
        memberPanel.add(new JScrollPane(memberTable), BorderLayout.CENTER);

        JPanel requestPanel = new JPanel(new BorderLayout(5, 5));
        String[] reqCols = {"รหัสคำขอ", "ผู้เสนอ", "เป้าหมาย", "บทบาทใหม่", "สถานะ", "Approve", "Reject"};
        requestModel = new DefaultTableModel(reqCols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        requestTable = new JTable(requestModel);
        requestTable.setFont(thaiFont);
        requestTable.getTableHeader().setFont(thaiFontBold);
        requestPanel.add(new JScrollPane(requestTable), BorderLayout.CENTER);

        tabbedPane.addTab("รายการสมาชิก", memberPanel);
        tabbedPane.addTab("คำขอเปลี่ยนบทบาท & สรุปผล", requestPanel);

        add(tabbedPane, BorderLayout.CENTER);

    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnLoadData = new JButton("ดึงข้อมูลใหม่");
        btnCreateRequest = new JButton("สร้างคำขอใหม่");
        btnVoteApprove = new JButton("ลงความเห็น: อนุมัติ");
        btnVoteReject = new JButton("ลงความเห็น: ไม่อนุมัติ");
        btnCancelRequest = new JButton("ยกเลิกคำขอ");

        buttonPanel.add(btnLoadData);
        buttonPanel.add(btnCreateRequest);
        buttonPanel.add(btnVoteApprove);
        buttonPanel.add(btnVoteReject);
        buttonPanel.add(btnCancelRequest);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void showMember(Membersmanager members) {
        memberModel.setRowCount(0);
        if (members != null && members.getMembers() != null) {
            for (SeedData.MemberData m : members.getMembers()) {
                Object[] row = {
                    m.getId(),
                    m.getName(),
                    m.getRole(),
                    m.isActive() ? "Active" : "Inactive"
                };
                memberModel.addRow(row);
            }
        }
    }

    public void showRequests(ChangeRolemanager requestManager) {
        requestModel.setRowCount(0);
        if (requestManager != null && requestManager.getRequests() != null) {
            for (SeedData.RequestData req : requestManager.getRequests()) {
                int approves = requestManager.countVotes(req.getId(), "APPROVE");
                int rejects = requestManager.countVotes(req.getId(), "REJECT");
                Object[] row = {
                    req.getId(),
                    req.getRequesterId(),
                    req.getTargetId(),
                    req.getNewRole(),
                    req.getStatus(),
                    approves,
                    rejects
                };
                requestModel.addRow(row);
            }
        }
    }

    public String showMemberSelectionDialog(String title, List<SeedData.MemberData> members) {
        if (members == null || members.isEmpty()) return null;
        
        String[] memberOptions = new String[members.size()];
        for (int i = 0; i < members.size(); i++) {
            SeedData.MemberData m = members.get(i);
            memberOptions[i] = m.getId() + " - " + m.getName() + " (" + m.getRole() + ")";
        }

        JComboBox<String> comboBox = new JComboBox<>(memberOptions);
        comboBox.setFont(thaiFont);

        int result = JOptionPane.showConfirmDialog(
            this,
            comboBox,
            title,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String selected = (String) comboBox.getSelectedItem();
            if (selected != null) {
                return selected.split(" - ")[0]; 
            }
        }
        return null;
    }

    public String showRoleSelectionDialog(String title) {
        String[] roles = {"PRODUCER", "FINANCE", "EDITOR", "CREATOR"};
        JComboBox<String> comboBox = new JComboBox<>(roles);
        comboBox.setFont(thaiFont);

        int result = JOptionPane.showConfirmDialog(
            this,
            comboBox,
            title,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            return (String) comboBox.getSelectedItem();
        }
        return null;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "แจ้งเตือน", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "ข้อผิดพลาด / ปฏิเสธ", JOptionPane.ERROR_MESSAGE);
    }

    public JTable getMemberTable() { return memberTable; }
    public JTable getRequestTable() { return requestTable; }
    public JButton getBtnLoadData() { return btnLoadData; }
    public JButton getBtnCreateRequest() { return btnCreateRequest; }
    public JButton getBtnVoteApprove() { return btnVoteApprove; }
    public JButton getBtnVoteReject() { return btnVoteReject; }
    public JButton getBtnCancelRequest() { return btnCancelRequest; }
}