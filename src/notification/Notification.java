package notification;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class Notification {
	private static final String LOCALHOST = "localhost";
	private static final int PORT_NMBER = 9092;

	private static final Configuration conf;
	private static final SocketIOServer server;

	static {
		conf = new Configuration();
		conf.setHostname(LOCALHOST);
		conf.setPort(PORT_NMBER);
		server = new SocketIOServer(conf);
	}

	public static void sendNotification(String receivingGroup, String message) {
		server.start();

		server.getBroadcastOperations().sendEvent(receivingGroup, message);

		server.stop();

	}

}
