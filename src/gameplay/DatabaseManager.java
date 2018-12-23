package gameplay;

import java.lang.reflect.Array;
import java.util.*;
import com.mongodb.*;

public class DatabaseManager {

    // properties
    private DBCollection userCollection;
    private DBCollection levelCollection;
    private DBCollection themeCollection;
    private DBCollection sandboxCollection;
    private DB dbObj;

    // constructor
    public DatabaseManager() {
        connectToDatabase();
    }

    public void connectToDatabase() {
        try {

            // connect to MongoDb
            MongoClient mongoClntObj = new MongoClient("localhost", 27017);
            System.out.println("--- Connection to database Successful ---\n\n"); // success message

            // get MongoDb database. If the database doesn't exists, MongoDb will automatically create it
            dbObj = mongoClntObj.getDB("wallsAndWarriors");

            // get MongoDb collections. If the collection doesn't exists, MongoDb will automatically create it
            levelCollection = dbObj.getCollection("levels");
            userCollection = dbObj.getCollection("users");
            themeCollection = dbObj.getCollection("themes");
            sandboxCollection = dbObj.getCollection("sandbox");
            insertThemes();
            addLevels();


//            System.out.println(dbObj.getCollectionNames()); // displays the existing collections in the database
        } catch (Exception e) {
            System.out.println(e); // failure message
        }
    }


    // methods

    // ============== ACHIEVEMENTS ============== //

    /*
        method to retrieve the number of coins a player has
        @param: username
        @return: number of coins
     */
    public int getCoins( String name) {
        //check if user already exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);

        if (userSearch.count() == 0) // user not found
            return 0;
        else { // user found
            DBObject doc = userSearch.next();
            return (int) doc.get("coins"); // retrieve coins and return
        }
    }



    /*
        method to update the number of coins a player has
        @param: username and numberOfCoins to be added
     */
    public void setCoins( String name, int numberOfCoins) {
        // create a query
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);

        int currentCoins = getCoins( name); // retrieving current number of coins

        // creating update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", numberOfCoins);
        updateQuery.put("diamonds", getDiamonds( name));
        updateQuery.put("unlockedLevels", getAllUnlockedLevels( name));
        updateQuery.put("music", getMusicSetting( name));
        updateQuery.put("soundEffect", getSoundEffectsSetting( name));
        updateQuery.put("currentTheme", getCurrentTheme( name));
        updateQuery.put("unlockedThemes", getUnlockedThemes( name));

        // update document
        userCollection.update(query, updateQuery);
    }



    /*
        method to retrieve the number of diamonds a player has
        @param: username
        @return: number of diamonds
     */
    public int getDiamonds( String name) {
        //check if user already exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);

        if (userSearch.count() == 0) // user not found
            return 0;
        else { // user found
            DBObject doc = userSearch.next();
            return (int) doc.get("diamonds"); // retrieve diamonds and return
        }
    }



    /*
        method to update the number of coins a player has
        @param: username and numberOfDiamonds to be added to the current number of diamonds
     */
    public void setDiamonds( String name, int numberOfDiamonds) {
        // create a query
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);

        int currentDiamonds = getDiamonds( name); // retrieving current number of diamonds

        // creating update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", getCoins( name));
        updateQuery.put("diamonds", numberOfDiamonds); // adding diamonds
        updateQuery.put("unlockedLevels", getAllUnlockedLevels( name));
        updateQuery.put("music", getMusicSetting( name));
        updateQuery.put("soundEffect", getSoundEffectsSetting( name));
        updateQuery.put("currentTheme", getCurrentTheme( name));
        updateQuery.put("unlockedThemes", getUnlockedThemes( name));

        // update document
        userCollection.update(query, updateQuery);
    }


    // ============== THEMES ============== //

    /*
        method to retrieve the number of coins a player has
        @param: username
        @return: number of coins
     */
    public String getCurrentTheme( String name) {
        //check if user already exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);


        if (userSearch.count() == 0) // user not found
            return "error";
        else { // user found
            DBObject doc = userSearch.next();
            String currentTheme =  (String) doc.get("currentTheme"); // retrieve theme and return
            return currentTheme;
        }
    }



    /*
        method to update the number of coins a player has
        @param: username and numberOfCoins to be added
     */
    public void setTheme( String name, String theme) {
        // create a query
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);

        // creating update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", getCoins( name));
        updateQuery.put("diamonds", getDiamonds( name));
        updateQuery.put("unlockedLevels", getAllUnlockedLevels( name));
        updateQuery.put("music", getMusicSetting( name));
        updateQuery.put("soundEffect", getSoundEffectsSetting( name));
        updateQuery.put("currentTheme", theme); // updating theme here
        updateQuery.put("unlockedThemes", getUnlockedThemes( name));

        // update document
        userCollection.update(query, updateQuery);
    }

    public void insertThemes() {
        if ( themeCollection.count() == 0) {
            BasicDBObject allThemes = new BasicDBObject();
            String[] themeArray = new String[5];

                BasicDBObject newTheme = new BasicDBObject();

                // add user to the collection
                themeArray[0] = "default";
                themeArray[1] = "three little pigs";
                themeArray[2] = "scooby doo";
                themeArray[3] = "lord of the rings";
                themeArray[4] = "hello there";

                allThemes.append("allThemes", themeArray);
                themeCollection.insert(allThemes);
        }
    }

    /*
        method to get themes
        themes are saved in a seperate database
        no parameters because list of ALL themes are global
        @return list of all themes
     */
    public ArrayList<String> getAllThemes() {
        ArrayList<String> arr = new ArrayList<String>();
        if ( themeCollection.count() != 0) {
            DBCursor cursor = themeCollection.find(); // finds all themes

            while (cursor.hasNext()) {
                DBObject doc = cursor.next();
                arr = (ArrayList<String>) doc.get("allThemes"); // gets the ArrayList saved in the database and save it in arr
            }

            // print all themes
//            System.out.println("List of Themes:");
//            for (String x : arr) {
//                System.out.println(x);
//            }
            return arr;
        }
        else {
            System.out.println("No themes to display.");
        }
        System.out.println();
        return arr;
    }


    /*
        method to unlock a theme
        @param: user's name and the theme he/she wishes to unlock
     */
    public void unlockTheme( String name, String newTheme) {
        // prepare a query to search for the user in question
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query); // find appropriate document
        DBObject doc = userSearch.next();
        ArrayList<String> updatedArray = (ArrayList<String>) doc.get("unlockedThemes"); // get the array to update it

        // check if the user already has that theme unlocked
        ArrayList<String> allThemes = getAllThemes();
        for ( String x : updatedArray) {
            if ( x.equals(newTheme))
                return;
        }

        updatedArray.add( newTheme); // add to array

        // prepare update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", getCoins( name));
        updateQuery.put("diamonds", getDiamonds( name));
        updateQuery.put("unlockedLevels", getAllUnlockedLevels( name));
        updateQuery.put("music", getMusicSetting( name));
        updateQuery.put("soundEffect", getSoundEffectsSetting( name));
        updateQuery.put("currentTheme", getCurrentTheme( name));
        updateQuery.put("unlockedThemes", updatedArray);

        userCollection.update( query, updateQuery); // update document
    }


    /*
        method telling the unlocked themes for a user
        @param: name of the user
        @return: Array list containing unlocked themes
     */
    public ArrayList<String> getUnlockedThemes( String name) {
        //check if user exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);

        DBObject doc = userSearch.next();
        ArrayList<String> unlockedArray = (ArrayList<String>) doc.get("unlockedThemes"); // get list of unlockedLevels for the specific player
        return unlockedArray;
    }





    // ============== UNLOCKED LEVELS ============== //

    /*
        method to retrieve the last unlocked level
        @returns level code of last unlocked level
     */
    public String getLastUnlockedLevel( String name) {
        //check if user exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);

        String levelCode;

        if (userSearch.count() == 0) // user not found
            levelCode = "error"; // if no user found
        else { // user found
            DBObject doc = userSearch.next();
            ArrayList<String> levelsArr = (ArrayList<String>) doc.get("unlockedLevels"); // get list of unlockedLevels for the specific player
            levelCode = levelsArr.get(levelsArr.size() - 1); // generate appropriate levelCode
        }
        return levelCode;
    }


    /*
        method to get a list of all unlocked levels by a single user
        @param username
        @return ArrayList containing level codes of all unlocked levels
     */
    public ArrayList<String> getAllUnlockedLevels(String name) {
        //check if user exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);

        DBObject doc = userSearch.next();
        ArrayList<String> unlockedArray = (ArrayList<String>) doc.get("unlockedLevels"); // get list of unlockedLevels for the specific player
        return unlockedArray;
    }

    public int getBoardSize( int levelNumber) {
        String levelCode = "slvl" + levelNumber; // get it into a form that is saved in the database

        // create a query and find the level
        BasicDBObject query = new BasicDBObject();
        query.put("levelCode", levelCode);
        DBCursor levelSearch = sandboxCollection.find(query);
        DBObject doc = levelSearch.next();

        int boardSize = (int) doc.get("size");
        return boardSize;
    }

    /*
        whenever user completes a challenge, this method must be called to update the last unlocked challenge
        @param username
     */
    public void updateUnlockedLevel( String name) {
        // prepare a query
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query); // find appropriate document
        DBObject doc = userSearch.next();
        ArrayList<String> updatedArray = (ArrayList<String>) doc.get("unlockedLevels"); // get the array to update it

        String oldLevelCode = getLastUnlockedLevel( name); // retrieve the last unlocked level
        char oldLevelChar =  oldLevelCode.charAt( oldLevelCode.length() - 1); // extract the number of the last unlocked level
        int oldLevelNumber = Character.getNumericValue( oldLevelChar); // convert character to numeric value

        int newLevelNumber = oldLevelNumber + 1; // increment the number
        String newLevelCode = "lvl" + newLevelNumber; // prepare a new level code

        updatedArray.add( newLevelCode); // add to array

        // prepare update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", getCoins( name));
        updateQuery.put("diamonds", getDiamonds( name));
        updateQuery.put("unlockedLevels", updatedArray);
        updateQuery.put("music", getMusicSetting( name));
        updateQuery.put("soundEffect", getSoundEffectsSetting( name));
        updateQuery.put("currentTheme", getCurrentTheme( name));
        updateQuery.put("unlockedThemes", getUnlockedThemes( name));

        userCollection.update( query, updateQuery); // update document
    }



    // ============== SETTINGS ============== //

    /*
        method to get music settings
        @param username
        @return: music volume
     */
    public int getMusicSetting( String name) {
        //check if user  exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);

        if (userSearch.count() == 0) // user not found
            return 0;
        else { // user found
            DBObject doc = userSearch.next();
            return (int) doc.get("music"); // retrieve value and return
        }
    }

    public void setMusicSetting( String name, int value) {
        // create a query
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);

        // creating update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", getCoins( name));
        updateQuery.put("diamonds", getDiamonds( name));
        updateQuery.put("unlockedLevels", getAllUnlockedLevels( name));
        updateQuery.put("music", value); // updated here
        updateQuery.put("soundEffect", getSoundEffectsSetting( name));
        updateQuery.put("currentTheme", getCurrentTheme( name));
        updateQuery.put("unlockedThemes", getUnlockedThemes( name));


        // update document
        userCollection.update(query, updateQuery);
    }


    /*
        method to get sound effect settings
        @param username
        @return: sound effects volume
     */

    public int getSoundEffectsSetting( String name) {
        //check if user  exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBCursor userSearch = userCollection.find(query);

        if (userSearch.count() == 0) // user not found
            return 0;
        else { // user found
            DBObject doc = userSearch.next();
            return (int) doc.get("soundEffect"); // retrieve value and return
        }
    }

    public void setSoundEffectsSetting( String name, int value) {
        // create a query
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);

        // creating update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", getCoins( name));
        updateQuery.put("diamonds", getDiamonds( name));
        updateQuery.put("unlockedLevels", getAllUnlockedLevels( name));
        updateQuery.put("music", getMusicSetting( name));
        updateQuery.put("soundEffect", value); // updated here
        updateQuery.put("currentTheme", getCurrentTheme( name));
        updateQuery.put("unlockedThemes", getUnlockedThemes( name));

        // update document
        userCollection.update(query, updateQuery);
    }

    /*
        method to save mute settings
        if mute option selected, values of musicSetting and soundSetting must be set to 0
        @param: username
     */
    public void muteSetting( String name) {
        // create a query
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);

        // creating update query
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.put("name", name);
        updateQuery.put("coins", getCoins( name));
        updateQuery.put("diamonds", getDiamonds( name));
        updateQuery.put("unlockedLevels", getAllUnlockedLevels( name));
        updateQuery.put("music", 0); // <---- muting done here
        updateQuery.put("soundEffect", 0); // <---- muting done here
        updateQuery.put("currentTheme", getCurrentTheme( name));
        updateQuery.put("unlockedThemes", getUnlockedThemes( name));

        // update document
        userCollection.update(query, updateQuery);
    }


    // ============== USER OPERATIONS ============== //

    /*
        Method to add a user to the database
        param: name of the user
     */
    public void addUser( String name) {
            BasicDBObject newUser = new BasicDBObject();

            //check if user already exists in the database
            BasicDBObject query = new BasicDBObject();
            query.put("name", name);
            DBCursor userSearch = userCollection.find(query);

            // if the user already exists
            if (userSearch.count() > 0) {
                System.out.println(name + " is already in use. Please try with another username.\n");
            }
            else {
                // set theme to default and update array
                ArrayList<String> unlockedThemes = new ArrayList<String>();
                unlockedThemes.add("default");

                // set first unlocked level to lvl1 and update array
                ArrayList<String> unlockedLevels = new ArrayList<String>();
                unlockedLevels.add("lvl1");

                // insert relevant properties
                newUser.append("name", name)
                    .append("coins", 0) // coins will be 0 when the game starts for the first time
                    .append("diamonds", 0) // diamonds will be 0 when the game starts for the first time
                    .append("trophies", 0) // trophies will be 0 when the game starts for the first time
                    .append("unlockedLevels", unlockedLevels)
                    .append("music", 100) // volume of music will be 100
                    .append("soundEffect", 100) // volume of soundEffect will be 100
                    .append("mute", false)
                    .append("currentTheme", "default")
                    .append("unlockedThemes", unlockedThemes);

                // insert user
                userCollection.insert(newUser);
                System.out.println(name + " added successfully.");
            }
    }



    /*
        Method to get a details of one user
        return: prints the list
     */
    public void getOneUser( String name) {


        if(userCollection.count() != 0) {

            BasicDBObject newUser = new BasicDBObject();

            //check if user exists in the database
            BasicDBObject query = new BasicDBObject();
            query.put("name", name);
            DBCursor cursor = userCollection.find(query);

            // add all the records to an arraylist
            while (cursor.hasNext()) {
                DBObject doc = cursor.next();

                // extract all relevant details
                int coins = getCoins( name);
                int diamonds = getDiamonds( name);
                String lastUnlockedLevel = getLastUnlockedLevel( name);
                int music = getMusicSetting( name);
                int soundEffect = getSoundEffectsSetting( name);
                String currentTheme = getCurrentTheme( name);
                ArrayList<String> unlockedThemes = getUnlockedThemes( name);

                // prepare a string
                String str = "Username: " + name + "\nCoins: " + coins + "\nDiamonds: " + diamonds + "\nLast level: " + lastUnlockedLevel + "\nMusic Volume: " + music + "\nSound Effects Volume: " + soundEffect + "\nCurrent Theme: " + currentTheme + "\nUnlocked Themes: " + unlockedThemes.toString();

                // print the string
                System.out.println(str);
            }
        }
        else { // handle if user does not exist
            System.out.println("User does not exist");
        }
        System.out.println();
    }



    /*
        Method to get a list of the users
        return: prints the list
    */
    public void getAllUsers() {
        if( userCollection.count() != 0) {
            ArrayList<String> arr = new ArrayList<String>();
            DBCursor cursor = userCollection.find();

            while (cursor.hasNext()) {
                DBObject doc = cursor.next();

                // extract all relevant details
                String name = (String) doc.get("name");
                int coins = getCoins( name);
                int diamonds = getDiamonds( name);
                String lastUnlockedLevel = getLastUnlockedLevel( name);
                int music = getMusicSetting( name);
                int soundEffect = getSoundEffectsSetting( name);
                String currentTheme = getCurrentTheme( name);
                ArrayList<String> unlockedThemes = getUnlockedThemes( name);

                // prepare a string
                String str = "Username: " + name + "\nCoins: " + coins + "\nDiamonds: " + diamonds + "\nLast level: " + lastUnlockedLevel + "\nMusic Volume: " + music + "\nSound Effects Volume: " + soundEffect + "\nCurrent Theme: " + currentTheme + "\nUnlocked Themes: " + unlockedThemes.toString();

                // adds the user info in the ArrayList
                arr.add(str);
            }

            // print results
            System.out.println("List of users:");
            for (String x : arr) {
                System.out.println(x);
                System.out.println();
            }
        }
        else {
            System.out.println("No users to display.");
        }
        System.out.println();
    }

    /*
        Method to get a list of the usernames
        return: arrayList containing names of all users
    */
    public ArrayList<String> getAllUsernames() {
        if( userCollection.count() != 0) {
            ArrayList<String> nameArray = new ArrayList<String>();
            DBCursor cursor = userCollection.find();

            while (cursor.hasNext()) {
                DBObject doc = cursor.next();

                // extract all relevant details
                String name = (String) doc.get("name");

                // adds the user info in the ArrayList
                nameArray.add(name);
            }

            // print results
            return nameArray;
        }
        else {
            return null;
        }
    }



    /*
        Method to delete just one user, according to the query provided as parameter
        @param: username to be deleted
    */
    public boolean deleteOneUser( String name) {
        if (dbObj.collectionExists("users")) {
            BasicDBObject query = new BasicDBObject();
            query.put("name", name);

            //check if user already exists in the database
            DBCursor userSearch = userCollection.find(query);
            DBObject toBeDeleted = userSearch.next();

            if (userSearch.count() == 0) {
                System.out.println("DELETE OPEATION FAILED: " + name + " does not exist in the database.\n");
                return false;
            }
            else {
                // remove user from the collection
                userCollection.remove(toBeDeleted);
                System.out.println(name + " deleted successfully.\n");
            }
        }
        return true;
    }



    /*
        Method denoting the "RESET GAME" functionality of the game
        Deletes all elements from the collection of users
     */
    public boolean deleteAllUsers() {
        // delete all user if any users exist
        if( userCollection.count() != 0) {
            BasicDBObject document = new BasicDBObject();
            userCollection.remove(document); // deleted
            System.out.println("All items deleted.\nNumber of elements in the database: " + userCollection.count());
            return true;
        }
        else {
            System.out.println("No users to delete.");
            return false;
        }
    }

    // ============== LEVELS ============== //

    /*
        method to add the level to the database
        @param levelCode and string of the level
     */
    public void addLevel(String levelCode, String levelString) {
        //check if the level already exists in the database
        BasicDBObject query = new BasicDBObject();
        query.put("levelString", levelString);
        DBCursor levelSearch = levelCollection.find(query);

        // if the level already exists
        if (levelSearch.count() > 0) {
//            System.out.println( levelCode + " already exists in the database.");
            System.out.print("");
        }
        else {
            BasicDBObject newLevel = new BasicDBObject();

            // Create new instance of newLevel
            newLevel.append("levelCode", levelCode)
                    .append("levelString", levelString);

            // add level to the collection
            levelCollection.insert(newLevel);
        }
    }

    /*
        helper method to be called in the constructor
     */
    public void addLevels() {
        addLevel("lvl1","IIEEEEEIIIIETSTEIIEEEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEBEBEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl2","IIEEEEEIIIIEBEEEIIEEEEEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl3","IIEEEEEIIIIEEEBEIIEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIETSTEIIIIEEEEEII");
        addLevel("lvl4","IIEEEEEIIIIEBEEEIIEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEBEREEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl5","IIEEEEEIIIIEREEEIIEEEEEEEEEEEETSTEEEEEEEEEEEEEREEEBEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl6","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEREEEEEEEEEEEEEEEEETSTEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl7","IIEEEEEIIIIEBEEEIIEEEEEEEEEEEEREEEEEEEEEEEEEEEEETSTEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl8","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEETEREEEEEESEEEEEEEETEREEEEEEEEEEEEEBEEEREEEEEEEEEEIIEEEBEIIIIEEEEEII");
        addLevel("lvl9","IIEEEEEIIIIEEEEEIIEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEREREBEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl10","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEREEEBEEEEEEEEEEETSTEEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl11","IIEEEEEIIIIEEEEEIIEEEEEEEEEETSTEEEEEEEEEEEEEEEBEREBEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl12","IIEEEEEIIIIEREEEIIEEEEEEEEEETSTEEEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEBEREEEEEEEEEEEEEEIIEREEEIIIIEEEEEII");
        addLevel("lvl13","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEETEREREEEESEEEEEEEETEEEBEEEEEEEEEEEREBEBEEEEEEEEEEEEIIEEEREIIIIEEEEEII");
        addLevel("lvl14","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEREEEEEEEEEEEEIIETSTEIIIIEEEEEII");
        addLevel("lvl15","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEEEEEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEETEREEEEEESEEEEEIIETEREIIIIEEEEEII");
        addLevel("lvl16","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEBEEEEEEEEEEEEEEEREBEREEEEEEEEEEEEEEETEEEEEEEESEEEEEIIETEEEIIIIEEEEEII");
        addLevel("lvl17","IIEEEEEIIIIEEEEEIIEEEEEEEEEETEEEEEEEESEEEEEEEETEEEBEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEIIEEEBEIIIIEEEEEII");
        addLevel("lvl18","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEEEEREEEEEEEEEEEBERETSTEEEEEEEEEEIIEEEREIIIIEEEEEII");
        addLevel("lvl19","IIEEEEEIIIIEBEEEIIEEEEEEEEEEEEEEEEEEEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEBEEEIIIIEEEEEII");
        addLevel("lvl20","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEETSTEEEEEEEEEEEBEEEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl21","IIEEEEEIIIIEBEEEIIEEEEEEEEEEBEREEEEEEEEEEEEEEETSTEBEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl22","IIEEEEEIIIIEREREIIEEEEEEEEEETEEEBEEEESEEEEEEEETEEEEEREEEEEEEEEEEEEEEBEREEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl23","IIEEEEEIIIIEEEREIIEEEEEEEEEETEREEEEEESEEEEEEEETEEEEEREEEEEEEEEEEBEEEREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl24","IIEEEEEIIIIEEEEEIIEEEEEEEEEEREEEEEEEEEEEEEEEEEEEBEREBEEEEEEEEEEERETSTEEEEEEEEEEEEIIEREEEIIIIEEEEEII");
        addLevel("lvl25","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEBETSTEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl26","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEBEEEEEEEEEEEEEEEEEREEETEEEEEEEESEEEEBEEETEEEEEEEEEEIIEREREIIIIEEEEEII");
        addLevel("lvl27","IIEEEEEIIIIEBEEEIIEEEEEEEEEEREEETSTEEEEEEEEEEEREEEEEREEEEEEEEEEEREBEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl28","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEETEREEEEEESEEEEEEEETEEEBEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEBEIIIIEEEEEII");
        addLevel("lvl29","IIEEEEEIIIIEEEEEIIEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEBEBEIIIIEEEEEII");
        addLevel("lvl30","IIEEEEEIIIIEEEEEIIEEEEEEEEEEREBEREEEEEEEEEEEEEREREEEEEEEEEEEEEEETSTEEEEEEEEEEEEEEIIEEEBEIIIIEEEEEII");
        addLevel("lvl31","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEBEEEEEEEEEEEEEEEEEREBEREEEEEEEEEEEEETSTEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl32","IIEEEEEIIIIEBEREIIEEEEEEEEEEEEBEREBEEEEEEEEEEEREEETSTEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl33","IIEEEEEIIIIEBEEEIIEEEEEEEEEEEEREEEEEEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl34","IIEEEEEIIIIEEEEEIIEEEEEEEEEEREEEREEEEEEEEEEEEEBETSTEBEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEBEIIIIEEEEEII");
        addLevel("lvl35","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEEETEEEEEEEESEEEEEERETEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl36","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEEEEEEEEEEEEEEEEETEEEBEEEESEEEEEEEETEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl37","IIEEEEEIIIIEEEEEIIEEEEEEEEEEREEEEEEEEEEEEEEEEEBEEETSTEEEEEEEEEEEREEEEEBEEEEEEEEEEIIEEEREIIIIEEEEEII");
        addLevel("lvl38","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEREBEREEEEEEEEEEEEERETSTEEEEEEEEEEEEEEEBEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl39","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBERETEEEEEEEESEEEEREEETEEEEEEEEEEEEEREBEREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl40","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEBEEEEEEEEEEEEEEETEEEEEREESEEEEEEEETEREBEREEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl41","IIEEEEEIIIIEEETEIIEEEEESEEEEEEEETEBEEEEEEEEEEEEEBEEEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl42","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEETSTEEEEEEEEEEEEEEEREBEEEEEEEEEEIIEBEEEIIIIEEEEEII");
        addLevel("lvl43","IIEEEEEIIIIEEEEEIIEEEEEEEEEERETEEEEEEEESEEEEEEBETEEEREEEEEEEEEEEEEREBEREEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl44","IIEEEEEIIIIEEEBEIIEEEEEEEEEEBEREREEEEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl45","IIEEEEEIIIIEREBEIIEEEEEEEEEEBEEETEEEEEEEESEEEEREEETEEEEEEEEEEEEEEEBEREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl46","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEEEERETEREEEEEESEEEIIEBETEIIIIEEEEEII");
        addLevel("lvl47","IIEEEEEIIIIERETEIIEEEEESEEEEBEEETEEEEEEEEEEEEEBEEEREEEEEEEEEEEEEEEEEREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl48","IIEEEEEIIIIEEETEIIEEEEESEEEEREBETEEEEEEEEEEEEEREEEEEREEEEEEEEEEEEEEEBEREEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl49","IIEEEEEIIIIEBEEEIIEEEEEEEEEEEETEEEEEEEESEEEEEEEETEEEEEEEEEEEEEEEBEREEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl50","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEETEREREEEESEEEEEEBETEEEBEEEEEEEEEEEREEEBEEEEEEEEEEEEIIEEEREIIIIEEEEEII");
        addLevel("lvl51","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEETEREBEEEESEEEEEEEETEEEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl52","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEEEBEEEEEEEEEEEEEEEREEEEEEEEEEEEERETEEEEEEEESEEEEEIIETEEEIIIIEEEEEII");
        addLevel("lvl53","IIEEEEEIIIIEEEBEIIEEEEEEEEEEEETEEEEEEEESEEEEEERETEREEEEEEEEEEEEEEEEEBEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl54","IIEEEEEIIIIEBEEEIIEEEEEEEEEETSTEEEEEEEEEEEEEEEEEBEREEEEEEEEEEEEEEEEEEEBEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl55","IIEEEEEIIIIEEEREIIEEEEEEEEEEEETSTEEEEEEEEEEEEEEEBEEEREEEEEEEEEEEREEEEEEEEEEEEEEEEIIEREBEIIIIEEEEEII");
        addLevel("lvl56","IIEEEEEIIIIEEEEEIIEEEEEEEEEETEREEEEEESEEEEEEEETEBEEEREEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEBEIIIIEEEEEII");
        addLevel("lvl57","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEETSTEEEEEEEEEEEEEEIIEREBEIIIIEEEEEII");
        addLevel("lvl58","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEEEEEEEEEEEEEEETSTEREEEEEEEEEEEEEBEREBEEEEEEEEEEEEIIEREBEIIIIEEEEEII");
        addLevel("lvl59","IIEEEEEIIIIEEEBEIIEEEEEEEEEEEEEEBEEEEEEEEEEEEEREEEEEREEEEEEEEEEEEETSTEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl60","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEBEEEEEEEEEEEEEEETEEEREEEESEEEEEEEETEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl61","IIEEEEEIIIIEBEEEIIEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEBETEEEEEEEESEEEEEIIETEEEIIIIEEEEEII");
        addLevel("lvl62","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEEEEBEEEEEEEEEEEEETEEEBEEEESEEEEEIIETEREIIIIEEEEEII");
        addLevel("lvl63","IIEEEEEIIIIEBEEEIIEEEEEEEEEEREEEEEEEEEEEEEEEEERETEREEEEEESEEEEEEEETEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl64","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEREREEEEEEEEEEEEEEEBEREEEEEEEEEEEEETSTEREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl65","IIEEEEEIIIIEEEEEIIEEEEEEEEEEREREBEEEEEEEEEEEEEBEEEREEEEEEEEEEEEEEETSTEEEEEEEEEEEEIIEREEEIIIIEEEEEII");
        addLevel("lvl66","IIEEEEEIIIIEEEEEIIEEEEEEEEEETSTEEEEEEEEEEEEEEEBEREEEEEEEEEEEEEEEREBEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl67","IIEEEEEIIIIEEEREIIEEEEEEEEEEEEBETSTEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEEBEEEEEEEEEEEEIIEEEREIIIIEEEEEII");
        addLevel("lvl68","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEEEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEEBETEEEEEEEESEEEEEIIETEREIIIIEEEEEII");
        addLevel("lvl69","IIEEEEEIIIIETEEEIIEEESEEEEEEEETEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEBEIIIIEEEEEII");
        addLevel("lvl70","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEBETEREEEEEESEEEEEERETEBEEEEEEEEEEEEEBEREREEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl71","IIEEEEEIIIIEEEEEIIEEEEEEEEEETSTEREEEEEEEEEEEEEREEEBEEEEEEEEEEEEEBEREREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl72","IIEEEEEIIIIEBEEEIIEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEETEEEEEEEESEEEEEEEETEBEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl73","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEREBEREEEEEEEEEEEEEREEEBEEEEEEEEEEEEERETSTEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl74","IIEEEEEIIIIEBEBEIIEEEEEEEEEEEETEEEREEEESEEEEEERETEREREEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl75","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEREEEEEEEEEEEEEEEBERETEEEEEEEESEEEERERETEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl76","IIEEEEEIIIIEEEEEIIEEEEEEEEEEREEEEEEEEEEEEEEEEEBERETEEEEEEEESEEEERERETEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl77","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEREBEEEEEEEEEEEEEEETEREEEEEESEEEEEEEETEREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl78","IIEEEEEIIIIEEEBEIIEEEEEEEEEEEETSTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEIIEBEEEIIIIEEEEEII");
        addLevel("lvl79","IIEEEEEIIIIEEEEEIIEEEEEEEEEEEEREBEEEEEEEEEEEEEEETSTEBEEEEEEEEEEEEEREREEEEEEEEEEEEIIEEEEEIIIIEEEEEII");
        addLevel("lvl80","IIEEEEEIIIIEEEEEIIEEEEEEEEEEBEREBEEEEEEEEEEEEEEEEETSTEEEEEEEEEEEEEREEEEEEEEEEEEEEIIEEEEEIIIIEEEEEII");

    }

    /*
        method aimed to make the getLevel process easier
        @param: INTEGER of the level
     */
    public void getLevel( int levelCode) {
        getLevel("lvl" + levelCode);
    }

    /*
        method to get a level
        @param: level code
        @return: 2D array containing the characters representing objects on the board
     */
    public char[][] getLevel(String levelCode) {
        // find the level
        DBObject query = new BasicDBObject("levelCode", levelCode);
        DBObject doc = levelCollection.findOne(query);

        String levelString = (String) doc.get("levelString"); // extract the level's String

//        System.out.println("PRINTING level NOW");

        // displays level
        int row = 11; // row will always be 11 in classic mode
        int col = 9; // col will always be 9 in classic mode
        char[][] arr = new char[row][col];
        int count = 0;

        // prints 2D array for testing purposes
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                arr[i][j] = levelString.charAt(count);
                count++;
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        return arr;
    }

    /*
        method to get a level
        @param: level code
        @return: 2D array containing the characters representing objects on the board
     */
    public ArrayList<char[][]> getAllLevels() {
        DBCursor cursor = levelCollection.find();
        ArrayList<char[][]> allLevelsArray = new ArrayList<char[][]>();

        while ( cursor.hasNext()) {
            DBObject doc = cursor.next();
            String levelString = (String) doc.get("levelString"); // extract the level's String
            char[][] arr = stringTo2DArray(levelString);
            allLevelsArray.add(arr);
        }
        return allLevelsArray;
    }


    // ============== SANDBOX ============== //

    /*
        gets number of user created levels
        @return: count of documents in the collection
     */
    public int getSandboxLevelsCount() {
        return (int) sandboxCollection.count();
    }


    /*
        method to add a level to the sandbpx
        @param: 2D char array, a wall array
     */
    public void addSandboxLevel( char[][] board, int[] wallArray, int boardSize) {
        String levelString = "";

        int row = board.length;
        int col = board[0].length;

        // converts 2D array into a level string
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                levelString += board[i][j];
//                System.out.print(arr[i][j] + " ");
            }
        }

        BasicDBObject newLevel = new BasicDBObject();
        int count = (int) (sandboxCollection.count() + 1);
        // Create new instance of newLevel
        newLevel.append("levelCode", "slvl" + count)
                .append("levelString", levelString)
                .append("walls", wallArray)
                .append("size", boardSize);

        // add level to the collection
        sandboxCollection.insert(newLevel);
    }


    /*
        get the user created level so another user can play it
        @param: level number (from GUI)
        @return: Object array containing two arrays regarding the sandbox level
     */
    public Object[] getSandboxLevel( int levelNumber) {
        Object[] arr = new Object[2]; // array of Object type
        String levelCode = "slvl" + levelNumber; // get it into a form that is saved in the database

        // create a query and find the level
        BasicDBObject query = new BasicDBObject();
        query.put("levelCode", levelCode);
        DBCursor levelSearch = sandboxCollection.find(query);
        DBObject doc = levelSearch.next();

        String levelString = (String) doc.get("levelString"); // extract string of the level
        char[][] levelArr = stringTo2DArray(levelString); // convert it into a 2D char array

        // get the array list containing walls
        ArrayList<Integer> wallList = (ArrayList<Integer>) doc.get("walls");

        // convert them into an array
        int[] wallArr = new int[wallList.size()];
        for ( int i = 0; i < wallList.size(); i++) {
            wallArr[i] = wallList.get(i);
        }

        // add the two arrays in the Object array
        arr[0] = levelArr;
        arr[1] = wallArr;

        return arr; // return object array
    }

    /*
        method to get a level
        @param: level code
        @return: 2D array containing the characters representing objects on the board
     */
    public ArrayList<Object[]> getAllSandboxLevels() {
        Object[] levelObjectArr = new Object[2]; // array of Object type
        DBCursor cursor = sandboxCollection.find();
        ArrayList<Object[]> allLevelsArray = new ArrayList<Object[]>();

        while ( cursor.hasNext()) {
            DBObject doc = cursor.next();
            String levelString = (String) doc.get("levelString"); // extract the level's String
            char[][] levelArr = stringTo2DArray(levelString);

            ArrayList<Integer> wallList = (ArrayList<Integer>) doc.get("walls");

            // convert them into an array
            int[] wallArr = new int[wallList.size()];
            for ( int i = 0; i < wallList.size(); i++) {
                wallArr[i] = wallList.get(i);
            }

            // add the two arrays in the Object array
            levelObjectArr[0] = levelArr;
            levelObjectArr[1] = wallArr;

            allLevelsArray.add(levelObjectArr);
        }
        return allLevelsArray;
    }

    // ============== OTHERS ============== //

    /*
        helper method to convert a string to a 2D array
        @return: a game board in a 2D char array
     */
    public char[][] stringTo2DArray( String levelString) {
        int row = levelString.length() / 9; // for sandbox mode, size of the board will differ
        int col = 9;
        char[][] arr = new char[row][col];
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                arr[i][j] = levelString.charAt(count);
                count++;
            }
        }
        return arr;
    }

    /*
        helper method to print a 2D array
     */
    public void print2DArray ( char[][] arr) {
        for ( int i = 0; i < arr.length; i++) {
            for ( int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + "   ");
            }
            System.out.println();
        }
    }
    public void removeAllSandboxLevels() {
        // delete all user if any users exist
        if( sandboxCollection.count() != 0) {
            BasicDBObject document = new BasicDBObject();
            sandboxCollection.remove(document); // deleted
            System.out.println("All items deleted.\nNumber of elements in the database: " + sandboxCollection.count());
        }
        else {
            System.out.println("No users to delete.");
        }
    }
}