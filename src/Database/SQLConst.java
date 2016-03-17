package Database;

/**
 * Created by Admin on 1/01/2016.
 */
public class SQLConst {
    public static final String SQL_GET_QUESTIONS =
            "SELECT * FROM (SELECT * FROM Question WHERE level = 1 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 1 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 1 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 1 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 1 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 1 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 6 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 7 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 8 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 9 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 10 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 11 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 12 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 13 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 14 ORDER BY RANDOM() LIMIT 1) " +
                    "UNION " +
                    "SELECT * FROM (SELECT * FROM Question WHERE level = 15 ORDER BY RANDOM() LIMIT 1)";
}
