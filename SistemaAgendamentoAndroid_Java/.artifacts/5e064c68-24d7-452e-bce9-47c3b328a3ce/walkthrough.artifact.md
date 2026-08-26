# Walkthrough - Redefinição de Senha com Verificação de Código

Elevamos o nível de segurança do fluxo de recuperação de acesso, adicionando uma etapa de validação via código de 6 dígitos antes de permitir a alteração da senha.

## O que foi implementado

### 1. Sistema de Código de Segurança
- Ao solicitar a recuperação na `ForgotPasswordActivity`, o sistema gera agora um **código aleatório de 6 dígitos**.
- Para fins de teste e simulação, o código é exibido em um `Toast` (balão de mensagem) logo após o clique em "Enviar Instruções".

### 2. Fluxo Passo a Passo (Wizard) na Tela de Redefinição
- Atualizamos a `activity_reset_password.xml` para funcionar em dois momentos:
    - **Passo 1 (Verificação):** O usuário vê apenas o campo para inserir o código. Os campos de senha ficam ocultos.
    - **Passo 2 (Alteração):** Somente se o código inserido for idêntico ao gerado, os campos de "Nova Senha" e "Confirmar Senha" são revelados com uma animação de transição suave.

### 3. Lógica de Validação Robusta
- **`ResetPasswordActivity.java`:** Gerencia a transição entre os passos e valida se as senhas coincidem no final do processo.
- **Segurança de Dados:** O e-mail e o código correto são passados de forma segura entre as telas para garantir que a redefinição ocorra apenas para o usuário identificado.

## Como Testar a Nova Segurança
1. Vá em **"Esqueceu a senha?"** na tela de login.
2. Digite o e-mail e clique em **"Enviar"**.
3. **Anote o código** que aparecerá no Toast preto na parte inferior da tela.
4. Na tela seguinte, insira esse código e clique em **"Verificar Código"**.
5. Observe que a tela mudará para o formulário de **Nova Senha**.
6. Digite a nova senha, confirme e clique em **"Salvar"**.

> [!TIP]
> Esta estrutura simula fielmente o que ocorre em apps de produção, onde o código é enviado por e-mail ou SMS.

> [!SUCCESS]
> **Fluxo Concluído:** A transição final redireciona o usuário para o Login, onde ele poderá usar sua nova credencial médica imediatamente.
