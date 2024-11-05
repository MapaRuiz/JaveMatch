document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/usuario')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta de la red: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            const perfilList = document.getElementById('perfil-list');
            perfilList.innerHTML = ''; // Limpiar el contenido anterior

            if (data.length === 0) {
                perfilList.innerHTML = '<p>No hay usuarios disponibles.</p>';
            } else {
                data.forEach(usuario => {
                    const div = document.createElement('div');
                    div.classList.add('perfil'); // Clase CSS para estilos
                    div.innerHTML = `
                        <h3>${usuario.nombre || 'Nombre no disponible'}</h3>
                        <p>${usuario.correo || 'Correo no disponible'}</p>
                    `;
                    perfilList.appendChild(div);
                });
            }
        })
        .catch(error => {
            console.error('Error al cargar los perfiles:', error);
            const perfilList = document.getElementById('perfil-list');
            perfilList.innerHTML = '<p>Error al cargar los perfiles. Intenta de nuevo más tarde.</p>';
        });
});
