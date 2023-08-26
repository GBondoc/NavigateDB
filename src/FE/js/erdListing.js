document.addEventListener("DOMContentLoaded", () => {
    const erdGrid = document.querySelector('.erd--grid');
    const errorMessageElement = document.getElementById('error-message');
    const token = localStorage.getItem('jwtToken');
    const publicUserId = localStorage.getItem('publicUserId');

    function createErdSquare(erdName) {
        const erdSquare = document.createElement('div');
        erdSquare.classList.add('erd-square');
        erdSquare.textContent = erdName;
        erdGrid.appendChild(erdSquare);
    }

    const selectedErdList = document.querySelector('.selected-erd-list');
    const confirmDeleteButton = document.querySelector('.confirm-delete-button');
    const cancelDeleteButton = document.querySelector('.cancel-delete-button');
    const deleteErdButton = document.querySelector('.delete-erds-button');
    const selectedErdsSection = document.querySelector('.selected-erds');

    const selectedErds = [];

    function confirmDelete() {
        // Perform the actual delete operation using API requests
        // Clear the selectedErds array
        // Clear the selectedErdList container
        selectedErds.length = 0;
        selectedErdList.innerHTML = '';
        selectedErdsSection.style.display = 'none'; // Hide the selected ERDs section
    }

    function cancelDelete() {
        selectedErds.length = 0;
        selectedErdList.innerHTML = '';
        selectedErdsSection.style.display = 'none';
    }

    function toggleSelectErdForDeletion(erdName) {
        const index = selectedErds.indexOf(erdName);
        if (index === -1) {
            selectedErds.push(erdName);
            const selectedErdElement = document.createElement('div');
            selectedErdElement.textContent = erdName;
            selectedErdList.appendChild(selectedErdElement);
        } else {
            selectedErds.splice(index, 1);
            const selectedErdElements = selectedErdList.childNodes;
            for (let i = 0; i < selectedErdElements.length; i++) {
                if (selectedErdElements[i].textContent === erdName) {
                    selectedErdList.removeChild(selectedErdElements[i]);
                    break;
                }
            }
        }
    }

    let deleteErdButtonClicked = false; // Flag to track Delete ERDs button click

    erdGrid.addEventListener('click', (event) => {
        if (deleteErdButtonClicked && event.target.classList.contains('erd-square')) {
            const erdName = event.target.textContent;
            toggleSelectErdForDeletion(erdName);
        }
    });

    deleteErdButton.addEventListener('click', () => {
        deleteErdButtonClicked = true;
        selectedErdsSection.style.display = 'block';
    });

    confirmDeleteButton.addEventListener('click', () => {
        confirmDelete();
        selectedErdsSection.style.display = 'none';
    });

    // Event listener for canceling deletion
    cancelDeleteButton.addEventListener('click', () => {
        cancelDelete();
        selectedErdsSection.style.display = 'none'; // Hide the selected ERDs section
        deleteErdButtonClicked = false; // Reset the flag after canceling
    });

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
