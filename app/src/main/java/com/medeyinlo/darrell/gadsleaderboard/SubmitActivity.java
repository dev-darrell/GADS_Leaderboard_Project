package com.medeyinlo.darrell.gadsleaderboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medeyinlo.darrell.gadsleaderboard.api.LeaderboardApiService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmitActivity extends AppCompatActivity {
    private static final String TAG = "SubmitActivity";
    private static final String BASE_FORM_URL = "https://docs.google.com/forms/d/e/";
    private Retrofit mRetrofit;
    private LeaderboardApiService mApiService;
    private EditText mFirstNameEt;
    private EditText mLastNameEt;
    private EditText mEmailEt;
    private EditText mProjectLinkEt;
    private AlertDialog mConfirmDialog;
    private ProgressBar mSubmitProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        mFirstNameEt = findViewById(R.id.first_name_edtxt);
        mLastNameEt = findViewById(R.id.last_name_edtxt);
        mEmailEt = findViewById(R.id.email_edtxt);
        mProjectLinkEt = findViewById(R.id.project_link_edtxt);
        mSubmitProgress = findViewById(R.id.submit_progressbar);
        Button submitBtn = findViewById(R.id.submit_form_btn);

        createRetrofitClientAndApi();

        setSupportActionBar(findViewById(R.id.submit_toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        submitBtn.setOnClickListener(view -> {
            mConfirmDialog = ConfirmSubmit();
            mConfirmDialog.show();
        });
    }

    private void sendProjectData() {
        String firstName = mFirstNameEt.getText().toString();
        String lastName = mLastNameEt.getText().toString();
        String email = mEmailEt.getText().toString();
        String projectLink = mProjectLinkEt.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !projectLink.isEmpty()) {
            mSubmitProgress.setVisibility(View.VISIBLE);
            Call<Void> submitCall = mApiService.submitProject(email, firstName, lastName, projectLink);
            clearAllText();

            submitCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    mSubmitProgress.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        new SubmitSuccess().show(getSupportFragmentManager(), getString(R.string.submit_success_dialogId));

                    } else {
                        new SubmitFailed().show(getSupportFragmentManager(), getString(R.string.submit_failure_dialogId));

                    }

                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    mSubmitProgress.setVisibility(View.GONE);
                    new SubmitFailed().show(getSupportFragmentManager(), getString(R.string.submit_failure_dialogId));

                    Log.e(TAG, "onFailure: " + t.toString(), t);
                }
            });


        } else {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
        }
    }

    private void clearAllText() {
        mFirstNameEt.setText("");
        mLastNameEt.setText("");
        mEmailEt.setText("");
        mProjectLinkEt.setText("");
    }

    private void createRetrofitClientAndApi() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_FORM_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        mApiService = mRetrofit.create(LeaderboardApiService.class);
    }


    public AlertDialog ConfirmSubmit() {
        AlertDialog alert = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation, null);

        Button confirmBtn = (Button) dialogView.findViewById(R.id.confirm_btn);
        FloatingActionButton cancelFab = (FloatingActionButton) dialogView.findViewById(R.id.cancel_fab);

        confirmBtn.setOnClickListener(view -> {
            mConfirmDialog.dismiss();
            sendProjectData();
        });

        cancelFab.setOnClickListener(view -> {
            mConfirmDialog.dismiss();
            clearAllText();
        });

        alert.setView(dialogView);

        return alert;
    }

    public static class SubmitSuccess extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.dialog_success, null));

            return builder.create();
        }
    }

    public static class SubmitFailed extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.dialog_failure, null));

            return builder.create();
        }
    }
}