package org.example;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import polyglot.ast.If;
import soot.*;
import soot.jimple.JimpleBody;
import soot.jimple.Stmt;
import soot.jimple.ThrowStmt;
import soot.toolkits.graph.*;
import soot.util.Chain;
import soot.options.Options;
import soot.util.HashChain;
import soot.UnitBox;
import soot.util.StringNumberer;


import javax.swing.*;
import java.lang.CharSequence;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String classPath = "E:\\Projects\\Java\\hello_world\\target\\classes";
        Options.v().set_keep_line_number(true);
        String className = "org.example.Main";
        Scene.v().setSootClassPath(Scene.v().defaultClassPath()+";"+classPath);

        SootClass sc = Scene.v().loadClassAndSupport(className);
//        sc.setApplicationClass();
        Scene.v().loadNecessaryClasses();



        List<SootMethod> methods = sc.getMethods();
        SootMethod method1 = sc.getMethodByName("test");
        JimpleBody jimpleBodyMain = (JimpleBody) method1.retrieveActiveBody();

        for(SootMethod method : methods)
        {
            BlockGraph bg = new ExceptionalBlockGraph(method.retrieveActiveBody());
            List<Block> blocks = bg.getBlocks();
//            System.out.println("Method:" + method.getName()+" block nums:" + blocks.size() );
            for (Block block: blocks)
            {
                Iterator<Unit> iterator = block.iterator();
                while(iterator.hasNext())
                {
                    Unit unit = iterator.next();
                    if(unit instanceof ThrowStmt)
                    {
                        List<Block> predBlocks = block.getPreds();
                        if (!predBlocks.isEmpty())
                        {
                            System.out.println("Method: "+method.getName()+"  qualified statement at:"+ unit.getJavaSourceStartLineNumber());
                        }
                    }
                }
            }
        }





        // 结束Soot分析
        PackManager.v().runPacks();
        PackManager.v().writeOutput();
    }
}

