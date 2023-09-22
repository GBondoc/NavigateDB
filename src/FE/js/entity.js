document.addEventListener("DOMContentLoaded", () => {
    const entityForm = document.querySelector("#createEntity");

    const token = localStorage.getItem('jwtToken');
    const publicUserId = localStorage.getItem('publicUserId');
    const selectedErd = localStorage.getItem('erdId');

    const relatedEntitySelect = document.getElementById('relatedEntity');
    const relationTypeSelect = document.getElementById('relationType');

    entityForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const entityNameInput = entityForm.querySelector(".form__input[placeholder='Entity Name']");
        const rowCountInput = entityForm.querySelector(".form__input[placeholder='Row Count']");
        const relatedEntityDropdown = document.getElementById('relatedEntity');
        const relationTypeDropdown = document.getElementById('relationType');

        const entityName = entityNameInput.value;
        const selectedRelatedEntity = relatedEntityDropdown.options[relatedEntityDropdown.selectedIndex].value;
        const selectedRelationType = relationTypeDropdown.options[relationTypeDropdown.selectedIndex].value;
        const rowCount = parseInt(rowCountInput.value, 10);

        if (isNaN(rowCount)) {
            console.log('Row Count must be a valid number.');
            return;
        }

        if (selectedRelatedEntity === "None" && selectedRelationType === "None")
            await commitEntityWithoutRelation(entityName, rowCount);
        else
            await commitEntityWithRelation(entityName, rowCount, selectedRelatedEntity, selectedRelationType);

    });

    async function commitEntityWithoutRelation(name, rowCount) {
        try {
            if (!token || !selectedErd) {
                console.error('Missing required data from localStorage.');
                return;
            }

            const input = {name, rowCount};
            console.log(input);

            const response = await fetch(`http://localhost:8080/NavigateDB/users/${publicUserId}/erds/${selectedErd}/entities`, {
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(input)
            });

            if (!response.ok) {
                throw new Error('Error inserting entities');
            }
        } catch (error) {
            console.error('Error adding entity:', error);
        }
    }

    async function commitEntityWithRelation(entityName, rowCount, selectedRelatedEntity, selectedRelationType) {

    }

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

    async function fetchRelationTypes() {
        try {
            const response = await fetch(`http://localhost:8080/NavigateDB/relations`, {
                method: 'GET',
                headers: {
                    'Authorization': token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Error fetching relation types');
            }

            const data = await response.json();

            relationTypeSelect.innerHTML = '';

            const defaultOption = document.createElement('option');
            defaultOption.value = 'None';
            defaultOption.text = 'None';
            relationTypeSelect.appendChild(defaultOption);

            data.forEach(relationType => {
                const option = document.createElement('option');
                option.value = relationType.relationId;
                option.text = relationType.relationType + ' nullable: ' + relationType.nullable;
                relationTypeSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Error fetching relation types:', error);
        }
    }

    fetchEntities();
    fetchRelationTypes();
});
