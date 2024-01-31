package com.example.womessafteyf;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class RegisterNumberActivity extends AppCompatActivity {

    TextInputEditText numberEdit1;
    TextInputEditText numberEdit2;
    TextInputEditText numberEdit3;
    TextInputEditText numberEdit4;
    TextInputEditText numberEdit5;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        numberEdit1 = findViewById(R.id.numberEdit1);
        numberEdit2 = findViewById(R.id.numberEdit2);
        numberEdit3 = findViewById(R.id.numberEdit3);
        numberEdit4 = findViewById(R.id.numberEdit4);
        numberEdit5 = findViewById(R.id.numberEdit5);

        databaseHelper = new MyDatabaseHelper(this);
    }

    public void saveNumber(View view) {
        String numberString1 = Objects.requireNonNull(numberEdit1.getText()).toString();
        String numberString2 = Objects.requireNonNull(numberEdit2.getText()).toString();
        String numberString3 = Objects.requireNonNull(numberEdit3.getText()).toString();
        String numberString4 = Objects.requireNonNull(numberEdit4.getText()).toString();
        String numberString5 = Objects.requireNonNull(numberEdit5.getText()).toString();

        if (validateNumber(numberString1) && validateNumber(numberString2) &&
                validateNumber(numberString3) && validateNumber(numberString4) &&
                validateNumber(numberString5)) {

            long result1 = databaseHelper.insertUserData(numberString1);
            long result2 = databaseHelper.insertUserData(numberString2);
            long result3 = databaseHelper.insertUserData(numberString3);
            long result4 = databaseHelper.insertUserData(numberString4);
            long result5 = databaseHelper.insertUserData(numberString5);

            if (result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1) {
                Toast.makeText(this, "Data saved to database", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter valid numbers!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateNumber(String number) {
        return number.length() == 11;
    }

    private static class MyDatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "mydatabase.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "user_data";
        private static final String COLUMN_NUMBER = "number";

        public MyDatabaseHelper(RegisterNumberActivity context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NUMBER + " TEXT PRIMARY KEY)";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Implement if needed
        }

        public long insertUserData(String number) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NUMBER, number);
            return db.insert(TABLE_NAME, null, values);
        }
    }
}


