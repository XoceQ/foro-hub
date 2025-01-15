<h1>Foro Hub API 🚀</h1>
<p>The Foro Hub API is designed to manage topics, providing key functionalities for creating, viewing, updating, and deleting them. This project aims to implement a robust REST API that adheres to best practices, ensuring precise validations, secure authentication, and a relational database for data persistence.</p>

<h2>Key Features ✨</h2>
## Key Features 🚀

### User Profile CRUD Operations 

- **GET /user**: Retrieves a list of all users from the database.
- **PUT /user**: Updates the data of an existing user.
- **POST /user**: Registers a new user in the database.
- **GET /user/{id}**: Retrieves the user’s registration details by their unique ID.
- **DELETE /user/{id}**: Marks a user as inactive, effectively removing them from the active users list.

### Authentication 🔐

- **POST /login**: Obtains a JWT token for the specified user, which is required for accessing protected endpoints. The token is used for authentication in subsequent requests.

### Topic Controller 📝

- **GET /topics**: Retrieves a list of all topics stored in the database.
- **PUT /topics**: Updates the data of an existing topic.
- **POST /topics**: Registers a new topic in the database.
- **GET /topics/{id}**: Retrieves the details of a specific topic using its unique ID.
- **DELETE /topics/{id}**: Marks a topic as inactive, effectively removing it from the active list of topics.

### Register Controller 📝

- **POST /register**: Allows for the registration of a new user profile.


<h2>Additional Functionalities 🛠️</h2>
<ul>
  <li>Authentication and authorization using JWT to ensure security. 🔒</li>
  <li>Interactive documentation with Swagger. 📑</li>
</ul>

<h2>Technologies and Versions 🧑‍💻</h2>
<ul>
  <li><strong>Java</strong>: Version 17. ☕</li>
  <li><strong>Maven</strong>: Version 4 or later. ⚙️</li>
  <li><strong>Spring Boot</strong>: Version 3.2.3. 🌱</li>
  <li><strong>MySQL</strong>: Version 8 or later. 🐬</li>
</ul>

<h2>Dependencies 📦</h2>
<p>The following tools and libraries are included to ensure the proper functioning of the API:</p>
<ul>
  <li><strong>Lombok</strong>: Simplifies code. ⚡</li>
  <li><strong>Spring Web</strong>: For implementing controllers and web services. 🌐</li>
  <li><strong>Spring Boot DevTools</strong>: Development tools. 🔧</li>
  <li><strong>Spring Data JPA</strong>: For data persistence with JPA. 💾</li>
  <li><strong>Flyway Migration</strong>: Database migration management. 🛠️</li>
  <li><strong>MySQL Driver</strong>: For connecting to MySQL. 🔌</li>
  <li><strong>Validation</strong>: For data validations. ✅</li>
  <li><strong>Spring Security</strong>: For API security. 🔐</li>
  <li><strong>Spring Doc</strong>: For auto-generating API documentation with Swagger. 📚</li>
  <li><strong>Auth0 (Java JWT)</strong>: For implementing JWT authentication. 🛡️</li>
</ul>

<h2>Project Objectives 🎯</h2>
<p>This challenge aims to consolidate skills in REST API development, focusing on:</p>
<ul>
  <li>Implementing routes following REST model best practices. 🛤️</li>
  <li>Validating data according to business rules. 📏</li>
  <li>Persisting information in a relational database system. 🗄️</li>
  <li>Integrating authentication and authorization services. 🔑</li>
  <li>Creating clear and useful documentation for developers. 🖊️</li>
</ul>

