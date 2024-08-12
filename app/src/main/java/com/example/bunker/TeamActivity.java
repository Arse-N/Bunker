package com.example.bunker;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bunker.common.fileio.JsonUtil;
import com.example.bunker.common.model.Teammate;
import com.example.bunker.common.service.TeamAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class TeamActivity extends BaseActivity implements TeamAdapter.OnItemRemoveListener {

    private RecyclerView recyclerView;

    private Dialog addTeammateDialog;
    private Button add, start, finalAdd;
    private TextInputEditText nameEditText, emailEditText;
    private TextInputLayout nameTextInputLayout, emailTextInputLayout;

    private ArrayList<Teammate> teammatesList;
    private TeamAdapter teamAdapter;

    private TextView headerTitle;

    private static final String FILE_NAME = "team.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        teammatesList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        headerTitle = findViewById(R.id.header_title);
        headerTitle.setText(R.string.teammates);
        teammatesList = JsonUtil.readFromJson(this);
        if (teammatesList == null) {
            teammatesList = new ArrayList<>();
        }
        teamAdapter = new TeamAdapter(teammatesList, this);

        recyclerView = findViewById(R.id.teammates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(teamAdapter);

        add = findViewById(R.id.add_button);
        start = findViewById(R.id.start);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeDialog();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teammatesList.size() >= 4) {
                    Intent intent = new Intent(TeamActivity.this, CardFlip.class);
                    startActivity(intent);
                } else {
                    showToast("minimum number of players is 4!");
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_team;
    }

    private void initializeDialog() {
        addTeammateDialog = new Dialog(TeamActivity.this);
        addTeammateDialog.setContentView(R.layout.add_teammate);
        CardView dialog = addTeammateDialog.findViewById(R.id.add_teammate_dialog);
        addTeammateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setBackgroundResource(R.drawable.dialog_background);
        addTeammateDialog.setCancelable(false);
        ImageView addItemDialogX = addTeammateDialog.findViewById(R.id.dialog_X);
        finalAdd = addTeammateDialog.findViewById(R.id.add_button);
        nameEditText = addTeammateDialog.findViewById(R.id.name_text);
//        emailEditText = addTeammateDialog.findViewById(R.id.email_text);
        nameTextInputLayout = addTeammateDialog.findViewById(R.id.name_text_input_layout);
//        emailTextInputLayout = addTeammateDialog.findViewById(R.id.email_text_input_layout);

        InputFilter[] inputFilters = new InputFilter[]{new InputFilter.LengthFilter(100)};
        nameEditText.setFilters(inputFilters);
//        emailEditText.setFilters(inputFilters);
        addTeammateDialog.show();

        addItemDialogX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeammateDialog.dismiss();
            }
        });

        finalAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    addTeammate(nameEditText.getText().toString());
                    addTeammateDialog.dismiss();
                }
            }
        });
        addTextWatchers();
    }

    private void addTeammate(String name) {
        teammatesList.add(new Teammate(name));
        teamAdapter.notifyItemInserted(teammatesList.size() - 1);
        JsonUtil.writeToJson(this, teammatesList);

    }


    @Override
    public void onItemRemove(int position) {
        teammatesList.remove(position);
        teamAdapter.notifyItemRemoved(position);
        teamAdapter.notifyItemRangeChanged(position, teammatesList.size());
        JsonUtil.writeToJson(this, teammatesList);

    }

    private boolean validateForm() {
        boolean valid = true;

        String name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            nameTextInputLayout.setError("Name is required");
            valid = false;
        } else if (name.length() > 15) {
            nameTextInputLayout.setError("Name must be less than 15 characters");
            valid = false;
        } else {
            nameTextInputLayout.setError(null);
        }

//        String email = emailEditText.getText().toString().trim();
//        if (email.isEmpty()) {
//            emailTextInputLayout.setError("Email is required");
//            valid = false;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            emailTextInputLayout.setError("Invalid email address");
//            valid = false;
//        } else {
//            emailTextInputLayout.setError(null);
//        }

        return valid;
    }

    private void addTextWatchers() {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    nameTextInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        emailEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().trim().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
//                    emailTextInputLayout.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
    }

    private void showToast(String text){
        Toast.makeText(TeamActivity.this, text, Toast.LENGTH_LONG).show();
    }

}