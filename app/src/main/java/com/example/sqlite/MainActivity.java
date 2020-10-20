package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     EditText edtcomputername,edtcomputertype;
     Button btnadd,btndelete;
     ListView listView;
     List<Computer> allcomputers;
     ArrayList<String> computersname;
     MySqliteHandeler databaseHandeler;
     ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtcomputername = findViewById(R.id.edtcomputername);
        edtcomputertype = findViewById(R.id.edtcomputerType);
        btnadd = findViewById(R.id.btnadd);
        btndelete = findViewById(R.id.btndelete);
        listView = findViewById(R.id.listview);
        databaseHandeler = new MySqliteHandeler(MainActivity.this);
        allcomputers = databaseHandeler.getAllComputer();
        computersname = new ArrayList<>();

        if (allcomputers.size()>0){
            for (int i =0;i<allcomputers.size();i++){
                Computer computer = allcomputers.get(i);
                computersname.add(computer.getComputername() + " - " + computer.getComputertype());
            }
        }

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,computersname);
        listView.setAdapter(adapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtcomputername.getText().toString().matches("") || edtcomputertype.getText().toString().matches("")){
                    return;
                }
                Computer computer = new Computer(edtcomputername.getText().toString(),edtcomputertype.getText().toString());

                allcomputers.add(computer);
                databaseHandeler.addComputer(computer);
                computersname.add(computer.getComputername() + " - " + computer.getComputertype());
                edtcomputername.setText("");
                edtcomputertype.setText("");
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allcomputers.size() > 0){
                    computersname.remove(0);
                    databaseHandeler.deleteComputer(allcomputers.get(0));
                    allcomputers.remove(0);
                }else {
                    return;
                }
            }
        });
         adapter.notifyDataSetChanged();
    }
}