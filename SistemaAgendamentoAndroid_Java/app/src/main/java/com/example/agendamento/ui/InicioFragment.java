package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.agendamento.R;
import com.example.agendamento.repository.AgendamentoRepository;
import java.util.concurrent.Executors;

public class InicioFragment extends Fragment {
    public InicioFragment(){super(R.layout.fragment_inicio);}
    @Override public void onViewCreated(View v,Bundle b){
        TextView t=v.findViewById(R.id.txtResumo);
        Executors.newSingleThreadExecutor().execute(()->{
            try{
                AgendamentoRepository r=new AgendamentoRepository();
                int c=r.listarClientes().size(),a=r.listarAgendamentos().size();
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> t.setText("Clientes: " + c + "\nAgendamentos: " + a + "\n\nBanco: MySQL"));
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> t.setText("Erro MySQL: " + e.getMessage()));
                }
            }
        });
    }
}
