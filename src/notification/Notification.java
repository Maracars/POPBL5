package notification;

import java.sql.Connection;
import java.sql.DriverManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Notification.
 * Class that encapsulates initializes SocketIO
 */
public class Notification {

	/** The Constant PG_DRIVER. */
	private static final String PG_DRIVER = "org.postgresql.Driver";
	
	/** The Constant DB_PASSWORD. */
	private static final String DB_PASSWORD = "Nestor123";
	
	/** The Constant DB_USERNAME. */
	private static final String DB_USERNAME = "naranairapp";
	
	/** The Constant URL. */
	private static final String URL = "jdbc:postgresql://localhost:5432/naranair";

	/**
	 * Start.
	 *
	 * @throws Throwable the throwable
	 */
	public static void start() throws Throwable {
		Connection lConn;
		String listenTo[] = { "mezua" };
		try {
			Class.forName(PG_DRIVER);
			lConn = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
			PGSocketIONotify cl = new PGSocketIONotify(lConn, listenTo);
			(new Thread(cl)).start();

			PGSocketIONotify.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Stop.
	 *
	 * @throws Throwable the throwable
	 */
	public static void stop() throws Throwable {
		PGSocketIONotify.stop();
	}

	/**
	 * Send notification.
	 *
	 * @param receivingGroup the receiving group
	 * @param message the message
	 */
	public static void sendNotification(String receivingGroup, String message) {
		PGSocketIONotify.sendNotification(receivingGroup, message);
	}

}
