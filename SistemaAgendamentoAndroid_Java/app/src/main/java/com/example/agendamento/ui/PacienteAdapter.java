package com.example.agendamento.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.model.Paciente;
import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.VH> {
    private final List<Paciente> items;
    private final OnPacienteClickListener listener;

    public interface OnPacienteClickListener {
        void onPacienteClick(Paciente p);
    }

    public PacienteAdapter(List<Paciente> items, OnPacienteClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paciente, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Paciente p = items.get(position);
        holder.txtNome.setText(p.getNome());
        holder.txtInfo.setText(p.getTelefone() + " | " + (p.getEmail() != null ? p.getEmail() : ""));
        holder.itemView.setOnClickListener(v -> listener.onPacienteClick(p));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtNome, txtInfo;
        VH(View v) {
            super(v);
            txtNome = v.findViewById(R.id.txtPacienteNomeItem);
            txtInfo = v.findViewById(R.id.txtPacienteInfoItem);
        }
    }
}
