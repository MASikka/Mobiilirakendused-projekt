package com.example.androidreviewapp.fragment;



import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.androidreviewapp.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.androidreviewapp.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordFragment extends Fragment {
    private TextInputEditText recovery_email;
    private Button userButton;
    FirebaseAuth firebaseAuth;


    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password,container,false);
        requireActivity().setTitle(getString(R.string.forgot_password_title));

        recovery_email = view.findViewById(R.id.email_text_forgot);
        userButton = view.findViewById(R.id.btnForgot);
        firebaseAuth = FirebaseAuth.getInstance();

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(recovery_email.getText().toString())){} else{
                firebaseAuth.sendPasswordResetEmail(recovery_email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Navigation.findNavController(getView())
                                            .navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
                                    Toast.makeText(getActivity(), getString(R.string.password_sent), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });}
            }
        });


        return view;
    }

}