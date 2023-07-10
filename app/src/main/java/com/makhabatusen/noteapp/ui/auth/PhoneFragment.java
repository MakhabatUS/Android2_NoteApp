package com.makhabatusen.noteapp.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.makhabatusen.noteapp.MainActivity;
import com.makhabatusen.noteapp.R;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {

    private EditText etPhone;
    private EditText etCode;
    private View viewPhone;
    private View viewCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private String verificationId;

    private TextView tvTimer;

    private CountDownTimer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPhone = view.findViewById(R.id.layout_view_phone);
        viewCode = view.findViewById(R.id.layout_view_code);

        etPhone = view.findViewById(R.id.et_phone);
        etCode = view.findViewById(R.id.et_code);

        tvTimer = view.findViewById(R.id.tv_timer);
        showViewPhone();
        view.findViewById(R.id.bt_next).setOnClickListener(v -> {
            requestSMS();
        });

        view.findViewById(R.id.bt_confirm).setOnClickListener(v -> {
            confirmCode();
        });

        initCallback();

    }

    private void confirmCode() {
        String code = etCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signIn(credential);
    }

    private void signIn(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            ((MainActivity) requireActivity()).closeFragment();
                        } else {
                            Toast.makeText(requireContext(), "Authorisation Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showViewPhone() {
        viewPhone.setVisibility(View.VISIBLE);
        viewCode.setVisibility(View.GONE);
    }

    private void showViewCode() {
        viewPhone.setVisibility(View.GONE);
        viewCode.setVisibility(View.VISIBLE);

        startTimer();
    }

    private void startTimer() {

        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String text = "00" + (millisUntilFinished / 1000);
                tvTimer.setText(text);
            }

            @Override
            public void onFinish() {

                showViewPhone();
            }
        };

        timer.start();
    }

    private void initCallback() {

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                etCode.setText(phoneAuthCredential.getSmsCode());
                Log.e("ololo", "onVerificationCompleted");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("ololo", "onVerificationFailed");
            }


            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                showViewCode();
            }
        };

    }

    private void requestSMS() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        PhoneAuthOptions options = PhoneAuthOptions
                .newBuilder(mAuth)
                .setPhoneNumber(etPhone.getText().toString().trim())
                .setTimeout(60L, TimeUnit.MILLISECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}