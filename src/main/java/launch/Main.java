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

//        Executor executor = tomcat.getConnector().getProtocolHandler().getExecutor();
//        Connector connector = new Connector();
//        connector.getProtocolHandler().
//        tomcat.setConnector(connector);

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "9090";
        }
System.out.println("Will run on port " + webPort);
        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.getConnector().setAttribute("maxThreads", "20");
        tomcat.getConnector().setAttribute("maxConnections", "250");

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

//declare an alternate location for your "WEB-INF/classes" dir:
        File additionWebInfClasses = new File("target/classes");
        VirtualDirContext resources = new VirtualDirContext();
        resources.setExtraResourcePaths("/WEB-INF/classes=" + additionWebInfClasses);
        ctx.setResources(resources);


        tomcat.start();
        tomcat.getServer().await();
    }
}