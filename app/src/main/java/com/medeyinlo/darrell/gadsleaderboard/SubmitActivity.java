package com.medeyinlo.darrell.gadsleaderboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        mFirstNameEt = findViewById(R.id.first_name_edtxt);
        mLastNameEt = findViewById(R.id.last_name_edtxt);
        mEmailEt = findViewById(R.id.email_edtxt);
        mProjectLinkEt = findViewById(R.id.project_link_edtxt);
        Button submitBtn = findViewById(R.id.submit_form_btn);

        createRetrofitClientAndApi();

        setSupportActionBar(findViewById(R.id.submit_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        submitBtn.setOnClickListener(view -> sendProjectData());
    }

    private void sendProjectData() {
        String firstName = mFirstNameEt.getText().toString();
        String lastName = mLastNameEt.getText().toString();
        String email = mEmailEt.getText().toString();
        String projectLink = mProjectLinkEt.getText().toString();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !projectLink.isEmpty()) {
            Call<Void> submitCall = mApiService.submitProject(email, firstName, lastName, projectLink);

            submitCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    clearAllText();
                    if (response.isSuccessful()) {
                        // TODO: Remove Toasts after adding dialogs for confirmation and failure.

                        new SubmitSuccess().show(getSupportFragmentManager(), "Success");

                    } else {
                        new SubmitFailed().show(getSupportFragmentManager(), "Failure");

                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "onFailure: Network request failed", t);

                    new SubmitFailed().show(getSupportFragmentManager(), "RequestFailure");

                    Toast.makeText(SubmitActivity.this, "Network Request Failed. Error = "
                            + t.toString(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Snackbar.make(Objects.requireNonNull(this.getCurrentFocus()), R.string.fill_all_fields,
                    BaseTransientBottomBar.LENGTH_LONG).show();
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


    public static class SubmitSuccess extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//      TODO: Adjust dialog layout so it creates a square dialog box; Create 2 new dialogs for confirmation and submission failure.
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