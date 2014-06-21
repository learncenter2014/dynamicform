package webapps;

import java.io.FileInputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import util.ServerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.MongoDBConnectionFactory;

public class WebInitListener implements ServletContextListener {
    protected static Logger LOG = LoggerFactory.getLogger(WebInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("init dynamic form war");

        LOG.info("init server.properties file");
        ServerContext.init(WebInitListener.class.getResourceAsStream("/server.properties"));
        sce.getServletContext().setAttribute("rootPath", sce.getServletContext().getContextPath());

        LOG.info("init /etc/db.properties file");
        try {
            ServerContext.init(new FileInputStream("/etc/db.properties"));
        } catch (Exception e) {
            LOG.error("Reading file has some exception {}", e.getMessage());
        }
        LOG.info("init MongoDB");
        MongoDBConnectionFactory.initDb();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("destroy dynamic form war");
        LOG.info("disconnect conection of MongoDB");
        MongoDBConnectionFactory.destroy();
    }

}
