package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test1.database.Database;
import com.example.test1.model.Job;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText eName, eContent, eDate;
    private Spinner sp;
    private RadioButton rOne, rGroup;
    private Button btUpdate, btDelete, btBack;
    private Job job;
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btUpdate.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btBack.setOnClickListener(this);
        eDate.setOnClickListener(this);
        db = new Database(this);
        Intent intent = getIntent();
        job = (Job) intent.getSerializableExtra("job");
        eName.setText(job.getName());
        eContent.setText(job.getContent());
        String[] date = job.getFinishDate().trim().split("-");
        String d = date[2]+"/"+date[1]+"/"+date[0];
        eDate.setText(d);
        int p = 0;
        for(int i = 0; i< sp.getCount();i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(job.getStatus())){
                p = i;
                break;
            }
        }
        sp.setSelection(p);
        boolean isCollab = job.isCollaborate();
        if(isCollab){
            rOne.setChecked(true);
            rGroup.setChecked(false);
        }
        else{
            rOne.setChecked(false);
            rGroup.setChecked(true);
        }
    }

    private void initView() {
        eName = findViewById(R.id.eName);
        eContent = findViewById(R.id.eContent);
        eDate = findViewById(R.id.eDate);
        sp = findViewById(R.id.spinner);
        rOne = findViewById(R.id.radio_one);
        rGroup = findViewById(R.id.radio_group);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);
        btBack = findViewById(R.id.btBack);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.status)));
    }

    @Override
    public void onClick(View v) {
        if(v==eDate){
            final Calendar c = Calendar.getInstance();
            int year=c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date="";
                    if(dayOfMonth < 10){
                        if(month>8){
                            date="0"+dayOfMonth+"/"+(month+1)+"/"+year;
                        }
                        else{
                            date="0"+dayOfMonth+"/0"+(month+1)+"/"+year;
                        }
                    }
                    else{
                        if(month>8){
                            date=dayOfMonth+"/"+(month+1)+"/"+year;
                        }
                        else{
                            date=dayOfMonth+"/0"+(month+1)+"/"+year;
                        }
                    }
                    eDate.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(v==btBack){
            finish();
        }
        if(v==btUpdate){
            String name = eName.getText().toString();
            String content = eContent.getText().toString();
            String date = eDate.getText().toString();
            String status = sp.getSelectedItem().toString();
            boolean collab=true;
            boolean collabone =rOne.isChecked();
            if(collabone==false) collab = false;
            if(name.isEmpty()) eName.setError("Không được để trống");
            if(content.isEmpty()) eContent.setError("Không được để trống");
            if(date.isEmpty()) eDate.setError("Không được để trống");
            if(!name.isEmpty() && !content.isEmpty() && !date.isEmpty()){
                Job job1 = new Job(job.getId(),name, content, date, status, collab);
                db.updateJob(job1);
                Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
        if(v==btDelete){
            int id = job.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setMessage("Ban co chac muon xoa "+job.getName()+" khong?");
            builder.setIcon(R.drawable.baseline_delete_24);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteJobById(id);
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            finish();
        }
    }
}