"""
API Endpoints for the Vampire Hunter Game
Version: 1.0
Developer: Carmen Mosquera
Description: This module defines the API endpoints that interact with the Vampire Hunter Game, including actions like
moving the player, collecting items, viewing inventory, and combat mechanics.
"""

from flask import Blueprint, jsonify, request
from game_objects import player, town

api_endpoints = Blueprint('api_endpoints', __name__)

#Endpoint: welcome message
@api_endpoints.route('/')
def index():
    return jsonify({'message': 'Welcome to the Vampire Hunter Game!'})


#Endpoint: get all the houses/location from the village ****************************
@api_endpoints.route('/houses', methods=['GET'])
def get_houses():
    """
    GET /houses
    :return: an array of houses
    """
    all_houses = []
    for house in town.houses_list:
        item_data = {
            'name': house.item.name,
            'description': house.item.description,
            'image_key': house.item.image_key,
        }
        house_data = {
            'name': house.name,
            'description': house.description,
            'item':item_data if house.item else None,
        }
        all_houses.append(house_data)
    return jsonify({'houses': all_houses})



#Endpoint: get a house information by its name **********************************
@api_endpoints.route('/house/<string:house_name>', methods=['GET'])
def get_house(house_name):
    """
    GET / house/<string:house_name>
    :param house_name:
    :return: the house object of the given name
    """
    result = town.find_house(house_name)
    if result is None:
        return jsonify({'error': 'House not found'}), 404
    return jsonify({'name': result.name, 'description': result.description, 'item': result.item.name if result.item else None})



#Endpoint: get all items from the houses ********************************
@api_endpoints.route('/items', methods=['GET'])
def get_all_items():
    """
    GET / items
    :return: an array of all items
    """
    items = []
    for house in town.houses_list:
        items.append({
            'name': house.item.name,
            'description': house.item.description,
            'image_key': house.item.image_key,
            'is_collected': house.item.is_collected
        })
    return jsonify({'items': items})



#Endpoint: get an item by its name **********************************
@api_endpoints.route('/item/<string:item_name>', methods=['GET'])
def get_item_by_name(item_name):
    """
    :param item_name:
    :return: the item object
    search an item in the houses items and returns the item object
    """
    for house in town.houses_list:
        item = house.item
        if item and item.name.lower() == item_name.lower():
            return jsonify({
                'name': item.name,
                'description': item.description,
                'image_key': item.image_key,
                'is_collected': item.is_collected
            })
    return jsonify({'error': 'Item not found'}), 404



#Endpoint: collect an item from the current house by entering house name **************************
@api_endpoints.route('/collect-item/<string:house_name>', methods=['POST'])
def collect_item(house_name):
    """
       POST /collect-item/<house_name>
       Collects an item from the specified house if available, adds it to the player's inventory, and returns the item.
       """
    house = town.find_house(house_name) #find the house in the town houses
    result = player.collect_item(house)  # if house is found, collect the item if available
    if result is None:
        return jsonify({'error': 'Item not found'}), 404
    return jsonify(
        {'name': result.name, 'description': result.description, 'image_key': result.image_key})  # send result


#Endpoint: allows user to use items from the inventory ***************************
@api_endpoints.route('/use-item', methods=['POST'])
def use_item():
    """
    POST /use-item
    :return: the item that the player used against the vampire
    """
    data = request.json
    item_name = data.get('item')

    if item_name in player.inventory:
        if item_name == 'Health Potion':
            return player.heal(50)
        elif item_name == 'UV Light':
            return {'message': 'You used the UV light to burn the vampire!'}
        else:
            return {'message': f'Used {item_name}'}
    else:
        return {'error': 'Item not found in inventory'}, 404



#Ednpoint: get player current status **************************
@api_endpoints.route('/status', methods=['GET'])
def status():
    """
        GET /status
        Returns the player's current health and location.
        """
    result = player.player_status()
    return jsonify(result)
