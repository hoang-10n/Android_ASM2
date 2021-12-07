package com.android.asm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserController.init(this);
        UserController.getAllUsers();
        ZoneController.init(this);
        ZoneController.getAllZones();

        LoginFrag loginFrag = new LoginFrag();
        RegisterFrag registerFrag = new RegisterFrag();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frag_container, loginFrag);
        ft.commit();

        Button loginBtn = findViewById(R.id.main_login_btn);
        Button registerBtn = findViewById(R.id.main_register_btn);

        loginBtn.setOnClickListener(v -> {
            if (!isLogin) {
                isLogin = true;
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                ft1.replace(R.id.main_frag_container, loginFrag);
                ft1.commit();
            } else {
                loginFrag.enterApp();
            }
        });

        registerBtn.setOnClickListener(v -> {
            if (isLogin) {
                isLogin = false;
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                ft1.replace(R.id.main_frag_container, registerFrag);
                ft1.commit();
            } else {
                registerFrag.enterApp();
            }
        });
    }
}