package edu.uncc.assignment06;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksListener, CreateTaskFragment.CreateTaskListener, SelectTaskDateFragment.SelectTaskDateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, new TasksFragment(), "tasks-fragment")
                .commit();
    }

    @Override
    public void goToCreateTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateTaskFragment(), "create-task-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelTask() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitTask(Task task) {
        Log.d("DEMO", "TASK CREATED - " + task.toString());
        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().findFragmentByTag("tasks-fragment");
        tasksFragment.addTask(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToDateSelect() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectTaskDateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelDate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitDate(String date) {
        Log.d("DEMO", "Date: " + date);
        CreateTaskFragment createTaskFragment = (CreateTaskFragment) getSupportFragmentManager().findFragmentByTag("create-task-fragment");
        createTaskFragment.setDate(date);
        getSupportFragmentManager().popBackStack();
    }
}