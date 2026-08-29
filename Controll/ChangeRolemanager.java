package Controll;

import Model.SeedData;
import java.util.ArrayList;
import java.util.List;

public class ChangeRolemanager {
    private List<SeedData.RequestData> requests;
    private List<SeedData.DecisionData> decisions;
    private Membersmanager membersManager;

    public ChangeRolemanager(List<SeedData.RequestData> requests, List<SeedData.DecisionData> decisions, Membersmanager membersManager) {
        this.requests = requests != null ? requests : new ArrayList<>();
        this.decisions = decisions != null ? decisions : new ArrayList<>();
        this.membersManager = membersManager;
    }

    public List<SeedData.RequestData> getRequests() { return requests; }
    public List<SeedData.DecisionData> getDecisions() { return decisions; }

    public String createRequest(String requesterId, String targetId, String newRole) {
        if (requesterId.equalsIgnoreCase(targetId)) {
            return "ผู้เสนอไม่สามารถเป็นสมาชิกเป้าหมายของคำขอตนเองได้";
        }
        for (SeedData.RequestData req : requests) {
            if (req.getTargetId().equalsIgnoreCase(targetId) && "PENDING".equalsIgnoreCase(req.getStatus())) {
                return "สมาชิกเป้าหมายมีคำขอที่อยู่ระหว่างรอพิจารณาแล้ว";
            }
        }
        String nextId = "C" + String.format("%02d", requests.size() + 1);
        SeedData.RequestData newReq = new SeedData.RequestData(nextId, requesterId, targetId, newRole, "PENDING");
        requests.add(newReq);
        return "SUCCESS";
    }

   
    public String voteRequest(String requestId, String voterId, String result) {
        SeedData.RequestData targetReq = null;
        for (SeedData.RequestData req : requests) {
            if (req.getId().equalsIgnoreCase(requestId)) {
                targetReq = req;
                break;
            }
        }
        if (targetReq == null) return "ไม่พบคำขอที่ระบุ";

        if (!"PENDING".equalsIgnoreCase(targetReq.getStatus())) {
            return "คำขอนี้สิ้นสุดแล้ว ไม่สามารถลงความเห็นได้";
        }

        SeedData.MemberData voter = membersManager.findMemberById(voterId);
        if (voter == null || !voter.isActive()) {
            return "สมาชิกผู้ลงความเห็นไม่มีสิทธิ์หรือไม่ได้อยู่ในสถานะ Active";
        }

        if (voterId.equalsIgnoreCase(targetReq.getRequesterId()) || voterId.equalsIgnoreCase(targetReq.getTargetId())) {
            return "ผู้เสนอและสมาชิกเป้าหมายไม่มีสิทธิ์ลงความเห็นต่อคำขอนี้";
        }

        for (SeedData.DecisionData dec : decisions) {
            if (dec.getRequestId().equalsIgnoreCase(requestId) && dec.getMemberId().equalsIgnoreCase(voterId)) {
                return "คุณได้ลงความเห็นต่อคำขอนี้ไปแล้ว";
            }
        }

        decisions.add(new SeedData.DecisionData(requestId, voterId, result));

        int approveCount = 0;
        int rejectCount = 0;
        for (SeedData.DecisionData dec : decisions) {
            if (dec.getRequestId().equalsIgnoreCase(requestId)) {
                if ("APPROVE".equalsIgnoreCase(dec.getResult())) approveCount++;
                else if ("REJECT".equalsIgnoreCase(dec.getResult())) rejectCount++;
            }
        }

        if (approveCount >= 2) {
            targetReq.setStatus("APPROVED");
            SeedData.MemberData targetMember = membersManager.findMemberById(targetReq.getTargetId());
            if (targetMember != null) {
                targetMember.setRole(targetReq.getNewRole());
            }
        } else if (rejectCount >= 2) {
            targetReq.setStatus("REJECTED");
        }

        return "SUCCESS";
    }

    // R5: ยกเลิกคำขอ
    public String cancelRequest(String requestId, String requesterId) {
        SeedData.RequestData targetReq = null;
        for (SeedData.RequestData req : requests) {
            if (req.getId().equalsIgnoreCase(requestId)) {
                targetReq = req;
                break;
            }
        }
        if (targetReq == null) return "ไม่พบคำขอ";

        if (!targetReq.getRequesterId().equalsIgnoreCase(requesterId)) {
            return "เฉพาะผู้เสนอคำขอเท่านั้นที่สามารถยกเลิกได้";
        }

        if (!"PENDING".equalsIgnoreCase(targetReq.getStatus())) {
            return "ไม่สามารถยกเลิกคำขอที่สิ้นสุดแล้วได้";
        }

        for (SeedData.DecisionData dec : decisions) {
            if (dec.getRequestId().equalsIgnoreCase(requestId)) {
                return "คำขอนี้ถูกลงความเห็นไปแล้ว ไม่สามารถยกเลิกได้";
            }
        }

        targetReq.setStatus("CANCELLED");
        return "SUCCESS";
    }

    public int countVotes(String requestId, String type) {
        int count = 0;
        for (SeedData.DecisionData dec : decisions) {
            if (dec.getRequestId().equalsIgnoreCase(requestId) && dec.getResult().equalsIgnoreCase(type)) {
                count++;
            }
        }
        return count;
    }
}