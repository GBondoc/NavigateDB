document.addEventListener("DOMContentLoaded", () => {
    const entityList = document.querySelector('.list');
    const entityDetails = document.querySelector('.entity-details');
    const errorMessageElement = document.getElementById('error-message');
    const token = localStorage.getItem('jwtToken');
    const publicUserId = localStorage.getItem('publicUserId');
    const selectedErd = localStorage.getItem('erdId');

    entityList.addEventListener('click', async (event) => {
        if (event.target.classList.contains('list-entity')) {
            const entityId = event.target.dataset.id;

            try {
                const response = await fetch(`http://localhost:8080/NavigateDB/users/${publicUserId}/erds/${selectedErd}/entities/${entityId}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': token,
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                });

                if (!response.ok) {
                    throw new Error('Failed to fetch entity details');
                }

                const entityDetailsData = await response.json();

                const entityNameElement = document.createElement('h2');
                entityNameElement.textContent = entityDetailsData.name;
                entityDetails.appendChild(entityNameElement);

            } catch (error) {
                console.error('Error fetching entity details:', error);
            }
        }
    });




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
                listItem.dataset.id = entityData.entityId;
                entityList.appendChild(listItem);
            });

        } catch (error) {
            console.error('Error fetching entities:', error);
            displayErrorMessage('An error occurred while fetching entities. Please try again later.');
        }
    }

    fetchEntities();

});
