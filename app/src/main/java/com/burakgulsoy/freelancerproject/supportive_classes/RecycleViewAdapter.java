package com.burakgulsoy.freelancerproject.supportive_classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.burakgulsoy.freelancerproject.AddEditTask;
import com.burakgulsoy.freelancerproject.R;
import com.burakgulsoy.freelancerproject.models.Task;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<Task> taskList;
    Context context;

    public RecycleViewAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_task, parent, false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //listeners and assigners

        holder.textView_ol_description.setText(taskList.get(position).getTask_description());
        holder.textView_ol_beginDate.setText(taskList.get(position).getTask_begin_date());
        holder.textView_ol_endDate.setText(taskList.get(position).getTask_end_date());
        holder.textView_ol_Status.setText(taskList.get(position).getTask_type());

        //editable
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEditTask.class);
                intent.putExtra("task_id", taskList.get(position).getTask_id());
                intent.putExtra("task_description", taskList.get(position).getTask_description());
                intent.putExtra("task_freelancerId", taskList.get(position).getFreelancer_id());
                intent.putExtra("task_type", taskList.get(position).getTask_type());
                intent.putExtra("task_beginDate", taskList.get(position).getTask_begin_date());
                intent.putExtra("task_endDate", taskList.get(position).getTask_end_date());

                intent.putExtra("loginIntent",taskList.get(position).getFreelancer_id());
                intent.putExtra("validator", -2);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //define views here for one_line_task.xml

        TextView textView_ol_descriptionLabel;
        TextView textView_ol_description;
        TextView textView_ol_taskStatusLabel;
        TextView textView_ol_Status;
        TextView textView_ol_beginDateLabel;
        TextView textView_ol_beginDate;
        TextView textView_ol_endDateLabel;
        TextView textView_ol_endDate;

        // editable
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_ol_descriptionLabel = itemView.findViewById(R.id.textView_ol_descriptionLabel);
            textView_ol_description = itemView.findViewById(R.id.textView_ol_description);
            textView_ol_taskStatusLabel = itemView.findViewById(R.id.textView_ol_taskStatusLabel);
            textView_ol_Status = itemView.findViewById(R.id.textView_ol_Status);
            textView_ol_beginDateLabel = itemView.findViewById(R.id.textView_ol_beginDateLabel);
            textView_ol_beginDate = itemView.findViewById(R.id.textView_ol_beginDate);
            textView_ol_endDateLabel = itemView.findViewById(R.id.textView_ol_endDateLabel);
            textView_ol_endDate = itemView.findViewById(R.id.textView_ol_endDate);

            // editable
            parentLayout = itemView.findViewById(R.id.oneLineTaskLayout);
        }
    }
}
