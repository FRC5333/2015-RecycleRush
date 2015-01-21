package frc.team5333.driver.net;

import frc.team5333.NetIDs;
import frc.team5333.driver.gui.GuiDriverPanel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Responsible for handling network connections between the Robot and the
 * Driver Station
 *
 * @author Jaci
 */
public class NetworkController {

    public Socket socket;
    public static String host;
    public int port;
    DataOutputStream writer;

    public NetworkController(int port) {
        this.port = port;
    }

    public static void setData(String hostname) {
        host = hostname;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            GuiDriverPanel.instance.refresh();
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            tryClose();
            GuiDriverPanel.instance.refresh();
        }
    }

    void tryClose() {
        try {
            if (!socket.isClosed())
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(NetIDs id, float value) {
        if (socket != null && socket.isConnected()) {
            try {
                writer.writeByte(id.id());
                writer.writeFloat(value);
            } catch (Exception e) {
                tryClose();
                GuiDriverPanel.instance.refresh();
            }
        }
    }

    public void sendMessage(NetIDs id, String value) {
        if (socket != null && socket.isConnected()) {
            try {
                writer.writeByte(id.id());
                writer.writeBytes(value);
            } catch (Exception e) {
                tryClose();
                GuiDriverPanel.instance.refresh();
            }
        }
    }

}
