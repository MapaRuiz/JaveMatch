document.addEventListener('DOMContentLoaded', function () {
    const notificationsList = document.getElementById('notifications-list');

    // Verifica que notificationsList existe en el DOM
    if (notificationsList) {
        console.log("Cargando notificaciones...");
        fetch('/api/notificacion')
            .then(response => response.json())
            .then(notifications => {
                notifications.forEach(notification => {
                    const notificationElement = document.createElement('div');
                    notificationElement.className = "notification";
                    notificationElement.innerHTML = `<p>${notification.mensaje}</p>`;
                    notificationsList.appendChild(notificationElement);
                });
            })
            .catch(error => {
                console.error('Error al cargar las notificaciones:', error);
            });
    } else {
        console.log("Elemento notifications-list no encontrado en la página.");
    }
});
