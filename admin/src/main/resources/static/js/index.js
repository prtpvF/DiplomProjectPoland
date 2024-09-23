function fetchSecuredData() {
    fetch('/api/your-secured-endpoint', {
        method: 'GET',
        headers: {
            'token': localStorage.getItem('jwtToken'), // Извлекаем токен из локального хранилища
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log('Response:', data);
    })
    .catch(error => console.error('Error:', error));
}

