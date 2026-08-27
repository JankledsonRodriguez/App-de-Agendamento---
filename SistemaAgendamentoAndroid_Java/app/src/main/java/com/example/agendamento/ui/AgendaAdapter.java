package com.example.agendamento.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.model.Consulta;
import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.VH> {
    private final List<Consulta> items;
    private final OnAgendaActionListener listener;

    public interface OnAgendaActionListener {
        void onAction(Consulta c, String action);
        void onReschedule(Consulta c);
    }

    public AgendaAdapter(List<Consulta> items, OnAgendaActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int t) {
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_agenda, p, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Consulta c = items.get(i);
        h.txtHora.setText(c.getHora());
        h.txtPaciente.setText(c.getPacienteNome());
        h.txtEspecialidade.setText(c.getEspecialidade());
        
        String status = c.getStatus() != null ? c.getStatus() : "AGENDADO";
        h.txtStatus.setText(status);
        h.txtStatus.setTextColor(getStatusColor(status));

        h.btnConfirmar.setOnClickListener(v -> listener.onAction(c, "CONFIRMADO"));
        h.btnFaltou.setOnClickListener(v -> listener.onAction(c, "FALTOU"));
        
        h.btnMais.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), h.btnMais);
            popup.getMenu().add("EM ATENDIMENTO");
            popup.getMenu().add("ATENDIDO");
            popup.getMenu().add("REAGENDAR");
            popup.getMenu().add("CANCELAR");
            
            popup.setOnMenuItemClickListener(item -> {
                String title = item.getTitle().toString();
                if ("REAGENDAR".equals(title)) {
                    listener.onReschedule(c);
                } else {
                    listener.onAction(c, title);
                }
                return true;
            });
            popup.show();
        });
    }

    private int getStatusColor(String status) {
        switch (status) {
            case "CONFIRMADO": return Color.parseColor("#2ECC71");
            case "EM ATENDIMENTO": return Color.parseColor("#F1C40F");
            case "ATENDIDO": return Color.parseColor("#27AE60");
            case "FALTOU": return Color.parseColor("#E74C3C");
            case "CANCELADO": return Color.parseColor("#95A5A6");
            default: return Color.parseColor("#3498DB"); // AGENDADO
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtHora, txtPaciente, txtEspecialidade, txtStatus;
        ImageButton btnConfirmar, btnFaltou, btnMais;
        VH(View v) {
            super(v);
            txtHora = v.findViewById(R.id.txtHoraAgenda);
            txtPaciente = v.findViewById(R.id.txtPacienteAgenda);
            txtEspecialidade = v.findViewById(R.id.txtEspecialidadeAgenda);
            txtStatus = v.findViewById(R.id.txtStatusMini);
            btnConfirmar = v.findViewById(R.id.btnConfirmarPresenca);
            btnFaltou = v.findViewById(R.id.btnMarcarFaltou);
            btnMais = v.findViewById(R.id.btnAcoesMais);
        }
    }
}
