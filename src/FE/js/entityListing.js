document.addEventListener("DOMContentLoaded", () => {
    const entityList = document.querySelector('.list');
    const errorMessageElement = document.getElementById('error-message');
    const token = localStorage.getItem('jwtToken');
    const publicUserId = localStorage.getItem('publicUserId');
    const selectedErd = localStorage.getItem('erdId');

    console.log(selectedErd);
    async function fetchEntities() {
        try {
            const response = await fetch(`http://localhost:8080/NavigateDB/users/${publicUserId}/erds/${selectedErd}/entities`, {
                method: 'GET',
                headers: {
                    'Authorization': token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Error fetching entities');
            }

            const data = await response.json();

            data.forEach(entityData => {
                const listItem = document.createElement('li');
                listItem.classList.add('list-entity');
                listItem.textContent = entityData.name;
                entityList.appendChild(listItem);
            });
        } catch (error) {
            console.error('Error fetching entities:', error);
            displayErrorMessage('An error occurred while fetching entities. Please try again later.');
        }
    }

    // Call the fetchEntities function to populate entities on page load
    fetchEntities();

});
