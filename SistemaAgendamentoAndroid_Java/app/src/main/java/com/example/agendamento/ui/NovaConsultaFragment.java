package com.example.agendamento.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.model.Especialidade;
import com.example.agendamento.model.Paciente;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class NovaConsultaFragment extends Fragment {
    private int selectedPacienteId = -1;
    private List<Paciente> pacientesList = new ArrayList<>();

    public NovaConsultaFragment() { super(R.layout.fragment_nova_consulta); }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        AutoCompleteTextView pNom = v.findViewById(R.id.edtPacienteNome);
        EditText d = v.findViewById(R.id.edtData);
        EditText h = v.findViewById(R.id.edtHora);
        AutoCompleteTextView s = v.findViewById(R.id.edtServico);
        EditText o = v.findViewById(R.id.edtObservacao);

        setupPatientSelector(pNom);
        setupDateSelector(d);
        setupTimeSelector(h);
        setupSpecialtySelector(s);

        v.findViewById(R.id.btnSalvarAgendamento).setOnClickListener(x -> {
            String data = d.getText().toString();
            String hora = h.getText().toString();
            String serv = s.getText().toString();
            String obs = o.getText().toString();

            if (selectedPacienteId == -1 || data.isEmpty() || hora.isEmpty() || serv.isEmpty()) {
                Toast.makeText(getContext(), "Selecione o paciente e preencha os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    boolean ok = new ClinicaRepository().inserirConsulta(
                            selectedPacienteId, data, hora, serv, obs
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
            });
        });
    }

    private void setupPatientSelector(AutoCompleteTextView actv) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                pacientesList = new ClinicaRepository().listarPacientes();
                List<String> names = new ArrayList<>();
                for (Paciente p : pacientesList) {
                    names.add(p.getNome());
                }

                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_dropdown_item_1line, names);
                        actv.setAdapter(adapter);
                        
                        actv.setOnItemClickListener((parent, view, position, id) -> {
                            String selectedName = (String) parent.getItemAtPosition(position);
                            for (Paciente p : pacientesList) {
                                if (p.getNome().equals(selectedName)) {
                                    selectedPacienteId = p.getId();
                                    break;
                                }
                            }
                        });
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao carregar pacientes: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }

    private void setupDateSelector(EditText edt) {
        edt.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                String date = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                edt.setText(date);
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void setupTimeSelector(EditText edt) {
        edt.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
                String time = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
                edt.setText(time);
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
        });
    }

    private void setupSpecialtySelector(AutoCompleteTextView actv) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Especialidade> specs = new ClinicaRepository().listarEspecialidades();
                List<String> names = new ArrayList<>();
                for (Especialidade es : specs) {
                    names.add(es.getNome());
                }

                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), 
                                android.R.layout.simple_dropdown_item_1line, names);
                        actv.setAdapter(adapter);
                    });
                }
            } catch (Exception e) {
                // Erro silencioso ou log
            }
        });
    }
}
