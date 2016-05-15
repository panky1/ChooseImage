package example.com.chooseimage;

import javax.inject.Inject;

/**
 * Created by dilip on 4/29/16.
 */
public class UserHelper {
    private User mCurrentUser;

    @Inject
    public UserHelper() {
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.mCurrentUser = currentUser;
    }
}
