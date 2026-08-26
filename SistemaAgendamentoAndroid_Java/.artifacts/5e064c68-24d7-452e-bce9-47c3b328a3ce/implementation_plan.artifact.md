# Plano de Implementação: Autocadastro de Médicos

Este plano visa criar o fluxo de autocadastro para médicos diretamente a partir da tela de login, garantindo que novos profissionais possam se registrar no sistema **Clinique+**.

## User Review Required

> [!IMPORTANT]
> Vou adicionar um novo botão "Não tem conta? Cadastre-se" na tela de login. Ao clicar, o médico será direcionado para uma nova tela de cadastro (`CadastroMedicoActivity`).

## Proposed Changes

### [Layouts]

#### [MODIFY] [activity_login.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/activity_login.xml)
- Adicionar um botão ou TextView clicável: "Não tem conta? Cadastre-se".

#### [NEW] [activity_cadastro_medico.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/activity_cadastro_medico.xml)
- Criar a tela de cadastro com o visual premium do app:
    - Campo: Nome Completo.
    - Campo: E-mail (Institucional).
    - Campo: Especialidade Médica.
    - Campo: CRM / Registro Profissional.
    - Campo: Senha de Acesso.
    - Botão: "Finalizar Cadastro".

---

### [Lógica (Java)]

#### [MODIFY] [LoginActivity.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/LoginActivity.java)
- Implementar a navegação para a `CadastroMedicoActivity`.

#### [NEW] [CadastroMedicoActivity.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/CadastroMedicoActivity.java)
- Lógica para capturar os dados e salvar no banco de dados.
- *Nota:* O cadastro inserirá os dados nas tabelas `usuarios` (para login) e `medicos` (para perfil profissional).

#### [MODIFY] [ClinicaRepository.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/repository/ClinicaRepository.java)
- Adicionar método `autocadastroMedico(nome, email, especialidade, crm, senha)` que realiza as duas inserções em uma transação.

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/AndroidManifest.xml)
- Registrar a nova Activity.

## Verification Plan

### Manual Verification
1. Abrir a tela de Login e verificar o novo botão de cadastro.
2. Clicar no botão e validar se a tela de Cadastro de Médico abre com o visual correto.
3. Preencher todos os campos e realizar o cadastro.
4. Tentar fazer login com as novas credenciais criadas.
