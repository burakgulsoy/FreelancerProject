package com.burakgulsoy.freelancerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.burakgulsoy.freelancerproject.models.Freelancer;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.FreelancerAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFreelancerInformation extends AppCompatActivity {

    RetrofitService retrofitService = new RetrofitService();
    FreelancerAPI freelancerAPI = retrofitService.getRetrofit().create(FreelancerAPI.class);

    EditText editText_updateName;
    EditText editText_updateSurname;
    EditText editText_updatePhoneNumber;
    EditText editText_updateEmail;
    EditText editText_updateDescription;

    Button btn_cancel;
    Button btn_save;

    private int freelancer_id;
    private String freelancer_phoneNumber;
    private String freelancer_email;

//    private boolean passwordValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_freelancer_information);
        setTitle("");

        Intent intent = getIntent();
        freelancer_id = intent.getIntExtra("loginIntent", -1);

        initializeComponents();
        fillComponents();
        setListeners();
    }


    public void initializeComponents() {
        editText_updateName = findViewById(R.id.editText_updateName);
        editText_updateSurname = findViewById(R.id.editText_updateSurname);
        editText_updatePhoneNumber = findViewById(R.id.editText_updatePhoneNumber);
        editText_updateEmail = findViewById(R.id.editText_updateEmail);
        editText_updateDescription = findViewById(R.id.editText_updateDescription);

        btn_cancel = findViewById(R.id.btn_update_cancel);
        btn_save = findViewById(R.id.btn_update_save);

    }

    public void fillComponents() {
        RetrofitService retrofitService = new RetrofitService();
        FreelancerAPI freelancerAPI = retrofitService.getRetrofit().create(FreelancerAPI.class);


        freelancerAPI.getById(freelancer_id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                Freelancer freelancer = response.body();

                editText_updateName.setText(freelancer.getName());
                editText_updateSurname.setText(freelancer.getSurname());
                editText_updatePhoneNumber.setText(freelancer.getPhone_number());
                editText_updateEmail.setText(freelancer.getEmail());
                editText_updateDescription.setText(freelancer.getFreelancer_description());

                freelancer_email = editText_updateEmail.getText().toString();
                freelancer_phoneNumber = editText_updatePhoneNumber.getText().toString();
            }

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {

            }
        });

    }

    public void setListeners() {

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailValidation();
                phoneValidation();


                // there shouldn't be any empty edit text
                if (!(editText_updateName.getText().toString().equals("")
                        && editText_updateSurname.getText().toString().equals("")
                        && editText_updatePhoneNumber.getText().toString().equals("")
                        && editText_updateEmail.getText().toString().equals("")
                        )) {


                    freelancerAPI.getById(freelancer_id).enqueue(new Callback<Freelancer>() {
                        @Override
                        public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {

                            Freelancer freelancer = response.body();

                            freelancer.setName(editText_updateName.getText().toString());
                            freelancer.setSurname(editText_updateSurname.getText().toString());
                            freelancer.setEmail(editText_updateEmail.getText().toString());
                            freelancer.setPhone_number(editText_updatePhoneNumber.getText().toString());
                            freelancer.setFreelancer_description(editText_updateDescription.getText().toString());

                            freelancerAPI.updateFreelancer(freelancer).enqueue(new Callback<Freelancer>() {
                                @Override
                                public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {

                                }

                                @Override
                                public void onFailure(Call<Freelancer> call, Throwable t) {

                                }
                            });

                            Toast.makeText(UpdateFreelancerInformation.this, "User information is updated successfully", Toast.LENGTH_SHORT).show();

//                            Intent intent = new Intent(UpdateFreelancerInformation.this, MyAccountPage.class);
//                            intent.putExtra("loginIntent", freelancer_id);
//                            startActivity(intent);
//                            finish();
                        }

                        @Override
                        public void onFailure(Call<Freelancer> call, Throwable t) {

                        }
                    });

                } else {
                    Toast.makeText(UpdateFreelancerInformation.this, "You should fill all text fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_btnCancel = new Intent(UpdateFreelancerInformation.this, MyAccountPage.class);
                intent_btnCancel.putExtra("loginIntent", freelancer_id);
                startActivity(intent_btnCancel);
                finish();
            }
        });
    }


    public void emailValidation() {

        freelancerAPI.getAll().enqueue(new Callback<List<Freelancer>>() {
            @Override
            public void onResponse(Call<List<Freelancer>> call, Response<List<Freelancer>> response) {
                List<Freelancer> freelancerList = response.body();

                List<String> freelancersEmailList = new ArrayList<>();

                for (int i = 0; i < freelancerList.size(); i++) {
                    freelancersEmailList.add(freelancerList.get(i).getEmail());
                }

                String textEmail = editText_updateEmail.getText().toString();

                //if users enter something different than their current email
                if (!textEmail.equals(freelancer_email)) {

                    String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$"; // ...@...

                    //check regex
                    if (!textEmail.matches(regexEmail)) {
                        Toast.makeText(UpdateFreelancerInformation.this, "Invalid email type", Toast.LENGTH_SHORT).show();
                    }
                    //check validation
                    else {

                        //if given email already exists (belongs to someone else), set editText ""
                        if (freelancersEmailList.contains(textEmail)) {
                            editText_updateEmail.setText("");
                            Toast.makeText(UpdateFreelancerInformation.this, "This email already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Freelancer>> call, Throwable t) {

            }
        });


    }

    public void phoneValidation() {
        freelancerAPI.getAll().enqueue(new Callback<List<Freelancer>>() {
            @Override
            public void onResponse(Call<List<Freelancer>> call, Response<List<Freelancer>> response) {
                List<Freelancer> freelancerList = response.body();

                List<String> freelancerPhoneList = new ArrayList<>();

                for (int i = 0; i < freelancerList.size(); i++) {
                    freelancerPhoneList.add(freelancerList.get(i).getPhone_number());
                }

                String textPhone = editText_updatePhoneNumber.getText().toString();

                //if users enter something different than their current phone number
                if (!textPhone.equals(freelancer_phoneNumber)) {

                    String regexPhoneNumber1 = "[5]{1}[\\d]{9}"; // 5.....
                    String regexPhoneNumber2 = "[0]{1}[5]{1}[\\d]{9}";  // 05.....

                    //check regex
                    if (!(textPhone.matches(regexPhoneNumber1) || textPhone.matches(regexPhoneNumber2))) {
                        Toast.makeText(UpdateFreelancerInformation.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    }
                    //check validation
                    else {
                        if (freelancerPhoneList.contains(textPhone)) {
                            editText_updatePhoneNumber.setText("");
                            Toast.makeText(UpdateFreelancerInformation.this, "This phone number already exists", Toast.LENGTH_SHORT).show();
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