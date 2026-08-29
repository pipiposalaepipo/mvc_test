package Controll;

import Views.MainScreen;
import javax.swing.JTable;

public class VoteController {
    private ChangeRolemanager requestManager;
    private Membersmanager membersManager;
    private MainScreen view;

    public VoteController(ChangeRolemanager requestManager, Membersmanager membersManager, MainScreen view) {
        this.requestManager = requestManager;
        this.membersManager = membersManager;
        this.view = view;
    }

    public void processVote(String voteType, Runnable onVoteSuccess) {
        JTable table = view.getRequestTable();
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            view.showError("กรุณาเลือกรายการคำขอในตารางก่อนทำการลงความเห็น");
            return;
        }

        String requestId = table.getValueAt(selectedRow, 0).toString();
        
        String voterId = view.showMemberSelectionDialog("เลือกสมาชิกผู้ลงความเห็น (" + voteType + "):", membersManager.getMembers());
        if (voterId == null) {
            return; 
        }

        String result = requestManager.voteRequest(requestId, voterId, voteType);
        
        if ("SUCCESS".equalsIgnoreCase(result)) {
            view.showMessage("บันทึกการลงความเห็น (" + voteType + ") เรียบร้อยแล้ว!");
            if (onVoteSuccess != null) {
                onVoteSuccess.run(); 
            }
        } else {
            view.showError(result);
        }
    }
}