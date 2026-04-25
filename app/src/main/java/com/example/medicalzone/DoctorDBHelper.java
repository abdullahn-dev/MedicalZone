package com.example.medicalzone;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DoctorDBHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "MedicalZone.db";
    private static final int DATABASE_VERSION = 2;

    // Table: Doctors (Authentication)
    private static final String TABLE_DOCTORS = "doctors";
    private static final String COLUMN_DOCTOR_ID = "id";
    private static final String COLUMN_DOCTOR_EMAIL = "email";
    private static final String COLUMN_DOCTOR_PASSWORD = "password";

    // Table: Doctor Profiles
    private static final String TABLE_DOCTOR_PROFILES = "doctor_profiles";
    private static final String COLUMN_PROFILE_ID = "id";
    private static final String COLUMN_PROFILE_NAME = "name";
    private static final String COLUMN_PROFILE_SPECIALIZATION = "specialization";
    private static final String COLUMN_PROFILE_YEARS_OF_EXPERIENCE = "years_of_experience";
    private static final String COLUMN_PROFILE_IMAGE = "profile_image";

    // Constructor to initialize the database helper
    public DoctorDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create doctors table
        String createDoctorsTable = "CREATE TABLE " + TABLE_DOCTORS + " ("
                + COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DOCTOR_EMAIL + " TEXT UNIQUE, "
                + COLUMN_DOCTOR_PASSWORD + " TEXT)";
        db.execSQL(createDoctorsTable);

        // Create doctor_profiles table
        String createProfilesTable = "CREATE TABLE " + TABLE_DOCTOR_PROFILES + " ("
                + COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PROFILE_NAME + " TEXT, "
                + COLUMN_PROFILE_SPECIALIZATION + " TEXT, "
                + COLUMN_PROFILE_YEARS_OF_EXPERIENCE + " INTEGER, "
                + COLUMN_PROFILE_IMAGE + " TEXT)";
        db.execSQL(createProfilesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR_PROFILES);
        onCreate(db);
    }

    // Register a doctor (email, username, password, and specialization)
    public boolean registerDoctor(String email, String username, String password, String specialization) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the doctor with this email already exists
        if (isDoctorExists(email)) {
            return false; // Doctor with this email already exists
        }

        // Insert doctor's authentication data into the doctors table
        ContentValues doctorValues = new ContentValues();
        doctorValues.put(COLUMN_DOCTOR_EMAIL, email);
        doctorValues.put(COLUMN_DOCTOR_PASSWORD, password);

        long doctorInsertResult = db.insert(TABLE_DOCTORS, null, doctorValues);

        if (doctorInsertResult != -1) {
            // After inserting doctor's credentials, insert the profile data
            ContentValues profileValues = new ContentValues();
            profileValues.put(COLUMN_PROFILE_NAME, username);
            profileValues.put(COLUMN_PROFILE_SPECIALIZATION, specialization);

            long profileInsertResult = db.insert(TABLE_DOCTOR_PROFILES, null, profileValues);

            // Return true if both doctor and profile are successfully inserted
            return profileInsertResult != -1;
        }

        // Return false if doctor registration failed
        return false;
    }

    // Check if a doctor with this email already exists
    public boolean isDoctorExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_DOCTOR_ID, COLUMN_DOCTOR_EMAIL};
        String selection = COLUMN_DOCTOR_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_DOCTORS, columns, selection, selectionArgs, null, null, null);
            return cursor.getCount() > 0; // If cursor contains data, doctor exists
        } finally {
            if (cursor != null) {
                cursor.close(); // Make sure to close the cursor to avoid memory leaks
            }
        }
    }

    // Authenticate the doctor (email and password verification)
    public boolean authenticateDoctor(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DOCTORS + " WHERE "
                + COLUMN_DOCTOR_EMAIL + "=? AND "
                + COLUMN_DOCTOR_PASSWORD + "=?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{email, password});
            return cursor.moveToFirst(); // If the cursor moves to the first row, authentication is successful
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor to prevent memory leaks
            }
        }
    }

    // Insert or Update doctor profile (name, specialization, years of experience, profile image URI)
    public boolean insertOrUpdateProfile(String name, String specialization, int yearsOfExperience, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if a profile for this doctor already exists (using name or another identifier if needed)
        String selection = COLUMN_PROFILE_NAME + "=?";
        String[] selectionArgs = {name};

        // Query to check if the profile exists
        Cursor cursor = null;
        long result = -1;
        try {
            cursor = db.query(TABLE_DOCTOR_PROFILES,
                    new String[] {COLUMN_PROFILE_ID, COLUMN_PROFILE_NAME, COLUMN_PROFILE_SPECIALIZATION},
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            ContentValues profileValues = new ContentValues();
            profileValues.put(COLUMN_PROFILE_NAME, name);
            profileValues.put(COLUMN_PROFILE_SPECIALIZATION, specialization);
            profileValues.put(COLUMN_PROFILE_YEARS_OF_EXPERIENCE, yearsOfExperience); // Add the years_of_experience column
            profileValues.put(COLUMN_PROFILE_IMAGE, imageUri); // Assuming you are storing the image URI

            if (cursor != null && cursor.getCount() > 0) {
                // Profile exists, update it
                cursor.moveToFirst(); // Move to the first row
                @SuppressLint("Range") int profileId = cursor.getInt(cursor.getColumnIndex(COLUMN_PROFILE_ID)); // Access the profile ID

                // Update the profile for this doctor
                result = db.update(TABLE_DOCTOR_PROFILES, profileValues, COLUMN_PROFILE_ID + "=?", new String[]{String.valueOf(profileId)});
            } else {
                // Profile doesn't exist, insert a new one
                result = db.insert(TABLE_DOCTOR_PROFILES, null, profileValues);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor to prevent memory leaks
            }
        }

        return result != -1; // Return true if insert or update was successful, false otherwise
    }
}
