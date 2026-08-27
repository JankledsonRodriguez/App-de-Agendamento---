package com.example.agendamento.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.model.Consulta;
import com.example.agendamento.repository.ClinicaRepository;
import com.google.android.material.chip.ChipGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AgendaMedicaFragment extends Fragment {
    private RecyclerView recycler;
    private CalendarView calendarView;
    private View calendarCard;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private String currentView = "DIARIA";
    private String filtroEspecialidade = null;
    private String filtroStatus = null;
    private String filtroDataInicio = null;
    private String filtroDataFim = null;

    public AgendaMedicaFragment() { super(R.layout.fragment_agenda_medica); }

    // Construtor para filtros vindos do Dashboard
    public AgendaMedicaFragment(String status, String dataInicio, String dataFim) {
        this();
        this.filtroStatus = status;
        this.filtroDataInicio = dataInicio;
        this.filtroDataFim = dataFim;
        if (dataInicio != null && !dataInicio.equals(dataFim)) {
            this.currentView = "CUSTOM";
        }
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        recycler = v.findViewById(R.id.recyclerAgenda);
        calendarView = v.findViewById(R.id.calendarViewAgenda);
        calendarCard = v.findViewById(R.id.calendarCard);
        ChipGroup group = v.findViewById(R.id.chipGroupViews);
        View fab = v.findViewById(R.id.fabFiltros);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Se veio do dashboard com filtros, podemos esconder os seletores de aba para não confundir
        if (filtroStatus != null || filtroDataInicio != null) {
            group.setVisibility(View.GONE);
            TextView title = v.findViewById(R.id.headerContainer).findViewWithTag("title"); // Puxar título se necessário
            // Para simplificar, apenas mostramos a lista filtrada
        }

        group.setOnCheckedStateChangeListener((group1, checkedIds) -> {
            filtroStatus = null; // Resetar filtros manuais ao trocar de aba
            filtroDataInicio = null;
            filtroDataFim = null;
            
            if (checkedIds.contains(R.id.chipDiaria)) {
                currentView = "DIARIA";
                calendarCard.setVisibility(View.GONE);
            } else if (checkedIds.contains(R.id.chipSemanal)) {
                currentView = "SEMANAL";
                calendarCard.setVisibility(View.GONE);
            } else if (checkedIds.contains(R.id.chipMensal)) {
                currentView = "MENSAL";
                calendarCard.setVisibility(View.VISIBLE);
            }
            carregarAgenda();
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            carregarAgendaParaData(selected);
        });

        fab.setOnClickListener(x -> abrirFiltros());

        carregarAgenda();
    }

    private void abrirFiltros() {
        String[] options = {"TODAS", "Cardiologia", "Dermatologia", "Pediatria", "Ortopedia", "Ginecologia", "Oftalmologia"};
        new android.app.AlertDialog.Builder(requireContext())
            .setTitle("Filtrar por Especialidade")
            .setItems(options, (dialog, which) -> {
                filtroEspecialidade = "TODAS".equals(options[which]) ? null : options[which];
                carregarAgenda();
            })
            .show();
    }

    private void carregarAgenda() {
        if (filtroDataInicio != null) {
            carregarDados(filtroDataInicio, filtroDataFim);
        } else {
            Calendar cal = Calendar.getInstance();
            carregarAgendaParaData(cal);
        }
    }

    private void carregarAgendaParaData(Calendar cal) {
        String start, end;
        if ("DIARIA".equals(currentView)) {
            start = end = sdf.format(cal.getTime());
        } else if ("SEMANAL".equals(currentView)) {
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            start = sdf.format(cal.getTime());
            cal.add(Calendar.DAY_OF_YEAR, 6);
            end = sdf.format(cal.getTime());
        } else { // MENSAL
            cal.set(Calendar.DAY_OF_MONTH, 1);
            start = sdf.format(cal.getTime());
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            end = sdf.format(cal.getTime());
        }
        carregarDados(start, end);
    }

    private void carregarDados(String start, String end) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Consulta> list = new ClinicaRepository().listarAgendaFiltrada(start, end, filtroEspecialidade, filtroStatus);
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        recycler.setAdapter(new AgendaAdapter(list, new AgendaAdapter.OnAgendaActionListener() {
                            @Override
                            public void onAction(Consulta c, String action) {
                                atualizarStatus(c, action);
                            }

                            @Override
                            public void onReschedule(Consulta c) {
                                abrirReagendamento(c);
                            }
                        }));
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }

    private void atualizarStatus(Consulta c, String status) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                boolean ok = new ClinicaRepository().atualizarStatusConsulta(c.getId(), status);
                if (ok && isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Status: " + status, Toast.LENGTH_SHORT).show();
                        carregarAgenda();
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void abrirReagendamento(Consulta c) {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            String novaData = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            new TimePickerDialog(requireContext(), (view2, hourOfDay, minute) -> {
                String novaHora = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
                efetuarReagendamento(c, novaData, novaHora);
            }, 10, 0, true).show();
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void efetuarReagendamento(Consulta c, String d, String h) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                boolean ok = new ClinicaRepository().reagendarConsulta(c.getId(), d, h);
                if (ok && isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Reagendado para " + d + " às " + h, Toast.LENGTH_SHORT).show();
                        carregarAgenda();
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao reagendar", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
