package uk.co.monotonic.advanced_memory_leaks.classloaders;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ApplicationRunner
{
    public static void main(final String[] args) throws Exception
    {
        final String pluginName =
            "uk.co.monotonic.advanced_memory_leaks.classloaders.ExamplePlugin";

        while (true)
        {
            System.out.println("Press any key to reload the plugins");
            System.in.read();

            final URL[] urls = { new File("target/classes").toURI().toURL()};
            final URLClassLoader classLoader = new URLClassLoader(urls, null);
            final Class<?> aClass = classLoader.loadClass(pluginName);
            final Object plugin = aClass.newInstance();
            aClass.getMethod("initialize").invoke(plugin);
        }
    }
}
