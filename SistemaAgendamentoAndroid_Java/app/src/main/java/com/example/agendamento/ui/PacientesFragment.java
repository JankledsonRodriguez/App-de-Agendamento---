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
import com.example.agendamento.model.Paciente;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.List;
import java.util.concurrent.Executors;

public class PacientesFragment extends Fragment {
    private RecyclerView recycler;

    public PacientesFragment() { super(R.layout.fragment_pacientes); }
    
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        recycler = v.findViewById(R.id.recyclerPacientes);
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        
        v.findViewById(R.id.btnNovoPaciente).setOnClickListener(x -> 
            ((MainActivity)requireActivity()).abrir(new NovoPacienteFragment())
        );

        carregarPacientes();
    }

    private void carregarPacientes() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Paciente> lista = new ClinicaRepository().listarPacientes();
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        recycler.setAdapter(new PacienteAdapter(lista, p -> {
                            ((MainActivity)requireActivity()).abrir(new ProntuarioFragment(p.getId()));
                        }));
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao carregar pacientes: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}
