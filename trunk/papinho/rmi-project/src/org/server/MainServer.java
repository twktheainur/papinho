/*
 *   This file is part of Papinho.
 *
 *   Papinho is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Papinho is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Papinho (see COPYING).  If not, see <http://www.gnu.org/licenses/>.
 */

package org.server;

import org.common.interfaces.PapinhoServerIface;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Main class for the server application.
 * This class has the Main implementation and is responsible for
 * starting the rmiregistry and publish the server stub with the name
 * "server" .
 * 
 * @see org.server.PapinhoServer
 */
public class MainServer {

    private String host;
    private int port;
    private Registry registry;

    /**
     * Class constructor
     * @param server String containing the host name or the IP address of the
     *               server running the <code>rmiregistry</code>&nbsp; e&nbsp;g&nbsp; 127.0.0.1.
     * @param port   Port number to which the <code>rmiregistry</code> is bound&nbsp; e&nbsp;g&nbsp; 8090.
     */
    public MainServer(String server, int port) {
        this.host = server;
        this.port = port;
    }

    /**
     * Exports the remote object on the rmiregistry for the server application and adds a shutdown hook
     * to cleanup when the JVM shuts down.
     */
    public void start() {
        try {
            PapinhoServerIface psi = new PapinhoServer();
            Remote stub = UnicastRemoteObject.exportObject(psi, 0);
            LocateRegistry.createRegistry(this.port);
            registry = LocateRegistry.getRegistry(this.host, this.port);
            registry.bind("server", stub);
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            Runtime.getRuntime().addShutdownHook(new ShutdownHook("server",psi,registry));
            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            return;
        }
    }
    /**
     * Main method of the server application
     * @param args [port, default:8090][host, default:127.0.0.1]
     */
    public static void main(String... args) {
        String default_host="127.0.0.1";
        int default_port=8090;
        if(args.length==0){
            System.out.println("Using default parameters..");
        }else if(args.length==1){
            default_port=Integer.parseInt(args[0]);
        }else if(args.length==2){
            default_host=args[0];
            default_port=Integer.parseInt(args[1]);
        }

        System.out.println("Starting the server in host:"+default_host+" port:"+default_port);
        
        MainServer ms = new MainServer(default_host, default_port); 
        ms.start();
    }

    /**
     * Inner class used as a shutdown hooks
     */
    private static class ShutdownHook extends Thread {

        private String name;
        private PapinhoServerIface serverIface;
        private Registry registry;

        /**
         * Constructor if the hook
         * @param name Name with which the server remote object is bound to the rmi registry.
         * @param serverIface Reference to he instance of the server remote interface.
         * @param reg Connection reference to the rmi registry.
         */
        public ShutdownHook(String name, PapinhoServerIface serverIface, Registry reg) {
            this.name = name;
            this.serverIface = serverIface;
            this.registry = reg;
        }

        /**
         * Unexports and Unbinds the server remote object from the rmi registry.
         */
        @Override
        public void run() {
            try {
                System.out.println("Server JVM shutdown...");
                System.out.println("Unregistering server remote object");
                UnicastRemoteObject.unexportObject(serverIface, true);
                registry.unbind(name);
            } catch (Exception e) {
            }

        }
    }

    /**
     * Getter for attribute host
     * @return Returns a string containing the host name or IP address of the rmi registry.
     */
    public String getHost() {
        return host;
    }

    /**
     * Getter for attribute port
     * @return Returns an integer containing the port number of the RMI registry.
     */
    public int getPort() {
        return port;
    }
}
