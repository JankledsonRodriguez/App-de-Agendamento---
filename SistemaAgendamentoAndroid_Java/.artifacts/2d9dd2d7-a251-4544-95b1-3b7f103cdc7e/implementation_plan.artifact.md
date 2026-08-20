# Implementation Plan - Configure Schedule Structure

The user wants to configure the `fragment_horarios.xml` layout to display a schedule structure including date, time, and day of the week, specifically focused on the current month (August 2026).

## User Review Required

> [!IMPORTANT]
> I will update the layout to include:
> - A dynamic month label (e.g., "Agosto 2026").
> - A horizontal date picker showing days of the current month.
> - A grid of available time slots.
> - I will also implement the logic in `HorariosFragment.java` to populate these views dynamically using the current system date.

## Proposed Changes

### UI Resources

#### [MODIFY] [fragment_horarios.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/SistemaAgendamentoAndroid_Java/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/fragment_horarios.xml)
- Add `android:id="@+id/txtMesAtual"` to the month TextView.
- Update placeholder text to "Agosto 2026".
- Add a new `RecyclerView` or `ChipGroup` for time slots if the current one is insufficient.
- Add a `TextView` to show the selected date details (e.g., "Terça-feira, 18 de Agosto").

#### [NEW] [item_date.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/SistemaAgendamentoAndroid_Java/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/item_date.xml)
- A simple layout for the horizontal date selector showing the day number and day abbreviation.

### Java Logic

#### [MODIFY] [HorariosFragment.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/SistemaAgendamentoAndroid_Java/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/HorariosFragment.java)
- Implement `onViewCreated` to:
    - Set the current month name in the header.
    - Generate a list of days for the current month.
    - Setup a `RecyclerView` with a custom adapter for the date selector.
    - Setup the `ChipGroup` with predefined time slots (e.g., 08:00 to 18:00).
    - Handle clicks on dates to update the available times (logic for now can be static or filtered).

#### [NEW] [DateAdapter.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/SistemaAgendamentoAndroid_Java/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/DateAdapter.java)
- A simple adapter for the horizontal date `RecyclerView`.

## Verification Plan

### Automated Tests
- Run a Gradle build to ensure no layout or code errors.

### Manual Verification
- Deploy the app and navigate to "Horários".
- Verify that the month shown is "Agosto 2026".
- Verify the horizontal date list shows days of August.
- Verify the time slots are visible and clickable.
