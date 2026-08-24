package com.example.agendamento.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.model.Especialidade;
import java.util.List;

public class EspecialidadeAdapter extends RecyclerView.Adapter<EspecialidadeAdapter.ViewHolder> {

    private final List<Especialidade> especialidades;
    private final OnEspecialidadeClickListener listener;

    public interface OnEspecialidadeClickListener {
        void onEspecialidadeClick(Especialidade especialidade);
    }

    public EspecialidadeAdapter(List<Especialidade> especialidades, OnEspecialidadeClickListener listener) {
        this.especialidades = especialidades;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_especialidade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Especialidade especialidade = especialidades.get(position);
        holder.txtNome.setText(especialidade.getNome());
        holder.txtDescricao.setText(especialidade.getDescricao());
        holder.imgIcone.setImageResource(especialidade.getIconeResId());
        
        holder.itemView.setOnClickListener(v -> listener.onEspecialidadeClick(especialidade));
    }

    @Override
    public int getItemCount() {
        return especialidades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtDescricao;
        ImageView imgIcone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeEspecialidade);
            txtDescricao = itemView.findViewById(R.id.txtDescricaoEspecialidade);
            imgIcone = itemView.findViewById(R.id.imgIcone);
        }
    }
}
