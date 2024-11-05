document.addEventListener('DOMContentLoaded', function () {
    const profileForm = document.getElementById('profile-form');
    const randomMatchBtn = document.getElementById('random-match-btn');

    profileForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(profileForm);
        const userProfile = {
            nombre: formData.get('name'),
            correo: formData.get('email'),
            intereses: formData.get('interests').split(','),
            plan: formData.get('plan')
        };

        fetch('/api/usuario', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userProfile)
        })
        .then(response => response.json())
        .then(data => {
            alert('Profile updated!');
        })
        .catch(error => {
            console.error('Error updating profile:', error);
        });
    });

    randomMatchBtn.addEventListener('click', function () {
        const usuarioId = 1;  // Replace with actual user ID
        fetch(`/api/usermatch/randomMatch?usuarioId=${usuarioId}`, {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById('random-match-result');
            resultDiv.innerHTML = `<p>Random match found: ${data.user1.nombre} and ${data.user2.nombre}</p>`;
        })
        .catch(error => {
            console.error('Error finding random match:', error);
        });
    });

    // Fetch and display matches
    fetch('/api/usermatch')
        .then(response => response.json())
        .then(matches => {
            const matchesList = document.getElementById('matches-list');
            matches.forEach(match => {
                const matchElement = document.createElement('div');
                matchElement.innerHTML = `
                    <p>Match between: ${match.user1.nombre} and ${match.user2.nombre}</p>
                    <button onclick="confirmFriendship(${match.id}, true)">Add as Friend</button>
                    <button onclick="confirmFriendship(${match.id}, false)">Reject</button>
                `;
                matchesList.appendChild(matchElement);
            });
        })
        .catch(error => {
            console.error('Error fetching matches:', error);
        });

    // Fetch and display notifications
    fetch('/api/notificacion')
        .then(response => response.json())
        .then(notifications => {
            const notificationsList = document.getElementById('notifications-list');
            notifications.forEach(notification => {
                const notificationElement = document.createElement('div');
                notificationElement.innerHTML = `<p>${notification.mensaje}</p>`;
                notificationsList.appendChild(notificationElement);
            });
        })
        .catch(error => {
            console.error('Error fetching notifications:', error);
        });
});

// Function to confirm or reject friendship
function confirmFriendship(matchId, isFriend) {
    fetch(`/api/amistad/confirmFriendship?matchId=${matchId}&isFriend=${isFriend}`, {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            alert('Friendship status updated!');
            location.reload();  // Refresh the page to see the updated match list
        } else {
            alert('Error updating friendship status');
        }
    })
    .catch(error => {
        console.error('Error confirming friendship:', error);
    });
}
