package org.example;
import soot.Scene;
import soot.options.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class SootInitializer {

    public static String sourceDirectory = System.getProperty("user.dir") + File.separator + "classes";
    public static String sourceClassPath = Scene.v().defaultClassPath();
    public static List<String> sourceJarsList = new ArrayList<>();
    //configure soot's options
    public static void ConfigureOptions()
    {
        Options.v().set_whole_program(true);
        Options.v().set_keep_line_number(true);
        Options.v().set_output_format(Options.output_format_none);
    }

    //Load dependencies path to soot's ClassPath
    public static void loadSootClasspPath()
    {
        File file = new File(sourceDirectory+File.separator+"DependencyJars");
        File[] tempList = file.listFiles();
        for (File value : tempList) {
            sourceClassPath += ";" + value.toString();
        }
        Scene.v().setSootClassPath(Scene.v().defaultClassPath()+";"+sourceClassPath);
    }

    // Load all jars in targetJars directory to analyze
    public static void loadTargetJars()
    {
        File file = new File(sourceDirectory+File.separator+"TargetJars");
        File[] templist = file.listFiles();
        for (File value : templist) {
            if (value.toString().contains(".jar")) {
                sourceJarsList.add(value.toString());
            }
        }
        Options.v().set_process_dir(sourceJarsList);
    }

    // init soot by loading SootClassPath and target jars
    public static void init()
    {
        ConfigureOptions();
        loadSootClasspPath();
        loadTargetJars();
    }

}
