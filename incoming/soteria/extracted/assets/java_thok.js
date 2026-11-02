let count = 0;
const counterElement = document.getElementById('counter');
const button = document.getElementById('button');
const maxValueInput = document.getElementById('maxValue');
const resetButton = document.getElementById('resetButton');
const completionMessage = document.getElementById('completionMessage');
const MAX_VALUE = 12;
const ORANGE_THRESHOLD = 10;

function updateCounterStyle() {
	if (count >= MAX_VALUE) {
		counterElement.classList.add('green');
		counterElement.classList.remove('orange');
		completionMessage.style.display = 'block';
	} else if (count >= ORANGE_THRESHOLD) {
		counterElement.classList.add('orange');
		counterElement.classList.remove('green');
		completionMessage.style.display = 'none';
	} else {
		counterElement.classList.remove('orange', 'green');
		completionMessage.style.display = 'none';
	}
}

function updateButtonState() {
	if (count >= MAX_VALUE) {
		button.disabled = true;
		if (navigator.vibrate) {
			navigator.vibrate([200, 100, 200]);
		}
	} else {
		button.disabled = false;
	}
}

function animateCounter() {
	counterElement.classList.add('counter-change');
	setTimeout(() => {
		counterElement.classList.remove('counter-change');
	}, 500);
}

button.addEventListener('click', () => {
	if (count < MAX_VALUE) {
		count++;
		counterElement.textContent = count;
		animateCounter();
		
		// Vibrate on every click
		if (navigator.vibrate) {
			navigator.vibrate(100);
		}
	}
	updateCounterStyle();
	updateButtonState();
});

resetButton.addEventListener('click', () => {
	count = 0;
	counterElement.textContent = count;
	animateCounter();
	updateCounterStyle();
	updateButtonState();
});

// Initialize
maxValueInput.value = MAX_VALUE;
updateCounterStyle();
updateButtonState();