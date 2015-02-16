package com.infinitemoments.moments.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.infinitemoments.moments.events.HideProgressBarEvent;
import com.infinitemoments.moments.events.ValidLoginEvent;
import com.infinitemoments.moments.listeners.LoginSignupListener;
import com.infinitemoments.moments.R;
import com.infinitemoments.moments.objects.User;
import com.infinitemoments.moments.models.UserObject;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmQuery;

public class MainActivity extends ActionBarActivity implements LoginSignupListener {
    private Realm realm;
    private String connectedUsername;

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

        // Build the query looking at all users:
        RealmQuery<UserObject> query = realm.where(UserObject.class);

        // Add query conditions:
        query.equalTo("username", user.username);
        UserObject loggedInUser = query.findFirst();

        if (loggedInUser == null){
            // All writes must be wrapped in a transaction to facilitate safe multi threading
            realm.beginTransaction();

            // No existing users stored, create new object
            UserObject storedUser = realm.createObject(UserObject.class);
            if (user.id != null) {
                storedUser.setId(user.id);
            }

            if (user.name != null) {
                storedUser.setName(user.name);
            }

            if (user.email != null) {
                storedUser.setEmail(user.email);
            }

            if (user.token != null) {
                storedUser.setToken(user.token);
            }

            if (user.username != null) {
                storedUser.setUsername(user.username);
                connectedUsername = user.username;
            }

            realm.commitTransaction();
        } else {
            // Found an existing user object, update it
            realm.beginTransaction();
            connectedUsername = user.username;
            loggedInUser.setToken(user.token);

            realm.commitTransaction();
        }

        //Update UI
        EventBus.getDefault().post(new HideProgressBarEvent());
    }

    @Override
    public void updateActionBarTitle(String title) {
        this.getSupportActionBar().setTitle(title);
    }

    public void onEvent(ValidLoginEvent event){
        //Login is valid, transition to next activity
        Intent i = new Intent(this, MomentActivity.class);
        i.putExtra("username", connectedUsername);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
