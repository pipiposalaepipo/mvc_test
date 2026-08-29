package Model;

import java.util.ArrayList;
import java.util.List;

public class SeedData {
    private List<MemberData> members = new ArrayList<>();
    private List<RequestData> role_change_requests = new ArrayList<>();
    private List<DecisionData> decisions = new ArrayList<>();

    public List<MemberData> getMembers() { return members; }
    public List<RequestData> getRequests() { return role_change_requests; }
    public List<DecisionData> getDecisions() { return decisions; }

    public static class MemberData {
        private String id;
        private String name;
        private String role;
        private boolean active;

        public MemberData(String id, String name, String role, boolean active) {
            this.id = id;
            this.name = name;
            this.role = role;
            this.active = active;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public boolean isActive() { return active; } 
    }

    public static class RequestData {
        private String id;
        private String requester_id;
        private String target_id;
        private String new_role;
        private String status; 

        public RequestData(String id, String requester_id, String target_id, String new_role, String status) {
            this.id = id;
            this.requester_id = requester_id;
            this.target_id = target_id;
            this.new_role = new_role;
            this.status = status;
        }

        public String getId() { return id; }
        public String getRequesterId() { return requester_id; }
        public String getTargetId() { return target_id; }
        public String getNewRole() { return new_role; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class DecisionData {
        private String request_id;
        private String member_id;
        private String result; 

        public DecisionData(String request_id, String member_id, String result) {
            this.request_id = request_id;
            this.member_id = member_id;
            this.result = result;
        }

        public String getRequestId() { return request_id; }
        public String getMemberId() { return member_id; }
        public String getResult() { return result; }
    }
}