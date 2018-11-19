package database;
/*
import java.util.*;
import com.mongodb.*;
*/
/*
public class DatabaseManager {

    // properties
    private DBCollection highscoreCollection;
    private DBCollection levelCollection;
    private DB dbObj;

    // constructor
    public DatabaseManager() {
        connectToDatabase();
    }

    public void connectToDatabase() {
        try {
            // connect to MongoDb
            MongoClient mongoClntObj = new MongoClient("localhost", 27017);

            // get MongoDb database. If the database doesn't exists, MongoDb will automatically create it
            dbObj = mongoClntObj.getDB("wallsAndWarriors");

            // get MongoDb collections. If the collection doesn't exists, MongoDb will automatically create it
            levelCollection = dbObj.getCollection("levels");
            highscoreCollection = dbObj.getCollection("highscores");
            System.out.println(dbObj.getCollectionNames()); // displays the existing collections in the database

            System.out.println("Connection to database Successful"); // success message
        } catch ( Exception e) {
            System.out.println(e); // failure message
        }
    }


    // methods
    /*
        Method to add a highscore to the database
        param: name and score of the user
        return: boolean values
     */
/*
    public void addHighScore( String name, int score) {
        if(dbObj.collectionExists("highscores")) {
            BasicDBObject newHighScore = new BasicDBObject();

            // Create new instance of newHighScore
            newHighScore.append("name", name)
                    .append("score", score);
            // add highscore to the collection
            highscoreCollection.insert(newHighScore);
//            System.out.println("Record added successfully.");
        }
    else {
            System.out.println("DATABASE ERROR: Could not add high score");
        }
    }

    // --------------------------------------------------------------- //

    /*
        Method to get a list of the high scores
        return: prints the list
     */
/*
    public void getHighScores() {
        ArrayList<String> arr = new ArrayList<String>();
        // finds all database records, sorts them in descending order based on their scores
        DBCursor cursor = highscoreCollection.find().sort( new BasicDBObject("score", -1));
        String name = "";
        int score = 0;
        String str = "";

        // add all the records to an arraylist
        while( cursor.hasNext()) {
            cursor.next();
            // stores name of player and their scores in the variable
            name = (String) cursor.next().get("name");
            score = (int)cursor.next().get("score");
            str = name + " — " + score;

            // adds the highscore info in the ArrayList
            arr.add( str);
        }
        // Displays the high score. For now we're printing all, but for the final game we're just going to print
        // the top 5 or 10
        for ( String x : arr) {
            System.out.println(x);
        }
    }

    // --------------------------------------------------------------- //

    /*
        Method denoting the "RESET HIGHSCORES" functionality of the game
        Deletes all elements from the collection of high scores
     */
/*
    public boolean clearHighscore() {
        // delete all scores if any highscores exist
        if(dbObj.collectionExists("highscores") && highscoreCollection.count() != 0) {
            BasicDBObject document = new BasicDBObject();
            highscoreCollection.remove(document); // deleted
            System.out.println("All items deleted.\nNumber of elements in the database: " + highscoreCollection.count());
            return true;
        }
        else {
            System.out.println("No highscores to delete.");
            return false;
        }
    }

    // --------------------------------------------------------------- //

    public void addLevel( String levelCode, String levelString) {
//        if(dbObj.collectionExists("levelCollection")) {
            BasicDBObject newLevel = new BasicDBObject();

            // Create new instance of newLevel
            newLevel.append("levelCode", levelCode)
                    .append("levelString", levelString);
            // add highscore to the collection
            levelCollection.insert(newLevel);
//        }
    }

    public String getLevel( String levelCode) {
        // finds all database records, sorts them in descending order based on their scores
        DBObject query = new BasicDBObject("levelCode", "lvl1");
        DBObject doc = levelCollection.findOne(query);

        String levelString = (String) doc.get("levelString");

        System.out.println("PRINTING level NOW");

        // displays level
        printLevel( levelString);
        return " ";
    }

    // levelString :: -BR--R--B-BTTR------
    public void printLevel( String levelString) {
        int row = 4;
        int col = 5;
        char[][] arr = new char[row][col]; // our board is of dimension 4x5
        int count = 0;
        // prints 2D array
        for ( int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                arr[i][j] = levelString.charAt(count);
                count++;
                System.out.print( arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
*/

