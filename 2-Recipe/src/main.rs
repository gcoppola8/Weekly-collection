use clap::{Arg, Command};
use rusqlite::{params, Connection, Result};

struct Ingredient {
    name: String,
    quantity: String,
}

struct Recipe {
    name: String,
    ingredients: Vec<Ingredient>,
}

impl Recipe {
    fn new(name: &str) -> Recipe {
        Recipe {
            name: name.to_string(),
            ingredients: Vec::new(),
        }
    }

    fn add_ingredient(&mut self, name: &str, quantity: &str) {
        self.ingredients.push(Ingredient {
            name: name.to_string(),
            quantity: quantity.to_string(),
        });
    }

    fn display(&self) {
        println!("Recipe: {}", self.name);
        for ingredient in &self.ingredients {
            println!("{} - {}", ingredient.name, ingredient.quantity);
        }
    }

    fn save_to_db(&self, conn: &Connection) -> Result<()> {
        conn.execute("INSERT INTO recipes (name) VALUES (?1)", params![self.name])?;

        let recipe_id = conn.last_insert_rowid();
        for ingredient in &self.ingredients {
            conn.execute(
                "INSERT INTO ingredients (recipe_id, name, quantity) VALUES (?1, ?2, ?3)",
                params![recipe_id, ingredient.name, ingredient.quantity],
            )?;
        }
        Ok(())
    }

    // fn load_from_db(conn: &Connection, name: &str) -> Result<Recipe> {
    //     let mut stmt = conn.prepare("SELECT id, name FROM recipes WHERE name = ?1")?;
    //     let recipe_iter = stmt.query_map(params![name], |row| {
    //         Ok(Recipe {
    //             name: row.get(1)?,
    //             ingredients: Vec::new(),
    //         })
    //     })?;

    //     let mut recipe = recipe_iter.into_iter().next().unwrap()?;
    //     let mut stmt =
    //         conn.prepare("SELECT name, quantity FROM ingredients WHERE recipe_id = ?1")?;
    //     let ingredient_iter = stmt.query_map(params![recipe.name], |row| {
    //         Ok(Ingredient {
    //             name: row.get(0)?,
    //             quantity: row.get(1)?,
    //         })
    //     })?;

    //     for ingredient in ingredient_iter {
    //         recipe.ingredients.push(ingredient?);
    //     }

    //     Ok(recipe)
    // }
}

fn initialize_db(conn: &Connection) -> Result<()> {
    conn.execute(
        "CREATE TABLE IF NOT EXISTS recipes (
                  id INTEGER PRIMARY KEY,
                  name TEXT NOT NULL UNIQUE
                  )",
        [],
    )?;
    conn.execute(
        "CREATE TABLE IF NOT EXISTS ingredients (
                  id INTEGER PRIMARY KEY,
                  recipe_id INTEGER NOT NULL,
                  name TEXT NOT NULL,
                  quantity TEXT NOT NULL,
                  FOREIGN KEY(recipe_id) REFERENCES recipes(id)
                  )",
        [],
    )?;
    Ok(())
}

fn main() {
    let conn = Connection::open("recipes.db").unwrap();
    let _ = initialize_db(&conn);

    let _matches = Command::new("Recipe Game")
        .version("1.0")
        .author("Gennaro Coppola <coppola612@gmail.com>")
        .about("A recipe store for travellers")
        .subcommand(
            Command::new("create")
                .about("Creates a new recipe")
                .arg(Arg::new("Name").required(true).index(1)),
        )
        .subcommand(
            Command::new("ingredients")
                .about("Edit ingredients to recipe")
                .arg(Arg::new("of").required(true).index(1)),
        )
        .arg(Arg::new("verbose").short('v').long("verbose"))
        .get_matches();

    let name = "Cavolfiore al forno";
    let ingredients = vec!["Cavolfiore", "1", "Olio", "q.b."];

    let mut recipe = Recipe::new(name);
    for ingredient in ingredients.chunks(2) {
        if ingredient.len() == 2 {
            recipe.add_ingredient(ingredient[0], ingredient[1]);
        }
    }

    recipe.save_to_db(&conn).unwrap();
    println!("Recipe saved to database:");
    recipe.display();
}
