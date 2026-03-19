package edu.uncc.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import edu.uncc.assignment06.databinding.FragmentTasksBinding;


public class TasksFragment extends Fragment {

    private ArrayList<Task> tasks = new ArrayList<>();
    private int taskIndex = 0;

    public void addTask(Task task) {
        tasks.add(task);
        // TODO: Sort tasks after adding
        sortTasksByDate();
    }

    public void sortTasksByDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                try {
                    Date d1 = formatter.parse(t1.getDate());
                    Date d2 = formatter.parse(t2.getDate());
                    return d2.compareTo(d1);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    return -1;
                }
            }
        });
    }

    public void deleteTask() {
        tasks.remove(taskIndex);
        if (!tasks.isEmpty()) {
            binding.textViewTasksCount.setText("You have " + tasks.size() + " tasks");
            showPrevTask();
        } else {
            binding.textViewTasksCount.setText("You have 0 tasks");
            binding.cardViewTask.setVisibility(View.INVISIBLE);
        }
    }

    public void displayTask() {
        int taskNumber = taskIndex + 1;
        Task currentTask = tasks.get(taskIndex);
        binding.textViewTaskName.setText(currentTask.getName());
        binding.textViewTaskDate.setText(currentTask.getDate());
        binding.textViewTaskPriority.setText(currentTask.getPriority());
        binding.textViewTaskOutOf.setText("Task " + taskNumber + " out of " + tasks.size());
    }

    public void showNextTask() {
        taskIndex = (taskIndex + 1) % tasks.size();
        displayTask();
    }

    public void showPrevTask() {
        taskIndex = (taskIndex - 1 + tasks.size()) % tasks.size();;
        displayTask();
    }


    public TasksFragment() {
        // Required empty public constructor
    }


    FragmentTasksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tasks");
        taskIndex = 0;

        if (tasks != null && !tasks.isEmpty()) {
            binding.textViewTasksCount.setText("You have " + tasks.size() + " tasks");
            displayTask();

        } else {
            binding.textViewTasksCount.setText("You have 0 tasks");
            binding.cardViewTask.setVisibility(View.INVISIBLE);
        }
        binding.buttonCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToCreateTask();
            }
        });
        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask();
            }
        });
        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextTask();
            }
        });
        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrevTask();
            }
        });
    }

    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;
    }

    public interface TasksListener {
        void goToCreateTask();
    }
}