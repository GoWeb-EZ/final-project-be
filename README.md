```mermaid
classDiagram
      Application --* Filter
      Application --* GlobalExceptionHandler
      Application --* Note
      Application --* User
      Filter --* AuthenticationFilter
      User --* Auth
      Auth --* AuthController
      Auth --* AuthService
      Auth --* AuthRepository
      Image --* ImageController
      Image --* ImageService
      Image --* ImageRepository
      Note --* Image
      Note --* NoteController
      Note --* NoteService
      Note --* NoteRepository
      User --* UserController
      User --* UserService
      User --* UserRepository
      AuthenticationFilter --* TokenManager
      AuthController -- OAuthManager
      AuthService --* OAuthManager
      ImageService --* ImageManager
      ImageController --* ImageManager
      GlobalExceptionHandler --* KakaoWorkBotManager

      


    
      
      
```
