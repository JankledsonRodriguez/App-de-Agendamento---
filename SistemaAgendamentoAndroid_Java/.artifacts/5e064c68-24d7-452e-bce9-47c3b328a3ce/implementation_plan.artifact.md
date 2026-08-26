# Plano de Refatoração de Banco de Dados e Repositório (Clinique+)

Este plano visa unificar toda a camada de dados para o domínio clínico, renomeando tabelas e colunas no SQL e no Repositório para eliminar de vez termos genéricos como "clientes" e "agendamentos".

## User Review Required

> [!IMPORTANT]
> Esta alteração modificará as strings SQL no código. Para que o aplicativo continue funcionando, o banco de dados MySQL precisará ser atualizado com os novos nomes de tabela e coluna. Vou fornecer o script SQL atualizado.

## Proposed Changes

### [Repositório e SQL]

#### [MODIFY] [ClinicaRepository.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/repository/ClinicaRepository.java)
- Renomear tabelas SQL: `clientes` -> `pacientes`, `agendamentos` -> `consultas`.
- Renomear colunas SQL: `cliente_id` -> `paciente_id`, `cliente_nome` -> `paciente_nome`.
- Ajustar métodos para usar o termo `CRM` ou `registro` em vez de `telefone` para os médicos.

#### [MODIFY] [agendamento.sql](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/sql/agendamento.sql)
- Atualizar o script de criação do banco para refletir a nova nomenclatura:
    - Tabela `clientes` passa a ser `pacientes`.
    - Tabela `agendamentos` passa a ser `consultas`.
    - Coluna `cliente_id` passa a ser `paciente_id`.
    - Tabela `profissionais` pode ser renomeada para `medicos` para maior sofisticação.

### [Modelos]

#### [MODIFY] [Medico.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/model/Medico.java)
- Garantir que o campo seja `registroCRM` e não `telefone`.

---

## Script SQL Sugerido (Consolidado)

```sql
-- Criar tabelas com nomes clínicos
CREATE TABLE pacientes(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(120) NOT NULL,
 telefone VARCHAR(30) NOT NULL,
 email VARCHAR(120)
);

CREATE TABLE medicos(
 id INT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(120) NOT NULL,
 especialidade VARCHAR(100),
 crm VARCHAR(30)
);

CREATE TABLE consultas(
 id INT AUTO_INCREMENT PRIMARY KEY,
 paciente_id INT NOT NULL,
 data DATE NOT NULL,
 hora TIME NOT NULL,
 especialidade VARCHAR(120) NOT NULL,
 observacao TEXT,
 status VARCHAR(30) DEFAULT 'AGENDADO',
 FOREIGN KEY(paciente_id) REFERENCES pacientes(id)
);
```

## Verification Plan

### Manual Verification
1. Executar o Build do projeto.
2. Fornecer ao usuário o script SQL para atualização do banco MySQL local.
3. Verificar no código Java se não restou nenhuma menção a `cliente_id` ou tabelas antigas.
