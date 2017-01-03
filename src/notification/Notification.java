package notification;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

// TODO: Auto-generated Javadoc
/**
 * The Class Notification.
 */
public class Notification {
	
	/** The Constant LOCALHOST. */
	private static final String LOCALHOST = "localhost";
	
	/** The Constant PORT_NMBER. */
	private static final int PORT_NMBER = 9092;

	/** The conf. */
	private static Configuration conf = null;
	
	/** The server. */
	private static SocketIOServer server = null;

	/**
	 * Start.
	 *
	 * @throws Throwable the throwable
	 */
	public static void start() throws Throwable {
		conf = new Configuration();
		conf.setHostname(LOCALHOST);
		conf.setPort(PORT_NMBER);
		server = new SocketIOServer(conf);
		server.start();
	}

	/**
	 * Stop.
	 *
	 * @throws Throwable the throwable
	 */
	public static void stop() throws Throwable {
		server.stop();
	}

	/**
	 * Send notification.
	 *
	 * @param receivingGroup the receiving group
	 * @param message the message
	 */
	public static void sendNotification(String receivingGroup, String message) {
		server.getBroadcastOperations().sendEvent(receivingGroup, message);
	}

}
