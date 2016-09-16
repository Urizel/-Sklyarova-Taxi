package com.taxi.taxiapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taxi.taxiapp.R;
import com.taxi.taxiapp.model.TaxiOrder;

import java.util.Collections;
import java.util.List;

public class TaxiOrdersListAdapter extends RecyclerView.Adapter<TaxiOrdersListAdapter.TaxiOrdersViewHolder> {

    private List<TaxiOrder> mOrdersList;
    private Callback callback;
    private Context mContext;

    public TaxiOrdersListAdapter(Context context) {
        this.mContext = context;
        this.mOrdersList = Collections.emptyList();
    }

    public TaxiOrdersListAdapter(List<TaxiOrder> ordersList) {
        this.mOrdersList = ordersList;
    }

    public void setTaxiOrders(List<TaxiOrder> ordersList) {
        this.mOrdersList = ordersList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public TaxiOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        final TaxiOrdersViewHolder viewHolder = new TaxiOrdersViewHolder(itemView);
        viewHolder.contentLayout.setOnClickListener(v -> {
            if (callback != null) {
                callback.onItemClick(viewHolder.taxiOrder);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaxiOrdersViewHolder holder, int position) {
        TaxiOrder taxiOrder = mOrdersList.get(position);
        holder.taxiOrder = taxiOrder;
        holder.startAddress.setText(taxiOrder.getStartAddress().getAddress());
        holder.endAddress.setText(taxiOrder.getEndAddress().getAddress());
        holder.date.setText(taxiOrder.getOrderTime());
        holder.amount.setText(taxiOrder.getPrice().getRefactorAmount().concat(" ").concat(mContext.getString(R.string.rub)));
    }

    @Override
    public int getItemCount() {
        return mOrdersList.size();
    }

    public static class TaxiOrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView startAddress;
        public TextView endAddress;
        public TextView date;
        public TextView amount;
        public TaxiOrder taxiOrder;
        public View contentLayout;

        public TaxiOrdersViewHolder(View itemView) {
            super(itemView);
            startAddress = (TextView) itemView.findViewById(R.id.tvStartAddress);
            endAddress = (TextView) itemView.findViewById(R.id.tvEndAddress);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            amount = (TextView) itemView.findViewById(R.id.tvAmount);
            contentLayout = itemView.findViewById(R.id.contentLayout);
        }
    }

    public interface Callback {
        void onItemClick(TaxiOrder order);
    }
}