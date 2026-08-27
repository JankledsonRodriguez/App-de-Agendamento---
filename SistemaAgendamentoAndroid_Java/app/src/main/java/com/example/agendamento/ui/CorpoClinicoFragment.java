package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.model.Medico;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CorpoClinicoFragment extends Fragment {
    private String especialidadeFiltro = null;

    public CorpoClinicoFragment() { super(R.layout.fragment_corpo_clinico); }
    
    // Construtor para filtro
    public CorpoClinicoFragment(String especialidade) {
        this();
        this.especialidadeFiltro = especialidade;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        if (especialidadeFiltro != null) {
            tvTitle.setText("Médicos: " + especialidadeFiltro);
        }

        RecyclerView r = v.findViewById(R.id.recyclerProfissionais);
        r.setLayoutManager(new LinearLayoutManager(requireContext()));

        v.findViewById(R.id.btnNovoProfissional).setOnClickListener(x -> 
            ((MainActivity)requireActivity()).abrir(new NovoMedicoFragment())
        );

        carregarMedicos(r);
    }

    private void carregarMedicos(RecyclerView r) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ClinicaRepository repo = new ClinicaRepository();
                List<Medico> medicos;
                
                if (especialidadeFiltro != null) {
                    medicos = repo.listarMedicosPorEspecialidade(especialidadeFiltro);
                } else {
                    medicos = repo.listarCorpoClinico();
                }

                ArrayList<String> list = new ArrayList<>();
                for (Medico m : medicos) {
                    list.add("Dr(a). " + m.getNome() + "\n" + m.getEspecialidade() + " | CRM: " + m.getRegistroCRM());
                }
                
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> r.setAdapter(new SimpleAdapter(list)));
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao carregar corpo clínico: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}
