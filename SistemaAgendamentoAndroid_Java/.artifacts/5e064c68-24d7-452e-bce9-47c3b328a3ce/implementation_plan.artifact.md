# Plano de Implementação: Layout em Grade para Corpo Clínico

Este plano visa padronizar a visualização do "Corpo Clínico" com o design de grade de 2 colunas já utilizado em Pacientes e Consultas, substituindo a lista simples por cards médicos profissionais.

## Proposed Changes

### [Layouts de Item]

#### [NEW] [item_medico.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/item_medico.xml)
- Criar um card elegante para os médicos.
- **Ícone:** Círculo azul com ícone de profissional de saúde.
- **Nome:** Nome do médico em destaque (negrito).
- **Especialidade:** Texto secundário com a área de atuação.
- **CRM:** Número de registro profissional.

---

### [Lógica de UI]

#### [NEW] [MedicoAdapter.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/MedicoAdapter.java)
- Adaptador específico para a classe `Medico`.
- Substituir o uso do `SimpleAdapter` genérico.

#### [MODIFY] [CorpoClinicoFragment.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/CorpoClinicoFragment.java)
- Trocar `LinearLayoutManager` por `GridLayoutManager(requireContext(), 2)`.
- Atualizar a lógica de carregamento para instanciar o novo `MedicoAdapter`.
- Remover a concatenação manual de strings que era enviada para o `SimpleAdapter`.

## Verification Plan

### Manual Verification
1. Abrir a aba **Corpo Clínico** (ou via Menu).
2. Verificar se a lista agora aparece em 2 colunas.
3. Validar se os nomes, especialidades e CRMs estão bem distribuídos nos novos cards.
4. Testar o filtro por especialidade (vindo da tela de Especialidades) para garantir que a grade se mantém correta.
