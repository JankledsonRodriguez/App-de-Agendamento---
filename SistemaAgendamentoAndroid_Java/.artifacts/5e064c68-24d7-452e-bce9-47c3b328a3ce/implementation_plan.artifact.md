# Plano de Reestruturação Completa: Domínio de Clínica Médica (Clinique+)

Este plano detalha a refatoração total do código-fonte e dos recursos para consolidar a transição de um sistema genérico de agendamento para um sistema especializado de **Gestão de Clínica Médica**.

## User Review Required

> [!IMPORTANT]
> Realizaremos uma refatoração em larga escala renomeando classes, arquivos Java e arquivos XML. Isso tornará o código muito mais legível e profissional. Embora a lógica de banco de dados (MySQL) permaneça a mesma para evitar migrações de esquema, os nomes no código refletirão o novo domínio médico.

## Proposed Changes

### 1. Modelos (Models)
Substituição de nomes genéricos por termos clínicos:
- `Cliente.java` -> `Paciente.java`
- `Agendamento.java` -> `Consulta.java`
- `Profissional.java` -> `Medico.java`

### 2. Repositório (Repository)
- `AgendamentoRepository.java` -> `ClinicaRepository.java`
- Atualização interna dos métodos (ex: `listarClientes()` -> `listarPacientes()`).

### 3. Interface do Usuário (UI) - Fragmentos
Renomeação de todos os fragmentos e seus respectivos layouts para consistência:
- `AgendamentosFragment` -> `ConsultasFragment` (`fragment_consultas.xml`)
- `NovoAgendamentoFragment` -> `NovaConsultaFragment` (`fragment_nova_consulta.xml`)
- `ProfissionaisFragment` -> `CorpoClinicoFragment` (`fragment_corpo_clinico.xml`)
- `NovoProfissionalFragment` -> `NovoMedicoFragment` (`fragment_novo_medico.xml`)
- `ServicosFragment` -> `EspecialidadesFragment` (`fragment_especialidades.xml`)

### 4. Navegação e IDs
- Atualização da `MainActivity.java` e `MenuFragment.java` para refletir os novos nomes de classe.
- Padronização de IDs dentro dos XMLs (ex: `recyclerAgendamentos` -> `recyclerConsultas`).

## Verification Plan

### Automated Verification
- Executar `gradle build` para garantir que todas as referências cruzadas e imports foram corrigidos.

### Manual Verification
1. Abrir o app e navegar por todas as seções (Painel, Pacientes, Consultas, Corpo Clínico, Especialidades).
2. Tentar cadastrar um novo Paciente e uma nova Consulta para validar a persistência.
3. Verificar se as telas carregam sem o erro de "Cannot resolve symbol".
