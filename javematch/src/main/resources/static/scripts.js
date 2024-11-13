// ---- Funciones para index.html y login.html ---- //
document.getElementById('registerForm')?.addEventListener('submit', async function(event) {
    event.preventDefault();  // Prevenir el comportamiento por defecto del formulario

    // Obtener los valores de los campos
    const nombre = document.getElementById('name').value;
    const correo = document.getElementById('email').value;
    const interesesSeleccionados = Array.from(document.querySelectorAll('#interests input:checked'))
        .map(checkbox => checkbox.value);
    const planMapping = {
        "Bronze": 1,  // "Bronze" es el plan 1
        "Silver": 2,  // "Silver" es el plan 2
        "Gold": 3     // "Gold" es el plan 3
    };

    // Obtén el nombre del plan seleccionado desde el formulario
    const planName = document.getElementById('plan').value;  // "Bronze", "Silver", "Gold"
    
    // Obtén el ID del plan usando el mapeo
    const plan_id = planMapping[planName];  // Esto te da el número correspondiente (1, 2, o 3)
    
    // Crear el objeto con los datos del usuario
    const usuarioData = {
        nombre: nombre,
        correo: correo,
        intereses: interesesSeleccionados.map(interes => ({ nombre: interes })),
        plan: { plan_id: plan_id }  // Aquí se usa el ID numérico del plan
    };

    // Realizar la solicitud POST
    try {
        const response = await fetch('/api/usuario/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(usuarioData)  // Enviar los datos como JSON
        });

        if (response.ok) {
            const data = await response.json();  // Obtener la respuesta JSON
            console.log('Usuario registrado exitosamente:', data);
            alert('Usuario registrado exitosamente');
        } else {
            const errorData = await response.json();
            console.error('Error al registrar el usuario:', errorData);
            alert('Hubo un error al registrar el usuario');
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Error al hacer la solicitud');
    }
    document.getElementById('registerForm').reset();
});

// Loguear usuario
async function loginUsuario(email) {
    try {
        const response = await fetch(`/api/usuario/login?email=${email}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const usuario = await response.json();  // Obtener los datos del usuario desde la respuesta
            alert('Login exitoso');
            localStorage.setItem('userId', usuario.id);  // Guardar el ID del usuario en localStorage
            window.location.href = 'match.html';  // Redirigir a la página de match
            return usuario;  // Devuelvo el usuario para poder hacer más acciones si es necesario
        } else {
            const errorData = await response.text();
            alert(errorData);  // Muestra el mensaje de error devuelto por el backend
            return null;  // En caso de error, no retorno ningún usuario
        }
    } catch (error) {
        console.error('Error al hacer la solicitud de login:', error);
        alert('Hubo un problema al intentar hacer login');
        return null;  // En caso de error en la solicitud, no retorno ningún usuario
    }
}

document.getElementById('loginForm')?.addEventListener('submit', async (event) => {
    event.preventDefault();  // Prevenir el envío normal del formulario
    const correo = document.getElementById('email').value;  // Obtener el correo del formulario
    const usuario = await loginUsuario(correo);  // Llamar a la función de login

        if (usuario) {
            // Si la respuesta fue exitosa, redirigir o hacer alguna otra acción
            alert('Login exitoso');
            localStorage.setItem('userId', usuario.id);  // Guardar el ID del usuario en localStorage
            window.location.href = 'match.html';  // Redirigir a la página de match
        } else {
            // Si el login falla, mostrar mensaje de error
            alert('Login fallido');
        }
});

let usuarios = [];
let currentIndex = 0;

async function fetchUsers() {
    try {
        const response = await fetch('https://8080-maparuiz-javematch-rzie4brrli7.ws-us116.gitpod.io/api/usuario')  // URL de tu API de usuarios  // Asegúrate de que esta ruta es la correcta
        usuarios = await response.json();

        if (usuarios.length > 0) {
            showUser(currentIndex);
        }
    } catch (error) {
        console.error('Error fetching users:', error);
    }
}

function showUser(index) {
    const user = usuarios[index];
    console.log(user);  // Verifica qué valores tiene el objeto user
    const userListElement = document.getElementById('user-list');

    if (user) {
        userListElement.innerHTML = `
            <div class="user-card">
                <h2>${user.nombre}</h2>
                <p>Intereses:</p>
                <ul>
                    ${user.intereses.map(interes => `<li>${interes}</li>`).join('')}
                </ul>
                <button id="accept-btn">Aceptar</button>
                <button id="reject-btn">Rechazar</button>
            </div>
        `;

        document.getElementById('accept-btn').addEventListener('click', () => {
            const likedUsuarioId = user.user_id;  // Asegúrate de que esto tiene un valor válido
            if (likedUsuarioId) {
                acceptUser(likedUsuarioId);
                currentIndex++;
                if (currentIndex < usuarios.length) {
                    showUser(currentIndex);
                }
            } else {
                console.error("Error: El ID de usuario es inválido");
            }
        });

        document.getElementById('reject-btn').addEventListener('click', () => {
            rejectUser(user.user_id);  // Usamos user.user_id aquí
            currentIndex++;
            if (currentIndex < usuarios.length) {
                showUser(currentIndex);
            }
        });
    }
}






document.addEventListener('DOMContentLoaded', fetchUsers);

// Cuando se hace click en "Aceptar"
function acceptUser(likedUsuarioId) {
    if (likedUsuarioId) {  // Asegúrate de que likedUsuarioId esté definido
        fetch(`/api/usermatch/accept/${likedUsuarioId}`, {
            method: 'POST',
        })
        .then(response => response.json())
        .then(data => {
            console.log("Match creado:", data);
        })
        .catch(error => console.error("Error:", error));
    } else {
        console.error("El ID del usuario es inválido para aceptar:", likedUsuarioId);
    }
}

// Cuando se hace click en "Rechazar"
function rejectUser(likedUsuarioId) {
    fetch(`/api/usermatch/reject/${likedUsuarioId}`, {
        method: 'POST',
    })
    .then(response => response.text())
    .then(data => {
        // Maneja la respuesta, por ejemplo, mostrar el siguiente usuario
        console.log(data);
    })
    .catch(error => console.error("Error:", error));
}


// Función para manejar el clic en Like
function handleLike(userId) {
    alert(`Has dado Like al usuario con ID ${userId}`);
    // Aquí puedes agregar la lógica para manejar el "like"
}

// Función para manejar el clic en Rechazar
function handleReject(userId) {
    alert(`Has rechazado al usuario con ID ${userId}`);
    // Aquí puedes agregar la lógica para manejar el "rechazo"
}

    
