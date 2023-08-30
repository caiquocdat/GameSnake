package hehe.caiquocdat.snakegame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hehe.caiquocdat.snakegame.R;
import hehe.caiquocdat.snakegame.model.HistoryModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private ArrayList<HistoryModel> historyList;
    private Context context;

    public HistoryAdapter(ArrayList<HistoryModel> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryModel historyModel = historyList.get(position);
        holder.pointTv.setText(String.valueOf(historyModel.getPoint()));
        holder.sttTv.setText(position+1+"");
        if (position % 2 == 0) {
            holder.itemRel.setBackgroundColor(0x33000000); // Màu trắng cho vị trí chẵn
        } else {
            holder.itemRel.setBackgroundColor(0x33FFFFFF); // Màu xám nhạt cho vị trí lẻ
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView pointTv,sttTv;
        RelativeLayout itemRel;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            pointTv = itemView.findViewById(R.id.pointTv);
            sttTv = itemView.findViewById(R.id.sttTv);
            itemRel = itemView.findViewById(R.id.itemRel);
        }
    }
}