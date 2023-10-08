
package org.example;
import java.util.Iterator;
import java.util.List;

import java.io.PrintStream;
import java.io.FileNotFoundException;
import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.ThrowStmt;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.ExceptionalBlockGraph;
import soot.util.Chain;



public class SearchSpecificPattern {
        public static void nameKeywordPatternAnalyze(boolean resultOutputMark) throws FileNotFoundException
        {
            int eligible_methods_cnt = 0, total_methods = 0;
            PrintStream ps = new PrintStream("nameKeywordPatternResult.txt");
            if(resultOutputMark) {
                System.setOut(ps);
            }

            Scene.v().loadNecessaryClasses();
            Chain<SootClass> classes = Scene.v().getApplicationClasses();

            for (SootClass sootClass: classes)
            {
                List<SootMethod> methods = sootClass.getMethods();
                for(SootMethod method:methods) {
                    String methodName = method.getName();
                    total_methods++;
                    //find the methods with the name containing keywords we are interested in
                    if (methodName.contains("check") || methodName.contains("detect"))
                    {
                        eligible_methods_cnt++;
                        System.out.println("Class:"+sootClass.getShortName() + " Method:" + method.getName() + "   Source Code Line:" + method.getJavaSourceStartLineNumber());

                    }
                }
            }
            System.out.println("total_Classes:" + classes.size());
            System.out.println("total methods: " + total_methods + "   " + "eligible methods:" + eligible_methods_cnt);
            if(resultOutputMark)
                ps.close();
        }
        public static void if_throwPatternAnalyze(boolean resultOutputMark) throws FileNotFoundException
        {
            int eligible_methods_cnt = 0, total_methods = 0;
            PrintStream ps = new PrintStream("if_throwPatternResult.txt");
            if(resultOutputMark) {
                System.setOut(ps);
            }
            Scene.v().loadNecessaryClasses();
            Chain<SootClass> classes = Scene.v().getApplicationClasses();
            for(SootClass sootClass:classes)
            {
                List<SootMethod> methods = sootClass.getMethods();
                for(SootMethod method: methods)
                {
                    Body body;
                    total_methods += 1;
                    // if the method retrieved doesn't have an activeBody, the function will throw a RuntimeException.
                    try{
                        body = method.retrieveActiveBody();
                    }
                    catch (RuntimeException e1)
                    {
                        //Soot can't analyze methods without activeBody, so we will skip them.
                        continue;
                    }
                    BlockGraph bg = new ExceptionalBlockGraph(body);
                    List<Block> blocks = bg.getBlocks();
                    for(Block block: blocks)
                    {
                        Iterator<Unit> unitIterator = block.iterator();
                        while (unitIterator.hasNext())
                        {
                            Unit unit = unitIterator.next();
                            if(unit instanceof ThrowStmt){
                                //If there are predecessors, it means CFG has branched before this block.
                                if(!block.getPreds().isEmpty())
                                {
                                    System.out.println("Class:"+sootClass.getShortName() + " Method:" + method.getName() + "    Source Code Line:" + unit.getJavaSourceStartLineNumber());
                                    eligible_methods_cnt += 1;
                                }

                            }
                        }
                    }
                }
            }
            System.out.println("total_Classes:" + classes.size());
            System.out.println("total methods: " + total_methods + "   " + "eligible methods:" + eligible_methods_cnt);
            if(resultOutputMark)
                ps.close();
        }

        public static void main(String[] args) throws FileNotFoundException {
            SootInitializer.init();
            if_throwPatternAnalyze(true);
            nameKeywordPatternAnalyze(true);

        }

}


