package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.concurrent.Executors;

public class NovaConsultaFragment extends Fragment {
    public NovaConsultaFragment() { super(R.layout.fragment_nova_consulta); }

    @Override
    public void onViewCreated(View v, Bundle b) {
        EditText c = v.findViewById(R.id.edtClienteId), d = v.findViewById(R.id.edtData), 
                 h = v.findViewById(R.id.edtHora), s = v.findViewById(R.id.edtServico), 
                 o = v.findViewById(R.id.edtObservacao);

        v.findViewById(R.id.btnSalvarAgendamento).setOnClickListener(x -> Executors.newSingleThreadExecutor().execute(() -> {
            try {
                boolean ok = new ClinicaRepository().inserirConsulta(
                        Integer.parseInt(c.getText().toString()), 
                        d.getText().toString(), 
                        h.getText().toString(), 
                        s.getText().toString(), 
                        o.getText().toString()
                );
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), ok ? "Consulta confirmada!" : "Erro ao confirmar consulta", Toast.LENGTH_SHORT).show();
                        if (ok) ((MainActivity) requireActivity()).abrir(new ConsultasFragment());
                    });
                }
            } catch (Exception z) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + z.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        }));
    }
}
