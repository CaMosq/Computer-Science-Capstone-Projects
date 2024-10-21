# Carmen Mosquera
# IT 140 SNHU
# Vampire Hunter, a text-based adventure game


# Game instructions
def instructions():
    print('\nWelcome to the village, Vampire Hunter!')
    print('\n\n------------  I N S T R U C T I O N S  --------------')
    print('-------------------------------------------------------')
    print('\nTo kill the vampire you must search the village and collect 8 items.')
    print('to move through the village use commands: north, south, west, or east.')
    print('To end the game type exit')
    print('If you find the item you need, just add it to your inventory.')
    print('\n-------------------- S T A R T I N G   G A M E ----------------------')
    print('-----------------------------------------------------------------------')


# Game valid commands
move_list = ['North', 'South', 'East', 'West', 'exit']


# Player status information
def player_info(location, f_inventory):
    print('Your location is: {}'.format(location))
    print('Your inventory:  {}'.format(f_inventory))


# Update location
def update_location(location, inventory):
    print('\nLocation updated!')
    player_info(location, inventory)  # print player status information


# get item from room
def get_item(location, r_inventory, item):
    if item != '':
        if item not in r_inventory:  # if player goes back to location don't duplicate item
            print('\n* * * *  New item found: {}  * * * *'.format(item))
            grab_item_command = input('Would you like to grab it?\n Reply Yes or No: ')

            if grab_item_command == 'Yes' or grab_item_command == 'yes':
                r_inventory.append(item)
                print('\nInventory Updated!')
            else:
                print('\nInventory not updated!')

            # show player status here only if a new item was grabbed
            player_info(location, r_inventory)


# Final battle to win or loose the game
def great_battle(inventory):
    if len(inventory) < 8:  # if player hasn't found 8 items, player loses the game
        print('\nThe vampire is attacking you \U0001f608')
        print('You dont have enough items to kill the vampire.')
        print('You have been killed.')
    else:
        # player wins the game
        print('\nThe vampire is attacking you')
        print('You have enough items to kill the vampire')
        print('Great job! You killed the vampire')


# main
def main():
    # village location's map and items
    village = {
        'Village Hall': {'East': 'Library', 'South': 'Carpentry'},
        'Library': {'West': 'Village Hall', 'South': 'Church', 'item': 'Ancient Book'},
        'Carpentry': {'East': 'Church', 'West': 'Hospital', 'North': 'Village Hall', 'item': 'Wood Stack'},
        'Church': {'West': 'Carpentry', 'North': 'Library', 'South': 'Gift Shop', 'item': 'Holy Water'},
        'Hospital': {'East': 'Carpentry', 'South': 'Blacksmith', 'item': 'Health Potion'},
        'Gift Shop': {'North': 'Church', 'West': 'Grocery Store', 'South': 'Storage', 'item': 'Crucifix'},
        'Grocery Store': {'East': 'Gift Shop', 'West': 'Blacksmith', 'item': 'Garlic'},
        'Blacksmith': {'North': 'Hospital', 'South': 'Vampire Mansion', 'East': 'Grocery Store',
                       'item': 'Silver Sword'},
        'Storage': {'North': 'Gift Shop', 'item': 'UV light'},
        'Vampire Mansion': {'North': 'Blacksmith', 'item': 'Vampire'}  # villain
    }

    # Show instructions to player
    instructions()

    # Show starting location to player
    current_location = 'Village Hall'
    item = ''
    print('Your starting location is: {}'.format(current_location))

    # Show starting inventory to player
    inventory = []
    print('Your inventory: {}'.format(inventory))

    # start the game
    while True:

        # ask player to enter move
        print('--------------------------------------')
        player_move = input('\nWhere would you like to go? : ')

        # if input is a valid move command
        if player_move in move_list:

            # if input is valid, and it's not 'exit'
            if player_move != 'exit':

                # if input connects current room to another room
                if player_move in village[current_location].keys():  # and current_location != 'Vampire Mansion':
                    current_location = village[current_location][player_move]  # set location
                    update_location(current_location, inventory)  # update location

                    # if current location is the vampire mansion
                    if current_location == 'Vampire Mansion':
                        great_battle(inventory)  # final battle
                        break
                    else:
                        # if current location is any other location, continue playing
                        # if current location has an item
                        if 'item' in village[current_location].keys():
                            item = village[current_location]['item']  # set item
                            get_item(current_location, inventory, item)  # get item
                        else:
                            # else if current location doesn't have an item
                            item = ''  # no item, continue game
                else:
                    # if there is no location in the input direction
                    print('\nOpps! nothing in that direction')
                    print("You're still in the: {}".format(current_location))
            else:
                # if input is valid but its 'exit'. end game
                break

        else:
            # if input is an invalid command
            print("\nOps! invalid command. Please enter a valid move")

    print('------------------- G A M E   O V E R --------------------')
    print('\nThank you for playing Vampire Hunter.')


if __name__ == '__main__':
    main()
