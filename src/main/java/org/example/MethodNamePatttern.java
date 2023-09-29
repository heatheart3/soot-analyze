
package org.example;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonObject;
import com.sun.javafx.scene.SceneEventDispatcher;
import org.omg.CORBA.PUBLIC_MEMBER;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.dexpler.tags.DoubleOpTag;
import soot.jimple.ThrowStmt;
import soot.options.Options;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.ExceptionalBlockGraph;
import soot.util.Chain;

import javax.swing.text.html.Option;

public class MethodNamePatttern {

    public static String sourceDirectory = System.getProperty("user.dir") + File.separator + "classes";
    public static String sourceClassPath = Scene.v().defaultClassPath();
    public static List<String> sourceJarsPath = new ArrayList<>();
        public static void setup()
        {
            Options.v().set_whole_program(true);
            Options.v().set_keep_line_number(true);
            Options.v().set_output_format(Options.output_format_none);
            Options.v().set_process_dir(sourceJarsPath);
//            Options.v().set_prepend_classpath(true);
//            Options.v().set_process_jar_dir(sourceJarsPath);

        }

        public static void loadJars()
        {
            File file = new File(sourceDirectory+File.separator+"jarDir");
            File[] tempList = file.listFiles();
            for(int i = 0; i < tempList.length; i++)
            {
                sourceClassPath += ";"+tempList[i].toString();
            }

        }
        public static void loadSourceJars()
        {
            File file = new File(sourceDirectory+File.separator);
            File[] templist = file.listFiles();
            for (int i = 0; i < templist.length; i ++)
            {
                if(templist[i].toString().contains(".jar"))
                {
                    sourceJarsPath.add(templist[i].toString());
                }
            }

        }
        public static int allThrowStmt()
        {
            Scene.v().setSootClassPath(Scene.v().defaultClassPath()+";"+sourceClassPath);
            String className = "org.apache.hadoop.hdfs.server.namenode.NameNode";
            SootClass sc =Scene.v().loadClassAndSupport(className);
            Scene.v().loadNecessaryClasses();
            sc.setApplicationClass();
            Scene.v().setMainClass(sc);
            Chain<SootClass> classes = Scene.v().getApplicationClasses();
            int cnt = 0;

            for(SootClass sootClass:classes)
            {
                List<SootMethod> methods = sootClass.getMethods();
                for(SootMethod method: methods)
                {
                    Body body = method.retrieveActiveBody();
                    BlockGraph bg = new ExceptionalBlockGraph(body);
                    List<Block> blocks = bg.getBlocks();
                    for(Block block: blocks)
                    {
                        Iterator<Unit> unitIterator = block.iterator();
                        while (unitIterator.hasNext())
                        {
                            Unit unit = unitIterator.next();
                            if(unit instanceof ThrowStmt) {
                                cnt ++;
                            }

                        }
                    }
                }
            }
            return cnt;
        }

        public static void nameKeywordPatternAnalyze()
        {
            Scene.v().setSootClassPath(Scene.v().defaultClassPath()+";"+sourceClassPath);
            String className = "org.apache.hadoop.hdfs.server.namenode.NameNode";
            SootClass sc =Scene.v().loadClassAndSupport(className);
            Scene.v().loadNecessaryClasses();
            sc.setApplicationClass();
            Scene.v().setMainClass(sc);
            Chain<SootClass> classes = Scene.v().getApplicationClasses();

            for (SootClass sootClass: classes)
            {
                List<SootMethod> methods = sootClass.getMethods();
                for(SootMethod method:methods) {
                    String methodName = method.getName();
                    if (methodName.contains("check") || methodName.contains("detect"))
                    {
                        System.out.println("Class:"+sootClass.getShortName() + " Method:" + method.getName() + "    Source Code Line:" + method.getJavaSourceStartLineNumber());

                    }
                }
            }
        }
        public static void if_throwPatternAnalyze()
        {
            Scene.v().setSootClassPath(Scene.v().defaultClassPath()+";"+sourceClassPath);
            String className = "org.apache.hadoop.hdfs.server.namenode.NameNode";
//            SootClass sc =Scene.v().loadClassAndSupport(className);
            Scene.v().loadNecessaryClasses();
//            sc.setApplicationClass();
//            Scene.v().setMainClass(sc);
            Chain<SootClass> classes = Scene.v().getApplicationClasses();
            System.out.println(classes.size());
            for(SootClass sootClass:classes)
            {
                List<SootMethod> methods = sootClass.getMethods();
                for(SootMethod method: methods)
                {
                    Body body = method.retrieveActiveBody();
                    BlockGraph bg = new ExceptionalBlockGraph(body);
                    List<Block> blocks = bg.getBlocks();
                    for(Block block: blocks)
                    {
                        Iterator<Unit> unitIterator = block.iterator();
                        while (unitIterator.hasNext())
                        {
                            Unit unit = unitIterator.next();
                            if(unit instanceof ThrowStmt){
                                if(!block.getPreds().isEmpty())
                                {
                                    System.out.println("Class:"+sootClass.getShortName() + " Method:" + method.getName() + "    Source Code Line:" + unit.getJavaSourceStartLineNumber());
                                }

                            }
                        }
                    }
                }
            }
        }

        public static void main(String[] args) {
            loadJars();
            loadSourceJars();
            setup();
            if_throwPatternAnalyze();
//            nameKeywordPatternAnalyze();


        }

}


