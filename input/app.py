from flask import Flask, render_template, request, redirect, url_for
from werkzeug.utils import secure_filename

app = Flask(__name__, static_folder='.', static_url_path='/static')

def save_product_image(image_file, product_id):
    if image_file is None or image_file.filename == "":
        return None

    original_name = secure_filename(image_file.filename)
    lower_name = original_name.lower()

    allowed = lower_name.endswith(".png") or lower_name.endswith(".jpg") or lower_name.endswith(".jpeg") or lower_name.endswith(".webp")

    if not allowed:
        return None

    image_name = str(product_id) + "_" + original_name
    image_path = app.static_folder + "/pics/" + image_name
    image_file.save(image_path)

    return image_name

products = [
    {"id": 1, "name": "Phone", "price": 300, "image": "phone.png"},
    {"id": 2, "name": "Laptop", "price": 800, "image": "laptop.png"},
    {"id": 3, "name": "Headset", "price": 50, "image": "headset.png"},
    {"id": 4, "name": "Smart Watch", "price": 150, "image": "smart_watch.png"},
    {"id": 5, "name": "iPad", "price": 450, "image": "ipad.png"},
    {"id": 6, "name": "Camera", "price": 650, "image": "camera.png"},
    {"id": 7, "name": "Keyboard", "price": 80, "image": "keyboard.png"}
]

@app.route('/')
def index():
    return render_template('index.jinja', products=products, title="Product Store")

@app.route('/add', methods=['GET', 'POST'])
def add_product():
    if request.method == 'POST':
        name = request.form.get('name')
        price = float(request.form.get('price'))
        new_id = 1
        for product in products:
            if product['id'] >= new_id:
                new_id = product['id'] + 1

        image_file = request.files.get('image')
        image_name = save_product_image(image_file, new_id)

        if image_name is None:
            return redirect(url_for('add_product'))

        new_product = {
            "id": new_id,
            "name": name,
            "price": price,
            "image": image_name
        }

        products.append(new_product)
        return redirect(url_for('index'))
    return render_template('add_product.jinja', title="Add Product")

@app.route('/edit/<int:product_id>', methods=['GET', 'POST'])
def edit_product(product_id):
    product = next((p for p in products if p['id'] == product_id), None)
    if not product:
        return redirect(url_for('index'))

    if request.method == 'POST':
        product['name'] = request.form.get('name')
        product['price'] = float(request.form.get('price'))
        image_file = request.files.get('image')

        if image_file is not None and image_file.filename != "":
            image_name = save_product_image(image_file, product_id)

            if image_name is None:
                return redirect(url_for('edit_product', product_id=product_id))

            product['image'] = image_name

        return redirect(url_for('index'))
    return render_template('edit_product.jinja', product=product, title="Edit Product")

@app.route('/product/<int:product_id>')
def product_details(product_id):
    product = next(
        (p for p in products if p['id'] == product_id),
        None
    )

    if not product:
        return redirect(url_for('index'))

    return render_template(
        'product_details.jinja',
        product=product,
        title="Product Details"
    )

@app.route('/delete/<int:product_id>')
def delete_product(product_id):
    new_list = []
    for p in products:
        if p['id'] != product_id:
            new_list.append(p)
    products.clear()
    for p in new_list:
        products.append(p)
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True)
