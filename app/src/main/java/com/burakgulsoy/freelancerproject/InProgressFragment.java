package com.burakgulsoy.freelancerproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burakgulsoy.freelancerproject.models.Task;
import com.burakgulsoy.freelancerproject.retrofit.RetrofitService;
import com.burakgulsoy.freelancerproject.retrofit.modelAPIs.TaskAPI;
import com.burakgulsoy.freelancerproject.supportive_classes.RecycleViewAdapter;
import com.burakgulsoy.freelancerproject.supportive_classes.TASK_STATUS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InProgressFragment extends Fragment {



    List<Task> taskList;
    private int freelancer_id;

    public InProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        freelancer_id = this.getArguments().getInt("freelancerid");
        fillTaskList(freelancer_id);
        Log.d("in progress fragment received freelancer_id: ", String.valueOf(freelancer_id));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("");

        return inflater.inflate(R.layout.fragment_in_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Intent intentFromLogin = getActivity().getIntent();
//        int freelancer_id = intentFromLogin.getIntExtra("loginIntent", -1);


//        // receives intent (freelancer_id) from login page and directs to AddEditTask
//        Intent intent = new Intent(getActivity(), AddEditTask.class);
//        intent.putExtra("loginIntent", freelancer_id);

    }

    public void fillTaskList(int freelancer_id) {

        RetrofitService retrofitService = new RetrofitService();
        TaskAPI taskAPI = retrofitService.getRetrofit().create(TaskAPI.class);

        taskAPI.getAll().enqueue(new Callback<List<Task>>() {
            RecyclerView recyclerView;
            RecyclerView.Adapter adapter;
            RecyclerView.LayoutManager layoutManager;

            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                taskList = response.body();
                List<Task> taskListWithCorrectFreelancerId = new ArrayList<Task>();

                Log.d("size", String.valueOf(taskList.size()));

                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getFreelancer_id() == freelancer_id && taskList.get(i).getTask_type().equals(TASK_STATUS.IN_PROGRESS.toString())) {
                        taskListWithCorrectFreelancerId.add(taskList.get(i));
                    }
                }
                Log.d("size", String.valueOf(taskListWithCorrectFreelancerId.size()));

                recyclerView = getView().findViewById(R.id.lv_inProgressList);

                recyclerView.setHasFixedSize(true);

                //get activity hata verirse this dene, .class dene
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