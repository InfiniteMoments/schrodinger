package com.infinitemoments.moments.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
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
import com.infinitemoments.moments.adapters.HomeFragmentsAdapter;
import com.infinitemoments.moments.models.UserObject;
import com.infinitemoments.moments.objects.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmQuery;

public class MomentActivity extends FragmentActivity {
    @InjectView(R.id.viewHolderTitle)
    PagerTitleStrip pageTitle;

    private HomeFragmentsAdapter pageAdapter;
    private Realm realm;
    private User connectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);
        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MomentListFragment.newInstance())
                    .commit();
        }

        getCurrentUserDetails(getIntent().getStringExtra("username"));

        Toast.makeText(this, connectedUser.username + " has logged in with token " + connectedUser.token, Toast.LENGTH_SHORT).show();

        List<Fragment> fragments = getFragments();
        pageAdapter = new HomeFragmentsAdapter(getSupportFragmentManager(), fragments);
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

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(MomentListFragment.newInstance());
        fList.add(FeedListFragment.newInstance());
        fList.add(ProfileFragment.newInstance());
        return fList;
    }
}
