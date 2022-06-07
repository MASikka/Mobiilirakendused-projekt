package com.example.androidreviewapp.fragment;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.*;
import android.widget.Button;

import androidx.annotation.*;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {
    private TextInputEditText login_email;
    private TextInputEditText login_password;
    private CoordinatorLayout contained;
    private LoginViewModel loginViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null){
                if (getView() != null) Navigation.findNavController(getView())
                        .navigate(R.id.action_loginFragment_to_searchFragment);
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,container,false);
        requireActivity().setTitle(getString(R.string.login_title));
        contained = view.findViewById(R.id.login_coordinator);
        login_email = view.findViewById(R.id.email_text);
        login_password =view.findViewById(R.id.etPasswordLogin);
        view.findViewById(R.id.btnLoginUser).setOnClickListener(view1 -> {
            String email = login_email.getText().toString().trim();
            String password = login_password.getText().toString().trim();
            if (email.length() > 0 && password.length() > 0){
                loginViewModel.login(email,password);
            } else {
                Snackbar.make(view.findViewById(R.id.login_coordinator),
                        getString(R.string.missing),Snackbar.LENGTH_LONG).show();
            }
        });
        view.findViewById(R.id.btnRegisterUser).setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });
        view.findViewById(R.id.btnForgotPassword).setOnClickListener(view1 -> {

            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
        });
        return view;
    }



}