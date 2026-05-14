package com.example.schoolschedule.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolschedule.R;
import com.example.schoolschedule.adapters.SubjectAdapter;
import com.example.schoolschedule.database.DatabaseHelper;
import com.example.schoolschedule.models.Subject;

import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    private EditText etSubjectName, etSubjectCode, etSubjectDescription;
    private Button btnAddSubject;
    private RecyclerView recyclerViewSubjects;
    private DatabaseHelper db;
    private SubjectAdapter adapter;
    private List<Subject> subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        db = new DatabaseHelper(this);

        etSubjectName = findViewById(R.id.etSubjectName);
        etSubjectCode = findViewById(R.id.etSubjectCode);
        etSubjectDescription = findViewById(R.id.etSubjectDescription);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        recyclerViewSubjects = findViewById(R.id.recyclerViewSubjects);

        recyclerViewSubjects.setLayoutManager(new LinearLayoutManager(this));

        loadSubjects();

        btnAddSubject.setOnClickListener(v -> addSubject());
    }

    private void loadSubjects() {
        subjectList = db.getAllSubjects();
        adapter = new SubjectAdapter(this, subjectList, db);
        recyclerViewSubjects.setAdapter(adapter);
    }

    private void addSubject() {
        String name = etSubjectName.getText().toString().trim();
        String code = etSubjectCode.getText().toString().trim();
        String description = etSubjectDescription.getText().toString().trim();

        if (name.isEmpty() || code.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        Subject subject = new Subject();
        subject.setName(name);
        subject.setCode(code);
        subject.setDescription(description);

        long result = db.addSubject(subject);
        if (result > 0) {
            Toast.makeText(this, "تم إضافة المادة بنجاح", Toast.LENGTH_SHORT).show();
            etSubjectName.setText("");
            etSubjectCode.setText("");
            etSubjectDescription.setText("");
            loadSubjects();
        } else {
            Toast.makeText(this, "فشل إضافة المادة", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSubjects();
    }
}
