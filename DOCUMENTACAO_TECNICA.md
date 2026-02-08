# Documentação Técnica - ToDo List App

## 1. Modelo de Dados

### 1.1 Entidade Principal: TodoEntity

A aplicação utiliza uma única entidade para armazenar as tarefas:

```kotlin
@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val userId: String,
)
```

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador único, auto-gerado |
| `title` | String | Título da tarefa (obrigatório) |
| `description` | String? | Descrição opcional |
| `isCompleted` | Boolean | Status de conclusão |
| `userId` | String | ID do usuário (Firebase UID) |

### 1.2 Modelo de Domínio: Todo

Para desacoplar a camada de dados da UI, existe um modelo de domínio:

```kotlin
data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
)
```

---

## 2. Implementação da Persistência

### 2.1 Banco de Dados Local (Room)

A persistência local utiliza **Room**, a biblioteca de abstração SQLite do Android Jetpack.

**Configuração:**
- Banco: `todo-app` (SQLite)
- Versão: 2 (migração destrutiva habilitada)
- Padrão: Singleton via `TodoDatabaseProvider`

**DAO (Data Access Object):**
```kotlin
@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity)

    @Delete
    suspend fun delete(entity: TodoEntity)

    @Query("SELECT * FROM todos WHERE userId = :userId")
    fun getAllByUser(userId: String): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getBy(id: Long): TodoEntity?
}
```

### 2.2 Autenticação (Firebase)

O Firebase Authentication gerencia a autenticação de usuários:

- **Método**: Email/Password
- **Persistência de sessão**: Gerenciada automaticamente pelo Firebase SDK
- **ID do usuário**: `FirebaseUser.uid` usado como chave para filtrar tarefas

**Fluxo de autenticação:**
1. Usuário faz login/cadastro
2. Firebase retorna `FirebaseUser` com UID único
3. UID é usado em todas as operações de CRUD para filtrar dados

### 2.3 Isolamento de Dados por Usuário

Cada tarefa é associada ao `userId` do usuário logado:

```kotlin
// Ao inserir uma nova tarefa
val entity = TodoEntity(
    title = title,
    description = description,
    isCompleted = false,
    userId = authRepository.currentUserId!!
)

// Ao buscar tarefas
dao.getAllByUser(userId = authRepository.currentUserId!!)
```

---

## 3. Melhorias Futuras

### 3.1 Sincronização na Nuvem
- **Atual**: Dados armazenados apenas localmente (Room)
- **Melhoria**: Usar **Firebase Firestore** para sincronizar entre dispositivos

### 3.2 Autenticação Social
- **Atual**: Apenas email/senha
- **Melhoria**: Adicionar login com Google, Facebook, Apple

### 3.3 Categorias e Tags
- **Atual**: Tarefas sem categorização
- **Melhoria**: Adicionar categorias, tags, cores e prioridades

### 3.4 Lembretes e Notificações
- **Atual**: Sem sistema de lembretes
- **Melhoria**: Integrar com **AlarmManager** ou **WorkManager** para notificações

### 3.5 Modo Offline Robusto
- **Atual**: Funciona offline, mas sem sync
- **Melhoria**: Implementar queue de sincronização para operações offline

### 3.6 Injeção de Dependência
- **Atual**: Instanciação manual de repositórios
- **Melhoria**: Usar **Hilt** de forma completa para toda injeção de dependência

### 3.7 Testes Automatizados
- **Atual**: Sem testes
- **Melhoria**: Adicionar testes unitários (ViewModels) e instrumentados (UI)

---

## 4. Decisões Técnicas

| Decisão | Justificativa |
|---------|---------------|
| Room + Firebase Auth | Room para persistência local rápida; Firebase para autenticação segura sem backend próprio |
| MVVM | Separação clara entre UI e lógica de negócio, facilita testes |
| StateFlow | Fluxo de dados reativo e lifecycle-aware |
| Migração destrutiva | Simplifica desenvolvimento; em produção usaria migrações incrementais |
| userId no Room | Permite múltiplos usuários no mesmo dispositivo com dados isolados |
