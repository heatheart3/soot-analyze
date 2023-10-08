package org.example;

import soot.*;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.Iterator;

public class TraceExceptionByCallGraph {

    public static void loadProcessClass(String classname)
    {
        SootClass c = Scene.v().loadClassAndSupport(classname);
        if (c != null)
            c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
        Scene.v().setMainClass(c);

        PackManager.v().runPacks();


        SootMethod src = Scene.v().getSootClass(classname).getMethodByName("put");
        CallGraph cg = Scene.v().getCallGraph();
        Iterator<Edge> targets = cg.edgesInto(src);
        while (targets.hasNext()) {
            System.out.println(src.getName() + " may be called by " + targets.next().src().getName());
        }
    }

    public static void main(String[] args) {
        SootInitializer.ConfigureOptions();
        SootInitializer.loadSootClasspPath();
        String classname = "org.apache.hadoop.hdfs.server.datanode.DataNode";

        loadProcessClass(classname);


    }
}