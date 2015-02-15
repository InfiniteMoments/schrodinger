package com.infinitemoments.moments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SignupFragment extends Fragment {
    LoginSignupListener mCallback;
    @InjectView(R.id.edtEmail)
    EditText email;
    @InjectView(R.id.edtPassword)
    EditText password;
    @InjectView(R.id.edtVerifyPassword)
    EditText verifyPassword;
    @InjectView(R.id.btnCompleteSignUp)
    Button signUp;
    @InjectView(R.id.authButton)
    LoginButton authButton;

    private UiLifecycleHelper uiHelper;
    private GraphUser facebookUser;
    private static final String TAG = "SignupFragment";

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.inject(this, view);

        mCallback.updateActionBarTitle("Sign up");
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("email"));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (LoginSignupListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoginSignupListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @OnClick (R.id.btnCompleteSignUp)
    public void onCompleteSignup(){
        if (email.getText() == null || email.getText().toString().trim().equals("")){
            Toast.makeText(this.getActivity(), "Enter an email in the email field", Toast.LENGTH_SHORT).show();
        }

        if (password.getText() == null || password.getText().toString().trim().equals("")){
            Toast.makeText(this.getActivity(), "Enter a password in the password field", Toast.LENGTH_SHORT).show();
        }

        if (verifyPassword.getText() == null || verifyPassword.getText().toString().trim().equals("")){
            Toast.makeText(this.getActivity(), "Enter a password in the verify password field", Toast.LENGTH_SHORT).show();
        }

        if (password.getText().toString().equals(verifyPassword.getText().toString())){
            // Proceed to complete sign up
        } else {
            // Passwords don't match
            Toast.makeText(this.getActivity(), "Passwords don't match, enter again!", Toast.LENGTH_SHORT).show();

            password.setText("");
            verifyPassword.setText("");
        }

    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            // Request user data and show the results
            //Create the Request
            Request meRequest = Request.newMeRequest(session, new Request.GraphUserCallback()
            {
                @Override
                public void onCompleted(GraphUser user, Response response)
                {
                    if(response.getError()==null)
                    {
                        Log.v(TAG, "Calling /me endpoint");
                        Log.v(TAG, String.valueOf(user.getProperty("email")));

                        facebookUser = user;
                        // Populate text fields
                        email.setText(String.valueOf(user.getProperty("email")));
                    }

                }
            });
            //Execute the request
            meRequest.executeAsync();
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
