package com.lasalle.mdpa.trinder.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.lasalle.mdpa.trinder.R;
import com.lasalle.mdpa.trinder.view.model.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @OnClick(R.id.email_sign_in_button)
    public void onSignInButtonClicked() {
        userViewModel.login(email.getText().toString(), password.getText().toString());
    }
}

