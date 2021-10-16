package com.example.kc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kc.client.RetrofitClient;
import com.example.kc.client.RetrofitService;
import com.example.kc.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private String CLIENT_ID = "test-client";
    private String GRANT_TYPE = "password";
    private String CLIENT_SECRET = "1aee6ef7-49b7-4c12-b8b0-d829c7f1be8e";
    private String SCOPE = "openid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            getAT();
        });

    }

    private void getAT() {

        RetrofitService retrofitService = RetrofitClient.getRetrofit().create(RetrofitService.class);

        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();

        Call<AccessToken> call = retrofitService.getAccessToken(CLIENT_ID, GRANT_TYPE, CLIENT_SECRET, SCOPE, username, password);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {

                    startActivity(new Intent(MainActivity.this, HomeActivity.class));

                } else {
                    Toast.makeText(MainActivity.this, "Error:", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}