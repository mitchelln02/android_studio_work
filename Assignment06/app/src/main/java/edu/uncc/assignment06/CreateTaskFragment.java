package edu.uncc.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import edu.uncc.assignment06.databinding.FragmentCreateTaskBinding;


public class CreateTaskFragment extends Fragment {

    private String taskDate;

    public void setDate(String date) {
        this.taskDate = date;
    }


    public CreateTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCreateTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Task");
        if (taskDate != null) {
            binding.textViewDate.setText(taskDate);
        } else {
            binding.textViewDate.setText("N/A");
        }
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editTextTaskName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a valid task name", Toast.LENGTH_SHORT).show();
                } else if (binding.textViewDate.getText().toString().equals("N/A")) {
                    Toast.makeText(getActivity(), "Please select a valid date", Toast.LENGTH_SHORT).show();
                } else {
                    String name = binding.editTextTaskName.getText().toString();
                    String date = binding.textViewDate.getText().toString();
                    int checkedId = binding.radioGroup.getCheckedRadioButtonId();
                    RadioButton checkedButton = binding.getRoot().findViewById(checkedId);
                    String priority = checkedButton.getText().toString();
                    mListener.submitTask(new Task(name, date, priority));
                }
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelTask();
            }
        });
        binding.buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToDateSelect();
            }
        });
    }

    CreateTaskListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateTaskListener) context;
    }

    public interface CreateTaskListener {
        void cancelTask();
        void submitTask(Task task);
        void goToDateSelect();
    }
}