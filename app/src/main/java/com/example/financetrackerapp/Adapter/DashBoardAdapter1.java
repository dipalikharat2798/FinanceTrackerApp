package com.example.financetrackerapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetrackerapp.Model.Data;
import com.example.financetrackerapp.Model.Data1;
import com.example.financetrackerapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class DashBoardAdapter1 extends FirebaseRecyclerAdapter<Data1, DashBoardAdapter1.MyviewHolder1> {

    public DashBoardAdapter1(@NonNull FirebaseRecyclerOptions<Data1> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder1 myviewHolder1, int i, @NonNull Data1 data) {
        myviewHolder1.amount_txt_income.setText(data.getAmount()+"");
        myviewHolder1.date_txt_income.setText(data.getDate());
        myviewHolder1.type_txt_income.setText(data.getType());
    }

    @NonNull
    @Override
    public MyviewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item_layout1, parent, false);
        return new DashBoardAdapter1.MyviewHolder1(view);
    }
    

    public class MyviewHolder1 extends RecyclerView.ViewHolder {
        TextView date_txt_income, type_txt_income, amount_txt_income;

        public MyviewHolder1(@NonNull View itemView) {
            super(itemView);
            date_txt_income = itemView.findViewById(R.id.date_txt_income2);
            type_txt_income = itemView.findViewById(R.id.type_txt_income2);
            amount_txt_income = itemView.findViewById(R.id.amount_txt_income2);
        }
    }
}
