# Plano de Implementação: Especialidades Médicas

Transformar a seção de "Serviços" em uma estrutura moderna e sofisticada de "Especialidades Médicas", adaptando o layout, a navegação e a persistência para o domínio de consultas médicas.

## User Review Required

> [!IMPORTANT]
> Vou renomear a seção de "Serviços" para "Especialidades" em todo o app. Isso inclui a troca de nomes em arquivos, layouts e no Menu principal.

## Proposed Changes

### [Nomenclatura e Navegação]

#### [MODIFY] [strings.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/values/strings.xml)
- Alterar "Serviços" para "Especialidades".

#### [MODIFY] [MenuFragment.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/MenuFragment.java)
- Atualizar a navegação do botão `btnServicos` para abrir o novo `EspecialidadesFragment`.

---

### [Model e Repositório]

#### [NEW] [Especialidade.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/model/Especialidade.java)
- Classe com `id`, `nome`, `descricao` e `iconeResId`.

#### [MODIFY] [AgendamentoRepository.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/repository/AgendamentoRepository.java)
- Adicionar método para listar as especialidades médicas (ex: Cardiologia, Pediatria, Dermatologia, etc.).

---

### [Layouts e UI]

#### [NEW] [item_especialidade.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/item_especialidade.xml)
- Layout moderno com um Card, ícone circular sofisticado e título da especialidade.

#### [MODIFY] [fragment_especialidades.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/fragment_servicos.xml)
- Ajustar o título para "Especialidades Médicas".
- Configurar o RecyclerView para exibir os itens em uma grade (Grid) para um visual mais sofisticado.

#### [NEW] [EspecialidadeAdapter.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/EspecialidadeAdapter.java)
- Adaptador customizado para exibir as especialidades com ícones e cores variadas.

## Verification Plan

### Manual Verification
1. Abrir o menu e clicar em "Especialidades".
2. Verificar se a grade de especialidades (Cardiologia, etc.) é exibida com o design moderno.
3. Garantir que o visual esteja sofisticado e alinhado com o restante do app.
