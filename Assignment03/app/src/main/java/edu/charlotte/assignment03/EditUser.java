package edu.charlotte.assignment03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editName2 = (EditText) findViewById(R.id.editName2);
        EditText editEmail2 = (EditText) findViewById(R.id.editEmail2);
        RadioGroup roleSelect2 = (RadioGroup) findViewById(R.id.roleSelect2);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("USER")) {
            User user = getIntent().getParcelableExtra("USER");
            editName2.setText(user.getName());
            editEmail2.setText(user.getEmail());
            int roleId = user.getRole();
            if (roleId == 0) {
                roleSelect2.check(R.id.student2);
            } else if (roleId == 1) {
                roleSelect2.check(R.id.employee2);
            } else {
                roleSelect2.check(R.id.other2);
            }
        }

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = findViewById(R.id.editName2);
                EditText editEmail = findViewById(R.id.editEmail2);
                RadioGroup roleSelect = findViewById(R.id.roleSelect2);

                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                int role = roleSelect.getCheckedRadioButtonId();

                if (name.isEmpty()) {
                    Toast toast = Toast.makeText(EditUser.this, "Please enter a valid name", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (email.isEmpty()) {
                    Toast toast = Toast.makeText(EditUser.this, "Please enter a valid email", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (role == -1) {
                    Toast toast = Toast.makeText(EditUser.this, "Please select a valid role", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int roleId = -1;
                    if (role == R.id.student2) {
                        roleId = 0;
                    } else if (role == R.id.employee2) {
                        roleId = 1;
                    } else {
                        roleId = 2;
                    }
                    User user = new User(name, email, roleId);
                    Intent intent = new Intent();
                    intent.putExtra("USER", user);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}