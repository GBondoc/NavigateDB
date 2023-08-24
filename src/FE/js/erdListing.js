document.addEventListener("DOMContentLoaded", () => {
    const erdGrid = document.querySelector('.erd--grid');
    const errorMessageElement = document.getElementById('error-message');
    const token = localStorage.getItem('jwtToken');
    const publicUserId = localStorage.getItem('publicUserId');

    // Function to create an ERD square
    function createErdSquare(erdName) {
        const erdSquare = document.createElement('div');
        erdSquare.classList.add('erd-square');
        erdSquare.textContent = erdName;
        erdGrid.appendChild(erdSquare);
    }

    // Function to display error message
    function displayErrorMessage(message) {
        errorMessageElement.textContent = message;
    }

    // Function to fetch and populate ERDs
    async function fetchErds() {
        try {
            const getErdsRespose = await fetch(`http://localhost:8080/NavigateDB/users/${publicUserId}/erds`, {
                method: 'GET',
                headers: {
                    'Authorization': token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });
            if (!getErdsRespose.ok) {
                throw new Error('Error fetching ERDs');
            }
            const data = await getErdsRespose.json();

            data.forEach(erdData => {
                createErdSquare(erdData.name);
            });
        } catch (error) {
            console.error('Error fetching ERDs:', error);
            displayErrorMessage('An error occurred while fetching ERDs. Please try again later.');
        }
    }

    // Fetch and populate ERDs on page load
    fetchErds();
});
