# Plano de Implementação: Relocação do Toggle de Tema para o Painel Início

Este plano visa mover o seletor de Modo Escuro/Claro do layout principal (`MainActivity`) para dentro do Painel de **Início**, resolvendo o problema de obstrução de informações em outras telas, mas mantendo a funcionalidade de alternância global.

## Proposed Changes

### [Layouts]

#### [MODIFY] [activity_main.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/activity_main.xml)
- Remover completamente o `btnThemeToggle` e seus componentes internos.
- A `MainActivity` ficará apenas com o container de fragmentos e a navegação inferior.

#### [MODIFY] [fragment_inicio.xml](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/res/layout/fragment_inicio.xml)
- Adicionar a estrutura do botão de Toggle (Pílula) ao final do `LinearLayout` principal, dentro do `ScrollView`.
- Alinhá-lo à direita com margens adequadas, garantindo que ele faça parte do conteúdo rolável do Início.

---

### [Lógica de UI]

#### [MODIFY] [MainActivity.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/MainActivity.java)
- Remover o método `setupThemeToggle()` e as referências ao botão de troca de tema.
- Manter o `applyTheme()` no `onCreate` para que o app inicie no tema correto.

#### [MODIFY] [InicioFragment.java](file:///C:/Users/jankledson59266826/AndroidStudioProjects/App-de-Agendamento---/SistemaAgendamentoAndroid_Java/app/src/main/java/com/example/agendamento/ui/InicioFragment.java)
- Implementar a lógica de clique do Toggle dentro do fragmento.
- A lógica envolverá:
    - Acessar as `SharedPreferences`.
    - Alternar o valor de `dark_mode`.
    - Chamar `AppCompatDelegate.setDefaultNightMode()`, o que reiniciará a Activity e aplicará o tema globalmente.

## Verification Plan

### Manual Verification
1. Abrir o aplicativo. Verificar se o botão de tema **não aparece** flutuando nas abas de Consultas ou Pacientes.
2. Ir para a tela de **Início**.
3. Rolar até o final da tela (se necessário) e clicar no botão de alternância de tema.
4. Confirmar se o aplicativo troca de tema corretamente em todas as seções.
5. Garantir que a preferência permanece salva ao navegar ou reiniciar o app.
