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
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.uncc.assignment06.databinding.FragmentSelectTaskDateBinding;

public class SelectTaskDateFragment extends Fragment {

    public SelectTaskDateFragment() {
        // Required empty public constructor
    }

    FragmentSelectTaskDateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectTaskDateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Task Date");
        final Date[] selectedDate = {new Date(binding.calendarView.getDate())};
        binding.calendarView.setMaxDate(System.currentTimeMillis());
        binding.calendarView.setDate(System.currentTimeMillis());

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Calendar selectedCal = Calendar.getInstance();
                selectedCal.set(year, month, day);
                selectedDate[0] = selectedCal.getTime();
                Log.d("CalendarView", "Date picked: " + selectedDate[0]);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelDate();
            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                String formattedDate = formatter.format(selectedDate[0]);
                mListener.submitDate(formattedDate);
            }
        });
    }
    SelectTaskDateListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectTaskDateListener) context;
    }

    public interface SelectTaskDateListener {
        void cancelDate();
        void submitDate(String date);
    }
}