package com.preethzcodez.ecommerceexample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.preethzcodez.ecommerceexample.R;
import com.preethzcodez.ecommerceexample.database.DB_Handler;
import com.preethzcodez.ecommerceexample.database.SessionManager;
import com.preethzcodez.ecommerceexample.pojo.User;
import com.preethzcodez.ecommerceexample.utils.Constants;

/**
 * Created by Preeth on 1/6/2018.
 */

public class Account extends Fragment {

    DB_Handler db_handler;
    TextView logout, name, email, mobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.account, container, false);

        // Get User
        db_handler = new DB_Handler(getActivity());
        SessionManager sessionManager = new SessionManager(getActivity());
        User user = db_handler.getUser(sessionManager.getSessionData(Constants.SESSION_EMAIL));

        // Set Values
        setIds(view);
        setValues(user);

        return view;
    }

    // Set Ids
    private void setIds(View view)
    {
        logout = (TextView) view.findViewById(R.id.logout);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        mobile = (TextView) view.findViewById(R.id.mobile);
    }

    // Set Values
    private void setValues(User user)
    {
        // Name
        name.setText(user.getName());

        // Email
        email.setText(user.getEmail());

        // Mobile
        mobile.setText(user.getMobile());
    }
}
