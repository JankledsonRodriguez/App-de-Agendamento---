package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.agendamento.R;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.concurrent.Executors;

public class InicioFragment extends Fragment {
    public InicioFragment(){super(R.layout.fragment_inicio);}
    @Override public void onViewCreated(View v,Bundle b){
        TextView txtPacientes = v.findViewById(R.id.txtQtdPacientes);
        TextView txtHorario = v.findViewById(R.id.txtProximoHorario);
        TextView t = v.findViewById(R.id.txtResumo);

        Executors.newSingleThreadExecutor().execute(()->{
            try{
                ClinicaRepository r = new ClinicaRepository();
                int totalPacientes = r.listarPacientes().size();
                
                // Pegando o próximo horário (simulação simples baseada na primeira consulta da lista)
                String proximo = "--";
                java.util.List<com.example.agendamento.model.Consulta> agds = r.listarConsultas();
                if(!agds.isEmpty()) {
                    proximo = agds.get(0).getHora();
                }

                String finalProximo = proximo;
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        txtPacientes.setText(String.valueOf(totalPacientes));
                        txtHorario.setText(finalProximo);
                        t.setText("Conexão com Clinique+ Ativa");
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        txtPacientes.setText("!");
                        t.setVisibility(View.VISIBLE);
                        t.setText("Erro de Conexão: " + e.getMessage());
                    });
                }
            }
        });
    }
}
