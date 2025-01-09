"""
Vampire Hunter Game - Backend API
Version: 1.0
Developer: Carmen Mosquera
Description: This Flask application serves as the backend for the Vampire Hunter Game, managing player actions, items,
locations, and interactions. The game involves collecting various items from locations to defeat a vampire.
"""


from flask import Flask
from flask_cors import CORS
from game_api import api_endpoints

app = Flask(__name__)
CORS(app)



# Add the game API endpoints from the separate module
app.register_blueprint(api_endpoints)

# Run the Flask application
if __name__ == "__game__":
    app.run(debug=True)