# UniMarket - Plataforma de Emprendimientos

## 📑 Índice

1. [Acerca de UniMarket](#-acerca-de-unimarket)
2. [Equipo de Desarrollo](#-equipo-de-desarrollo)
3. [Roles y Funcionalidades](#-roles-y-funcionalidades)
4. [Estado de Implementación](#-estado-de-implementación)
5. [Arquitectura y Tecnologías](#-arquitectura-y-tecnologías)
6. [Próximos Pasos](#-próximos-pasos)
7. [Notas de Desarrollo](#-notas-de-desarrollo)

## 🎯 Acerca de UniMarket

UniMarket es una plataforma diseñada para conectar emprendedores con compradores, facilitando la gestión de emprendimientos y la comercialización de productos. La plataforma está enfocada en proporcionar una experiencia intuitiva tanto para emprendedores que buscan promocionar sus productos como para compradores que desean explorar y adquirir productos de manera segura.

### Características Principales

- Gestión completa de emprendimientos
- Sistema de productos con variantes y especificaciones
- Gestión de pedidos y seguimiento
- Interfaz moderna y responsive
- Sistema de roles y permisos

## 👥 Equipo de Desarrollo

### Integrantes

- Andrés David Parra García
- Maria Alejandra Mantilla Coral
- Silem Nabib Villa Contreras
- Alejandro Amu
- Gerson Hurtado

## 🔑 Roles y Funcionalidades

### Roles del Sistema

#### Emprendedor

El rol de Emprendedor está diseñado para usuarios que desean promover sus productos o servicios dentro de la plataforma. Además de tener acceso a las funcionalidades básicas de un comprador, los emprendedores pueden gestionar sus propios emprendimientos.

#### Comprador

El rol de Comprador es el rol por defecto asignado a todas las nuevas cuentas. Los compradores tienen acceso a las funcionalidades básicas de la plataforma para interactuar con los emprendimientos y realizar compras.

## 📱 Estado de Implementación

### Funcionalidades Implementadas

#### Emprendedor

✅ **Gestión de Perfil**

- Visualización del perfil de emprendedor
- Lista de emprendimientos asociados
- Opción para editar información personal

✅ **Gestión de Emprendimientos**

- Creación de nuevos emprendimientos
- Edición de información básica (nombre, slogan, descripción)
- Configuración de contacto (email, teléfono)
- Personalización visual (imágenes de perfil y banner)
- Selección de categoría de emprendimiento

✅ **Gestión de Colaboradores**

- Visualización de miembros del emprendimiento
- Gestión básica de colaboradores

✅ **Gestión de Productos**

- Creación y edición de productos
- Configuración de variantes de productos
- Gestión de especificaciones técnicas
- Control de inventario con alertas de stock bajo
- Visualización de productos en el emprendimiento
- Gestión de imágenes de productos
- Sistema de reseñas y calificaciones

✅ **Gestión de Pedidos**

- Visualización de lista de pedidos con filtros
- Información de cliente y productos
- Seguimiento de estado de pedidos (Pendiente, Completado)
- Visualización de historial de pedidos

### Funcionalidades Pendientes

#### Emprendedor

❌ **Análisis y Reportes**

- Estadísticas de ventas
- Reportes de productos más vendidos
- Métricas de rendimiento
- Dashboard de análisis

#### Cliente

**❌ Exploración Básica**

- Pantalla de inicio con lista de productos
- Visualización de productos disponibles
- Navegación básica entre productos

❌ **Exploración Avanzada**

- Catálogo completo de productos
- Búsqueda y filtrado avanzado
- Visualización detallada de emprendimientos
- Sistema de categorías

❌ **Compras**

- Carrito de compras
- Proceso de checkout
- Selección de métodos de pago
- Historial de compras
- Reseñas y calificaciones

## 🏗️ Arquitectura y Tecnologías

### Stack Tecnológico

- **Frontend**: Jetpack Compose
- **Arquitectura**: MVVM con Clean Architecture
- **Gestión de Estado**: StateFlow
- **Navegación**: Sistema de rutas con argumentos
- **Inyección de Dependencias**: Hilt
- **Backend**: Directus

## 🚀 Próximos Pasos

1. Implementar sistema de carrito de compras y checkout
2. Desarrollar sistema de análisis y reportes para emprendedores
3. Implementar sistema de pagos y envíos
4. Desarrollar sistema de búsqueda y filtrado avanzado
5. Implementar sistema de reseñas y calificaciones para compradores

## 📝 Notas de Desarrollo

### Estado Actual

- Sistema de autenticación y gestión de roles completamente implementado
- Navegación y rutas configuradas para todas las funcionalidades
- Gestión de productos con soporte para variantes y especificaciones
- Sistema de pedidos con seguimiento de estado
- Interfaz de usuario moderna y responsive

### Consideraciones Técnicas

- Implementación de scroll infinito para listas largas
- Manejo de estados de carga y errores
- Validación de formularios
- Gestión de caché para datos frecuentes
- Soporte para múltiples roles y permisos
