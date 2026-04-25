package com.example.medicalzone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AdminDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "medical_zone.db";
    private static final int DATABASE_VERSION = 1;

    // Table for Patients
    private static final String TABLE_PATIENTS = "patients";
    private static final String COLUMN_PATIENT_NAME = "name";

    // Table for Doctors
    private static final String TABLE_DOCTORS = "doctors";
    private static final String COLUMN_DOCTOR_NAME = "name";

    // SQL Query to Create Patients Table
    private static final String CREATE_TABLE_PATIENTS =
            "CREATE TABLE " + TABLE_PATIENTS + " ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_PATIENT_NAME + " TEXT NOT NULL"
                    + ");";

    // SQL Query to Create Doctors Table
    private static final String CREATE_TABLE_DOCTORS =
            "CREATE TABLE " + TABLE_DOCTORS + " ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_DOCTOR_NAME + " TEXT NOT NULL"
                    + ");";

    public AdminDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Tables for Patients and Doctors
        db.execSQL(CREATE_TABLE_PATIENTS);
        db.execSQL(CREATE_TABLE_DOCTORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        onCreate(db);
    }

    // -------------------------
    // Patient Methods
    // -------------------------

    public boolean addPatient(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_NAME, name);

        long result = db.insert(TABLE_PATIENTS, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<String> getAllPatients() {
        ArrayList<String> patientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PATIENT_NAME + " FROM " + TABLE_PATIENTS, null);

        if (cursor.moveToFirst()) {
            do {
                patientList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return patientList;
    }

    public void deletePatient(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATIENTS, COLUMN_PATIENT_NAME + "=?", new String[]{name});
        db.close();
    }

    // -------------------------
    // Doctor Methods
    // -------------------------

    public boolean addDoctor(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DOCTOR_NAME, name);

        long result = db.insert(TABLE_DOCTORS, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<String> getAllDoctors() {
        ArrayList<String> doctorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DOCTOR_NAME + " FROM " + TABLE_DOCTORS, null);

        if (cursor.moveToFirst()) {
            do {
                doctorList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return doctorList;
    }

    public void deleteDoctor(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCTORS, COLUMN_DOCTOR_NAME + "=?", new String[]{name});
        db.close();
    }
}
