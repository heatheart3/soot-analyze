package org.example;

import soot.*;
import soot.jimple.toolkits.callgraph.Sources;
import soot.toolkits.graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.infoflow.CallChain;

import java.net.SocketTimeoutException;
import java.util.*;

public class TraceExceptionByCallGraph {
    static String resultPath = "."+File.separator+"result"+File.separator;
    static String traceResultPath = resultPath+"trace_result"+File.separator;
    public static void loadProcessClass(String classname, String fileName, boolean resultOutputMark) throws FileNotFoundException {
        //输出流重定向
        PrintStream ps = new PrintStream(new FileOutputStream(new File(traceResultPath+fileName)));
        if(resultOutputMark) {
            System.setOut(ps);
        }

        SootClass c = Scene.v().loadClassAndSupport(classname);
        if (c != null)
            c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
//        Scene.v().setMainClass(c);
        //必须调用runPacks才能构成callGraph
        PackManager.v().runPacks();

//        List<SootMethod> sootMethods = Scene.v().getSootClass(classname).getMethods();
//        for(SootMethod sootMethod: sootMethods)
//        {
//            System.out.println(sootMethod.getName());
//        }
        SootMethod targetMethod = Scene.v().getSootClass(classname).getMethodByName("verifyChunks");
        CallGraph callGraph = Scene.v().getCallGraph();
        // 获取调用链
        List<SootMethod> callChain = getCallChain(callGraph, targetMethod);


        // 输出调用链
        System.out.println("Call Chain for " + targetMethod + ":");
        for (SootMethod method : callChain) {
            if(method != null)
                System.out.println(method.getSignature());
            else
                System.out.println("null");
        }
        if(resultOutputMark)
            ps.close();
    }
    private static List<SootMethod> getCallChain(CallGraph callGraph, SootMethod targetMethod) {
        List<SootMethod> callChain = new ArrayList<>();
        Set<SootMethod> visited = new HashSet<>();

        // 从目标方法开始进行深度优先搜索
        dfs(callGraph, targetMethod, callChain, visited);

        return callChain;
    }
    private static void dfs(CallGraph callGraph, SootMethod currentMethod,
                            List<SootMethod> callChain, Set<SootMethod> visited) {
        // 防止循环调用
        if (visited.contains(currentMethod)) {
            SootMethod markEndMethod = null;
            callChain.add(markEndMethod);
            return;
        }

        // 将当前方法添加到调用链
        callChain.add(currentMethod);
        visited.add(currentMethod);

        // 获取当前方法的所有调用者
        Iterator<MethodOrMethodContext> callers = new Sources(callGraph.edgesInto(currentMethod));

        // 递归遍历调用链
        while (callers.hasNext()) {
            SootMethod caller = callers.next().method();
            dfs(callGraph, caller, callChain, visited);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        SootInitializer.ConfigureOptions();
        SootInitializer.loadSootClasspPath();
        String classname = "org.apache.hadoop.hdfs.server.datanode.BlockReceiver";
        loadProcessClass(classname,"functionCallTrace.txt", true);
    }
}