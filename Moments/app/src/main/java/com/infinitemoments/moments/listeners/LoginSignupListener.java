package com.infinitemoments.moments.listeners;

import com.infinitemoments.moments.objects.User;

/**
 * Created by Salman on 2/13/2015.
 */
public interface LoginSignupListener {
    public void showSignUpFragment();
    public void showLoginFragment();
    public void storeLoggedInUser(User user);
    public void updateActionBarTitle(String title);
}
