package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.model.Consulta;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.List;
import java.util.concurrent.Executors;

public class ConsultasFragment extends Fragment {
    private RecyclerView recycler;

    public ConsultasFragment() { super(R.layout.fragment_consultas); }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        recycler = v.findViewById(R.id.recyclerAgendamentos);
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        
        v.findViewById(R.id.btnNovoAgendamento).setOnClickListener(x -> 
            ((MainActivity)requireActivity()).abrir(new NovaConsultaFragment())
        );

        carregarConsultas();
    }

    private void carregarConsultas() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Consulta> lista = new ClinicaRepository().listarConsultas();
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        ConsultaAdapter adapter = new ConsultaAdapter(lista, (consulta, novoStatus) -> {
                            alterarStatus(consulta, novoStatus);
                        });
                        recycler.setAdapter(adapter);
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao carregar: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }

    private void alterarStatus(Consulta c, String novoStatus) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                boolean ok = new ClinicaRepository().atualizarStatusConsulta(c.getId(), novoStatus);
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (ok) {
                            Toast.makeText(getContext(), "Status atualizado!", Toast.LENGTH_SHORT).show();
                            carregarConsultas(); // Recarregar a lista
                        } else {
                            Toast.makeText(getContext(), "Erro ao atualizar status", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}
