package com.example.agendamento.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private final List<DayInfo> days;
    private int selectedPos = 0;
    private OnDateSelectedListener listener;

    public interface OnDateSelectedListener {
        void onDateSelected(DayInfo day);
    }

    public DateAdapter(List<DayInfo> days, OnDateSelectedListener listener) {
        this.days = days;
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayInfo day = days.get(position);
        holder.dayAbbrev.setText(day.abbreviation);
        holder.dayNumber.setText(String.valueOf(day.number));

        if (position == selectedPos) {
            holder.card.setStrokeColor(Color.parseColor("#2D62ED"));
            holder.card.setCardBackgroundColor(Color.parseColor("#EEF4FF"));
            holder.dayNumber.setTextColor(Color.parseColor("#2D62ED"));
            holder.dayAbbrev.setTextColor(Color.parseColor("#2D62ED"));
        } else {
            holder.card.setStrokeColor(Color.parseColor("#E9ECEF"));
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.dayNumber.setTextColor(Color.parseColor("#212529"));
            holder.dayAbbrev.setTextColor(Color.parseColor("#6C757D"));
        }

        holder.itemView.setOnClickListener(v -> {
            int oldPos = selectedPos;
            selectedPos = holder.getAdapterPosition();
            notifyItemChanged(oldPos);
            notifyItemChanged(selectedPos);
            if (listener != null) listener.onDateSelected(day);
        });
    }

    @Override public int getItemCount() { return days.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView dayAbbrev, dayNumber;
        ViewHolder(View v) {
            super(v);
            card = v.findViewById(R.id.cardDate);
            dayAbbrev = v.findViewById(R.id.txtDayAbbrev);
            dayNumber = v.findViewById(R.id.txtDayNumber);
        }
    }

    public static class DayInfo {
        String abbreviation;
        int number;
        String fullDate; // YYYY-MM-DD
        String displayFull; // e.g. "Terça-feira, 18 de Agosto"
        DayInfo(String a, int n, String f, String d) {
            abbreviation = a; number = n; fullDate = f; displayFull = d;
        }
    }
}
