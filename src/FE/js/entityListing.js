document.addEventListener("DOMContentLoaded", () => {
    const entityList = document.querySelector('.list');

    const entityNames = ["Entity 1", "Entity 2", "Entity 3", "Entity 4", "Entity 5"];

    entityNames.forEach(entityName => {
        const listItem = document.createElement('li');
        listItem.classList.add('list-entity');
        listItem.textContent = entityName;
        entityList.appendChild(listItem);
    });
});
