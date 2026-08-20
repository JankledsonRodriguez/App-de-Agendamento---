# Sistema de Agendamento - Java + Android Studio + MySQL

VERSÃO JAVA. NÃO POSSUI KOTLIN E NÃO POSSUI PHP.

## Estrutura
- Java
- XML
- Android Studio
- MySQL
- JDBC
- SQL
- CRUD

## Telas
1. Splash
2. Login
3. Início
4. Agendamentos
5. Clientes
6. Novo Cliente
7. Novo Agendamento
8. Serviços
9. Profissionais
10. Horários
11. Relatórios
12. Configurações

## Banco
Importe `sql/agendamento.sql` no MySQL/phpMyAdmin.

## Conexão
Edite:
`app/src/main/java/com/example/agendamento/database/DatabaseConfig.java`

Emulador:
HOST = 10.0.2.2

Celular físico:
HOST = IP do computador.

## Login
admin@agenda.com
123456

## IMPORTANTE
Esta conexão direta Android -> MySQL é para uso acadêmico. Em um sistema real, recomenda-se usar uma API.
