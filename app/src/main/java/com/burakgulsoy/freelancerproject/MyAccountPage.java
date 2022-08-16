package com.burakgulsoy.freelancerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.burakgulsoy.freelancerproject.models.Freelancer;
import com.burakgulsoy.freelancerproject.models.Role;
import com.burakgulsoy.freelancerproject.models.Task;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.FreelancerAPI;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.RoleAPI;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.TaskAPI;
import com.burakgulsoy.freelancerproject.supportive_classes.TASK_STATUS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountPage extends AppCompatActivity {

    private int freelancer_id;
    private int freelancer_roleId;

    TextView textView_name;
    TextView textView_role;
    TextView textView_phoneNumber;
    TextView textView_email;
    TextView textView_allTasks;
    TextView textView_ToDoTasks;
    TextView textView_InProgressTasks;
    TextView textView_DoneTasks;
    TextView textView_freelancerDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_page);
        setTitle("");

        Intent intent = getIntent();
        freelancer_id = intent.getIntExtra("loginIntent", -1);
        Log.d("my account freelancer id", String.valueOf(freelancer_id));


        componentInitializer();
        fillComponents();
        setListeners();
    }


    public void componentInitializer() {
        textView_name = findViewById(R.id.textView_map_freelancerName);
        textView_role = findViewById(R.id.textView_map_freelancerRole);
        textView_phoneNumber = findViewById(R.id.textView_map_phoneNumber);
        textView_email = findViewById(R.id.textView_map_email);
        textView_allTasks = findViewById(R.id.textView_map_numberOf_AllTasks);
        textView_ToDoTasks = findViewById(R.id.textView_map_numberOf_ToDoTasks);
        textView_InProgressTasks = findViewById(R.id.textView_map_numberOf_InProgressTasks);
        textView_DoneTasks = findViewById(R.id.textView_map_numberOf_DoneTasks);
        textView_freelancerDescription = findViewById(R.id.textView_map_freelancerDescription);
    }

    public void fillComponents() {
        RetrofitService retrofitService = new RetrofitService();

        FreelancerAPI freelancerAPI = retrofitService.getRetrofit().create(FreelancerAPI.class);
        RoleAPI roleAPI = retrofitService.getRetrofit().create(RoleAPI.class);
        TaskAPI taskAPI = retrofitService.getRetrofit().create(TaskAPI.class);

        freelancerAPI.getById(freelancer_id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                Freelancer freelancer = response.body();

                textView_name.setText(freelancer.getName());
                textView_phoneNumber.setText(freelancer.getPhone_number());
                textView_email.setText(freelancer.getEmail());
                textView_freelancerDescription.setText(freelancer.getFreelancer_description());
                freelancer_roleId = freelancer.getRole_id();

                //set textView_map_freelancerRole
                roleAPI.getById(freelancer_roleId).enqueue(new Callback<Role>() {
                    @Override
                    public void onResponse(Call<Role> call, Response<Role> response) {
                        Role role = response.body();
                        textView_role.setText(role.getRole_name());
                    }

                    @Override
                    public void onFailure(Call<Role> call, Throwable t) {

                    }
                });

                //set number of tasks for each category
                taskAPI.getAll().enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        List<Task> taskList = response.body();

                        List<Task> toDoTaskList = new ArrayList<Task>();
                        List<Task> inProgressTaskList = new ArrayList<Task>();
                        List<Task> doneTasklist = new ArrayList<Task>();

                        for (int i = 0; i < taskList.size(); i++) {

                            if (taskList.get(i).getFreelancer_id() == freelancer_id) {

                                String task_type = taskList.get(i).getTask_type();

                                switch (task_type) {

                                    case "TO_DO":
                                        toDoTaskList.add(taskList.get(i));
                                        break;
                                    case "IN_PROGRESS":
                                        inProgressTaskList.add(taskList.get(i));
                                        break;
                                    case "DONE":
                                        doneTasklist.add(taskList.get(i));
                                        break;
                                }
                            }
                        }

                        int numberOfAllTasks = toDoTaskList.size() + inProgressTaskList.size() + doneTasklist.size();

                        textView_allTasks.setText(String.valueOf(numberOfAllTasks));
                        textView_ToDoTasks.setText(String.valueOf(toDoTaskList.size()));
                        textView_InProgressTasks.setText(String.valueOf(inProgressTaskList.size()));
                        textView_DoneTasks.setText(String.valueOf(doneTasklist.size()));
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {

                    }
                });

            }


            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {

            }
        });

    }

    public void setListeners() {
        Button btn_homepage = findViewById(R.id.btn_homepage);
        Button btn_updateInformation = findViewById(R.id.btn_updateAccountInformation);

        btn_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSend = new Intent(MyAccountPage.this, FreelancerPageActivity.class);
                intentToSend.putExtra("loginIntent", freelancer_id);
                startActivity(intentToSend);
                finish();
            }
        });

        btn_updateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccountPage.this, UpdateFreelancerInformation.class);
                intent.putExtra("loginIntent", freelancer_id);
//                intent.putExtra("freelancerEmail", textView_email.getText().toString());
//                intent.putExtra("freelancerPhone", textView_phoneNumber.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}