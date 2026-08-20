package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.repository.AgendamentoRepository;
import java.util.concurrent.Executors;

public class NovoAgendamentoFragment extends Fragment {
    public NovoAgendamentoFragment(){super(R.layout.fragment_novo_agendamento);}
    @Override public void onViewCreated(View v,Bundle b){
        EditText c=v.findViewById(R.id.edtClienteId),d=v.findViewById(R.id.edtData),h=v.findViewById(R.id.edtHora),
                s=v.findViewById(R.id.edtServico),o=v.findViewById(R.id.edtObservacao);
        v.findViewById(R.id.btnSalvarAgendamento).setOnClickListener(x->Executors.newSingleThreadExecutor().execute(()->{
            try{
                boolean ok=new AgendamentoRepository().inserirAgendamento(Integer.parseInt(c.getText().toString()),d.getText().toString(),h.getText().toString(),s.getText().toString(),o.getText().toString());
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), ok ? "Agendamento cadastrado" : "Não cadastrado", Toast.LENGTH_SHORT).show();
                        if (ok) ((MainActivity) getActivity()).abrir(new AgendamentosFragment());
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
