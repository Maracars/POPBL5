package notification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class PGSocketIONotify implements Runnable {
	private PGConnection pgConn;
	private static  SocketIOServer server ;
	
	public PGSocketIONotify(Connection conn) throws SQLException, InterruptedException {
		
		Configuration conf = new Configuration();
		conf.setHostname("localhost");
		conf.setPort(9092);
		//9092 portuan egongo da socket.io-ko komunikazinua
		PGSocketIONotify.server =  new SocketIOServer(conf);
		this.pgConn = (PGConnection) conn;
		Statement listenStatement = conn.createStatement();
		//Ze mezu entzun bihar daben esan, nahi beste mezu entzun leike.
		listenStatement.execute("LISTEN mezua");
		listenStatement.close();
	}

	@Override
	public void run() {

		while (true) {
			try {

				PGNotification notifications[] = pgConn.getNotifications();
				

				if (notifications != null) {
					for (PGNotification pgNotification : notifications) {
						System.out.println("Got notification: " + pgNotification.getName() + " with payload: "
								+ pgNotification.getParameter());
						//PGk JSON bat bidaltzen dau, hori gero javascripten tratauko da
						String [] tableInfo = pgNotification.getParameter().split(">");
						
						
						server.getBroadcastOperations().sendEvent("chatevent", tableInfo[1]);

					}
				}

				// wait a while before checking again
				Thread.sleep(500);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws Exception {
		

		Class.forName("org.postgresql.Driver");

		// Konexino url-a sortu
		String url = "jdbc:postgresql://localhost:5432/naranair";
		// pasahitza eta erabiltzailiakin konexinua sortu
		Connection lConn = DriverManager.getConnection(url, "naranairapp", "Nestor123");
		
		// Thread-a sortu
		PGSocketIONotify cl = new PGSocketIONotify(lConn);
		// Threada korridu
        (new Thread(cl)).start();

		server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();

	}
}

