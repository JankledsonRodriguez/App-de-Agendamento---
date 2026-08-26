# Plano de Limpeza Final e Consolidação de Branding (Clinique+)

Este plano visa realizar a última etapa de "limpeza" do projeto, removendo redundâncias, centralizando recursos de texto e corrigindo inconsistências de marca remanescentes, conforme as mensagens de erro e o logcat apresentados.

## User Review Required

> [!IMPORTANT]
> Vou mover todas as mensagens de texto que ainda estão "soltas" nos arquivos de layout para o arquivo central `strings.xml`. Isso resolve avisos do Android Studio e organiza melhor o projeto. Também atualizarei o nome oficial do app no sistema (Manifest) para **Clinique+**.

## Proposed Changes

### [Recursos Globais]

#### [MODIFY] [strings.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/values/strings.xml)
- Adicionar todas as novas strings extraídas dos layouts de cadastro e login.
- Padronizar os nomes de recursos para o domínio médico.

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/AndroidManifest.xml)
- Alterar `android:label` para `@string/app_name` (Clinique+), corrigindo a última referência a "Agenda Fácil".

---

### [Refatoração de Layouts]

#### [MODIFY] Todos os Arquivos XML em `res/layout`
- Substituir textos fixos por referências `@string/...`. Isso inclui:
    - Botões de "Finalizar Cadastro".
    - Dicas (hints) de campos como "CRM" e "Especialidade".
    - Títulos de telas como "Verificação" e "Recuperação".

---

### [Limpeza de Arquivos]

#### [DELETE] Pastas Vazias e Temporárias
- Remover pastas físicas redundantes se encontradas (como `layout-land` vazia).
- Garantir que não existam classes duplicadas em pacotes de teste que possam confundir a visualização do IDE.

## Verification Plan

### Manual Verification
1. Abrir o `AndroidManifest.xml` e verificar se o nome do app está correto.
2. Abrir qualquer layout e confirmar que não há mais "hardcoded strings" (textos em amarelo).
3. Executar um **Rebuild Project** para garantir que a compactação e alinhamento do pacote (zipalign) sejam refeitos, o que deve mitigar os erros de `PackageManager` no Logcat.
