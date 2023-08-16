function setFormMessage(formElement, type, message) {
    const messageElement = formElement.querySelector(".form__message");

    messageElement.textContent = message;
    messageElement.classList.remove("form__message--success", "form_message--error");
    messageElement.classList.add('form__message--${type}');
}

function setInputError(inputElement, message) {
    inputElement.classList.add("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent = message;
}

function clearInputError(inputElement) {
    inputElement.classList.remove("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent = "";
}

document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.querySelector("#login");
    const createAccountForm = document.querySelector("#createAccount");

    document.querySelector("#linkCreateAccount").addEventListener("click", (e) => {
        e.preventDefault();
        loginForm.classList.add("form--hidden");
        createAccountForm.classList.remove("form--hidden");
    });

    document.querySelector("#linkLogin").addEventListener("click", (e) => {
        e.preventDefault();
        loginForm.classList.remove("form--hidden");
        createAccountForm.classList.add("form--hidden");
    });

    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Perform login
        const emailInput = loginForm.querySelector(".form__input[placeholder='Email']");
        const passwordInput = loginForm.querySelector(".form__input[placeholder='Password']");
        const email = emailInput.value;
        const password = passwordInput.value;

        try {
            const response = await fetch('http://localhost:8080/NavigateDB/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({email, password})
            });

            console.log('Response:', response);

            if (response.ok) {
                const data = await response.json();
                const token = data.token;

                // Store the token in localStorage
                localStorage.setItem('jwtToken', token);

                setFormMessage(loginForm, 'success', 'Logged in');
            } else {
                setFormMessage(loginForm, 'error', 'Invalid email/password combination');
            }
        } catch (err) {
            console.error("An error occurred: ", err);
            setFormMessage(loginForm, "error", "An error occurred during login");
        }

    });

    document.querySelectorAll(".form__input").forEach((inputElement) => {
        inputElement.addEventListener("blur", (e) => {
            if(e.target.id === "signUpUsername" && e.target.value.length > 0 && e.target.value.length < 10) {
                setInputError(inputElement, "Username must be at least 10 characters long");
            }
        });

        inputElement.addEventListener("input", () => {
            clearInputError(inputElement);
        });
    });
});