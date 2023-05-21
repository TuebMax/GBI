import java.util.HashSet;
import java.util.List;

public class ProfileSet extends HashSet<ProfileSet> {
    public List<String> profile1 = null;
    public List<String> profile2 = null;

    @Override
    public boolean add(ProfileSet profileSet) {
        if(profileSet.size() > 2) throw new IllegalArgumentException("ProfileSet must contain at most 2 elements");
        return super.add(profileSet);
    }

    public List<String> getProfile1() {
        return profile1;
    }

    public List<String> getProfile2() {
        return profile2;
    }

    public void setProfile(List<String> profile1) {
        if(this.profile1 == null){
            this.profile1 = profile1;
        } else if(this.profile2 == null){
            this.profile2 = profile1;
        } else {
            throw new UnsupportedOperationException("Cannot set profile");
        }
    }

}
