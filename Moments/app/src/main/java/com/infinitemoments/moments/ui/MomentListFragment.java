package com.infinitemoments.moments.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.infinitemoments.moments.R;
import com.infinitemoments.moments.listeners.LoginSignupListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * This fragment leads the user to login and sign up fragments
 */
public class MomentListFragment extends Fragment {
    @InjectView(R.id.btnSignUp)
    Button signUpButton;

    @InjectView(R.id.btnLogin)
    Button loginButton;

    LoginSignupListener mCallback;

    /**
     * @return A new instance of fragment StarterFragment.
     */
    public static MomentListFragment newInstance() {
        MomentListFragment fragment = new MomentListFragment();
        return fragment;
    }

    public MomentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starter, container, false);
        ButterKnife.inject(this, view);

        mCallback.updateActionBarTitle("Moments");

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (LoginSignupListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoginSignupListener");
        }
    }

    @OnClick(R.id.btnLogin)
    public void onLogin() {
        mCallback.showLoginFragment();
    }

    @OnClick(R.id.btnSignUp)
    public void onSignUp() {
        mCallback.showSignUpFragment();
    }
}
