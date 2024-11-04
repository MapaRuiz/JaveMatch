document.getElementById('add-user-btn').addEventListener('click', function() {
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const interests = document.getElementById('interests').value.split(',').map(interest => interest.trim()); // Separar intereses por comas
    const plan = document.getElementById('plan').value;

    const usuarioData = {
        nombre: name,
        correo: email,
        intereses: interests.map(interes => ({ nombre: interes })), // Crear objeto de interés
        plan: { nombre: plan } // Asumiendo que `plan` es un objeto
    };

    fetch('/api/usuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(usuarioData),
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Error al crear usuario');
    })
    .then(data => {
        console.log('Usuario creado:', data);
        // Aquí puedes añadir lógica para limpiar el formulario o mostrar un mensaje de éxito
    })
    .catch(error => {
        console.error('Error:', error);
        // Manejar errores (puedes mostrar un mensaje al usuario)
    });
});
