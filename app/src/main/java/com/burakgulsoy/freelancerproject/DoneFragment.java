package com.burakgulsoy.freelancerproject;

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


public class DoneFragment extends Fragment {

    List<Task> taskList;
    private int freelancer_id;

    public DoneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        freelancer_id = this.getArguments().getInt("freelancerid");
        fillTaskList(freelancer_id);
        Log.d("done fragment received freelancer_id:", String.valueOf(freelancer_id));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("");

        return inflater.inflate(R.layout.fragment_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

                Log.d("done fragment, size of all tasklist", String.valueOf(taskList.size()));

                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getFreelancer_id() == freelancer_id && taskList.get(i).getTask_type().equals(TASK_STATUS.DONE.toString())) {
                        taskListWithCorrectFreelancerId.add(taskList.get(i));
                    }
                }
                Log.d("done tasklist size: ", String.valueOf(taskListWithCorrectFreelancerId.size()));

                recyclerView = getView().findViewById(R.id.lv_doneList);

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