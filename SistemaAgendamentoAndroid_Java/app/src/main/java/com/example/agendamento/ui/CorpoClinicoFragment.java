package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.model.Medico;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.List;
import java.util.concurrent.Executors;

public class CorpoClinicoFragment extends Fragment {
    private String especialidadeFiltro = null;
    private RecyclerView recycler;

    public CorpoClinicoFragment() { super(R.layout.fragment_corpo_clinico); }
    
    public CorpoClinicoFragment(String especialidade) {
        this();
        this.especialidadeFiltro = especialidade;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        if (especialidadeFiltro != null) {
            tvTitle.setText("Médicos: " + especialidadeFiltro);
        }

        recycler = v.findViewById(R.id.recyclerProfissionais);
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        v.findViewById(R.id.btnNovoProfissional).setOnClickListener(x -> 
            ((MainActivity)requireActivity()).abrir(new NovoMedicoFragment())
        );

        carregarMedicos();
    }

    private void carregarMedicos() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ClinicaRepository repo = new ClinicaRepository();
                List<Medico> medicos;
                
                if (especialidadeFiltro != null) {
                    medicos = repo.listarMedicosPorEspecialidade(especialidadeFiltro);
                } else {
                    medicos = repo.listarCorpoClinico();
                }

                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (medicos.isEmpty()) {
                            Toast.makeText(getContext(), "Nenhum médico encontrado.", Toast.LENGTH_SHORT).show();
                        }
                        recycler.setAdapter(new MedicoAdapter(medicos));
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao carregar corpo clínico: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}
