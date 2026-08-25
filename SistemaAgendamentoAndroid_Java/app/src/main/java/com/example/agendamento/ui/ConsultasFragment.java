package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.model.Consulta;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ConsultasFragment extends Fragment {
    public ConsultasFragment() { super(R.layout.fragment_consultas); }

    @Override
    public void onViewCreated(View v, Bundle b) {
        RecyclerView r = v.findViewById(R.id.recyclerAgendamentos);
        r.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        v.findViewById(R.id.btnNovoAgendamento).setOnClickListener(x -> 
            ((MainActivity)requireActivity()).abrir(new NovaConsultaFragment())
        );

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ArrayList<String> list = new ArrayList<>();
                for (Consulta c : new ClinicaRepository().listarConsultas()) {
                    list.add(c.getData() + " " + c.getHora() + "\nPaciente: " + c.getPacienteNome() + 
                             "\nEspecialidade: " + c.getEspecialidade() + "\nStatus: " + c.getStatus());
                }
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
