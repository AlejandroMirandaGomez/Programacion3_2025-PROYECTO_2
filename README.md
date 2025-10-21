# Sistema de Prescripción y Despacho de Recetas

**Proyecto #1 – EIF206 Programación 3 (2025-II)**  
**Universidad Nacional – Escuela de Informática**

---

## Integrantes

- Arnaldo Jara Bartels
- Alejandro Miranda Gómez
- Keylor Segura Miranda

---

## Descripción

Este sistema de escritorio en **Java** permite la **prescripción y despacho de recetas médicas** en un hospital estatal.  
Incluye funcionalidades para médicos, farmaceutas y administradores, siguiendo una **arquitectura en capas** y el patrón **Modelo-Vista-Controlador (MVC)**.

La información se persiste en archivos **XML** y la interfaz incluye **dashboards con estadísticas gráficas**.

---

## Instrucciones de ejecución

1. Abrir el proyecto en **IntelliJ IDEA** (u otro IDE con soporte Maven).
2. Asegurarse de que las dependencias Maven se descarguen automáticamente.
3. Ejecutar el proyecto desde el IDE.

---

## Dependencias

El proyecto utiliza las siguientes librerías externas:

- [LGoodDatePicker](https://github.com/LGoodDatePicker/LGoodDatePicker) – `com.github.lgooddatepicker:LGoodDatePicker:11.2.1`
- [JFreeChart](https://www.jfree.org/jfreechart/) – `org.jfree:jfreechart:1.5.4`
- [Jakarta XML Bind API](https://projects.eclipse.org/projects/ee4j.jaxb) – `jakarta.xml.bind:jakarta.xml.bind-api:4.0.0`
- [JAXB Impl](https://javaee.github.io/jaxb-v2/) – `com.sun.xml.bind:jaxb-impl:4.0.0`

---

## Archivos de datos

El sistema persiste la información en el archivo:

```
Programacion3_2025-PROYECTO_1/data/data.xml
```

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

## Funcionalidades principales

- **Login y gestión de usuarios** (administradores, médicos y farmaceutas).
- **Prescripción de recetas** por parte de los médicos.
- **Despacho de recetas** por parte de los farmaceutas.
- **Gestión de listas**: médicos, farmaceutas, pacientes y medicamentos.
- **Dashboard de estadísticas**:
  - Cantidad de medicamentos prescritos por mes (**gráfico de línea**).
  - Estado de recetas (**gráfico de pastel**).
  - Histórico de recetas con detalles de búsqueda y consulta.

---

## Repositorio en GitHub

Puedes copiar o ver el repositorio en el siguiente enlace:  
[Programacion3_2025-PROYECTO_1](https://github.com/AlejandroMirandaGomez/Programacion3_2025-PROYECTO_1.git)
