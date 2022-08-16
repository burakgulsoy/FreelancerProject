package com.burakgulsoy.freelancerproject;

import com.burakgulsoy.freelancerproject.*;
import com.burakgulsoy.freelancerproject.models.Task;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.TaskAPI;
import com.burakgulsoy.freelancerproject.supportive_classes.RecycleViewAdapter;
import com.burakgulsoy.freelancerproject.supportive_classes.TASK_STATUS;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoListFragment extends Fragment {

    List<Task> taskList;
    private int freelancer_id;



    public ToDoListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        freelancer_id = this.getArguments().getInt("freelancerid");
        fillTaskList(freelancer_id);
        Log.d("received", String.valueOf(freelancer_id));



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("");

//        int freelancer_id = this.getArguments().getInt("freelancerid");
//        fillTaskList(freelancer_id);

//        Log.d("received", String.valueOf(freelancer_id));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_addNewTask = view.findViewById(R.id.btn_AddNewTask);

        // opens AddEditTask page to add new task
        btn_addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // without this intent operation page needs to be refreshed to get the data
                Intent intentFromLogin = getActivity().getIntent();
                int freelancer_id = intentFromLogin.getIntExtra("loginIntent", -1);

                // receives intent (freelancer_id) from login page and directs to AddEditTask
                Intent intent = new Intent(getActivity(), AddEditTask.class);
                intent.putExtra("loginIntent", freelancer_id);
                intent.putExtra("deleteValidator", -2);
                startActivity(intent);

//                getActivity().finish();
            }
        });




    }



    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public void fillTaskList(int freelancer_id) {

        RetrofitService retrofitService = new RetrofitService();
        TaskAPI taskAPI = retrofitService.getRetrofit().create(TaskAPI.class);

        taskAPI.getAll().enqueue(new Callback<List<Task>>() {


            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                taskList = response.body();
                List<Task> taskListWithCorrectFreelancerId = new ArrayList<Task>();

                Log.d("size", String.valueOf(taskList.size()));

                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getFreelancer_id() == freelancer_id && taskList.get(i).getTask_type().equals(TASK_STATUS.TO_DO.toString())) {
                        taskListWithCorrectFreelancerId.add(taskList.get(i));
                    }
                }
                Log.d("tasklist size for TODO LIST:", String.valueOf(taskListWithCorrectFreelancerId.size()));


                recyclerView = getView().findViewById(R.id.lv_toDoList);

                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

                adapter = new RecycleViewAdapter(taskListWithCorrectFreelancerId, getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });


    }
}