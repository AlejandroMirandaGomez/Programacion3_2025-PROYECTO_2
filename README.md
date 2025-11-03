# Sistema de Prescripción y Despacho de Recetas – Proyecto #2

**EIF206 – Programación 3 (2025-II)**  
**Universidad Nacional – Escuela de Informática**

---

## Integrantes

- Arnaldo Jara Bartels  
- Alejandro Miranda Gómez  
- Keylor Segura Miranda

---

## Resumen

Este proyecto es una evolución del Proyecto #1 y migra a una **arquitectura distribuida** con **Frontend** y **Backend** ejecutándose como procesos independientes que se comunican por **sockets** (concurrencia mediante **threads**) y con **persistencia en MySQL**. El **Backend** concentra la lógica de negocio y el acceso a datos; el **Frontend** implementa la capa de presentación (MVC) y nunca accede directamente a la base de datos.  
Además, se introduce un tercer módulo **Protocol**, que define las **clases de identidad/entidades y contratos de mensaje** compartidos entre Frontend y Backend para tipar la comunicación.

---

## Login

El sistema distingue tres tipos de usuarios: **administradores, médicos y farmaceutas**.

- **Administradores**

   - Usuario: `admin`, Contraseña: `admin`
   - Usuario: `1`, Contraseña: `1`

- **Médicos y farmaceutas**
   - Al ser creados, su contraseña inicial es igual a su **ID**.
   - En el primer inicio de sesión deben ingresar el mismo valor en el campo de **ID** y en el de **contraseña**.
   - Posteriormente, pueden cambiar su clave en cualquier momento desde la opción correspondiente.

---

## Estructura del repositorio (monorepo)

```
/ 
├─ Frontend/             # Aplicación de escritorio (MVC). Presentación y controladores.
├─ Backend/              # Servidor. Lógica de negocio + capa de acceso a datos.
├─ Protocol/             # POJOs/DTOs compartidos (identidad, mensajes, enums, errores).
├─ _CosasBaseDeDatos/                   # Scripts SQL
└─ README.md             # Este archivo.
```

---

## Módulos

### 1) Backend (Servidor)
- Expone servicios para autenticación, prescripción, despacho y consultas de catálogos/listas.
- Administra la conexión con **MySQL** (único componente que accede a la BD).
- Gestiona **notificaciones asíncronas** (login/logout/mensajes) hacia Frontend conectados.

### 2) Frontend (Cliente de escritorio)
- Implementa **MVC** (Modelo–Vista–Controlador) y la interacción con usuario.
- Mantiene **una conexión** al Backend; envía peticiones y recibe notificaciones en **background**.
- Incluye área lateral «**Usuarios activos**» y vista para **mensajería**.

### 3) Protocol (Contratos compartidos)
- **Entidades/identidad**: `Usuario`, `Medico`, `Farmaceutico`, `Paciente`, `Medicamento`, `Receta`, `Prescripcion`, etc.
- **Enums y errores** compartidos, para garantizar compatibilidad de versiones.

---

## Persistencia de datos

- **Base de datos**: MySQL.  
- **Script**: `_CosasBaseDeDatos/proyecto2.sql` (DDL y datos mínimos).  

> Nota: En Proyecto #2 se **elimina la persistencia en XML** del Proyecto #1 y se sustituye por **MySQL**.

---

## Dependencias principales

> El proyecto usa **Maven**. Las dependencias están divididas por módulo.

### Backend
- **MySQL Connector**: `mysql:mysql-connector-j:8.x`

### Frontend
- **LGoodDatePicker**: `com.github.lgooddatepicker:LGoodDatePicker:11.2.1`
- **JFreeChart** (para dashboards): `org.jfree:jfreechart:1.5.4`

---

## Funcionalidades (alineadas con rúbrica)

1. **Ingreso (login)** y **cambio de clave**.  
2. **Prescripción** de recetas.  
3. **Despacho** de recetas.  
4. **Lista de Médicos**.  
5. **Lista de Farmacéuticos**.  
6. **Lista de Pacientes**.  
7. **Catálogo de Medicamentos**.  
8. **Dashboard (Indicadores)**.  
9. **Histórico de Recetas**.  
10. **Usuarios activos** (área lateral).  
11. **Mensajería**: enviar y **recibir** mensajes (ventanas emergentes).

---

## Repositorio en GitHub

Puedes copiar o ver el repositorio en el siguiente enlace:  
[Programacion3_2025-PROYECTO_2](https://github.com/AlejandroMirandaGomez/Programacion3_2025-PROYECTO_2.git)
