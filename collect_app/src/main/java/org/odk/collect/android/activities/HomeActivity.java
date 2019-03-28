package org.odk.collect.android.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.logic.FormDetails;
import org.odk.collect.android.preferences.AdminKeys;
import org.odk.collect.android.preferences.AdminPreferencesActivity;
import org.odk.collect.android.preferences.PreferencesActivity;
import org.odk.collect.android.tasks.DownloadFormsTask;
import org.odk.collect.android.utilities.ApplicationConstants;

import java.util.ArrayList;

public class HomeActivity extends CollectAbstractActivity {

    private SharedPreferences adminPreferences;
    private static final int PASSWORD_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolbar();
        downloadForm();
        adminPreferences = this.getSharedPreferences(
                AdminPreferencesActivity.ADMIN_PREFERENCES, 0);


        // enter data button. expects a result.
        LinearLayout enterDataButton = findViewById(R.id.inspect_school);
        enterDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(),
                            FormChooserList.class);
                    startActivity(i);
                }
            }
        });

        // send data button. expects a result.
        LinearLayout sendDataButton = findViewById(R.id.submit_form);
        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(),
                            InstanceUploaderList.class);
                    startActivity(i);
                }
            }
        });


        //View sent forms
        LinearLayout viewSentFormsButton = findViewById(R.id.view_filled_forms);
        viewSentFormsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(), InstanceChooserList.class);
                    i.putExtra(ApplicationConstants.BundleKeys.FORM_MODE,
                            ApplicationConstants.FormModes.VIEW_SENT);
                    startActivity(i);
                }
            }
        });

        LinearLayout viewIssuesButton = findViewById(R.id.view_issues);
        viewIssuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Collect.allowClick(getClass().getName())) {
                    Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                    intent.putExtra("url", "https://forum.opendatakit.org/t/17973");
                    startActivity(intent);
                }
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] options = {"About Us", "Tutorial Videos", "Logout"};
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0) {
                            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                        } else if(item == 1) {
                            Log.e("HomeActivity", "Videos screen called");
                        } else if(item == 2) {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setLayout(150, 150);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                wmlp.gravity = Gravity.TOP | Gravity.LEFT;
                wmlp.x = 0;   //x position
                wmlp.y = 100;   //y position

                dialog.show();
            }
        });
    }

    private void downloadForm(){
        ArrayList<FormDetails> filesToDownload = new ArrayList<>();
        FormDetails fm = new FormDetails(
                "School Inspection TEST",
                "http://142.93.214.208:8080/formXml?formId=build_School-Inspection-TEST_1549218200",
                null,
                "build_School-Inspection-TEST_1549218200",
                "",
                null,
                null,
                false,
                false);
        filesToDownload.add(fm);
        DownloadFormsTask downloadFormsTask = new DownloadFormsTask();
        downloadFormsTask.execute(filesToDownload);
    }
}

