package com.burakgulsoy.freelancerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.burakgulsoy.freelancerproject.models.Freelancer;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.FreelancerAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        setTitle("Login Page");
    }

    public void login(View view) {
        loginOperation();

    }

    public void loginOperation() {

        RetrofitService retrofitService = new RetrofitService();
        FreelancerAPI freelancerAPI = retrofitService.getRetrofit().create(FreelancerAPI.class);

        EditText txtPhoneOrEmail = findViewById(R.id.etxtPhoneNumberOrEmail);
        EditText txtPassword = findViewById(R.id.etxtPassword);

        String getTextPhoneOrEmail = txtPhoneOrEmail.getText().toString();
        String password = txtPassword.getText().toString();


        freelancerAPI.getAll().enqueue(new Callback<List<Freelancer>>() {
            @Override
            public void onResponse(Call<List<Freelancer>> call, Response<List<Freelancer>> response) {

                boolean isPhoneOrMailValid = false;
                int indexOfFreelancer = -1;

                List<Freelancer> freelancerList = response.body();

                for (int i = 0; i < freelancerList.size(); i++) {
                    if (freelancerList.get(i).getEmail().equals(getTextPhoneOrEmail) || freelancerList.get(i).getPhone_number().equals(getTextPhoneOrEmail)) {
                        indexOfFreelancer = i;
                        isPhoneOrMailValid = true;
                        break;
                    }
                }
                    // check if there is a valid phone number / email
                if (isPhoneOrMailValid == false) {
                    Toast.makeText(LoginPage.this, "Mail or phone number does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    // if username is valid, then check for password  (might not be good for security concerns, could change it)
                    if (freelancerList.get(indexOfFreelancer).getPassword().equals(password) == false) {
                        Toast.makeText(LoginPage.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    } else {
                    // if the password is true, check if the account is activated or not

                        if (freelancerList.get(indexOfFreelancer).getIs_validated() == 0) {
                            //if it is not activated
                            Toast.makeText(LoginPage.this, "Your account has not been activated yet", Toast.LENGTH_SHORT).show();
                        } else {
                            //it's activated, all set, login
                            Toast.makeText(LoginPage.this, "Successful login", Toast.LENGTH_SHORT).show();

                            Intent successfulLoginIntent = new Intent(LoginPage.this,FreelancerPageActivity.class);
                            successfulLoginIntent.putExtra("loginIntent", freelancerList.get(indexOfFreelancer).getFreelancer_id());
                            startActivity(successfulLoginIntent);
                            finish();
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Freelancer>> call, Throwable t) {

            }
        });

    }
}