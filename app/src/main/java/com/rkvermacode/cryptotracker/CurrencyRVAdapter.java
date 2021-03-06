package com.rkvermacode.cryptotracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder>{

    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public CurrencyRVAdapter(ArrayList<CurrencyRVModel> currencyRVModelArrayList, Context context) {
        this.context = context;
        this.currencyRVModelArrayList = currencyRVModelArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item,parent,false);
       return new CurrencyRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrencyRVModel currencyRVModel = currencyRVModelArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVModel.getName());
        holder.symbolTV.setText(currencyRVModel.getSymbol());
        holder.rateTV.setText("$ "+df2.format(currencyRVModel.getPrice()));
    }

    @Override
    public int getItemCount() {
        return currencyRVModelArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView currencyNameTV, symbolTV, rateTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            currencyNameTV = itemView.findViewById(R.id.tvCurrencyName);
            symbolTV = itemView.findViewById(R.id.tvSymbol);
            rateTV = itemView.findViewById(R.id.tvCurrencyRate);
        }
    }
}
