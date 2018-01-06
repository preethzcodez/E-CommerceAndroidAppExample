package com.preethzcodez.ecommerceexample.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.preethzcodez.ecommerceexample.MainActivity;
import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.database.SessionManager;
import com.preethzcodez.ecommerceexample.pojo.User;
import com.preethzcodez.ecommerceexample.utils.Constants;
import com.preethzcodez.ecommerceexample.utils.Util;

/**
 * Created by Preeth on 1/6/2018.
 */

public class SignUp extends Fragment {

    EditText name, email, password, mobile, address1, address2, pin, state, country;
    Button signUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sign_up, container, false);

        setIds(view);
        setClickListeners();

        return view;
    }

    // Set Ids
    private void setIds(View view) {
        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        mobile = (EditText) view.findViewById(R.id.mobile);
        signUp = (Button) view.findViewById(R.id.signup);
    }

    // Set Click Listeners
    private void setClickListeners() {
        // Sign Up
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set Values To User Model
                User user = new User();
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setMobile(mobile.getText().toString());
                user.setPassword(password.getText().toString());

                // Validate Fields
                if (user.getName().trim().length() > 0) {
                    if (user.getEmail().trim().length() > 0) {
                        if (Util.isValidEmail(user.getEmail())) {
                            if (user.getMobile().trim().length() > 0) {
                                if (user.getPassword().trim().length() > 0) {

                                    // Register User
                                    DB_Handler db_handler = new DB_Handler(getActivity());
                                    long isInserted = db_handler.registerUser(user.getName(), user.getEmail(), user.getMobile(), user.getPassword());
                                    if (isInserted != -1) {
                                        // Save Session
                                        SessionManager sessionManager = new SessionManager(getActivity());
                                        sessionManager.saveSession(Constants.SESSION_EMAIL, user.getEmail());
                                        sessionManager.saveSession(Constants.SESSION_PASSWORD, user.getPassword());

                                        // Load Main Activity
                                        Intent i = new Intent(getActivity(), MainActivity.class);
                                        startActivity(i);
                                        getActivity().finish();
                                    } else {
                                        showErrorToastEmailExists();
                                    }

                                } else {
                                    showErrorToast("Password");
                                }
                            } else {
                                showErrorToast("Mobile Number");
                            }
                        } else {
                            showErrorToastEmailNotValid();
                        }
                    } else {
                        showErrorToast("Email Id");
                    }
                } else {
                    showErrorToast("Full Name");
                }
            }
        });
    }

    // Show Error Toast
    private void showErrorToast(String value) {
        Toast.makeText(getActivity(), value + R.string.BlankError, Toast.LENGTH_LONG).show();
    }

    // Show Error Toast - Email Not Valid
    private void showErrorToastEmailNotValid() {
        Toast.makeText(getActivity(), R.string.EmailError, Toast.LENGTH_LONG).show();
    }

    // Show Error Toast - Email Exists
    private void showErrorToastEmailExists() {
        Toast.makeText(getActivity(), R.string.EmailExistsError, Toast.LENGTH_LONG).show();
    }
}
