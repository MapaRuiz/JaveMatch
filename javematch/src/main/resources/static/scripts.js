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
            localStorage.setItem('userId', usuario.userId);// Guardar el ID del usuario en localStorage
            localStorage.setItem('userName', usuario.nombre);
            alert('Login exitoso');  
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
            localStorage.setItem('userId', usuario.userId);  // Guardar el ID del usuario en localStorage
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
        const response = await fetch('https://8080-maparuiz-javematch-rzie4brrli7.ws-us116.gitpod.io/api/usuario')  
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
            const likedUsuarioId = user.userId;  // Asegúrate de que esto tiene un valor válido
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
            rejectUser(user.userId);  // Usamos user.user_id aquí
            currentIndex++;
            if (currentIndex < usuarios.length) {
                showUser(currentIndex);
            }
        });
    }
}

const loggedInUserId = localStorage.getItem('userId');

// Función principal para obtener y mostrar los "matches"
async function fetchMatches() {
    try {
        const response = await fetch(`https://8080-maparuiz-javematch-rzie4brrli7.ws-us116.gitpod.io/api/usermatch/mutual/${loggedInUserId}`);
        const matches = await response.json();
        console.log("Matches fetched:", matches);

        const matchListElement = document.getElementById('user-list-match');
        matchListElement.innerHTML = '';

        if (matches.length > 0) {
            for (const match of matches) {
                const user2Details = await fetchUserDetails(match.user2);

                const userCard = document.createElement('div');
                userCard.classList.add('user-card');
                userCard.innerHTML = `
                    <h2>${user2Details.nombre || 'Nombre no disponible'}</h2>
                    <p>Intereses: ${user2Details.intereses.join(', ') || 'No tiene intereses disponibles'}</p>
                    <button class="call-btn">Iniciar videollamada</button>
                `;

                matchListElement.appendChild(userCard);

                userCard.querySelector('.call-btn').addEventListener('click', () => {
                    startVideoCall(match.user2.userId);
                });
            }
        } else {
            matchListElement.innerHTML = '<p>No hay coincidencias disponibles.</p>';
        }
    } catch (error) {
        console.error("Error fetching matches:", error);
        const matchListElement = document.getElementById('user-list-match');
        matchListElement.innerHTML = '<p>Error al cargar coincidencias. Intenta de nuevo más tarde.</p>';
    }
}

// Función para obtener los detalles del usuario
async function fetchUserDetails(userId) {
    try {
        const response = await fetch(`https://8080-maparuiz-javematch-rzie4brrli7.ws-us116.gitpod.io/api/usuario/${userId}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching user details:", error);
        return { nombre: 'Nombre no disponible', intereses: [] };
    }
}

// Función para redirigir a la página de videollamada
function startVideoCall(userId) {
    console.log(`Iniciando videollamada con el usuario ${userId}`);
    window.location.href = `videollamada.html?userId=${userId}`;
}

// Llama a la función para cargar los matches
fetchMatches();


document.addEventListener('DOMContentLoaded', fetchUsers);

function acceptUser(likedUsuarioId) {
    const usuarioId = Number(localStorage.getItem('userId')); // Obtener el ID del usuario logueado desde localStorage
    console.log('usuarioId:', usuarioId);

    if (likedUsuarioId && usuarioId) { 
        fetch(`/api/usermatch/accept/${likedUsuarioId}?usuarioId=${usuarioId}`, {
            method: 'POST',
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error al aceptar usuario");
            }
            return response.json();
        })
        .then(data => {
            console.log("Match creado y notificación enviada:", data);
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

document.addEventListener('DOMContentLoaded', initializePage);

async function initializePage() {
    const userId = localStorage.getItem('userId');
    if (userId) {
        try {
            // Obtener datos del usuario de la API
            const response = await fetch(`/api/usuario/${userId}`);
            const userData = await response.json();

            console.log(userData);  // Verifica qué contiene userData

            // Saludo personalizado y detalles del usuario
            document.getElementById('userName').textContent = userData.nombre;
            document.getElementById('userFullName').textContent = userData.nombre;

            // Verificar si 'userData.plan' tiene el atributo 'nombre' y mostrarlo
            if (userData.plan) {
                document.getElementById('userPlan').textContent = userData.plan;  // Asegúrate de que `plan` tenga un valor
            } else {
                document.getElementById('userPlan').textContent = 'Sin plan asignado';
            }

            // Lista de intereses
            const interestsList = document.getElementById('userInterests');
            interestsList.innerHTML = ''; // Limpiar la lista antes de agregar los nuevos intereses

            // Verificamos si 'userData.intereses' existe y contiene elementos
            if (userData.intereses && Array.isArray(userData.intereses)) {
                const interestsHtml = userData.intereses.map(interes => `<li>${interes}</li>`).join('');
                interestsList.innerHTML = interestsHtml;
            } else {
                interestsList.innerHTML = '<li>No tiene intereses registrados.</li>';
            }

        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const updatePlanBtn = document.getElementById("updatePlanBtn");
    const updatePlanSelect = document.getElementById("updatePlan");
    const userId = localStorage.getItem("userId");

    if (!userId) {
        console.error("No se encontró userId en el localStorage.");
        alert("Error: No se puede actualizar el plan sin identificar al usuario.");
        return;
    }

    async function updatePlan() {
        const planId = updatePlanSelect.value; 

        if (!planId) {
            alert("Por favor selecciona un plan antes de continuar.");
            return;
        }

        try {
            const response = await fetch(`/api/usuario/actualizarPlan?usuarioId=${userId}&planId=${planId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                const updatedUser = await response.json();
                console.log("Usuario actualizado:", updatedUser);

                // Actualizar la interfaz con el nuevo plan
                document.getElementById("userPlan").innerText = updatedUser.plan.nombre;
                alert("Plan actualizado con éxito.");
            } else {
                console.error("Error en la respuesta del servidor:", response.status);
                alert("Error al actualizar el plan. Inténtalo nuevamente.");
            }
        } catch (error) {
            console.error("Error en la solicitud:", error);
            alert("Hubo un problema al procesar la solicitud. Por favor, inténtalo nuevamente.");
        }
    }

    // Asignar evento al botón de actualizar
    updatePlanBtn.addEventListener("click", updatePlan);
});

document.getElementById("addInterest").addEventListener("click", async () => {
    const newInterestInput = document.getElementById("newInterest");
    const newInterestName = newInterestInput.value.trim();

    if (!newInterestName) {
        alert("Por favor, introduce un interés válido.");
        return;
    }

    const usuarioId = localStorage.getItem("userId"); 
    // Cuerpo del interés a enviar al backend
    const interestData = {
        nombre: newInterestName,
    };

    try {
        // Crear el interés y vincularlo al usuario
        const response = await fetch(`/api/usuario/addInteres?usuarioId=${usuarioId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(interestData),
        });

        if (response.ok) {
            const updatedUser = await response.json();
            alert(`Interés "${newInterestName}" añadido con éxito.`);
            newInterestInput.value = ""; 
            console.log("Usuario actualizado:", updatedUser);
            await initializePage();
        } else {
            const error = await response.json();
            console.error("Error al agregar el interés:", error);
            alert("No se pudo añadir el interés. Intenta de nuevo.");
        }
    } catch (error) {
        console.error("Error en la solicitud:", error);
        alert("Ocurrió un error al procesar la solicitud.");
    }
});