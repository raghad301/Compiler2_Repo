(function () {
    "use strict";

    const STORAGE_KEY = "compiler_store_products";
    const SOURCE_KEY = "compiler_store_source_signature";

    function isStaticMode() {
        return window.location.protocol === "file:"
            || window.location.pathname.toLowerCase().endsWith(".html");
    }

    function numberValue(value) {
        const parsed = Number(value);
        return Number.isFinite(parsed) ? parsed : 0;
    }

    function formatPrice(value) {
        const number = numberValue(value);
        return Number.isInteger(number) ? String(number) : number.toFixed(2);
    }

    function readSeedProducts() {
        return Array.from(document.querySelectorAll("[data-product-id]"))
            .map(card => ({
                id: numberValue(card.dataset.productId),
                name: card.dataset.productName || "",
                price: numberValue(card.dataset.productPrice),
                image: card.dataset.productImage || "default.jpg"
            }))
            .filter(product => product.id > 0 && product.name !== "");
    }

    function saveProducts(products) {
        window.localStorage.setItem(STORAGE_KEY, JSON.stringify(products));
    }

    function loadProducts(seedProducts) {
        if (seedProducts.length > 0) {
            const signature = JSON.stringify(seedProducts);
            if (window.localStorage.getItem(SOURCE_KEY) !== signature) {
                saveProducts(seedProducts);
                window.localStorage.setItem(SOURCE_KEY, signature);
            }
        }

        try {
            const stored = JSON.parse(window.localStorage.getItem(STORAGE_KEY) || "[]");
            return Array.isArray(stored) ? stored : [];
        } catch (error) {
            saveProducts(seedProducts);
            return seedProducts;
        }
    }

    function queryProductId(name) {
        return numberValue(new URLSearchParams(window.location.search).get(name));
    }

    function createActionLink(label, href, className) {
        const link = document.createElement("a");
        link.textContent = label;
        link.href = href;
        if (className) link.className = className;
        return link;
    }

    function productImageSource(image) {
        const value = image || "";
        if (value.startsWith("data:")
            || value.startsWith("blob:")
            || value.startsWith("http://")
            || value.startsWith("https://")
            || value.startsWith("/")
            || value.startsWith("pics/")) {
            return value;
        }
        return "pics/" + value;
    }

    function productImageLabel(image) {
        const value = image || "";
        return value.startsWith("data:") ? "Uploaded image" : value;
    }

    function readImageFile(input) {
        const file = input && input.files ? input.files[0] : null;
        if (!file) return Promise.resolve(null);

        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.addEventListener("load", () => resolve(reader.result));
            reader.addEventListener("error", () => reject(reader.error));
            reader.readAsDataURL(file);
        });
    }

    function createProductCard(product) {
        const card = document.createElement("article");
        card.className = "product-card";
        card.dataset.productId = String(product.id);
        card.dataset.productName = product.name;
        card.dataset.productPrice = String(product.price);
        card.dataset.productImage = product.image || "default.jpg";

        const image = document.createElement("img");
        image.className = "product-image";
        image.src = productImageSource(product.image);
        image.alt = product.name;

        const name = document.createElement("h2");
        name.textContent = product.name;

        const price = document.createElement("p");
        price.className = "price";
        price.textContent = "$" + formatPrice(product.price);

        const actions = document.createElement("nav");
        actions.className = "card-actions";
        actions.setAttribute("aria-label", "Actions for " + product.name);
        actions.appendChild(createActionLink(
            "Details", "product_details.html?product_id=" + encodeURIComponent(product.id)));
        actions.appendChild(createActionLink(
            "Edit", "edit_product.html?product_id=" + encodeURIComponent(product.id)));
        actions.appendChild(createActionLink(
            "Delete", "index.html?delete_product=" + encodeURIComponent(product.id),
            "danger delete-link"));

        card.append(image, name, price, actions);
        return card;
    }

    function renderIndex(products) {
        const list = document.getElementById("products-list");
        const emptyState = document.getElementById("empty-state");
        if (!list) return;

        list.replaceChildren();
        products.forEach(product => list.appendChild(createProductCard(product)));
        if (emptyState) emptyState.hidden = products.length !== 0;
    }

    function initializeIndex() {
        const seedProducts = readSeedProducts();
        let products = loadProducts(seedProducts);
        const deleteId = queryProductId("delete_product");

        if (deleteId > 0) {
            products = products.filter(product => numberValue(product.id) !== deleteId);
            saveProducts(products);
            window.history.replaceState({}, "", "index.html");
        }

        renderIndex(products);

        document.addEventListener("click", event => {
            const deleteLink = event.target.closest(".delete-link");
            if (deleteLink && !window.confirm("Delete this product?")) {
                event.preventDefault();
            }
        });
    }

    function initializeAddForm() {
        const form = document.getElementById("product-form");
        if (!form) return;

        form.addEventListener("submit", async event => {
            event.preventDefault();
            const products = loadProducts([]);
            const nextId = products.reduce(
                (largest, product) => Math.max(largest, numberValue(product.id)), 0) + 1;

            const image = await readImageFile(document.getElementById("image"));
            if (!image) return;

            products.push({
                id: nextId,
                name: document.getElementById("name").value.trim(),
                price: numberValue(document.getElementById("price").value),
                image: image
            });
            saveProducts(products);
            window.location.href = "index.html";
        });
    }

    function initializeEditForm() {
        const form = document.getElementById("product-form");
        if (!form) return;

        const productId = queryProductId("product_id")
            || numberValue(form.dataset.productId);
        const products = loadProducts([]);
        const product = products.find(item => numberValue(item.id) === productId);

        if (!product) {
            form.replaceChildren(document.createTextNode("Product not found."));
            return;
        }

        document.getElementById("name").value = product.name;
        document.getElementById("price").value = String(product.price);
        form.addEventListener("submit", async event => {
            event.preventDefault();
            product.name = document.getElementById("name").value.trim();
            product.price = numberValue(document.getElementById("price").value);

            const replacement = await readImageFile(document.getElementById("image"));
            if (replacement) product.image = replacement;

            saveProducts(products);
            window.location.href = "index.html";
        });
    }

    function initializeDetails() {
        const productId = queryProductId("product_id");
        const products = loadProducts([]);
        const product = products.find(item => numberValue(item.id) === productId);
        const details = document.getElementById("product-details");
        if (!details) return;

        if (!product) {
            details.replaceChildren(document.createTextNode("Product not found."));
            return;
        }

        const image = document.getElementById("product-image");
        image.src = productImageSource(product.image);
        image.alt = product.name;
        document.getElementById("product-name").textContent = product.name;
        document.getElementById("product-price").textContent =
            "$" + formatPrice(product.price);
        document.getElementById("product-image-name").textContent =
            "Image: " + productImageLabel(product.image);
        document.getElementById("edit-product-link").href =
            "edit_product.html?product_id=" + encodeURIComponent(product.id);
    }

    document.addEventListener("DOMContentLoaded", function () {
        if (!isStaticMode()) return;

        switch (document.body.dataset.page) {
            case "index": initializeIndex(); break;
            case "add": initializeAddForm(); break;
            case "edit": initializeEditForm(); break;
            case "details": initializeDetails(); break;
        }
    });
})();
