package Controll;

import Model.Repository;
import Model.SeedData;
import Views.MainScreen;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MainController {
    private Repository repo;
    private SeedData currentData;
    private MainScreen view;
    private Membersmanager membersManager;
    private ChangeRolemanager requestManager;
    private VoteController voteController;

    public MainController() {
        this.repo = new Repository();

        SwingUtilities.invokeLater(() -> {
            this.view = new MainScreen();
            loadAndDisplayData();
            initController();
            this.view.setVisible(true);
        });
    }

    private void loadAndDisplayData() {
        this.currentData = repo.loadInitialData();
        if (currentData != null && currentData.getMembers() != null) {
            this.membersManager = new Membersmanager(currentData.getMembers());
            this.requestManager = new ChangeRolemanager(currentData.getRequests(), currentData.getDecisions(), membersManager);
            
            this.voteController = new VoteController(requestManager, membersManager, view);
            
            refreshUI();
        } else {
            JOptionPane.showMessageDialog(view, "ไม่สามารถอ่านไฟล์ Model/seed_data.json ได้ กรุณาตรวจสอบตำแหน่งไฟล์", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshUI() {
        view.showMember(membersManager);
        view.showRequests(requestManager);
    }

    private void initController() {
      
        view.getBtnLoadData().addActionListener(e -> {
            loadAndDisplayData();
            view.showMessage("ดึงข้อมูลใหม่เรียบร้อยแล้ว");
        });

        view.getBtnCreateRequest().addActionListener(e -> {
            String requesterId = view.showMemberSelectionDialog("เลือกผู้เสนอคำขอ (Requester):", membersManager.getMembers());
            if (requesterId == null) return;

            String targetId = view.showMemberSelectionDialog("เลือกสมาชิกเป้าหมาย (Target):", membersManager.getMembers());
            if (targetId == null) return;

            String newRole = view.showRoleSelectionDialog("เลือกบทบาทใหม่ที่ต้องการเปลี่ยน:");
            if (newRole == null) return;

            String result = requestManager.createRequest(requesterId, targetId, newRole);
            if ("SUCCESS".equalsIgnoreCase(result)) {
                view.showMessage("สร้างคำขอเปลี่ยนบทบาทสำเร็จ!");
                refreshUI();
            } else {
                view.showError(result);
            }
        });

        
        view.getBtnVoteApprove().addActionListener(e -> 
            voteController.processVote("APPROVE", this::refreshUI)
        );

        view.getBtnVoteReject().addActionListener(e -> 
            voteController.processVote("REJECT", this::refreshUI)
        );

        // 4. ยกเลิกคำขอ
        view.getBtnCancelRequest().addActionListener(e -> {
            int selectedRow = view.getRequestTable().getSelectedRow();
            if (selectedRow == -1) {
                view.showError("กรุณาเลือกรายการคำขอในตารางก่อน");
                return;
            }

            String requestId = view.getRequestTable().getValueAt(selectedRow, 0).toString();
            String requesterId = view.showMemberSelectionDialog("เลือกสมาชิกผู้เสนอคำขอ เพื่อยืนยันการยกเลิก:", membersManager.getMembers());
            if (requesterId == null) return;

            String result = requestManager.cancelRequest(requestId, requesterId);
            if ("SUCCESS".equalsIgnoreCase(result)) {
                view.showMessage("ยกเลิกคำขอสำเร็จ!");
                refreshUI();
            } else {
                view.showError(result);
            }
        });
    }
}