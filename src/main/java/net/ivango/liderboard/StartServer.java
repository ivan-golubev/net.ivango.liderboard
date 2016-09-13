package net.ivango.liderboard;


import com.google.inject.Guice;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.EnumSet;

public class StartServer {

    public static void main(String[] args) throws Exception {
        new StartServer().startServer();
    }

    /**
     * starts the server and blocks the current thread
     * */
    public void startServer() throws Exception {
        Server server = startServerAsync();
        server.join();
    }

    /**
     * starts the server in background
     * */
    public Server startServerAsync() throws Exception {
        Guice.createInjector(
                Stage.DEVELOPMENT,
                new LeaderboardModule()
        );

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));
        context.addServlet(DefaultServlet.class, "/*");

        server.start();
        return server;
    }

}
