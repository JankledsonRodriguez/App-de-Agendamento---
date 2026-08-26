# Walkthrough - Autocadastro de Médicos no Clinique+

Implementamos o fluxo completo de autocadastro para que novos médicos possam se registrar no sistema diretamente pela tela de login, mantendo a identidade visual premium e a integridade dos dados.

## O que foi implementado

### 1. Novo Ponto de Entrada no Login
- Adicionamos o botão **"Não tem conta? Cadastre-se"** logo abaixo das opções de acesso.
- Configuramos a navegação para abrir a nova tela de cadastro médico.

### 2. Tela de Cadastro de Médicos (`Clinique+ Style`)
- Criamos a `activity_cadastro_medico.xml` com um formulário completo e sofisticado:
    - **Dados Pessoais:** Nome completo e E-mail Institucional.
    - **Dados Profissionais:** Especialidade Médica e CRM/Registro.
    - **Segurança:** Definição de senha com suporte a visualização (toggle).
    - **Visual:** Card centralizado com cantos arredondados (`24dp`) sobre fundo institucional azul.

### 3. Lógica de Autocadastro (Transação Segura)
- **`CadastroMedicoActivity.java`:** Gerencia a captura dos dados e valida se todos os campos foram preenchidos.
- **`ClinicaRepository.java`:** Criamos o método `autocadastroMedico`. Ele utiliza uma **transação SQL** para garantir que o médico seja cadastrado corretamente em duas tabelas ao mesmo tempo:
    - `usuarios`: Para permitir que ele faça login no sistema.
    - `medicos`: Para manter seu perfil profissional completo.

## Detalhes Técnicos
- **Activity:** `CadastroMedicoActivity` registrada no `AndroidManifest.xml`.
- **Integridade:** O uso de `setAutoCommit(false)` no repositório garante que, se houver falha em qualquer parte do cadastro, nada seja salvo pela metade, evitando dados "órfãos".

## Como Testar
1. Na tela de **Login**, clique em **"Cadastre-se"**.
2. Preencha o formulário com os dados do novo médico.
3. Clique em **"Finalizar Cadastro"**.
4. O sistema retornará à tela de login e você já poderá acessar com o e-mail e senha recém-criados.

> [!TIP]
> O design utiliza `TextInputLayout` com ícones dinâmicos, proporcionando uma experiência de preenchimento rápida e intuitiva.

> [!SUCCESS]
> **Build Status:** Sucedido. O fluxo de registro está totalmente operacional e integrado ao banco de dados MySQL.
