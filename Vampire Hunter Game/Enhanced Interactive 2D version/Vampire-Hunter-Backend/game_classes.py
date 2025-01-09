"""
Game Classes for the Vampire Hunter Game
Version: 1.0
Developer: Carmen Mosquera
Description: This module contains the definitions for the core game classes: Item, Town, House, and Player.
"""
import json
import logging

# =================================================================================================
# =====================================  Item class  ==============================================
# =================================================================================================

class Item:
    """
    This class represents an item that can be collected, used, or displayed in the game.

    Attributes:
        name (str): Name of the item.
        description (str): Description of the item.
        image_key (str): Image reference for the item.
        weight (int): Weight of the item (default is 0).
        durability (int): Durability of the item (default is 100).
        is_collected (bool): Status indicating if the item is collected (default is False).
    """
    def __init__(self, name, description, image_key, weight=0, durability=100):
        self.name = name
        self.description = description
        self.image_key = image_key
        self.weight = weight
        self.durability = durability
        self.is_collected = False

    def __str__(self):
        """Return the string representation of the item (its name)."""
        return self.name

    def use(self):
        """Simulate using the item, reducing its durability. If durability reaches zero, the item breaks."""
        self.durability -= 10
        if self.durability <= 0:
            return {'message': f'{self.name} broke!'}
        return {'message': f'{self.name} used. Durability: {self.durability}'}

    def collect(self):
        """Mark the item as collected if it has not already been collected."""
        if not self.is_collected:
            self.is_collected = True
            return {'message': f'You have collected the {self.name}'}
        else:
            return {'message': f'The {self.name} is already in your inventory.'}

    def show_info(self):
        """Display the item's information, including its name and description."""
        return {'message': f'Item: {self.name}', 'Description': self.description}

    def is_collected_status(self):
        """Return the collection status of the item."""
        return self.is_collected

# =================================================================================================
# =====================================  House class  =============================================
# =================================================================================================

class House:
    """
    This class represents a location (house) in the game where an item may be found.

    Attributes:
        name (str): Name of the house.
        description (str): Description of the house.
        item (Item): The item present in the house (default is None).
    """
    def __init__(self, name, description, item: Item = None):
        self.name = name
        self.description = description
        self.item = item

    def set_item(self, new_item):
        """Assign a new item to the house."""
        self.item = new_item

# =================================================================================================
# =====================================  Town class  ==============================================
# =================================================================================================

class Town:
    """
    This class represents the town that contains multiple houses, each house has an item.

    Attributes:
        houses_list (list): A list of House objects present in the town.
    """
    def __init__(self):
        """Initialize the town with predefined houses and items."""
        # Define items
        ancient_book = Item("Ancient Book", "A dusty book filled with forgotten lore.", "Ancient-book")
        holy_water = Item("Holy Water", "Water blessed by the village priest.", "Holy-water")
        silver_sword = Item("Silver Sword", "A powerful weapon against supernatural creatures.", "Silver-sword")
        wood_stack = Item("Wood Stack", "A sharpened piece of oak wood.", "Wood-stack")
        health_potion = Item("Health Potion", "A magical red liquid that restores health.", "Health-potion")
        crucifix = Item("Crucifix", "A silver cross blessed by the village priest.", "Crucifix")
        garlic = Item("Garlic", "Known to repel vampires.", "Garlic")
        uv_light = Item("UV Light", "Emits ultraviolet light, dangerous to vampires.", "UV-light")
        vampire = Item("Vampire", "A dangerous creature.", "Vampire")
        # town_map = Item("Town Map", "A map with the locations of important items.", "Town-map")
        money = Item("Money", "Currency to pay the vampire hunter.", "Money")

        # Define houses
        village_hall = House("Village Hall", "Once bustling with life, now eerily silent.", money)
        vampire_mansion = House("Vampire Mansion", "A shadowy mansion, home to the vampire.", vampire)
        library = House("Library", "A damp library with ancient knowledge.", ancient_book)
        carpentry = House("Carpentry", "A windmill with tools for crafting.", wood_stack)
        church = House("Church", "A place blessed with holy water.", holy_water)
        hospital = House("Hospital", "A place to find a healing potion.", health_potion)
        gift_shop = House("Gift Shop", "An eerie shop with religious relics.", crucifix)
        grocery_store = House("Grocery Store", "A store filled with garlic.", garlic)
        blacksmith = House("Blacksmith", "A blacksmith's forge with a silver sword.", silver_sword)
        storage = House("Storage", "A storage area containing a UV light.", uv_light)

        # Add houses to the town
        self.houses_list = [village_hall, vampire_mansion, library, carpentry, church, hospital, gift_shop, grocery_store,
                            blacksmith, storage]

    def add_house(self, house):
        """Add a new house to the town."""
        self.houses_list.append(house)

    def find_house(self, input_nickname):
        """Find a house by its name or alias."""
        for house in self.houses_list:
            if house.name.lower() == input_nickname.lower():
                return house
        return None

# =================================================================================================
# =====================================  Player class  ============================================
# =================================================================================================

class Player:
    """
    This class represents the player in the game.

    Attributes:
        name (str): Player's name.
        inventory (list): List of collected items.
        current_location (str): Player's current location.
        health (int): Player's health points (default is 100).
    """
    def __init__(self, name, start_location):
        self.name = name
        self.inventory = []
        self.current_location = start_location
        self.health = 100

    # collects an item from the current house : this function needs the town object to get the houses' info.
    def collect_item(self, house):
        """Collect an item from the specified house if it's available."""
        if house.item is not None and house.item not in self.inventory:
            self.inventory.append({
                'name': house.item.name,
                'description': house.item.description,
                'image_key': house.item.image_key
            })
            collected_item = house.item
            return collected_item
        return None


    def player_status(self):
        """Return the player's current location and inventory."""
        if self.inventory:
            return {'location': self.current_location, 'inventory': self.inventory}
        else:
            return {'message': 'Your inventory is empty'}, 404


    def take_damage(self, amount):
        """Reduce the player's health by the specified amount."""
        self.health -= amount
        if self.health <= 0:
            return {'message': 'You have been defeated.'}
        return {'message': f'You took {amount} damage. Health: {self.health}'}

    def heal(self, amount):
        """Heal the player by the specified amount."""
        self.health += amount
        return {'message': f'You healed {amount}. Health: {self.health}'}


    # Player class can check weight
    def check_inventory_weight(self):
        """Calculate the total weight of all items in the player's inventory."""
        total_weight = sum(item.weight for item in self.inventory)
        return total_weight


# Save and Load Player progress in a JSON file   *********** JSON **************
# function to save the game in a Json file
def save_game(player_j):
    game_data = {
        'name': player_j.name,
        'inventory': player_j.inventory,
        'current_location': player_j.current_location,
        'health': player_j.health
    }
    with open('save_game.json', 'w') as file:
        json.dump(game_data, file)
    return {'message': 'Game saved!'}


# function to load the game from a Json file
def load_game(player):
    with open('save_game.json', 'r') as file:
        game_data = json.load(file)
    player.name = game_data['name']
    player.inventory = game_data['inventory']
    player.current_location = game_data['current_location']
    player.health = game_data['health']
    return {'message': 'Game loaded!'}


# Function to add Leaderboards
leaderboard = []
def update_leaderboard(player_l):
    leaderboard.append({'player': player_l.name, 'inventory_size': len(player_l.inventory)})
    leaderboard.sort(key=lambda x: x['inventory_size'], reverse=True)


# function to implement logging to track player actions and store it in a file or db for analytics
logging.basicConfig(filename='game.log', level=logging.INFO)
def log_action(player_lo, action):
    logging.info(f'{player_lo.name} performed action: {action}')

