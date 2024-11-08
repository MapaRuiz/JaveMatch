const API_URL = 'http://localhost:8080/api';

// Función para manejar solicitudes genéricas
async function apiRequest(endpoint, method = 'GET', body = null) {
    try {
        const response = await fetch(`${API_URL}${endpoint}`, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: body ? JSON.stringify(body) : null,
        });

        if (response.ok) {
            return await response.json();
        } else {
            const errorData = await response.json();
            console.error('Error en la solicitud:', response.status, errorData);
            alert(`Error en la solicitud: ${response.status}. Detalles: ${errorData.message || 'No especificado'}`);
            return null;
        }
    } catch (error) {
        console.error('Error de conexión:', error);
        alert('Error de conexión. Inténtalo de nuevo.');
        return null;
    }
}


// ---- Funciones para index.html y login.html ---- //

// Registrar usuario
async function registerUsuario(usuarioData) {
    const response = await apiRequest('/usuario/register', 'POST', usuarioData);
    if (response) {
        alert('Usuario registrado exitosamente');
    } else {
        alert('Error al registrar el usuario');
    }
    return response;
}

// Loguear usuario
async function loginUsuario(email) {
    const response = await apiRequest(`/usuario/login?email=${email}`, 'POST');
    if (response) {
        alert('Login exitoso');
    } else {
        alert('Error en el login');
    }
    return response;
}

// ---- Funciones para videollamada.html ---- //

// Obtener una videollamada por ID
async function getVideollamadaById(id) {
    const response = await apiRequest(`/videollamada/${id}`, 'GET');
    if (response) {
        alert('Videollamada obtenida correctamente');
    } else {
        alert('Videollamada no encontrada');
    }
    return response;
}

// Agregar un juego a una videollamada existente
async function addJuegoToVideollamada(videollamadaId, juegoData) {
    const response = await apiRequest(`/videollamada/${videollamadaId}/addJuego`, 'POST', juegoData);
    if (response) {
        alert('Juego añadido a la videollamada');
    } else {
        alert('Error al añadir el juego');
    }
    return response;
}

// Función para crear un reporte
async function createReporte(reporteData) {
    const response = await apiRequest('/reporte/createReport', 'POST', reporteData);
    if (response) {
        alert('Reporte creado exitosamente');
    } else {
        alert('Error al crear el reporte');
    }
    return response;
}

// ---- Funciones para perfil.html ---- //

// Función para obtener el perfil del usuario
async function getUsuarioById(usuarioId) {
    const response = await apiRequest(`/usuario/${usuarioId}`, 'GET');
    if (response) {
        alert('Perfil del usuario obtenido correctamente');
    } else {
        alert('Usuario no encontrado');
    }
    return response;
}

// Función para añadir un interés al usuario
async function addInteres(usuarioId, interesData) {
    const response = await apiRequest(`/usuario/addInteres?usuarioId=${usuarioId}`, 'POST', interesData);
    if (response) {
        alert('Interés añadido al usuario');
    } else {
        alert('Error al añadir el interés');
    }
    return response;
}

// Función para actualizar el plan del usuario
async function upgradePlan(usuarioId, planData) {
    const response = await apiRequest(`/usuario/upgradePlan?usuarioId=${usuarioId}`, 'POST', planData);
    if (response) {
        alert('Plan del usuario actualizado');
    } else {
        alert('Error al actualizar el plan');
    }
    return response;
}

// Función para obtener las notificaciones del usuario
async function getAllNotificaciones() {
    const response = await apiRequest('/notificacion', 'GET');
    if (response) {
        alert('Notificaciones obtenidas correctamente');
    } else {
        alert('Error al obtener las notificaciones');
    }
    return response;
}

// ---- Funciones para match.html ---- //

// Obtener y mostrar todos los matches
async function getAllMatches() {
    const matches = await apiRequest('/usermatch', 'GET');
    if (matches) {
        document.getElementById('matchesResult').textContent = JSON.stringify(matches, null, 2);
    } else {
        alert('Error al obtener los matches');
    }
}

// Crear un match aleatorio para el usuario
async function randomMatch(usuarioId) {
    const match = await apiRequest(`/usermatch/randomMatch?usuarioId=${usuarioId}`, 'POST');
    if (match) {
        alert('Match aleatorio creado con éxito');
    } else {
        alert('Error al crear match aleatorio');
    }
}

// Obtener un usuario aleatorio
async function getRandomUsuario() {
    const usuario = await apiRequest('/usuario/random', 'GET');
    if (usuario) {
        document.getElementById('randomUserResult').textContent = JSON.stringify(usuario, null, 2);
    } else {
        alert('No se encontró ningún usuario aleatorio');
    }
}

// Dar "Like" a un usuario
async function likeUser(usuarioId, likedUsuarioId) {
    const like = await apiRequest(`/userlike/likeUser?usuarioId=${usuarioId}&likedUsuarioId=${likedUsuarioId}`, 'POST');
    if (like) {
        alert('Usuario dado Like');
    } else {
        alert('Error al dar Like');
    }
}

// Rechazar a un usuario
async function rejectUser(usuarioId, rechazadoUsuarioId) {
    const rechazo = await apiRequest(`/rechazo/reject?usuarioId=${usuarioId}&rechazadoUsuarioId=${rechazadoUsuarioId}`, 'POST');
    if (rechazo) {
        alert('Usuario rechazado');
    } else {
        alert('Error al rechazar al usuario');
    }
}

// Confirmar amistad con un usuario
async function confirmFriendship(matchId, isFriend) {
    const amistad = await apiRequest(`/amistad/confirmFriendship?matchId=${matchId}&isFriend=${isFriend}`, 'POST');
    if (amistad) {
        alert(isFriend ? 'Amistad confirmada' : 'Amistad rechazada');
    } else {
        alert('Error al actualizar la amistad');
    }
}

// Inicializar eventos para la página de registro
function initializeRegisterPage() {
    document.getElementById('registerForm')?.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Construcción del objeto usuarioData con intereses y plan estructurados correctamente
        const usuarioData = {
            nombre: document.getElementById('name').value,
            correo: document.getElementById('email').value, // Cambiado a 'correo' para coincidir con el backend
            intereses: Array.from(document.querySelectorAll('input[name="intereses"]:checked')).map(input => ({ nombre: input.value })), // Estructura correcta de intereses
            plan: { nombre: document.getElementById('plan').value } // Plan como objeto con el nombre
        };

        try {
            const result = await registerUsuario(usuarioData);
            if (result) {
                alert('Registro exitoso');
                window.location.href = 'login.html';
            } else {
                alert('Error al registrar el usuario');
            }
        } catch (error) {
            console.error('Error en el registro:', error);
            alert('Error al registrar el usuario. Verifica los datos e inténtalo nuevamente.');
        }
    });
}


// Inicializar eventos para la página de login
function initializeLoginPage() {
    document.getElementById('loginForm')?.addEventListener('submit', async (event) => {
        event.preventDefault();
        const email = document.getElementById('email').value;
        const usuario = await loginUsuario(email);
        if (usuario) {
            alert('Login exitoso');
            localStorage.setItem('userId', usuario.id);  // Guardar el ID del usuario en localStorage
            window.location.href = 'match.html';
        } else {
            alert('Login fallido');
        }
    });
}

// Inicializar eventos para la página de videollamada
function initializeVideollamadaPage() {
    // Evento para buscar una videollamada por ID
    document.getElementById('searchVideoCall').addEventListener('click', async () => {
        const videoCallId = document.getElementById('videoCallId').value;
        const videollamada = await getVideollamadaById(videoCallId);
        if (videollamada) {
            document.getElementById('videocallResult').textContent = JSON.stringify(videollamada, null, 2);
        } else {
            alert('Videollamada no encontrada');
        }
    });

    // Evento para añadir un juego a la videollamada
    document.getElementById('addGame').addEventListener('click', async () => {
        const videollamadaId = document.getElementById('videoCallId').value;
        const gameName = document.getElementById('gameName').value;
        const juegoData = { nombre: gameName };
        const result = await addJuegoToVideollamada(videollamadaId, juegoData);
        if (result) {
            alert('Juego añadido a la videollamada');
        } else {
            alert('Error al añadir el juego');
        }
    });

    // Evento para enviar un reporte
    document.getElementById('submitReport').addEventListener('click', async () => {
        const reportType = document.getElementById('reportType').value;
        const reportDescription = document.getElementById('reportDescription').value;
        const videoCallId = document.getElementById('videoCallId').value;
        const reporteData = {
            autorId: 1,  // Reemplazar con el ID del autor si está disponible
            reportadoId: 2,  // Reemplazar con el ID del usuario reportado
            tipo: reportType,
            descripcion: reportDescription,
            videollamadaId: videoCallId
        };
        
        const result = await createReporte(reporteData);
        if (result) {
            alert('Reporte enviado correctamente');
        } else {
            alert('Error al enviar el reporte');
        }
    });
}

// ---- Inicializar eventos para la página de perfil ----
function initializePerfilPage() {
    const usuarioId = localStorage.getItem('userId'); // Obtener el ID real del usuario logueado desde localStorage

    if (!usuarioId) {
        alert('No se encontró el ID del usuario. Por favor, inicia sesión.');
        window.location.href = 'login.html';
        return;
    }

    // Evento para cargar el perfil del usuario
    document.getElementById('buscarPerfil')?.addEventListener('click', async () => {
        const usuario = await getUsuarioById(usuarioId);
        if (usuario) {
            document.getElementById('perfilResult').textContent = JSON.stringify(usuario, null, 2);
        } else {
            alert('Usuario no encontrado');
        }
    });

    // Evento para añadir un interés
    document.getElementById('addInteres')?.addEventListener('click', async () => {
        const interesName = document.getElementById('newInteres').value;
        const interesData = { nombre: interesName };
        const result = await addInteres(usuarioId, interesData);
        if (result) {
            alert('Interés añadido');
        } else {
            alert('Error al añadir el interés');
        }
    });

    // Evento para actualizar el plan
    document.getElementById('updatePlan')?.addEventListener('click', async () => {
        const planName = document.getElementById('planSelect').value;
        const planData = { nombre: planName };
        const result = await upgradePlan(usuarioId, planData);
        if (result) {
            alert('Plan actualizado');
        } else {
            alert('Error al actualizar el plan');
        }
    });

    // Evento para mostrar las notificaciones
    document.getElementById('verNotificaciones')?.addEventListener('click', async () => {
        const notificaciones = await getAllNotificaciones();
        if (notificaciones) {
            document.getElementById('notificacionesResult').textContent = JSON.stringify(notificaciones, null, 2);
        } else {
            alert('Error al obtener las notificaciones');
        }
    });
}

// ---- Inicializar Eventos para la Página de Match ---- //
function initializeMatchPage() {
    const usuarioId = localStorage.getItem('userId'); // Obtener el ID del usuario logueado del almacenamiento local

    // Evento para mostrar todos los matches
    document.getElementById('showAllMatches')?.addEventListener('click', getAllMatches);

    // Evento para crear un match aleatorio
    document.getElementById('randomMatchButton')?.addEventListener('click', () => {
        randomMatch(usuarioId);
    });

    // Evento para obtener y mostrar un usuario aleatorio
    document.getElementById('showRandomUser')?.addEventListener('click', getRandomUsuario);

    // Evento para dar "Like" al usuario mostrado
    document.getElementById('likeUserButton')?.addEventListener('click', async () => {
        const randomUsuarioId = document.getElementById('randomUserId').value;
        await likeUser(usuarioId, randomUsuarioId);
    });

    // Evento para rechazar al usuario mostrado
    document.getElementById('rejectUserButton')?.addEventListener('click', async () => {
        const randomUsuarioId = document.getElementById('randomUserId').value;
        await rejectUser(usuarioId, randomUsuarioId);
    });

    // Evento para aceptar una solicitud de amistad
    document.getElementById('acceptFriendshipButton')?.addEventListener('click', async () => {
        const matchId = document.getElementById('friendMatchId').value;
        await confirmFriendship(matchId, true);
    });

    // Evento para rechazar una solicitud de amistad
    document.getElementById('rejectFriendshipButton')?.addEventListener('click', async () => {
        const matchId = document.getElementById('friendMatchId').value;
        await confirmFriendship(matchId, false);
    });
}

// Detectar y inicializar la página correcta
function initializePage() {
    const pathname = window.location.pathname;
    if (pathname.includes('index.html')) {
        initializeRegisterPage();
    } else if (pathname.includes('login.html')) {
        initializeLoginPage();
    } else if (pathname.includes('videollamada.html')) {
        initializeVideollamadaPage();
    } else if (pathname.includes('perfil.html')) {
        initializeProfilePage();
    } else if (pathname.includes('match.html')) {
        initializeMatchPage();
    }
    
}

// Llamar a initializePage para configurar eventos al cargar el documento
document.addEventListener('DOMContentLoaded', initializePage);
