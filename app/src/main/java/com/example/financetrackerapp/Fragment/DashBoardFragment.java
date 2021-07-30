package com.example.financetrackerapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.financetrackerapp.Adapter.DashBoardAdapter;
import com.example.financetrackerapp.Model.Data;
import com.example.financetrackerapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class DashBoardFragment extends Fragment {

    private FloatingActionButton fab_main_btn;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;

    private TextView fab_income_txt, fab_expense_txt;

    private boolean isOpen=false;

    private Animation fadeOpen,fadeClose;

    private TextView totalIncome_tv,totalExpence_tv;
    private RecyclerView mIncomeRecycler,mExpenseRecycler;
    DashBoardAdapter adapter;
    //firebase

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    public DashBoardFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myview = inflater.inflate(R.layout.fragment_dash_board, container, false);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncomeDatabase= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase=FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        fab_main_btn=myview.findViewById(R.id.fab_main_plus_btn);
        fab_income_btn=myview.findViewById(R.id.income_Ft_btn);
        fab_expense_btn=myview.findViewById(R.id.expense_Ft_btn);

        fab_income_txt=myview.findViewById(R.id.income_ft_text);
        fab_expense_txt=myview.findViewById(R.id.expense_ft_text);
        totalIncome_tv=myview.findViewById(R.id.setIncome_tv);
        totalExpence_tv=myview.findViewById(R.id.setExpense_tv);
        mIncomeRecycler=myview.findViewById(R.id.dashborad_income);
        mExpenseRecycler=myview.findViewById(R.id.dashborad_Expense);

        fadeOpen= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_open);
        fadeClose=AnimationUtils.loadAnimation(getActivity(),R.anim.fade_close);


        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                addData();
                setAnimation();
            }
        });

        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double totalvalue = 0;
                for(DataSnapshot mysnapshot : snapshot.getChildren()){
                    Data data = mysnapshot.getValue(Data.class);
                    totalvalue+=data.getAmount();
                }
                totalIncome_tv.setText(totalvalue+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double totalvalue = 0;
                for(DataSnapshot mysnapshot : snapshot.getChildren()){
                    Data data = mysnapshot.getValue(Data.class);
                    totalvalue+=data.getAmount();
                }
                totalExpence_tv.setText(totalvalue+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mIncomeRecycler.setHasFixedSize(true);
        mIncomeRecycler.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        mIncomeRecycler.setHasFixedSize(true);
        mIncomeRecycler.setLayoutManager(linearLayoutManager1);

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mIncomeDatabase, Data.class)
                        .build();

        adapter=new DashBoardAdapter(options);
        mIncomeRecycler.setAdapter(adapter);
        return myview;
    }

    private void addData(){

        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInsert();
            }
        });
        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseDataInsert();
            }
        });
    }

    public void setAnimation(){
        if (isOpen){
            fab_income_btn.startAnimation(fadeClose);
            fab_expense_btn.startAnimation(fadeClose);
            fab_income_btn.setClickable(false);
            fab_expense_btn.setClickable(false);

            fab_income_txt.startAnimation(fadeClose);
            fab_expense_txt.startAnimation(fadeClose);
            fab_income_txt.setClickable(false);
            fab_expense_txt.setClickable(false);
            isOpen=false;
        }else{
            fab_income_btn.startAnimation(fadeOpen);
            fab_expense_btn.startAnimation(fadeOpen);
            fab_income_btn.setClickable(true);
            fab_expense_btn.setClickable(true);

            fab_income_txt.startAnimation(fadeOpen);
            fab_expense_txt.startAnimation(fadeOpen);
            fab_income_txt.setClickable(true);
            fab_expense_txt.setClickable(true);
            isOpen=true;
        }
    }
    public void incomeDataInsert(){
        //add data
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView = inflater.inflate(R.layout.custom_layout_for_ipdata, null);
        myDialog.setView(myView);

        final  AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final Spinner itemSpinner = myView.findViewById(R.id.itemsspinner);
        final EditText amount1 = myView.findViewById(R.id.amount);
        final EditText note1=myView.findViewById(R.id.note);
        final Button cancel = myView.findViewById(R.id.cancel);
        final  Button save = myView.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amount1.getText().toString();
                String type = itemSpinner.getSelectedItem().toString();
                String note = note1.getText().toString();
                if (TextUtils.isEmpty(amount)){
                    amount1.setError("Amount is required!");
                    return;
                }
                if (TextUtils.isEmpty(note)){
                    note1.setError("Note is Empty!");
                    return;
                }
                if (type.equals("Select item")){
                    Toast.makeText(getContext(), "Select a valid item", Toast.LENGTH_SHORT).show();
                }
                double final_amt=Double.parseDouble(amount);
                String id = mIncomeDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(final_amt,type,note,id,mDate);
                mIncomeDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(), "Data Added", Toast.LENGTH_SHORT).show();
                setAnimation();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimation();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void expenseDataInsert(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView = inflater.inflate(R.layout.custom_layout_for_expensedata, null);
        myDialog.setView(myView);

        final  AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final Spinner itemSpinner = myView.findViewById(R.id.itemsspinner);
        final EditText amount1 = myView.findViewById(R.id.amount);
        final EditText note1=myView.findViewById(R.id.note);
        final Button cancel = myView.findViewById(R.id.cancel);
        final  Button save = myView.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amount1.getText().toString();
                String type = itemSpinner.getSelectedItem().toString();
                String note = note1.getText().toString();
                if (TextUtils.isEmpty(amount)){
                    amount1.setError("Amount is required!");
                    return;
                }
                if (TextUtils.isEmpty(note)){
                    note1.setError("Note is Empty!");
                    return;
                }
                if (type.equals("Select item")){
                    Toast.makeText(getContext(), "Select a valid item", Toast.LENGTH_SHORT).show();
                }
                double final_amt=Double.parseDouble(amount);
                String id = mIncomeDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(final_amt,type,note,id,mDate);
                mExpenseDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(), "Data Added", Toast.LENGTH_SHORT).show();
                setAnimation();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}