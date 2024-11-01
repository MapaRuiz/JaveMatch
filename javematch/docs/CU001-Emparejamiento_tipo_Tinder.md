# CU001 - Emparejamiento tipo Tinder

Cuando un usuario revisa el perfil de otro usuario sugerido por la aplicación, puede aceptar o rechazar la recomendación. Si el usuario acepta la recomendación, el sistema realiza un emparejamiento si hay reciprocidad. Si se rechaza, se muestra un perfil distinto mientras no se haya excedido el máximo de consultas de acuerdo con su plan de usuario.

## Guion -- Curso normal de eventos

1. **Visualización de Perfiles**: La pantalla muestra los datos del usuario actual y del usuario sugerido.
2. **Aceptación de Recomendación**: El usuario acepta la recomendación.
3. **Verificación de Reciprocidad**: El sistema verifica si el otro usuario también aceptó la recomendación.
4. **Registro de Emparejamiento**: Si hay reciprocidad, el sistema guarda la información del emparejamiento.
5. **Verificación de Límite de Consultas**: El sistema verifica si el usuario ha excedido el límite de consultas según su plan.
6. **Muestra del Siguiente Perfil**: El sistema muestra el siguiente perfil sugerido.

## Flujo alterno

1. **Rechazo de Recomendación**: El usuario rechaza la recomendación.
2. **Verificación de Límite de Consultas**: El sistema verifica si el usuario ha excedido el límite de consultas según su plan.
3. **Muestra del Siguiente Perfil**: El sistema muestra un nuevo perfil sugerido.

## Excepciones -- Respecto al flujo principal

- **Fallo en la Verificación de Reciprocidad**: Si el sistema no puede verificar si el otro usuario aceptó la recomendación, el emparejamiento no se realiza y se notifica al usuario del fallo.

- **Límite de Consultas Excedido**: Si el usuario ha excedido el límite de consultas según su plan, el sistema le sugiere una actualización de plan en lugar de mostrar un nuevo perfil.

- **Error en la Carga de Perfiles**: Si el sistema falla al cargar un nuevo perfil, se muestra un mensaje de error y el usuario tiene la opción de intentar nuevamente.