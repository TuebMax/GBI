import java.util.HashSet;
import java.util.List;

/**
 * Assignment 04
 * Authors:Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * A data structure to store the different profiles of a multiple sequence alignment in their correct order.
 */
public class ProfileSet extends HashSet<ProfileSet> {
    private List<String> profile1 = null;
    private List<String> profile2 = null;

    // Ensure that the ProfileSet only can contain 2 profiles
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

    // Set the profile1 if it is not set yet, otherwise set profile2, otherwise throw an exception
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
