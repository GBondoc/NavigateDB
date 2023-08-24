document.addEventListener("DOMContentLoaded", () => {
    const entityForm = document.querySelector("#createEntity");

    entityForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const entityNameInput = entityForm.querySelector(".form__input[placeholder='Entity Name']");
        const rowCountInput = entityForm.querySelector(".form__input[placeholder='Row Count']");

        const entityName = entityNameInput.value;
        const rowCount = rowCountInput.value;
        const token = localStorage.getItem('jwtToken');
        const publicUserId = localStorage.getItem('publicUserId');


    });
});