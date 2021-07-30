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


public class DashBoardAdapter extends FirebaseRecyclerAdapter<Data, DashBoardAdapter.MyviewHolder> {

    public DashBoardAdapter(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int i, @NonNull Data data) {
        myviewHolder.amount_txt_income.setText(data.getAmount()+"");
        myviewHolder.date_txt_income.setText(data.getDate());
        myviewHolder.type_txt_income.setText(data.getType());
    }



    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item_layout, parent, false);
        return new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView date_txt_income, type_txt_income, amount_txt_income;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            date_txt_income =itemView.findViewById(R.id.date_txt_income1);
            type_txt_income=itemView.findViewById(R.id.type_txt_income1);
            amount_txt_income=itemView.findViewById(R.id.amount_txt_income1);
        }
    }
}
