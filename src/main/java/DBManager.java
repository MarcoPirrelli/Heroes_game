import java.sql.*;

/**
 * Class to create and manage a connection to the db.
 */
public class DBManager {
    Connection connection;
    Statement statement;
    PreparedStatement saveStatement;
    int saveSlot;
    final int MAX_SAVES = 3;

    /**
     * Creates a connection, a statement and a prepared statement.
     * Checks for database integrity and resets the table if necessary.
     */
    public DBManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\databases\\project.db");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        try {
            statement.executeQuery("select SaveId, HeroName, HeroAge, Service, Health, Fame, Money, Loyalty, Mana, Luck, Completed, EventId, Wand, Curse, Scale, Crow from saves");
        } catch (SQLException e) {
            try {
                statement.executeUpdate("drop table if exists saves");
                statement.executeUpdate("""
                        create table saves(
                        SaveId int primary key,
                        HeroName text not null,
                        HeroAge int check(HeroAge >= 0),
                        Service int check(Service >= 0),
                        Health int check(Health between 0 and 100),
                        Fame int check(Fame between 0 and 100),
                        Money int check(Money between 0 and 100),
                        Loyalty int check(Loyalty between 0 and 100),
                        Mana int check(Mana between 0 and 100),
                        Luck int check(Luck >= 0),
                        Completed int check(Completed >= 0),
                        EventId int check(EventId >= 0),
                        Wand int check(Wand in (0, 1)),
                        Curse int check(Curse in (0, 1)),
                        Scale int check(Scale in (0, 1)),
                        Crow int check(Crow in (0, 1))
                        )""");
            } catch (Exception e2) {
                e2.printStackTrace();
                System.exit(0);
            }
        }
        try {
            saveStatement = connection.prepareStatement("insert or replace into saves values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * Sets which save slot the EventManager saves the current game to.
     *
     * @param saveSlot int between 1 and maxSaves
     */
    public void setSaveSlot(int saveSlot) {
        this.saveSlot = saveSlot;
    }

    /**
     * Returns the current save slot.
     *
     * @return save slot number
     */
    public int getSaveSlot() {
        return saveSlot;
    }

    /**
     * Returns the first empty save slot number (>= 1).
     * If all save slots are full, returns 0.
     *
     * @return non-negative int
     */
    public int firstEmptySlot() {
        int first = 0;
        try {
            ResultSet r = statement.executeQuery("select SaveId from saves where SaveId <= " + MAX_SAVES + " order by 1");
            for (int i = 1; i <= MAX_SAVES; i++) {
                if (!r.next() || r.getInt("SaveId") > i) {
                    first = i;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return first;
    }

    /**
     * Load a game from a previous save.
     *
     * @param saveId int between 1 and maxSaves
     */
    public void load(int saveId) {
        try {
            ResultSet r = statement.executeQuery("select * from saves where SaveId = " + saveId);
            if (r.isAfterLast()) {
                setSaveSlot(saveId);
                EventManager.newGame();
                return;
            }
            saveSlot = r.getInt("SaveId");
            Hero.name = r.getString("HeroName");
            Hero.age = r.getInt("HeroAge");
            Hero.yearsOfService = r.getInt("Service");
            Hero.setHealth(r.getInt("Health"));
            Hero.setFame(r.getInt("Fame"));
            Hero.setMoney(r.getInt("Money"));
            Hero.setLoyalty(r.getInt("Loyalty"));
            Hero.setMana(r.getInt("Mana"));
            Hero.setLuck(r.getInt("Luck"));
            Hero.completedEvents = r.getInt("Completed");
            Hero.currentId = r.getInt("EventId");
            Hero.artefacts[Hero.WAND] = r.getBoolean("Wand");
            Hero.artefacts[Hero.CURSE] = r.getBoolean("Curse");
            Hero.artefacts[Hero.SCALE] = r.getBoolean("Scale");
            Hero.artefacts[Hero.CROW] = r.getBoolean("Crow");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Delete a game save to free up a slot.
     *
     * @param saveId int between 1 and MAX_SAVES
     */
    public void deleteSave(int saveId) {
        try {
            statement.executeUpdate("delete from saves where SaveId = " + saveId);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void save() throws RuntimeException {
        if (saveSlot <= 0 || saveSlot > MAX_SAVES) throw new RuntimeException("Error: Illegal save slot!");
        try {
            saveStatement.setInt(1, saveSlot);
            saveStatement.setString(2, Hero.name);
            saveStatement.setInt(3, Hero.age);
            saveStatement.setInt(4, Hero.yearsOfService);
            saveStatement.setInt(5, Hero.getHealth());
            saveStatement.setInt(6, Hero.getFame());
            saveStatement.setInt(7, Hero.getMoney());
            saveStatement.setInt(8, Hero.getLoyalty());
            saveStatement.setInt(9, Hero.getMana());
            saveStatement.setInt(10, Hero.getLuck());
            saveStatement.setInt(11, Hero.completedEvents);
            saveStatement.setInt(12, Hero.currentId);
            saveStatement.setInt(13, (Hero.artefacts[Hero.WAND] ? 1 : 0));
            saveStatement.setInt(14, (Hero.artefacts[Hero.CURSE] ? 1 : 0));
            saveStatement.setInt(15, (Hero.artefacts[Hero.SCALE] ? 1 : 0));
            saveStatement.setInt(16, (Hero.artefacts[Hero.CROW] ? 1 : 0));

            saveStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Returns a ResultSet containing the saves' information meant for display.
     *
     * @return ResultSet with 0 to MAX_SAVES rows
     */
    public ResultSet getAllSaves() {
        ResultSet ret = null;
        try {
            ret = statement.executeQuery("select SaveId, HeroName, Service, Completed from saves where SaveId <= " + MAX_SAVES);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return ret;
    }
}
