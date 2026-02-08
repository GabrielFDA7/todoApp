# ToDo List App 📝

Aplicativo de lista de tarefas desenvolvido em **Kotlin** com **Jetpack Compose**, integrado com **Firebase Authentication** para autenticação de usuários.

---

## 📱 Funcionalidades

- **Autenticação de Usuários**
  - Login com email e senha
  - Cadastro de novos usuários
  - Logout

- **Gerenciamento de Tarefas**
  - Criar novas tarefas
  - Editar tarefas existentes
  - Marcar como concluída
  - Excluir tarefas

- **Persistência por Usuário**
  - Cada usuário vê apenas suas próprias tarefas
  - Dados persistem entre sessões

---

## 📸 Telas

| Tela | Descrição |
|------|-----------|
| **Login** | Autenticação com email/senha, link para cadastro |
| **Sign Up** | Cadastro com email, senha e confirmação |
| **Lista de Tarefas** | Exibe todas as tarefas do usuário logado |
| **Adicionar/Editar** | Formulário para criar ou editar uma tarefa |

---

## 🏗️ Arquitetura

O projeto segue a arquitetura **MVVM (Model-View-ViewModel)** com separação clara de camadas:

```
app/
├── data/                    # Camada de Dados
│   ├── auth/                # Autenticação Firebase
│   │   ├── AuthRepository.kt
│   │   └── AuthRepositoryImpl.kt
│   ├── TodoDao.kt           # Data Access Object (Room)
│   ├── TodoDatabase.kt      # Configuração do banco
│   ├── TodoEntity.kt        # Entidade do banco
│   ├── TodoRepository.kt    # Interface do repositório
│   └── TodoRepositoryImpl.kt
│
├── domain/                  # Camada de Domínio
│   └── Todo.kt              # Modelo de domínio
│
├── navigation/              # Navegação
│   └── TodoNavHost.kt       # Configuração das rotas
│
└── ui/                      # Camada de Apresentação
    ├── feature/
    │   ├── login/           # Tela de Login
    │   ├── signup/          # Tela de Cadastro
    │   ├── list/            # Lista de Tarefas
    │   └── addedit/         # Adicionar/Editar Tarefa
    └── theme/               # Tema Material3
```

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|------------|-----|
| **Kotlin** | Linguagem principal |
| **Jetpack Compose** | UI declarativa |
| **Room** | Persistência local SQLite |
| **Firebase Auth** | Autenticação de usuários |
| **Navigation Compose** | Navegação type-safe |
| **ViewModel** | Gerenciamento de estado |
| **StateFlow** | Fluxo de dados reativo |
| **Coroutines** | Operações assíncronas |

---

## 🚀 Como Executar

1. Clone o repositório
2. Abra no Android Studio
3. Configure o Firebase:
   - Crie um projeto no [Firebase Console](https://console.firebase.google.com)
   - Adicione um app Android com package `com.example.todolist`
   - Baixe o `google-services.json` e coloque em `app/`
   - Ative **Email/Password** em Authentication
4. Execute no emulador ou dispositivo

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.
