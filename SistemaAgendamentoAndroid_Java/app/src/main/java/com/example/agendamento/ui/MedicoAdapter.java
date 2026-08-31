package com.example.agendamento.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.model.Medico;
import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.VH> {
    private final List<Medico> items;

    public MedicoAdapter(List<Medico> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medico, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Medico m = items.get(position);
        holder.txtNome.setText("Dr(a). " + m.getNome());
        holder.txtEspecialidade.setText(m.getEspecialidade());
        holder.txtCRM.setText("CRM: " + m.getRegistroCRM());
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtNome, txtEspecialidade, txtCRM;
        VH(View v) {
            super(v);
            txtNome = v.findViewById(R.id.txtMedicoNomeItem);
            txtEspecialidade = v.findViewById(R.id.txtMedicoEspecialidadeItem);
            txtCRM = v.findViewById(R.id.txtMedicoCRMItem);
        }
    }
}
