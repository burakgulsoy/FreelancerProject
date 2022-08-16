package com.burakgulsoy.freelancerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.burakgulsoy.freelancerproject.models.Freelancer;
import com.burakgulsoy.freelancerproject.models.Role;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.FreelancerAPI;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.RoleAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        setTitle("Register Page");
        fillRoleSpinner();
    }


    public void register(View view) {
        firstRegisterOperation();
    }


    public boolean checkMailAndPhone(List<Freelancer> freelancerList, EditText txtPhoneNumber, EditText txtEmail) {
        List<String> freelancerPhoneNumbersList = new ArrayList<String>();
        List<String> freelancerEmailsList = new ArrayList<String>();
        boolean isValid = true;

        for (int i = 0; i < freelancerList.size(); i++) {
            freelancerPhoneNumbersList.add(freelancerList.get(i).getPhone_number());
        }

        for (int i = 0; i < freelancerList.size(); i++) {
            freelancerEmailsList.add(freelancerList.get(i).getEmail());
        }

        if (freelancerPhoneNumbersList.contains(txtPhoneNumber.getText().toString())) {
            Toast.makeText(RegisterPage.this, "This phone number already exists", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (freelancerEmailsList.contains(txtEmail.getText().toString())) {
            Toast.makeText(RegisterPage.this, "This email address already exists", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    public boolean checkRegex() {
        boolean isValid = true;

        EditText txtName = findViewById(R.id.editText_updateName);
        EditText txtSurname = findViewById(R.id.editText_updateSurname);
        EditText txtPhoneNumber = findViewById(R.id.editText_updatePhoneNumber);
        EditText txtEmail = findViewById(R.id.editText_updateEmail);


        String regexNameAndSurname = "[A-Za-z]+"; //only letters   !!! ö ü gibi İngiliz olmayan harflere izin vermiyor
        String regexPhoneNumber1 = "[5]{1}[\\d]{9}"; // 5.....
        String regexPhoneNumber2 = "[0]{1}[5]{1}[\\d]{9}";  // 05.....
        String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$"; // ...@...


        if (!txtName.getText().toString().matches(regexNameAndSurname)) {
            Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!txtSurname.getText().toString().matches(regexNameAndSurname)) {
            Toast.makeText(this, "Invalid surname", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!(txtPhoneNumber.getText().toString().matches(regexPhoneNumber1) || txtPhoneNumber.getText().toString().matches(regexPhoneNumber2))) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!txtEmail.getText().toString().matches(regexEmail)) {
            Toast.makeText(this, "Invalid email type", Toast.LENGTH_SHORT).show();
            isValid = false;
        }


        return isValid;
    }

    //retrieve data from role table to fill our spinner with rolename values
    public void fillRoleSpinner() {
        Spinner spinnerRole = findViewById(R.id.spinnerRole);

        RetrofitService retrofitService = new RetrofitService();
        RoleAPI roleAPI = retrofitService.getRetrofit().create(RoleAPI.class);

        roleAPI.getAll().enqueue(new Callback<List<Role>>() {
            @Override
            public void onResponse(Call<List<Role>> call, Response<List<Role>> response) {
//                Toast.makeText(RegisterPage.this, "successful retrieval", Toast.LENGTH_SHORT).show();

                List<Role> roleList = response.body();
                List<String> roleNames = new ArrayList<>();


                for (int i = 0; i < roleList.size(); i++) {
                    roleNames.add(roleList.get(i).getRole_name());
//                    Log.d("roles", roleList.get(i).getRolename());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterPage.this, android.R.layout.simple_spinner_item, roleNames);
                spinnerRole.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Role>> call, Throwable t) {
                Toast.makeText(RegisterPage.this, "retrieval failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void firstRegisterOperation() {
        EditText txtName = findViewById(R.id.editText_updateName);
        EditText txtSurname = findViewById(R.id.editText_updateSurname);
        EditText txtPhoneNumber = findViewById(R.id.editText_updatePhoneNumber);
        EditText txtEmail = findViewById(R.id.editText_updateEmail);
        EditText txtPassword = findViewById(R.id.editText_currentPassword);
        Spinner spinnerRole = findViewById(R.id.spinnerRole);
        EditText txtDescription = findViewById(R.id.etxtMultilLineTellAboutYourself);

        String name = txtName.getText().toString();
        String surname = txtSurname.getText().toString();
        String phoneNumber = txtPhoneNumber.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        int roleid = spinnerRole.getSelectedItemPosition();
        String freelancer_description = txtDescription.getText().toString();


        Freelancer freelancer = new Freelancer(name, surname, email, phoneNumber, password, (roleid + 1), 0,freelancer_description);

        RetrofitService retrofitService = new RetrofitService();
        FreelancerAPI freelancerAPI = retrofitService.getRetrofit().create(FreelancerAPI.class);


        freelancerAPI.getAll().enqueue(new Callback<List<Freelancer>>() {
            @Override
            public void onResponse(Call<List<Freelancer>> call, Response<List<Freelancer>> response) {


                List<Freelancer> freelancerList = response.body();


                boolean isMailAndPhoneValid = checkMailAndPhone(freelancerList, txtPhoneNumber, txtEmail);
                boolean isRegexValid = checkRegex();

                // check if phone number and email address are suitable for registering, then add the freelancer to the database
                if (isMailAndPhoneValid == true && isRegexValid == true) {
                    freelancerAPI.addFreelancer(freelancer)
                            .enqueue(new Callback<Freelancer>() {
                                @Override
                                public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
//                                    Toast.makeText(RegisterPage.this, "Register successful", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Freelancer> call, Throwable t) {
//                                    Toast.makeText(RegisterPage.this, "Register failed", Toast.LENGTH_SHORT).show();

                                    //aslında fail vermemesi lazım
                                    Toast.makeText(RegisterPage.this, "Register successful", Toast.LENGTH_SHORT).show();

                                    Log.d("Error", "Error occured", t);
                                }
                            });

                }

            }

            @Override  //getAll()
            public void onFailure(Call<List<Freelancer>> call, Throwable t) {

            }
        });

    }




}