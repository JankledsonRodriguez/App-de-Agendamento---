package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.model.Paciente;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class PacientesFragment extends Fragment {
    public PacientesFragment(){super(R.layout.fragment_pacientes);}
    
    @Override public void onViewCreated(View v, Bundle b){
        RecyclerView r = v.findViewById(R.id.recyclerPacientes);
        r.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        v.findViewById(R.id.btnNovoPaciente).setOnClickListener(x -> 
            ((MainActivity)requireActivity()).abrir(new NovoPacienteFragment())
        );

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ArrayList<String> list = new ArrayList<>();
                for(Paciente p : new ClinicaRepository().listarPacientes())
                    list.add("#" + p.getId() + " - " + p.getNome() + "\n" + p.getTelefone() + " | " + p.getEmail());
                
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> r.setAdapter(new SimpleAdapter(list)));
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}
