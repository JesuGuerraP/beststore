# Proyecto BestStore (CRUD + Búsqueda)

BestStore es una aplicación web de comercio electrónico que implementa operaciones CRUD (Crear, Leer, Actualizar y Eliminar) y funcionalidades de búsqueda, utilizando Spring Boot, Spring MVC, MySQL y Thymeleaf.

## Características Principales

- **Gestión de Productos**: Los usuarios administradores pueden crear, leer, actualizar y eliminar productos en el catálogo.
- **Búsqueda de Productos**: Los usuarios finales pueden buscar productos por nombre, categoría o descripción.
- **Gestión de Órdenes**: Los usuarios administradores pueden ver, actualizar y eliminar las órdenes de los clientes.
- **Interfaz de Usuario**: Los usuarios finales pueden navegar por el catálogo de productos, buscar productos, agregar productos al carrito y realizar pedidos.

## Tecnologías Utilizadas

- **Backend**: Spring Boot, Spring MVC, Spring Data JPA, MySQL
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Infraestructura**: Docker, Kubernetes, AWS

## Cómo Comenzar

1. Clona el repositorio:
   ```
   git clone https://tu-plataforma.com/usuario/beststore.git
   ```
2. Configura la base de datos MySQL:
   - Crea una base de datos llamada `beststore`
   - Actualiza el archivo `application.properties` con tus credenciales de MySQL
3. Compila y ejecuta la aplicación:
   ```
   cd beststore
   ./gradlew bootRun
   ```
4. Accede a la aplicación en `http://localhost:8080`

## Características CRUD

- **Productos**:
  - Crear un nuevo producto
  - Listar todos los productos
  - Actualizar un producto existente
  - Eliminar un producto

- **Órdenes**:
  - Listar todas las órdenes
  - Actualizar el estado de una orden
  - Eliminar una orden

## Búsqueda de Productos

Los usuarios finales pueden buscar productos por nombre, categoría o descripción utilizando el formulario de búsqueda en la página principal.

## Contribuir

Si deseas contribuir al proyecto BestStore, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tu función o corrección.
3. Realiza los cambios y asegúrate de que todas las pruebas pasan.
4. Envía un pull request con una descripción detallada de tus cambios.

## Licencia

Este proyecto se distribuye bajo la [Licencia MIT](LICENSE).
