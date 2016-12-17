package notification;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class Notification {
	private static final String LOCALHOST = "localhost";
	private static final int PORT_NMBER = 9092;

	private static Configuration conf = null;
	private static SocketIOServer server = null;

	public static void start() throws Throwable {
		conf = new Configuration();
		conf.setHostname(LOCALHOST);
		conf.setPort(PORT_NMBER);
		server = new SocketIOServer(conf);
		server.start();
	}

	public static void stop() throws Throwable {
		server.stop();
	}

	public static void sendNotification(String receivingGroup, String message) {
		server.getBroadcastOperations().sendEvent(receivingGroup, message);
	}

}
