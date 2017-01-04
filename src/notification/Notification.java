package notification;

import java.sql.Connection;
import java.sql.DriverManager;

import com.corundumstudio.socketio.SocketIOServer;

public class Notification {
	
	private static final String PG_DRIVER = "org.postgresql.Driver";
	private static final String DB_PASSWORD = "Nestor123";
	private static final String DB_USERNAME = "naranairapp";
	private static final String URL = "jdbc:postgresql://localhost:5432/naranair";

	private static SocketIOServer server = null;

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

	public static void stop() throws Throwable {
		server.stop();
	}

	public static void sendNotification(String receivingGroup, String message) {
		server.getBroadcastOperations().sendEvent(receivingGroup, message);
	}

}
