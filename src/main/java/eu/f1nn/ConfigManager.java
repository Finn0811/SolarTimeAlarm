package eu.f1nn;

import eu.f1nn.config.Config;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

/**
 * Created by Finn on 26.12.2020.
 */
public class ConfigManager {
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    private final String path = "./";
    private final String name = "config.yml";
    private final File file;
    private Config config;

    public ConfigManager() {
        this.file = new File(this.name);
    }

    private void createNewFile() {
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig(InputStream is) {
        if (file.exists())
            return;

        // create empty file if we got no InputStream with default config
        if (is == null) {
            this.createNewFile();
            return;
        }

        this.saveResource(is, this.name);
    }

    private void saveResource(InputStream in, String resourcePath) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in ");
        }

        File outFile = new File(path, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(path, resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists()) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                logger.error("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            logger.error("Could not save " + outFile.getName() + " to " + outFile);
            ex.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            Constructor constructor = new Constructor(Config.class);
            Yaml yaml = new Yaml(constructor);
            this.config = yaml.load(new FileInputStream(this.getFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public Config getConfig() {
        return this.config;
    }
}