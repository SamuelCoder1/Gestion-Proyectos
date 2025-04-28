# Gestion-Proyectos

# 📂 Gestión de Proyectos / Project Management

---

## 🔗 Clonar el repositorio / Clone the repository

```bash
git clone https://github.com/SamuelCoder1/Gestion-Proyectos.git
cd Gestion-Proyectos

🚀 Technologies & Versions
Backend

Java 17 (Amazon Corretto)

Spring Boot 3.4.5

MySQL

Frontend

Angular CLI 19.2.7

Node.js 22.15.0

npm 10.9.2

Visual Studio Code

⚙️ Prerequisites
IDE for backend: IntelliJ IDEA

JDK 17 (Amazon Corretto)

MySQL up and running

IDE for frontend: Visual Studio Code

Angular CLI globally installed:

npm install -g @angular/cli@19.2.7
🔧 Backend Setup
Open the project in IntelliJ IDEA.

Point to JDK 17 (Amazon Corretto).

Edit src/main/resources/application.properties with your MySQL credentials:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
If the database does not exist, Spring Boot will create it automatically (when credentials match).

Build & run:

./mvnw clean install
./mvnw spring-boot:run
🖥️ Frontend Setup
From the project root, cd frontend and open in Visual Studio Code.

Install dependencies:

npm install
Ensure the Java backend is running on http://localhost:8080.

Start Angular dev server:

ng serve -o
Your browser will open at http://localhost:4200.

▶️ Run Order
Backend (Spring Boot) must be running first.

Frontend (ng serve -o) connects to it.

🇪🇸 Español
🚀 Tecnologías y Versiones
Backend

Java 17 (Amazon Corretto)

Spring Boot 3.4.5

MySQL

Frontend

Angular CLI 19.2.7

Node.js 22.15.0

npm 10.9.2

Visual Studio Code

⚙️ Requisitos Previos
IDE para backend: IntelliJ IDEA

JDK 17 (Amazon Corretto)

MySQL instalado y en ejecución

IDE para frontend: Visual Studio Code

Angular CLI instalado globalmente:

npm install -g @angular/cli@19.2.7
🔧 Configuración del Backend
Abre el proyecto en IntelliJ IDEA.

Apunta al JDK 17 (Amazon Corretto).

Edita src/main/resources/application.properties con tus credenciales de MySQL:

spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
Si la base de datos no existe, Spring Boot la creará automáticamente (si las credenciales son correctas).

Compila y ejecuta:

./mvnw clean install
./mvnw spring-boot:run
🖥️ Configuración del Frontend
Desde la raíz del proyecto, cd frontend y abre en Visual Studio Code.

Instala dependencias:

npm install
Asegúrate de que el backend (Java) esté ejecutándose en http://localhost:8080.

Inicia el servidor de desarrollo de Angular:

ng serve -o
Esto abrirá automáticamente el navegador en http://localhost:4200.

▶️ Orden de Ejecución
Primero el backend (Spring Boot).

Después el frontend (ng serve -o).

📁 Estructura del Proyecto
/
├── backend/   # Servicio Spring Boot (Java 17, Corretto, Spring Boot 3.4.5)
└── frontend/  # App Angular (CLI 19.2.7, Node 22.15.0, npm 10.9.2)
🤝 Contribuciones / Contributing
Haz un fork del repositorio.

