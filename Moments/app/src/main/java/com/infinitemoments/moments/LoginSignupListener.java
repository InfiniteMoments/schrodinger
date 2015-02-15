package com.infinitemoments.moments;

import io.realm.Realm;

/**
 * Created by Salman on 2/13/2015.
 */
public interface LoginSignupListener {
    public void showSignUpFragment();
    public void showLoginFragment();
    public void storeLoggedInUser(User user);
    public void updateActionBarTitle(String title);
}
