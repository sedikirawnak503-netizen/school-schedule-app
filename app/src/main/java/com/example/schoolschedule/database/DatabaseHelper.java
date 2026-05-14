package com.example.schoolschedule.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.schoolschedule.models.Class;
import com.example.schoolschedule.models.Schedule;
import com.example.schoolschedule.models.Subject;
import com.example.schoolschedule.models.Teacher;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "school_schedule.db";
    private static final int DATABASE_VERSION = 1;

    // جداول قاعدة البيانات
    private static final String TABLE_SCHEDULE = "schedule";
    private static final String TABLE_CLASS = "class";
    private static final String TABLE_TEACHER = "teacher";
    private static final String TABLE_SUBJECT = "subject";

    // الأعمدة الشاملة
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_SPECIALIZATION = "specialization";
    private static final String KEY_CODE = "code";
    private static final String KEY_DESCRIPTION = "description";

    // أعمدة جدول الجداول
    private static final String KEY_CLASS_NAME = "class_name";
    private static final String KEY_TEACHER_NAME = "teacher_name";
    private static final String KEY_SUBJECT_NAME = "subject_name";
    private static final String KEY_DAY = "day";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_LOCATION = "location";

    // أعمدة جدول الفصول
    private static final String KEY_LEVEL = "level";
    private static final String KEY_STUDENT_COUNT = "student_count";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // إنشاء جدول الجداول
        String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + TABLE_SCHEDULE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CLASS_NAME + " TEXT,"
                + KEY_TEACHER_NAME + " TEXT,"
                + KEY_SUBJECT_NAME + " TEXT,"
                + KEY_DAY + " TEXT,"
                + KEY_START_TIME + " TEXT,"
                + KEY_END_TIME + " TEXT,"
                + KEY_LOCATION + " TEXT)";
        db.execSQL(CREATE_SCHEDULE_TABLE);

        // إنشاء جدول الفصول
        String CREATE_CLASS_TABLE = "CREATE TABLE " + TABLE_CLASS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_LEVEL + " TEXT,"
                + KEY_STUDENT_COUNT + " INTEGER)";
        db.execSQL(CREATE_CLASS_TABLE);

        // إنشاء جدول المعلمين
        String CREATE_TEACHER_TABLE = "CREATE TABLE " + TABLE_TEACHER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_SPECIALIZATION + " TEXT)";
        db.execSQL(CREATE_TEACHER_TABLE);

        // إنشاء جدول المواد
        String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_CODE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT)";
        db.execSQL(CREATE_SUBJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        onCreate(db);
    }

    // عمليات جدول الجداول
    public long addSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLASS_NAME, schedule.getClassName());
        values.put(KEY_TEACHER_NAME, schedule.getTeacherName());
        values.put(KEY_SUBJECT_NAME, schedule.getSubjectName());
        values.put(KEY_DAY, schedule.getDay());
        values.put(KEY_START_TIME, schedule.getStartTime());
        values.put(KEY_END_TIME, schedule.getEndTime());
        values.put(KEY_LOCATION, schedule.getLocation());
        return db.insert(TABLE_SCHEDULE, null, values);
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCHEDULE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                schedule.setClassName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLASS_NAME)));
                schedule.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEACHER_NAME)));
                schedule.setSubjectName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_SUBJECT_NAME)));
                schedule.setDay(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DAY)));
                schedule.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_START_TIME)));
                schedule.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_END_TIME)));
                schedule.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION)));
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return schedules;
    }

    public Schedule getScheduleById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCHEDULE, null, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        Schedule schedule = null;
        if (cursor.moveToFirst()) {
            schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
            schedule.setClassName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLASS_NAME)));
            schedule.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_TEACHER_NAME)));
            schedule.setSubjectName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_SUBJECT_NAME)));
            schedule.setDay(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DAY)));
            schedule.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_START_TIME)));
            schedule.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_END_TIME)));
            schedule.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION)));
        }
        cursor.close();
        return schedule;
    }

    public int updateSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLASS_NAME, schedule.getClassName());
        values.put(KEY_TEACHER_NAME, schedule.getTeacherName());
        values.put(KEY_SUBJECT_NAME, schedule.getSubjectName());
        values.put(KEY_DAY, schedule.getDay());
        values.put(KEY_START_TIME, schedule.getStartTime());
        values.put(KEY_END_TIME, schedule.getEndTime());
        values.put(KEY_LOCATION, schedule.getLocation());
        return db.update(TABLE_SCHEDULE, values, KEY_ID + "=?", new String[]{String.valueOf(schedule.getId())});
    }

    public int deleteSchedule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SCHEDULE, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteAllSchedules() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE, null, null);
    }

    // عمليات جدول الفصول
    public long addClass(Class schoolClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, schoolClass.getName());
        values.put(KEY_LEVEL, schoolClass.getLevel());
        values.put(KEY_STUDENT_COUNT, schoolClass.getStudentCount());
        return db.insert(TABLE_CLASS, null, values);
    }

    public List<Class> getAllClasses() {
        List<Class> classes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLASS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Class schoolClass = new Class();
                schoolClass.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                schoolClass.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                schoolClass.setLevel(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LEVEL)));
                schoolClass.setStudentCount(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_STUDENT_COUNT)));
                classes.add(schoolClass);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classes;
    }

    public int updateClass(Class schoolClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, schoolClass.getName());
        values.put(KEY_LEVEL, schoolClass.getLevel());
        values.put(KEY_STUDENT_COUNT, schoolClass.getStudentCount());
        return db.update(TABLE_CLASS, values, KEY_ID + "=?", new String[]{String.valueOf(schoolClass.getId())});
    }

    public int deleteClass(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CLASS, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    // عمليات جدول المعلمين
    public long addTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, teacher.getName());
        values.put(KEY_EMAIL, teacher.getEmail());
        values.put(KEY_PHONE, teacher.getPhone());
        values.put(KEY_SPECIALIZATION, teacher.getSpecialization());
        return db.insert(TABLE_TEACHER, null, values);
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHER, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Teacher teacher = new Teacher();
                teacher.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                teacher.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                teacher.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)));
                teacher.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)));
                teacher.setSpecialization(cursor.getString(cursor.getColumnIndexOrThrow(KEY_SPECIALIZATION)));
                teachers.add(teacher);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return teachers;
    }

    public int updateTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, teacher.getName());
        values.put(KEY_EMAIL, teacher.getEmail());
        values.put(KEY_PHONE, teacher.getPhone());
        values.put(KEY_SPECIALIZATION, teacher.getSpecialization());
        return db.update(TABLE_TEACHER, values, KEY_ID + "=?", new String[]{String.valueOf(teacher.getId())});
    }

    public int deleteTeacher(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TEACHER, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    // عمليات جدول المواد
    public long addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, subject.getName());
        values.put(KEY_CODE, subject.getCode());
        values.put(KEY_DESCRIPTION, subject.getDescription());
        return db.insert(TABLE_SUBJECT, null, values);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SUBJECT, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                subject.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                subject.setCode(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CODE)));
                subject.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)));
                subjects.add(subject);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return subjects;
    }

    public int updateSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, subject.getName());
        values.put(KEY_CODE, subject.getCode());
        values.put(KEY_DESCRIPTION, subject.getDescription());
        return db.update(TABLE_SUBJECT, values, KEY_ID + "=?", new String[]{String.valueOf(subject.getId())});
    }

    public int deleteSubject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SUBJECT, KEY_ID + "=?", new String[]{String.valueOf(id)});
    }
}
