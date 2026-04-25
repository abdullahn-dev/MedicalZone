package com.example.medicalzone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PatientDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PatientsDB.db";
    private static final int DATABASE_VERSION = 1;

    // Patient Table
    private static final String TABLE_NAME = "Patients";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_AGE = "age";
    private static final String COL_GENDER = "gender";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_DISEASE = "disease";
    private static final String COL_PROFILE_IMAGE = "profile_image";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Appointments Table
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COL_APPOINTMENT_ID = "appointment_id";
    private static final String COL_APPOINTMENT_PATIENT_ID = "patient_id";
    private static final String COL_APPOINTMENT_DOCTOR_ID = "doctor_id";
    private static final String COL_APPOINTMENT_DATE = "appointment_date";
    private static final String COL_APPOINTMENT_TIME = "appointment_time";
    private static final String COL_APPOINTMENT_REASON = "reason";

    public PatientDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPatientsTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_AGE + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_EMAIL + " TEXT UNIQUE, " +
                COL_PHONE + " TEXT, " +
                COL_DISEASE + " TEXT, " +
                COL_PROFILE_IMAGE + " BLOB)";
        db.execSQL(createPatientsTable);

        String createAppointmentsTable = "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                COL_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_APPOINTMENT_PATIENT_ID + " INTEGER, " +
                COL_APPOINTMENT_DOCTOR_ID + " INTEGER, " +
                COL_APPOINTMENT_DATE + " TEXT, " +
                COL_APPOINTMENT_TIME + " TEXT, " +
                COL_APPOINTMENT_REASON + " TEXT)";
        db.execSQL(createAppointmentsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    // Register user
    public boolean registerUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Authenticate user
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query to select username and password from the table
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_USERNAME, COLUMN_PASSWORD},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        // Check if any record was found
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close(); // Close the cursor after use
        return isAuthenticated; // Return true if authenticated, otherwise false
    }

    // Insert a new patient record
    public boolean insertPatient(String name, String age, String gender, String email, String phone, String disease, byte[] profileImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        values.put(COL_GENDER, gender);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_DISEASE, disease);
        values.put(COL_PROFILE_IMAGE, profileImage);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Retrieve all patients
    public List<String> getAllPatients() {
        List<String> patientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_NAME + ", " + COL_AGE + ", " + COL_GENDER + ", " + COL_DISEASE + ", " + COL_PHONE + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String patientInfo = "Name: " + cursor.getString(0) +
                        ", Age: " + cursor.getString(1) +
                        ", Gender: " + cursor.getString(2) +
                        ", Disease: " + cursor.getString(3) +
                        ", Phone: " + cursor.getString(4);
                patientList.add(patientInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return patientList;
    }

    // Update a patient record
    public boolean updatePatient(int id, String name, String age, String gender, String email, String phone, String disease, byte[] profileImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        values.put(COL_GENDER, gender);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_DISEASE, disease);
        if (profileImage != null) {
            values.put(COL_PROFILE_IMAGE, profileImage);
        }

        int result = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Delete a patient record
    public boolean deletePatient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Retrieve patient profile image
    public byte[] getPatientProfileImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_PROFILE_IMAGE + " FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            byte[] image = cursor.getBlob(0);
            cursor.close();
            return image;
        }
        return null;
    }
}
