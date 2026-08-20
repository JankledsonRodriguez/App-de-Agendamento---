package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.repository.AgendamentoRepository;
import java.util.concurrent.Executors;

public class NovoClienteFragment extends Fragment {
    public NovoClienteFragment(){super(R.layout.fragment_novo_cliente);}
    @Override public void onViewCreated(View v,Bundle b){
        EditText n=v.findViewById(R.id.edtNome),t=v.findViewById(R.id.edtTelefone),e=v.findViewById(R.id.edtEmailCliente);
        v.findViewById(R.id.btnSalvarCliente).setOnClickListener(x->Executors.newSingleThreadExecutor().execute(()->{
            try{
                boolean ok=new AgendamentoRepository().inserirCliente(n.getText().toString(),t.getText().toString(),e.getText().toString());
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), ok ? "Cliente cadastrado" : "Não cadastrado", Toast.LENGTH_SHORT).show();
                        if (ok) ((MainActivity) getActivity()).abrir(new ClientesFragment());
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
