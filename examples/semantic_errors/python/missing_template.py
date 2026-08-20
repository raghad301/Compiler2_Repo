from flask import Flask, render_template
app = Flask(__name__)
value = render_template("missing_template.jinja")
