document.addEventListener("DOMContentLoaded", function () {
	// Define each settings panel to load
	const settingsPanels = [
		{
			type: "rev",
			containerId: "settingsContainer_rev",
			file: "settings_rev.html",
			imgPrefix: "zimg"
		},
		{
			type: "default",
			containerId: "settingsContainer_other",
			file: "settings.html",
			imgPrefix: "img" // Adjust this to your actual image type
		}
	];

	settingsPanels.forEach(panel => loadSettings(panel));
});

function loadSettings({ type, containerId, file, imgPrefix }) {
	const container = document.getElementById(containerId);
	if (!container) return;

	fetch(file)
		.then(res => res.text())
		.then(html => {
			container.innerHTML = html;
			initSettings(type, imgPrefix);
		});
}

function initSettings(type, imgPrefix) {
	const btn = document.getElementById(`settingsBtn${type === "default" ? "" : `_${type}`}`);
	const menu = document.getElementById(`settingsMenu${type === "default" ? "" : `_${type}`}`);
	const toggle = document.getElementById(`toggle${type === "default" ? "" : `_${type}`}Images`);
	const storageKey = `${type}_showImages`;

	// Load saved preference or default to true
	const showImages = localStorage.getItem(storageKey) !== "false";
	toggle.checked = showImages;
	toggleImages(imgPrefix, showImages);

	toggle.addEventListener("change", () => {
		const checked = toggle.checked;
		localStorage.setItem(storageKey, checked);
		toggleImages(imgPrefix, checked);
	});

	// Show/hide images by src prefix
	function toggleImages(prefix, show) {
		document.querySelectorAll(`img[src^="${prefix}"]`).forEach(img => {
			img.style.display = show ? 'block' : 'none';
		});
	}

	// Toggle menu
	btn.addEventListener("click", () => {
		menu.style.display = (menu.style.display === "block") ? "none" : "block";
	});

	// Close menu on outside click
	document.addEventListener("click", function (e) {
		if (!btn.contains(e.target) && !menu.contains(e.target)) {
			menu.style.display = "none";
		}
	});
}
