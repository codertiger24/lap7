package com.example.lap7;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore database;
    Button btnInsert,btnUpdate,btnDelete;
    TextView tvResult;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        btnInsert = findViewById(R.id.btnInsert);
        database  = FirebaseFirestore.getInstance();//khoi tao database

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFirebase(tvResult);
            }
        });
        btnUpdate = findViewById(R.id.btnSua);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFirebase(tvResult);
            }
        });
        btnDelete = findViewById(R.id.btnxoa);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFirebase(tvResult);
            }
        });
        SelectDataFormFirebase(tvResult);
    }



    String id = "";
    ToDo toDo = null;

    public void insertFirebase(TextView tvResult){
        id = UUID.randomUUID().toString();//Lay 1 id bat ky
        //tao doi tuong de insert
        toDo = new ToDo(id,"title2","content2","date2","type2");
        //chuyen doi sang doi tuong co the thao tac voi fire base
        HashMap<String, Object> maptoDo = toDo.conveHashMap();
        //insert vao database
        database.collection("TODO").document(id).set(maptoDo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tvResult.setText("Them thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });


    }
    public void updateFirebase(TextView tvResult){
        id = "2543f5c7-a9c1-4f4f-b8d0-dd42b54a9200";
        toDo = new ToDo(id,"suatitle","suacontent","suadate","suatype");
        database.collection("TODO").document(toDo.getId()).update(toDo.conveHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tvResult.setText("Sua thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });
    }
    public void deleteFirebase(TextView tvResult) {
        id = "2543f5c7-a9c1-4f4f-b8d0-dd42b54a9200";
        database.collection("TODO").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tvResult.setText("Xoa thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });
    }
    String strResult = "";
    public ArrayList<ToDo> SelectDataFormFirebase(TextView tvResult){
        ArrayList<ToDo> list  = new ArrayList<>();
        database.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){//sau khi lay du lieu thanh cong
                            strResult = "";
                            //doc theo tung dong du lieu
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                //chuyen dong doc duoc sang doi tuong
                                ToDo toDo1 = documentSnapshot.toObject(ToDo.class);

                                //chuyen doi tuong thanh chuoi
                                strResult += "Id:"+toDo1.getId()+"\n";
                                list.add(toDo1);//them vao list


                            }
                            //hien thi ket qua
                            tvResult.setText(strResult);
                        }else {
                            tvResult.setText("doc du lieu that bai");
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });
        return list;
    }
}