package com.example.agendamento.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.model.Consulta;
import java.util.List;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.VH> {
    private final List<Consulta> consultas;
    private final OnStatusChangeListener listener;

    public interface OnStatusChangeListener {
        void onStatusChange(Consulta consulta, String novoStatus);
    }

    public ConsultaAdapter(List<Consulta> consultas, OnStatusChangeListener listener) {
        this.consultas = consultas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consulta, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Consulta c = consultas.get(position);
        holder.txtNome.setText(c.getPacienteNome());
        
        String info = c.getEspecialidade();
        if (c.getMedicoNome() != null) {
            info += " | Dr(a). " + c.getMedicoNome();
        }
        holder.txtEspecialidade.setText(info);
        
        holder.txtDataHora.setText(c.getData() + " | " + c.getHora());
        
        String status = c.getStatus() != null ? c.getStatus().toUpperCase() : "AGENDADO";
        holder.txtStatus.setText(status);
        applyStatusStyle(holder.txtStatus, status);

        holder.itemView.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), holder.txtStatus);
            popup.getMenu().add("AGENDADO");
            popup.getMenu().add("CONFIRMADO");
            popup.getMenu().add("EM ATENDIMENTO");
            popup.getMenu().add("ATENDIDA");
            
            popup.setOnMenuItemClickListener(item -> {
                listener.onStatusChange(c, item.getTitle().toString());
                return true;
            });
            popup.show();
        });
    }

    private void applyStatusStyle(TextView tv, String status) {
        int color;
        switch (status) {
            case "CONFIRMADO": color = Color.parseColor("#2ECC71"); break;
            case "EM ATENDIMENTO": color = Color.parseColor("#F1C40F"); break;
            case "ATENDIDA": color = Color.parseColor("#27AE60"); break;
            default: color = Color.parseColor("#0061FF"); break; // AGENDADO
        }

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(24);
        tv.setBackground(gd);
    }

    @Override
    public int getItemCount() { return consultas.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtNome, txtEspecialidade, txtDataHora, txtStatus;
        VH(View v) {
            super(v);
            txtNome = v.findViewById(R.id.txtPacienteNome);
            txtEspecialidade = v.findViewById(R.id.txtEspecialidade);
            txtDataHora = v.findViewById(R.id.txtDataHora);
            txtStatus = v.findViewById(R.id.txtStatusBadge);
        }
    }
}
