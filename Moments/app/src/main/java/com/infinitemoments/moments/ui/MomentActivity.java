package com.infinitemoments.moments.ui;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.infinitemoments.moments.R;
import com.infinitemoments.moments.models.UserObject;
import com.infinitemoments.moments.objects.User;

import io.realm.Realm;
import io.realm.RealmQuery;

public class MomentActivity extends ActionBarActivity {
    private Realm realm;
    private User connectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        getCurrentUserDetails(getIntent().getStringExtra("username"));

        Toast.makeText(this, connectedUser.username + " has logged in with token " + connectedUser.token, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getCurrentUserDetails(String username) {
        // Open the default realm ones for the UI thread.
        realm = Realm.getInstance(this);

        // Build the query looking at all users:
        RealmQuery<UserObject> query = realm.where(UserObject.class);

        // Add query conditions:
        query.equalTo("username", username);
        UserObject loggedInUser = query.findFirst();

        connectedUser = new User(loggedInUser);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_moment, container, false);
            return rootView;
        }
    }
}
