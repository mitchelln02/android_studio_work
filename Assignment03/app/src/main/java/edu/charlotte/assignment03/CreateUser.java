package edu.charlotte.assignment03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Duration;

public class CreateUser extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        EditText editName = findViewById(R.id.editName);
        EditText editEmail = findViewById(R.id.editEmail);
        RadioGroup roleSelect = findViewById(R.id.roleSelect);

        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        int role = roleSelect.getCheckedRadioButtonId();

        if (name.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT);
            toast.show();
        } else if (email.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT);
            toast.show();
        } else if (role == -1) {
            Toast toast = Toast.makeText(this, "Please select a valid role", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            int roleId = -1;
            if (role == R.id.student) {
                roleId = 0;
            } else if (role == R.id.employee) {
                roleId = 1;
            } else {
                roleId = 2;
            }
            User user = new User(name, email, roleId);
            Intent intent = new Intent(CreateUser.this, Profile.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            finish();
        }
    }
}