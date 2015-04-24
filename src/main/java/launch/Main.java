package launch;

import java.io.File;
import java.util.concurrent.Executor;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

public class Main {

    public static void main(String[] args) throws Exception {

        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "9000";
        }

        System.out.println("Will run on port " + webPort);
        tomcat.setPort(Integer.valueOf(webPort));

        // Reduced the number of threads from the default of 200 to 20 to highlight the impact of
        // blocking threads on responsiveness
        tomcat.getConnector().setAttribute("maxThreads", "20");
        tomcat.getConnector().setAttribute("maxConnections", "250");

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        File additionWebInfClasses = new File("target/classes");
        VirtualDirContext resources = new VirtualDirContext();
        resources.setExtraResourcePaths("/WEB-INF/classes=" + additionWebInfClasses);
        ctx.setResources(resources);


        tomcat.start();
        tomcat.getServer().await();
    }
}