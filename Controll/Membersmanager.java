package Controll;

import Model.SeedData;
import java.util.List;

public class Membersmanager {

    private List<SeedData.MemberData> members;

    public Membersmanager(List<SeedData.MemberData> members) {
        this.members = members;
    }

    public List<SeedData.MemberData> getMembers() {
        return members;
    }

    public SeedData.MemberData findMemberById(String id) {
        if (members == null) return null;
        for (SeedData.MemberData m : members) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }
}