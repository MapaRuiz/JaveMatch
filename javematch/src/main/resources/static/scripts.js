//Registro
document.getElementById('registerForm')?.addEventListener('submit', async function(event) {
    event.preventDefault();

    const nombre = document.getElementById('name').value;
    const correo = document.getElementById('email').value;
    const interesesSeleccionados = Array.from(document.querySelectorAll('#interests input:checked'))
        .map(checkbox => checkbox.value);
    const planMapping = {
        "Bronze": 1,
        "Silver": 2,
        "Gold": 3
    };

    const planName = document.getElementById('plan').value;
    const plan_id = planMapping[planName];
    const usuarioData = {
        nombre: nombre,
        correo: correo,
        intereses: interesesSeleccionados.map(interes => ({ nombre: interes })),
        plan: { plan_id: plan_id }
    };

    try {
        const response = await fetch('/api/usuario/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(usuarioData)
        });

        if (response.ok) {
            const data = await response.json();
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

//Login
async function loginUsuario(email) {
    try {
        const response = await fetch(`/api/usuario/login?email=${email}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const usuario = await response.json();
            alert('Login exitoso');
            localStorage.setItem('userId', usuario.userId);
            localStorage.setItem('userName', usuario.nombre);
            alert('Login exitoso');
            window.location.href = 'match.html';
            return usuario;
        } else {
            const errorData = await response.text();
            alert(errorData);
            return null;
        }
    } catch (error) {
        console.error('Error al hacer la solicitud de login:', error);
        alert('Hubo un problema al intentar hacer login');
        return null;
    }
}

document.getElementById('loginForm')?.addEventListener('submit', async (event) => {
    event.preventDefault();
    const correo = document.getElementById('email').value;
    const usuario = await loginUsuario(correo);

        if (usuario) {
            alert('Login exitoso');
            localStorage.setItem('userId', usuario.userId);
            window.location.href = 'match.html';
        } else {
            alert('Login fallido');
        }
});

document.addEventListener('DOMContentLoaded', fetchUsers);
const loggedInUserId = localStorage.getItem('userId');

//Aceptar Usuarios

function acceptUser(likedUsuarioId) {
    console.log('usuarioId:', loggedInUserId);

    if (likedUsuarioId && loggedInUserId) { 
        fetch(`/api/usermatch/accept/${likedUsuarioId}?usuarioId=${loggedInUserId}`, {
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

//Rechazar Usuarios

function rejectUser(likedUsuarioId) {
    fetch(`/api/usermatch/reject/${likedUsuarioId}`, {
        method: 'POST',
    })
    .then(response => response.text())
    .then(data => {
        console.log(data);
    })
    .catch(error => console.error("Error:", error));
}

//Mostrar usuarios

let usuarios = [];
let currentIndex = 0;
async function fetchUsers() {
    try {
        const response = await fetch('/api/usuario')  
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
    console.log(user);
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
            const likedUsuarioId = user.userId;
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
            rejectUser(user.userId);
            currentIndex++;
            if (currentIndex < usuarios.length) {
                showUser(currentIndex);
            }
        });
    }
}

//Mostrar matches

async function fetchMatches() {
    try {
        // Llamada al backend para obtener los matches mutuos del usuario logueado
        const response = await fetch(`/api/usermatch/mutual/${loggedInUserId}`);
        const matches = await response.json();
        console.log("Matches fetched:", matches);

        // Referencia al contenedor donde se mostrarán los matches
        const matchListElement = document.getElementById('user-list-match');
        matchListElement.innerHTML = ''; // Limpia la lista antes de llenarla

        // Verifica si hay matches disponibles
        if (matches.length > 0) {
            for (const match of matches) {
                // Determinar quién es el otro usuario en el match
                const otherUserId = match.user1.userId === loggedInUserId ? match.user2.userId : match.user1.userId;
                const otherUserDetails = await fetchUserDetails(otherUserId); // Llama a la función para obtener los detalles del usuario

                // Crear el elemento para el usuario
                const userCard = document.createElement('div');
                userCard.classList.add('user-card');
                userCard.innerHTML = `
                    <h2>${otherUserDetails.nombre || 'Nombre no disponible'}</h2>
                    <p>Intereses: ${otherUserDetails.intereses.join(', ') || 'No tiene intereses disponibles'}</p>
                    <button class="call-btn">Iniciar videollamada</button>
                `;

                // Añadir la tarjeta del usuario al contenedor
                matchListElement.appendChild(userCard);

                userCard.querySelector('.call-btn').addEventListener('click', async () => {
                    try {
                        const matchId = match.userMatchId; // Obtiene el ID del match
                        const videoCallResponse = await fetch(`/api/videollamada/createWithMatch?matchId=${matchId}`, {
                            method: 'POST',
                        });
                
                        if (videoCallResponse.ok) {
                            const videoCallData = await videoCallResponse.json();
                            if (videoCallData.id) { // Verifica que el ID exista
                                alert(`Videollamada creada exitosamente con ID: ${videoCallData.id}`);
                                localStorage.setItem('matchId', matchId); // Guarda el ID del match
                                window.location.href = `videollamada.html`;
                            } else {
                                alert("No se pudo obtener el ID de la videollamada.");
                            }
                        } else {
                            alert("Hubo un error al crear la videollamada.");
                        }
                    } catch (error) {
                        console.error("Error al crear videollamada:", error);
                        alert("Ocurrió un error al intentar crear la videollamada.");
                    }
                });
            }
        } else {
            // Si no hay matches, mostrar un mensaje
            matchListElement.innerHTML = '<p>No hay coincidencias disponibles.</p>';
        }
    } catch (error) {
        console.error("Error fetching matches:", error);
        const matchListElement = document.getElementById('user-list-match');
        matchListElement.innerHTML = '<p>Error al cargar coincidencias. Intenta de nuevo más tarde.</p>';
    }
}

async function fetchUserDetails(userId) {
    try {
        const response = await fetch(`/api/usuario/${userId}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching user details:", error);
        return { nombre: 'Nombre no disponible', intereses: [] };
    }
}

fetchMatches();

// Función para redirigir a la página de videollamada
function startVideoCall(userId) {
    console.log(`Iniciando videollamada con el usuario ${userId}`);
    window.location.href = `videollamada.html?userId=${userId}`;
}

// Llama a la función para cargar los matches
document.addEventListener('DOMContentLoaded', initializePage);
//Mostrar perfil
async function initializePage() {
    const userId = localStorage.getItem('userId');
    if (userId) {
        try {
            const response = await fetch(`/api/usuario/${userId}`);
            const userData = await response.json();

            console.log(userData);
            document.getElementById('userName').textContent = userData.nombre;
            document.getElementById('userFullName').textContent = userData.nombre;

            if (userData.plan) {
                document.getElementById('userPlan').textContent = userData.plan;
            } else {
                document.getElementById('userPlan').textContent = 'Sin plan asignado';
            }

            const interestsList = document.getElementById('userInterests');
            interestsList.innerHTML = '';

            if (userData.intereses && Array.isArray(userData.intereses)) {
                const interestsHtml = userData.intereses.map(interes => `<li>${interes}</li>`).join('');
                interestsList.innerHTML = interestsHtml;
            } else {
                interestsList.innerHTML = '<li>No tiene intereses registrados.</li>';
            }

            await loadUserNotifications(userId);

        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    }
}

//Cargar Notificaciones

async function loadUserNotifications() {
    try {
        const userId = localStorage.getItem("userId");
        const response = await fetch(`/api/notificacion/usuario/${userId}`);
        if (!response.ok) throw new Error('Error al obtener las notificaciones.');

        const notifications = await response.json();
        console.log(notifications);

        const notificationsData = document.getElementById('notificationsData');
        notificationsData.innerHTML = '';

        if (notifications.length > 0) {
            notifications.forEach(notification => {
                const notificationItem = document.createElement('div');
                notificationItem.className = 'notification-item';
                notificationItem.innerHTML = `
                    <p><strong>Mensaje:</strong> ${notification.mensaje}</p>
                    <p><strong>Fecha:</strong> ${new Date(notification.fechaEnvio).toLocaleString()}</p>
                    <hr>
                `;
                notificationsData.appendChild(notificationItem);
            });
        } else {
            notificationsData.innerHTML = '<p>No tienes notificaciones.</p>';
        }
    } catch (error) {
        console.error('Error al cargar las notificaciones:', error);
    }
}

//Cambiar Plan

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
    updatePlanBtn.addEventListener("click", updatePlan);
});

//Añadir Interes

document.addEventListener("DOMContentLoaded", function () {
    // Verifica que el elemento con ID 'addInterest' exista antes de agregar el eventListener
    const addInterestButton = document.getElementById("addInterest");
    if (addInterestButton) {
        addInterestButton.addEventListener("click", async () => {
            const newInterestInput = document.getElementById("newInterest");
            const newInterestName = newInterestInput.value.trim();

            if (!newInterestName) {
                alert("Por favor, introduce un interés válido.");
                return;
            }

            const usuarioId = localStorage.getItem("userId"); 
            const interestData = {
                nombre: newInterestName,
            };

            try {
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
                    newInterestInput.value = ""; // Limpiar el campo de entrada
                    console.log("Usuario actualizado:", updatedUser);
                    await initializePage(); // Refresca la página o lo que sea necesario después de actualizar
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
    }

    // Asegúrate de que todos los demás elementos necesarios también estén disponibles
    const updatePlanBtn = document.getElementById("updatePlan");
    if (updatePlanBtn) {
        updatePlanBtn.addEventListener("click", updatePlan);
    }

    const matchListElement = document.getElementById("matchList");
    if (matchListElement) {
        matchListElement.innerHTML = ''; // O cualquier acción que necesites
    }

    // Asegúrate de que los elementos que vas a manipular existen
    const userNameElement = document.getElementById('userName');
    if (userNameElement) {
        userNameElement.textContent = "Juan Perez"; // O el nombre de usuario que corresponda
    }
    
   
});


document.addEventListener("DOMContentLoaded", function() {
    console.log("DOM completamente cargado");
   

async function MostrarVideollamada() {
    const matchId = 1; // Cambiar si es dinámico, puede ser obtenido de la URL o localStorage

    if (!matchId) {
        alert("No se ha encontrado el matchId.");
        return;
    }

    try {
        const response = await fetch(`/api/videollamada/match/${matchId}`);
        const videollamadaData = await response.json();

        if (response.ok) {
            console.log("Datos de videollamada:", videollamadaData);

            // Actualiza la fecha y el estado de la videollamada
            document.getElementById('fecha-llamada').textContent = new Date(videollamadaData.fechaVideollamada).toLocaleString();
            document.getElementById('estado-llamada').textContent = videollamadaData.estado;

            // Manejo de los usuarios del match
            const user1Details = videollamadaData.match.user1;
            const user2Details = videollamadaData.match.user2;

            const userListDiv = document.getElementById('user-list-videollamada');
            userListDiv.innerHTML = ''; // Limpia la lista antes de llenarla

            // Mostrar los detalles de los dos usuarios
            if (user1Details && user2Details) {
                userListDiv.innerHTML = `
                    <p><strong>${user1Details.nombre}</strong> (Usuario 1)</p>
                    <p><strong>${user2Details.nombre}</strong> (Usuario 2)</p>
                `;
            } else {
                // Si no se encuentran detalles, mostrar el ID de los usuarios
                userListDiv.innerHTML = `
                    <p><strong>Usuario 1 ID:</strong> ${user1Details.userId}</p>
                    <p><strong>Usuario 2 ID:</strong> ${user2Details}</p>
                `;
            }
        } else {
            alert("Error al obtener la videollamada: " + response.status);
        }
    } catch (error) {
        console.error("Error al cargar la videollamada:", error);
        alert("Ocurrió un error al cargar la videollamada.");
    }
}

});