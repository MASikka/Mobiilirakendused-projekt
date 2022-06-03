package com.example.androidreviewapp.fragment;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.viewmodel.LoginViewModel;
import com.example.androidreviewapp.viewmodel.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.example.androidreviewapp.viewmodel.LoginViewModel;

public class RegisterFragment extends Fragment {

    private TextInputEditText register_email;
    private TextInputEditText register_password;
    private TextInputEditText confirm_password;
    private CoordinatorLayout contained;
    private RegisterViewModel registerViewModel;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null){
                if (getView() != null) Navigation.findNavController(getView())
                        .navigate(R.id.action_registerFragment_to_searchFragment);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment,container,false);
        setHasOptionsMenu(true);
        requireActivity().setTitle(getString(R.string.register_title));
        contained = view.findViewById(R.id.register_coordinator);
        register_email = view.findViewById(R.id.etEmailRegister);
        register_password =view.findViewById(R.id.etPasswordRegister);
        confirm_password = view.findViewById(R.id.etPasswordConfirm);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_register,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.register_user){
            String email = register_email.getText().toString().trim();
            String password = register_password.getText().toString().trim();
            String confirm = confirm_password.getText().toString().trim();

            if (isAnyStringNullOrEmpty(email,password,confirm)){
                Snackbar.make(contained,getString(R.string.missing),Snackbar.LENGTH_SHORT).show();
            }else{
                if (password.length() < 6 || confirm.length() < 6){
                    Snackbar.make(contained,getString(R.string.passLength),Snackbar.LENGTH_SHORT).show();
                }else{
                    if (password.equals(confirm)){
                        registerViewModel.userRegistration(email,password);
                        Navigation.findNavController(getView())
                                .navigate(R.id.action_registerFragment_to_loginFragment);

                    }else{
                        Snackbar.make(contained,getString(R.string.noMatch),Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return false;
    }


    public static boolean isAnyStringNullOrEmpty(String... strings) {
        for (String s : strings)
            if (s == null || s.isEmpty())
                return true;
        return false;
    }
}