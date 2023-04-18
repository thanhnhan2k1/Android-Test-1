package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp;
    private EditText eName, eContent, eDate;
    private RadioButton one, group;
    private Button btAdd, btCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spinner);
        eName = findViewById(R.id.eName);
        eContent = findViewById(R.id.eContent);
        eDate = findViewById(R.id.eDate);
        one = findViewById(R.id.radio_one);
        group = findViewById(R.id.radio_group);
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.status)));
    }

    @Override
    public void onClick(View v) {
        if(v==eDate){
            final Calendar c = Calendar.getInstance();
            int year=c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        if(v==btCancel){
            finish();
        }
        if(v==btAdd){
            String name = eName.getText().toString();
            String content = eContent.getText().toString();
            String date = eDate.getText().toString();
            String status = sp.getSelectedItem().toString();
            boolean collab=true;
            boolean collabone =one.isChecked();
            if(collabone==false) collab = false;
            Database db = new Database(this);
            if(name.isEmpty()) eName.setError("Không được để trống");
            if(content.isEmpty()) eContent.setError("Không được để trống");
            if(date.isEmpty()) eDate.setError("Không được để trống");
            if(!name.isEmpty() && !content.isEmpty() && !date.isEmpty()){
                Job job = new Job(name, content, date, status, collab);
                db.addJob(job);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }
}