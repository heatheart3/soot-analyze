package org.example;
import java.io.FilterOutputStream;
import java.util.Collections;
import java.util.Iterator;
import soot.*;
import soot.jimple.*;
import soot.options.Options;
import javax.xml.transform.Source;
import java.util.Map;

import soot.Scene;
import soot.SootClass;
import soot.Transform;
import soot.util.Chain;


public class ProjectNamePattern {

    public static void main(String[] args) {
        // 设置Soot选项
        Options.v().set_whole_program(true);
        Options.v().set_output_format(Options.output_format_none);

        // 加载指定类
        String classPath = "E:\\Projects\\Java\\hadoop-hdfs-project\\hadoop-hdfs\\target\\classes";
        Scene.v().setSootClassPath(Scene.v().defaultClassPath()+";"+classPath);
        Scene.v().loadNecessaryClasses();


        Chain<SootClass> classes = Scene.v().getApplicationClasses();
        System.out.println(classes.size());
        for (SootClass aClass : classes) {
            if (aClass.getName().startsWith("org.apache.hadoop")) {
                for (SootMethod method : aClass.getMethods()) {
                    if (method.getName().contains("check") || method.getName().contains("detector")) {
                        System.out.println("Class: " + aClass.getName() + ", Method: " + method.getName());
                    }
                }
            }
        }
    }
}


