package com.example.schoolschedule.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolschedule.R;
import com.example.schoolschedule.adapters.ClassAdapter;
import com.example.schoolschedule.database.DatabaseHelper;
import com.example.schoolschedule.models.Class;

import java.util.List;

public class ClassesActivity extends AppCompatActivity {

    private EditText etClassName, etClassLevel, etStudentCount;
    private Button btnAddClass;
    private RecyclerView recyclerViewClasses;
    private DatabaseHelper db;
    private ClassAdapter adapter;
    private List<Class> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        db = new DatabaseHelper(this);

        etClassName = findViewById(R.id.etClassName);
        etClassLevel = findViewById(R.id.etClassLevel);
        etStudentCount = findViewById(R.id.etStudentCount);
        btnAddClass = findViewById(R.id.btnAddClass);
        recyclerViewClasses = findViewById(R.id.recyclerViewClasses);

        recyclerViewClasses.setLayoutManager(new LinearLayoutManager(this));

        loadClasses();

        btnAddClass.setOnClickListener(v -> addClass());
    }

    private void loadClasses() {
        classList = db.getAllClasses();
        adapter = new ClassAdapter(this, classList, db);
        recyclerViewClasses.setAdapter(adapter);
    }

    private void addClass() {
        String className = etClassName.getText().toString().trim();
        String classLevel = etClassLevel.getText().toString().trim();
        String studentCountStr = etStudentCount.getText().toString().trim();

        if (className.isEmpty() || classLevel.isEmpty() || studentCountStr.isEmpty()) {
            Toast.makeText(this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        int studentCount = Integer.parseInt(studentCountStr);
        Class newClass = new Class();
        newClass.setName(className);
        newClass.setLevel(classLevel);
        newClass.setStudentCount(studentCount);

        long result = db.addClass(newClass);
        if (result > 0) {
            Toast.makeText(this, "تم إضافة الفصل بنجاح", Toast.LENGTH_SHORT).show();
            etClassName.setText("");
            etClassLevel.setText("");
            etStudentCount.setText("");
            loadClasses();
        } else {
            Toast.makeText(this, "فشل إضافة الفصل", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClasses();
    }
}
