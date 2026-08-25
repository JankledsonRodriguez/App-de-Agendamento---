package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.agendamento.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HorariosFragment extends Fragment {
    private ChipGroup chipGroup;
    private TextView txtDataSel;
    private CalendarView calendarView;

    public HorariosFragment() {
        super(R.layout.fragment_horarios);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);
        chipGroup = view.findViewById(R.id.chipGroupHorarios);
        txtDataSel = view.findViewById(R.id.txtDataSelecionada);

        setupCalendar();
        setupTimeSlots();

        view.findViewById(R.id.btnConfirmarHorario).setOnClickListener(v -> {
            int checkedId = chipGroup.getCheckedChipId();
            if (checkedId != -1) {
                Chip chip = view.findViewById(checkedId);
                Toast.makeText(getContext(), "Consulta agendada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Selecione um horário", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd 'de' MMMM", new Locale("pt", "BR"));
        
        // Data inicial
        Calendar initial = Calendar.getInstance();
        txtDataSel.setText(sdf.format(initial.getTime()));

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            txtDataSel.setText(sdf.format(selected.getTime()));
        });
    }

    private void setupTimeSlots() {
        chipGroup.removeAllViews();
        String[] times = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        for (String time : times) {
            Chip chip = new Chip(requireContext());
            chip.setText(time);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setChipBackgroundColorResource(android.R.color.white);
            chip.setChipStrokeColorResource(R.color.primary);
            chip.setChipStrokeWidth(1f);
            chipGroup.addView(chip);
        }
    }
}
