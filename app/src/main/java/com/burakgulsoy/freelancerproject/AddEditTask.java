package com.burakgulsoy.freelancerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.burakgulsoy.freelancerproject.models.Task;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.TaskAPI;
import com.burakgulsoy.freelancerproject.supportive_classes.TASK_STATUS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditTask extends AppCompatActivity {


    Spinner taskStatusSpinner;
    EditText editText_taskDescription;
    EditText editText_beginDate;
    EditText editText_endDate;

    int freelancer_id;

    //editable
    private int task_id;
    private String task_description;
    private int task_freelancerId;
    private String task_type;
    private String task_beginDate;
    private String task_endDate;
    private int updateValidator;
    private int deleteValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        setTitle("");

        Intent intent = getIntent();

        freelancer_id = intent.getIntExtra("loginIntent", -1);

        updateValidator = intent.getIntExtra("validator", -1);
        deleteValidator = intent.getIntExtra("deleteValidator", -1);
        decideIfDeleteIsButtonEnabled(deleteValidator);

        task_id = intent.getIntExtra("task_id", -1);
        task_description = intent.getStringExtra("task_description");
        //it could have been equaled to freelancer_id
        task_freelancerId = intent.getIntExtra("task_freelancerId", -1);
        task_type = intent.getStringExtra("task_type");
        task_beginDate = intent.getStringExtra("task_beginDate");
        task_endDate = intent.getStringExtra("task_endDate");

        editText_taskDescription = findViewById(R.id.editText_taskDescription);
        editText_beginDate = findViewById(R.id.editText_beginDate);
        editText_endDate = findViewById(R.id.editText_endDate);

        // get TASK_STATUS enum values and add them to spinner
        fillSpinner();
        // it changes the text of btn_save  ("Add" or "Update")
        setEditableOperation(updateValidator);
        // operations for cancel, delete and add/update buttons
        setListeners();
    }


    public void fillSpinner() {
        taskStatusSpinner = findViewById(R.id.spinner_TaskType);
        ArrayAdapter<Enum> adapter = new ArrayAdapter<Enum>(AddEditTask.this, android.R.layout.simple_spinner_item, TASK_STATUS.values());
        taskStatusSpinner.setAdapter(adapter);
    }


    public void setListeners() {
        Button btn_addTask = findViewById(R.id.btn_save);
        Button btn_cancel = findViewById(R.id.btn_cancel);
        Button btn_delete = findViewById(R.id.btn_delete);


        btn_addTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RetrofitService retrofitService = new RetrofitService();
                TaskAPI taskAPI = retrofitService.getRetrofit().create(TaskAPI.class);

                // maybe we could declare these string values outside the method, should try
                String taskDescription = editText_taskDescription.getText().toString();
                String beginDate = editText_beginDate.getText().toString();
                String endDate = editText_endDate.getText().toString();

                boolean isDateRegexValid = checkDateRegex(beginDate, endDate);

//                if (isDateRegexValid) {
                    // add the task to database
                    if (btn_addTask.getText().equals("Add")) {

                        Task task = new Task(taskDescription, freelancer_id, taskStatusSpinner.getSelectedItem().toString(), beginDate, endDate);

                        taskAPI.addTask(task).enqueue(new Callback<Task>() {
                            @Override
                            public void onResponse(Call<Task> call, Response<Task> response) {
//                        Toast.makeText(AddEditTask.this, "Task is successfully added", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Task> call, Throwable t) {
//                        Toast.makeText(AddEditTask.this, "A problem occured while adding the task", Toast.LENGTH_SHORT).show();

                                // it shouldn't fail actually
                                Toast.makeText(AddEditTask.this, "Task is successfully added", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // update the task
                    } else if (btn_addTask.getText().equals("Update")) {

                        Task task = new Task(task_id, taskDescription, freelancer_id, taskStatusSpinner.getSelectedItem().toString(), beginDate, endDate);

                        Log.d("task id", String.valueOf(task_id));
                        Log.d("task freelancer id ", String.valueOf(task_freelancerId));
                        Log.d("task description", task_description);
                        Log.d("task begin date", task_beginDate);
                        Log.d("task end date", task_endDate);
                        Log.d("task type", task_type);

                        taskAPI.updateTask(task).enqueue(new Callback<Task>() {
                            @Override
                            public void onResponse(Call<Task> call, Response<Task> response) {

                            }

                            @Override
                            public void onFailure(Call<Task> call, Throwable t) {

                            }
                        });

                    }

                    // return to ToDoListFragment
                    Intent intent_btnAddTask = new Intent(AddEditTask.this, FreelancerPageActivity.class);
                    intent_btnAddTask.putExtra("loginIntent", freelancer_id);
                    startActivity(intent_btnAddTask);
                    finish();

                    btn_addTask.setText("Add");
//                }
//                else {
//                    Toast.makeText(AddEditTask.this, "Date is in wrong format", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditTask.this, FreelancerPageActivity.class);
                intent.putExtra("loginIntent", freelancer_id);
                startActivity(intent);

                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitService retrofitService = new RetrofitService();
                TaskAPI taskAPI = retrofitService.getRetrofit().create(TaskAPI.class);

                String taskDescription = editText_taskDescription.getText().toString();
                String beginDate = editText_beginDate.getText().toString();
                String endDate = editText_endDate.getText().toString();

                Task task = new Task(task_id, taskDescription, freelancer_id, taskStatusSpinner.getSelectedItem().toString(), beginDate, endDate);

                taskAPI.deleteTask(task).enqueue(new Callback<Task>() {
                    @Override
                    public void onResponse(Call<Task> call, Response<Task> response) {

                    }

                    @Override
                    public void onFailure(Call<Task> call, Throwable t) {

                    }
                });

                Intent intent = new Intent(AddEditTask.this, FreelancerPageActivity.class);
                intent.putExtra("loginIntent", freelancer_id);
                startActivity(intent);

                finish();
            }
        });

    }


    public void setEditableOperation(int validator) {
        Button btn_addTask = findViewById(R.id.btn_save);

        if (validator == -2) {
            btn_addTask.setText("Update");

            editText_taskDescription.setText(task_description);
            editText_beginDate.setText(task_beginDate);
            editText_endDate.setText(task_endDate);

            switch (task_type) {
                case "TO_DO":
                    taskStatusSpinner.setSelection(0);
                    break;
                case "IN_PROGRESS":
                    taskStatusSpinner.setSelection(1);
                    break;
                case "DONE":
                    taskStatusSpinner.setSelection(2);
                    break;
            }

        }

    }


    public void decideIfDeleteIsButtonEnabled(int deleteValidator) {

        Button btn_delete = findViewById(R.id.btn_delete);

        if (deleteValidator == -2) {
            btn_delete.setEnabled(false);
        } else if (deleteValidator == -1) {
            btn_delete.setEnabled(true);
        }

    }

    public boolean checkDateRegex(String task_beginDate, String task_endDate) {

        //doesn't apply for leap year exceptions
        //checks the differences for months with 30-31 days
        String dateRegex = "^\\d{4}-(02-(0[1-9]|[12][0-9])|(0[469]|11)-(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))$";

        if (task_beginDate.matches(dateRegex) && task_endDate.matches(dateRegex)) {
            return true;
        }

        return false;
    }


}