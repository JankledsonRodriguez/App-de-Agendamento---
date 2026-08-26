# Plano de Implementação: Redefinição de Senha com Código de Verificação

Este plano detalha a adição de uma etapa de segurança no fluxo de recuperação de senha: a verificação de um código enviado por e-mail (simulado) antes de permitir a criação da nova senha.

## User Review Required

> [!IMPORTANT]
> O processo de redefinição de senha agora exigirá um código de 6 dígitos. Para fins de teste, o código será gerado e exibido via `Toast` na tela anterior, simulando o recebimento por e-mail.

## Proposed Changes

### [Navegação e Fluxo]

#### [MODIFY] [ForgotPasswordActivity.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ForgotPasswordActivity.java)
- Gerar um código aleatório de 6 dígitos.
- Passar o e-mail e o código gerado para a próxima tela (`ResetPasswordActivity`).
- Exibir o código em um `Toast` para que o usuário possa usá-lo (simulação de e-mail).

---

### [Interface do Usuário (Layout)]

#### [MODIFY] [activity_reset_password.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/activity_reset_password.xml)
- Adicionar um campo `TextInputLayout` para o "Código de Verificação".
- Organizar o layout para que os campos de senha fiquem inicialmente ocultos ou desabilitados, aparecendo apenas após a validação do código.

---

### [Lógica (Java)]

#### [MODIFY] [ResetPasswordActivity.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ResetPasswordActivity.java)
- Implementar a lógica de "Avançar":
    1. O usuário insere o código recebido.
    2. O app verifica se o código bate com o que foi "enviado".
    3. Se correto, o app libera os campos de nova senha e confirmação.
    4. Ao final, salva a senha no banco de dados e retorna ao Login.

## Verification Plan

### Manual Verification
1. No Login, clicar em "Esqueceu a senha?".
2. Inserir o e-mail e clicar em "Enviar".
3. Observar o código gerado no `Toast`.
4. Na tela de redefinição, inserir o código e clicar em "Verificar Código".
5. Confirmar que os campos de senha foram liberados.
6. Definir a nova senha e verificar se o retorno ao Login ocorre com sucesso.
