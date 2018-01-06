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

public class SignIn extends Fragment {

    Button signIn;
    EditText email, password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sign_in, container, false);

        setIds(view);
        setClickListeners();

        return view;
    }

    // Set Ids
    private void setIds(View view) {
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        signIn = (Button) view.findViewById(R.id.signin);
    }

    // Set Click Listeners
    private void setClickListeners() {
        // Sign In
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set Values To User Model
                User user = new User();
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());

                // Validate Fields
                if (user.getEmail().trim().length() > 0) {
                    if (Util.isValidEmail(user.getEmail())) {
                        if (user.getPassword().trim().length() > 0) {

                            // Sign In User
                            DB_Handler db_handler = new DB_Handler(getActivity());
                            user = db_handler.getUser(user.getEmail());
                            try {
                                if (user.getEmail().trim().length() > 0) {
                                    // Save Session
                                    SessionManager sessionManager = new SessionManager(getActivity());
                                    sessionManager.saveSession(Constants.SESSION_EMAIL, user.getEmail());
                                    sessionManager.saveSession(Constants.SESSION_PASSWORD, user.getPassword());

                                    // Load Main Activity
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                    getActivity().finish();
                                } else {
                                    showInvalidUser();
                                }
                            } catch (NullPointerException e) {
                                showInvalidUser();
                            }

                        } else {
                            showErrorToast("Password");
                        }

                    } else {
                        showErrorToastEmailNotValid();
                    }
                } else {
                    showErrorToast("Email Id");
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

    // Show Invalid User
    private void showInvalidUser() {
        Toast.makeText(getActivity(), R.string.InvalidUser, Toast.LENGTH_LONG).show();
    }
}
