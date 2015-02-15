package com.infinitemoments.moments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.realm.Realm;

public class MainActivity extends ActionBarActivity implements LoginSignupListener {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, StarterFragment.newInstance())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSignUpFragment() {
        SignupFragment newFragment = SignupFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void showLoginFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, loginFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void storeLoggedInUser(User user) {
        // Open the default realm ones for the UI thread.
        realm = Realm.getInstance(this);

        UserObject loggedInUser = realm.where(UserObject.class).findFirst();

        if (loggedInUser == null){
            // All writes must be wrapped in a transaction to facilitate safe multi threading
            realm.beginTransaction();

            // No existing users stored, create new object
            UserObject storedUser = realm.createObject(UserObject.class);
            storedUser.setId(user.id);
            storedUser.setName(user.name);
            storedUser.setEmail(user.email);
            storedUser.setToken(user.token);
            realm.commitTransaction();
        } else {
            // Found an existing user object, update it
            realm.beginTransaction();
            loggedInUser.setId(user.id);
            loggedInUser.setName(user.name);
            loggedInUser.setEmail(user.email);
            loggedInUser.setToken(user.token);
            realm.commitTransaction();
        }
    }

    @Override
    public void updateActionBarTitle(String title) {
        this.getSupportActionBar().setTitle(title);
    }
}
