package notification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

// TODO: Auto-generated Javadoc
/**
 * The Class PGSocketIONotify.
 */
public class PGSocketIONotify implements Runnable {
	
	/** The Constant LOCALHOST. */
	private static final String LOCALHOST = "localhost";
	
	/** The Constant SPLITTER. */
	private static final String SPLITTER = ">";
	
	/** The Constant PG_DRIVER. */
	private static final String PG_DRIVER = "org.postgresql.Driver";
	
	/** The Constant DB_PASSWORD. */
	private static final String DB_PASSWORD = "Nestor123";
	
	/** The Constant DB_USERNAME. */
	private static final String DB_USERNAME = "naranairapp";
	
	/** The Constant URL. */
	private static final String URL = "jdbc:postgresql://localhost:5432/naranair";

	/** The Constant LOOP_TIME. */
	private static final int LOOP_TIME = 500;
	
	/** The Constant PORT_NMBER. */
	private static final int PORT_NMBER = 9092;
	
	/** The pg conn. */
	private PGConnection pgConn;
	
	/** The conf. */
	private static Configuration conf;
	
	/** The server. */
	private static SocketIOServer server;

	/**
	 * Start.
	 */
	public static void start() {
		conf = new Configuration();
		conf.setHostname(LOCALHOST);
		conf.setPort(PORT_NMBER);
		server = new SocketIOServer(conf);
		server.start();

	}

	/**
	 * Stop.
	 */
	public static void stop() {
		server.stop();
	}

	/**
	 * Instantiates a new PG socket IO notify.
	 *
	 * @param conn the conn
	 * @throws SQLException the SQL exception
	 * @throws InterruptedException the interrupted exception
	 */
	public PGSocketIONotify(Connection conn) throws SQLException, InterruptedException {

		// 9092 portuan egongo da socket.io-ko komunikazinua
		this.pgConn = (PGConnection) conn;
		Statement listenStatement = conn.createStatement();
		// Ze mezu entzun bihar daben esan, nahi beste mezu entzun leike.
		listenStatement.execute("LISTEN mezua");
		listenStatement.close();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		while (true) {
			try {

				PGNotification notifications[] = pgConn.getNotifications();

				if (notifications != null) {
					for (PGNotification pgNotification : notifications) {

						// PGk JSON bat bidaltzen dau, hori gero javascripten
						// tratauko da
						String[] tableInfo = pgNotification.getParameter().split(SPLITTER);
						server.getBroadcastOperations().sendEvent("chatevent", tableInfo[1]);

					}
				}

				// wait a while before checking again
				Thread.sleep(LOOP_TIME);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String args[]) throws Exception {

		Class.forName(PG_DRIVER);

		// Konexino url-a sortu
		// pasahitza eta erabiltzailiakin konexinua sortu
		Connection lConn = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);

		// Thread-a sortu
		PGSocketIONotify cl = new PGSocketIONotify(lConn);
		// Threada korridu
		(new Thread(cl)).start();

		server.start();

		Thread.sleep(Integer.MAX_VALUE);

		server.stop();

	}

}
