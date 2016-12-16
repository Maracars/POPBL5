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
		server.start();

	}

	public static void sendNotification(String receivingGroup, String message) {
		
		server.getBroadcastOperations().sendEvent("53e79c4e736a52d01e21c6d663473fc8", message);

	}

}
