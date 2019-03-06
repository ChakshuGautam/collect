package org.odk.collect.android.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.odk.collect.android.R;

public class LoginActivity extends CollectAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.login_submit);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                endLoginActivity();
            }
        });
    }

    private void endLoginActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
