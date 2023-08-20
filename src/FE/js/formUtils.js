function setFormMessage(formElement, type, message) {
    const messageElement = formElement.querySelector(".form__message");

    messageElement.textContent = message;
    messageElement.classList.remove("form__message--success", "form__message--error");
    messageElement.classList.add(`form__message--${type}`);
}

function setInputError(inputElement, message) {
    inputElement.classList.add("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent = message;
}

function clearInputError(inputElement) {
    inputElement.classList.remove("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent = "";
}

function clearFormFields(form) {
    form.querySelectorAll(".form__input").forEach((inputElement) => {
        inputElement.value = ""; // Clear the value of the input field
    });
}

// Helper function to clear input errors for a specific form
function clearFormErrors(form) {
    form.querySelectorAll(".form__input").forEach((inputElement) => {
        clearInputError(inputElement);
    });
}