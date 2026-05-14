package com.example.schoolschedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolschedule.activities.AddScheduleActivity;
import com.example.schoolschedule.activities.ClassesActivity;
import com.example.schoolschedule.activities.TeachersActivity;
import com.example.schoolschedule.activities.SubjectsActivity;
import com.example.schoolschedule.adapters.ScheduleAdapter;
import com.example.schoolschedule.database.DatabaseHelper;
import com.example.schoolschedule.models.Schedule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private DatabaseHelper db;
    private List<Schedule> scheduleList;
    private List<Schedule> filteredList;
    private Button btnAddSchedule, btnClasses, btnTeachers, btnSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // إعداد قاعدة البيانات
        db = new DatabaseHelper(this);

        // إعداد العناصر
        recyclerView = findViewById(R.id.recyclerViewSchedule);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);
        btnClasses = findViewById(R.id.btnClasses);
        btnTeachers = findViewById(R.id.btnTeachers);
        btnSubjects = findViewById(R.id.btnSubjects);

        // إعداد RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // جلب البيانات
        loadSchedules();

        // إعداد المستمعات
        btnAddSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
            startActivity(intent);
        });

        btnClasses.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClassesActivity.class);
            startActivity(intent);
        });

        btnTeachers.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TeachersActivity.class);
            startActivity(intent);
        });

        btnSubjects.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubjectsActivity.class);
            startActivity(intent);
        });
    }

    private void loadSchedules() {
        scheduleList = db.getAllSchedules();
        filteredList = new ArrayList<>(scheduleList);
        adapter = new ScheduleAdapter(this, filteredList, db);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSchedules();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return true;
    }

    private void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(scheduleList);
        } else {
            for (Schedule schedule : scheduleList) {
                if (schedule.getClassName().toLowerCase().contains(text.toLowerCase()) ||
                    schedule.getTeacherName().toLowerCase().contains(text.toLowerCase()) ||
                    schedule.getSubjectName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(schedule);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_all) {
            db.deleteAllSchedules();
            loadSchedules();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
