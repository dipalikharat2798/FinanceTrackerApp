package com.example.financetrackerapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetrackerapp.Model.Data;
import com.example.financetrackerapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class DashBoardAdapter extends FirebaseRecyclerAdapter<Data, DashBoardAdapter.MyviewHolder1> {

    public DashBoardAdapter(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder1 myviewHolder1, int i, @NonNull Data data) {
        myviewHolder1.amount_txt_income.setText(data.getAmount()+"");
        myviewHolder1.date_txt_income.setText(data.getDate());
        myviewHolder1.type_txt_income.setText(data.getType());
    }

    @NonNull
    @Override
    public MyviewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item_layout, parent, false);
        return new DashBoardAdapter.MyviewHolder1(view);
    }
    

    public class MyviewHolder1 extends RecyclerView.ViewHolder {
        TextView date_txt_income, type_txt_income, amount_txt_income;

        public MyviewHolder1(@NonNull View itemView) {
            super(itemView);
            date_txt_income = itemView.findViewById(R.id.date_txt_income1);
            type_txt_income = itemView.findViewById(R.id.type_txt_income1);
            amount_txt_income = itemView.findViewById(R.id.amount_txt_income1);
        }
    }
}
