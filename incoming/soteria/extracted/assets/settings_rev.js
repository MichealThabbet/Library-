document.addEventListener("DOMContentLoaded", function() {
    // Create settings container if it doesn't exist
    let container = document.getElementById('settingsContainer');
    if (!container) {
        container = document.createElement('div');
        container.id = 'settingsContainer';
        document.body.prepend(container);
    }

    // Fallback settings HTML in case fetch fails
    const fallbackSettingsHTML = `
        <button id="settingsBtn">⚙️</button>
        <div id="settingsMenu" style="display: none;">
            <label>
                <input type="checkbox" id="toggle_revImages">
                <span>صور سفر الرؤيا</span>
            </label>
        </div>
    `;

    // Try to load settings content
    fetch('settings_rev.html')
        .then(res => {
            if (!res.ok) throw new Error('Failed to load settings');
            return res.text();
        })
        .then(html => {
            container.innerHTML = html;
            initSettings();
        })
        .catch(err => {
            console.error('Error loading settings:', err);
            // Load fallback settings
            container.innerHTML = fallbackSettingsHTML;
            initSettings();
        });

    function initSettings() {
        const btn = document.getElementById("settingsBtn");
        const menu = document.getElementById("settingsMenu");
        const toggle_revImages = document.getElementById("toggle_revImages");

        if (!btn || !menu || !toggle_revImages) {
            console.error("Could not find settings elements");
            return;
        }

        // Load saved preference or default to true
        const rev_showImages = localStorage.getItem("rev_showImages") !== "false";
        toggle_revImages.checked = rev_showImages;
        toggleImages(rev_showImages);

        toggle_revImages.addEventListener("change", () => {
            const checked = toggle_revImages.checked;
            localStorage.setItem("rev_showImages", checked);
            toggleImages(checked);
        });

        function toggleImages(show) {
            document.querySelectorAll('img').forEach(img => {
                img.style.display = show ? 'block' : 'none';
            });
        }

        btn.addEventListener("click", (e) => {
            e.stopPropagation();
            menu.style.display = (menu.style.display === "block") ? "none" : "block";
        });

        document.addEventListener("click", function(e) {
            if (!btn.contains(e.target) && !menu.contains(e.target)) {
                menu.style.display = "none";
            }
        });

        // Add styles dynamically in case CSS didn't load
        const dynamicStyles = `
            #settingsContainer {
                position: fixed;
                top: 10px;
                right: 10px;
                z-index: 9999;
                opacity: 0.6;
                transition: opacity 0.3s ease;
                font-size: 1rem;
            }
            #settingsContainer:hover {
                opacity: 1;
            }
            #settingsBtn {
                width: 2.5rem;
                height: 2.5rem;
                background-color: black;
                color: white;
                border: none;
                border-radius: 50%;
                font-size: 1.2rem;
                cursor: pointer;
                display: flex;
                justify-content: center;
                align-items: center;
                opacity: 0.7;
                transition: opacity 0.3s ease;
            }
            #settingsBtn:hover {
                opacity: 1;
            }
            #settingsMenu {
                position: absolute;
                top: 40px;
                right: 0;
                background-color: black;
                color: white;
                border: 1px solid #ddd;
                padding: 0.5rem 1rem;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
                z-index: 10000;
                opacity: 0.9;
                transition: opacity 0.3s ease;
                border-radius: 0.75rem;
                font-size: 1rem;
                white-space: nowrap;
            }
            #settingsMenu:hover {
                opacity: 1;
            }
            #settingsMenu label {
                display: flex;
                align-items: center;
                gap: 0.5rem;
                margin: 0.5rem 0;
                cursor: pointer;
                font-size: 1em;
                white-space: nowrap;
            }
            #settingsMenu input[type="checkbox"] {
                width: 1em;
                height: 1em;
                flex-shrink: 0;
            }
        `;

        const styleElement = document.createElement('style');
        styleElement.textContent = dynamicStyles;
        document.head.appendChild(styleElement);
    }
});