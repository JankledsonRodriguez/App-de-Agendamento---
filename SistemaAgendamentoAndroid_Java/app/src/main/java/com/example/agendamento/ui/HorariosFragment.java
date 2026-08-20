package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HorariosFragment extends Fragment {
    private RecyclerView rvDates;
    private ChipGroup chipGroup;
    private TextView txtMes, txtDataSel;

    public HorariosFragment() {
        super(R.layout.fragment_horarios);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDates = view.findViewById(R.id.rvDates);
        chipGroup = view.findViewById(R.id.chipGroupHorarios);
        txtMes = view.findViewById(R.id.txtMesAtual);
        txtDataSel = view.findViewById(R.id.txtDataSelecionada);

        setupCalendar();
        setupTimeSlots();

        view.findViewById(R.id.btnConfirmarHorario).setOnClickListener(v -> {
            int checkedId = chipGroup.getCheckedChipId();
            if (checkedId != -1) {
                Chip chip = view.findViewById(checkedId);
                Toast.makeText(getContext(), "Agendado para: " + chip.getText(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Selecione um horário", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(2026, Calendar.AUGUST, 1); // Forçar Agosto 2026 conforme solicitado

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", new Locale("pt", "BR"));
        txtMes.setText(monthFormat.format(cal.getTime()));

        List<DateAdapter.DayInfo> days = new ArrayList<>();
        SimpleDateFormat abbrevFormat = new SimpleDateFormat("EEE", new Locale("pt", "BR"));
        SimpleDateFormat fullFormat = new SimpleDateFormat("EEEE, dd 'de' MMMM", new Locale("pt", "BR"));
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDays; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i);
            String abbrev = abbrevFormat.format(cal.getTime()).substring(0, 3).toUpperCase();
            days.add(new DateAdapter.DayInfo(
                    abbrev,
                    i,
                    sqlFormat.format(cal.getTime()),
                    fullFormat.format(cal.getTime())
            ));
        }

        rvDates.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvDates.setAdapter(new DateAdapter(days, day -> {
            txtDataSel.setText(day.displayFull);
            // Aqui poderia filtrar horários ocupados no banco
        }));

        // Seleção inicial
        if (!days.isEmpty()) {
            txtDataSel.setText(days.get(0).displayFull);
        }
    }

    private void setupTimeSlots() {
        chipGroup.removeAllViews();
        String[] times = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        for (String time : times) {
            Chip chip = new Chip(getContext());
            chip.setText(time);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setChipStrokeColorResource(R.color.primary);
            chip.setChipStrokeWidth(1f);
            chipGroup.addView(chip);
        }
    }
}
