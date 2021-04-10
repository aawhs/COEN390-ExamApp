package edu.coen390.androidapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.examapp.R;

import edu.coen390.androidapp.Controller.DatabaseHelper;
import edu.coen390.androidapp.Model.Course;
import edu.coen390.androidapp.Model.Professor;

public class ProfessorActivity extends AppCompatActivity {
    public static final String COURSE_INTENT = "COURSE";
    private static final String TAG = "ProfessorActivity";

    private Course course;
    private Button generateReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra(COURSE_INTENT);
        Log.d(TAG, "After getIntent " + course.toString());

        setupUI();
    }

    private void setupUI() {
        generateReportButton = findViewById(R.id.professorGenerateReportButton);
        generateReportButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfessorActivity.this, ProfessorReportActivity.class);
            intent.putExtra(COURSE_INTENT, course);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}