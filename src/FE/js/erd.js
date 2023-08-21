document.addEventListener("DOMContentLoaded", () => {
    const erdForm = document.querySelector("#createErd");

    erdForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const nameInput = erdForm.querySelector(".form__input[placeholder='Erd Name']");
        const name = nameInput.value;
        const token = localStorage.getItem('jwtToken');
        const publicUserId = localStorage.getItem('publicUserId');

        try {

            const createErdResponse = await fetch(`http://localhost:8080/NavigateDB/users/${publicUserId}/erds`, {
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({name})
            });

            if(createErdResponse.ok) {
                setFormMessage(erdForm, 'success', 'Erd has been created');
            } else {
                if(name.length === 0) {
                    setFormMessage(erdForm, 'error', "Field can't be empty");
                } else {
                    setFormMessage(erdForm, 'error', 'An erd with the same name already exists for this user');
                }
            }
        } catch (err) {
            setFormMessage(erdForm, "error", "An error occurred during erd creation");
        }
    });
});