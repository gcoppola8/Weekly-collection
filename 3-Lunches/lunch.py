import random

main_dishes_calories = {
    "Grilled Chicken": 300,
    "Beef Steak": 400,
    "Vegetable Stir Fry": 250,
    "Spaghetti Bolognese": 350,
    "Fish Tacos": 320,
    "Chicken Alfredo": 450,
    "Pulled Pork Sandwich": 500,
    "Vegetarian Pizza": 280
}

side_dishes_calories = {
    "French Fries": 350,
    "Caesar Salad": 150,
    "Garlic Bread": 200,
    "Mashed Potatoes": 250,
    "Steamed Vegetables": 100,
    "Coleslaw": 180,
    "Onion Rings": 300,
    "Rice Pilaf": 220,
    "Baked Beans": 200,
    "Macaroni and Cheese": 400
}

desserts_calories = {
    "Chocolate Cake": 450,
    "Apple Pie": 300,
    "Ice Cream Sundae": 350,
    "Cheesecake": 400
}

if __name__ == "__main__":
    lunch_main = []
    lunch_side = []
    lunch_dessert = []

    while len(lunch_main) < 7:
        lunch_main.append(random.choice(list(main_dishes_calories.keys())))
        lunch_side.append(random.choice(list(side_dishes_calories.keys())))
        lunch_dessert.append(random.choice(list(desserts_calories.keys())))

    for i in range(7):
        print(f"Day {i+1}:")
        print(f"  Main Dish: {lunch_main[i]} ({main_dishes_calories[lunch_main[i]]} calories)")
        print(f"  Side Dish: {lunch_side[i]} ({side_dishes_calories[lunch_side[i]]} calories)")
        print(f"  Dessert: {lunch_dessert[i]} ({desserts_calories[lunch_dessert[i]]} calories)")
        print()
