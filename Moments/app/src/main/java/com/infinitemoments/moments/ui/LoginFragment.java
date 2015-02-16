package com.infinitemoments.moments.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.infinitemoments.moments.Constants;
import com.infinitemoments.moments.objects.Credentials;
import com.infinitemoments.moments.proxies.HeisenbergProxy;
import com.infinitemoments.moments.listeners.LoginSignupListener;
import com.infinitemoments.moments.R;
import com.infinitemoments.moments.objects.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.mime.TypedByteArray;

public class LoginFragment extends Fragment {
    @InjectView(R.id.edtUsername)
    EditText username;

    @InjectView(R.id.edtPassword)
    EditText password;

    private LoginSignupListener mCallback;
    private RestAdapter restAdapter;
    private HeisenbergProxy proxy;

    private static final String TAG = "LoginFragment";

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("xx--LOG--xx"))
                .build();
        proxy = restAdapter.create(HeisenbergProxy.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        mCallback.updateActionBarTitle("Login");

        return view;
    }

    @OnClick(R.id.btnLogin)
    public void doLogin() {
        if (username.getText() == null || username.getText().toString().trim().equals("")){
            Toast.makeText(this.getActivity(), "Enter username for your account", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText() == null || password.getText().toString().trim().equals("")){
            Toast.makeText(this.getActivity(), "Enter password for your account", Toast.LENGTH_SHORT).show();
            return;
        }

        Credentials cred = new Credentials();
        cred.username = username.getText().toString();
        cred.password = password.getText().toString();

        proxy.postLogin(cred, new Callback<User>() {
            @Override
            public void success(User response, retrofit.client.Response response2) {
                Log.v(TAG, "Sign up successful!");
                Log.v(TAG, "Raw response: " + response.token.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.v(TAG, error.getMessage());
                final String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                Log.v("failure", json.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
}
