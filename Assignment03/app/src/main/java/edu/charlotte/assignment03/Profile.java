package edu.charlotte.assignment03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    private final String[] roles = {"Student", "Employee", "Other"};
    User user;

    ActivityResultLauncher<Intent> editUserForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getParcelableExtra("USER") != null) {
                user = (User) result.getData().getParcelableExtra("USER");
                TextView name = findViewById(R.id.profileName);
                TextView email = findViewById(R.id.profileEmail);
                TextView role = findViewById(R.id.profileRole);
                name.setText("Name: " + user.getName());
                email.setText("Email: " + user.getEmail());
                role.setText(("Role: " + roles[user.getRole()]));
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView name = findViewById(R.id.profileName);
        TextView email = findViewById(R.id.profileEmail);
        TextView role = findViewById(R.id.profileRole);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra("USER")) {
            user = getIntent().getParcelableExtra("USER");
            name.setText("Name: " + user.getName());
            email.setText("Email: " + user.getEmail());
            role.setText(("Role: " + roles[user.getRole()]));

        }
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditUser.class);
                intent.putExtra("USER", user);
                editUserForResult.launch(intent);

            }
        });
    }
}