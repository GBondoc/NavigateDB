document.addEventListener("DOMContentLoaded", () => {
    const entityForm = document.querySelector("#createEntity");

    const token = localStorage.getItem('jwtToken');
    const publicUserId = localStorage.getItem('publicUserId');
    const selectedErd = localStorage.getItem('erdId');

    const relatedEntitySelect = document.getElementById('relatedEntity');

    entityForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const entityNameInput = entityForm.querySelector(".form__input[placeholder='Entity Name']");
        const rowCountInput = entityForm.querySelector(".form__input[placeholder='Row Count']");
        const dropdown = document.getElementById('relatedEntity');

        const entityName = entityNameInput.value;
        const rowCount = rowCountInput.value;
        const selectedOption = dropdown.options[dropdown.selectedIndex].value;
    });


    async function fetchEntities() {
        try {
            if (!token || !selectedErd) {
                console.error('Missing required data from localStorage.');
                return;
            }

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

            relatedEntitySelect.innerHTML = '';

            const defaultOption = document.createElement('option');
            defaultOption.value = 'None';
            defaultOption.text = 'None';
            relatedEntitySelect.appendChild(defaultOption);

            data.forEach(entityData => {
                const option = document.createElement('option');
                option.value = entityData.name;
                option.text = entityData.name;
                relatedEntitySelect.appendChild(option);
            });
        } catch (error) {
            console.error('Error fetching entities:', error);
        }
    }

    fetchEntities();
});
