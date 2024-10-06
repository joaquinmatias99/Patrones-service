# Patrones-service

Este microservicio clasifica patrones de caracteres en positivos y negativos, almacena los totales acumulados en una base de datos, y expone una API REST para validar los patrones y obtener estadísticas de las verificaciones realizadas.

Tecnologias y dependencias Utilizadas
- Kotlin con JDK 17
- SpringBoot
- Hibernate
- Maven
- Mysql
- Docker
- Herramienta de prueba Postman

## Funcionalidad

### A - Validar Patrones de caracteres

Dado una lista de cadenas que representa patrones de caracteres, el microservicio evalua cada uno de los elementos de la lista, de acuerdo a las siguientes reglas

- "0RH+" se considera positivo
- "0RH-" se considera negativo

Cada validación actualiza las estadísticas almacenadas en la base de datos, incrementando el total de positivos o negativos según corresponda.
En caso de encontrarse alguno de estos, el servicio devuelve un HTTP 200 OK y en caso no haber ninguno presente, se manda un HTTP 403- FORBIDDEN.

### B - Obtener estadisticas acumuladas.

Este endpoint expone las estadísticas acumuladas de todas las operaciones realizadas por el servicio. Devuelve la cantidad total de patrones positivos y negativos.

## Requisitos previos

- Tener Docker instalado listo para usar 

## Como Correr el proyecto
git ad
- Hay que tener Docker instalado
- Usamos el comando docker run
- Utilizamos la coleccion postman que se encuentra en ..."ruta"
- ¡Listo para probar!


