document.addEventListener("DOMContentLoaded", () => {
    const logoutButton = document.getElementById('logout-button');

    function logout() {
        // Expire jwt access token on back
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('publicUserId');
        localStorage.removeItem('email');
        window.location.href = "../index.html";
    }

    logoutButton.addEventListener('click', () => {
        logout();
    });
});
