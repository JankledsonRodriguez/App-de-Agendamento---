# Plano de Implementação: Especialidades Dinâmicas

Este plano visa sincronizar a tela de "Especialidades" com os médicos realmente cadastrados no sistema. A lista deixará de ser fixa e passará a ser gerada automaticamente a partir das áreas de atuação dos médicos no corpo clínico.

## Proposed Changes

### [Repositório]

#### [MODIFY] [ClinicaRepository.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/repository/ClinicaRepository.java)
- Alterar o método `listarEspecialidades()` para:
    - Realizar um `SELECT DISTINCT especialidade FROM medicos`.
    - Cruzar esses nomes com a tabela `especialidades` para obter descrições e ícones (se existirem).
    - Se uma especialidade de um médico não estiver na tabela de referências, usar uma descrição padrão (ex: "Atendimento Especializado") e um ícone genérico.

---

### [Lógica de UI]

#### [MODIFY] [EspecialidadesFragment.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/EspecialidadesFragment.java)
- Atualizar para carregar a lista de forma assíncrona (usando `Executors`), já que agora faremos uma consulta real ao banco de dados MySQL.
- Garantir que a lista se atualize sempre que a tela for aberta, refletindo novos médicos cadastrados instantaneamente.

## Verification Plan

### Manual Verification
1. Abrir a aba **Especialidades** e notar as áreas atuais.
2. Cadastrar um **Novo Médico** com uma especialidade inédita (ex: "Neurologia").
3. Voltar para a aba **Especialidades**.
4. Verificar se o card de "Neurologia" apareceu automaticamente na lista.
5. Clicar no novo card e confirmar se o médico cadastrado aparece na listagem filtrada.
