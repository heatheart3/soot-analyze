
package org.example;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import java.io.PrintStream;
import java.io.FileNotFoundException;

import javafx.scene.transform.Shear;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import soot.*;
import soot.jimple.ThrowStmt;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.ExceptionalBlockGraph;
import soot.util.Chain;
import sun.nio.cs.US_ASCII;


public class SearchSpecificPattern {
        static String resultPath = "."+File.separator+"result"+File.separator;
        static String analysisResultPath = resultPath + "analysis_result"+File.separator;
        public static void nameKeywordPatternAnalyze(boolean resultOutputMark, String fileName) throws FileNotFoundException
        {
            int eligible_methods_cnt = 0, total_methods = 0;
            PrintStream ps = new PrintStream(new FileOutputStream(new File(analysisResultPath+fileName)));
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
        public static void if_throwPatternAnalyze(boolean resultOutputMark, String fileName) throws FileNotFoundException
        {
            int eligible_methods_cnt = 0, total_methods = 0;
            PrintStream ps = new PrintStream(new FileOutputStream(new File(analysisResultPath+fileName)));
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
                        //avoid catching try-catch block
                        if(!block.getHead().getUseBoxes().isEmpty()) {
                            String headUseValueString = block.getHead().getUseBoxes().get(0).getValue().toString();
                            if (headUseValueString.equals("@caughtexception")) {
                                continue;
                            }
                        }

                        Iterator<Unit> unitIterator = block.iterator();
                        while (unitIterator.hasNext())
                        {
                            Unit unit = unitIterator.next();

                            if(unit instanceof ThrowStmt){
                                //If there are predecessors, it means CFG has branched before this block.
                                if(!block.getPreds().isEmpty())
                                {
                                    /* avoid throwstmt produced by synchronized. the logic of synchronized block is similar
                                      to try block. Every stmt may throw an exception thus Jimple code will add a basic blocks
                                      to catch exception. Besides, the process of exiting monitor can cause an exception,
                                      so Jimple code adds a basic block to catch exception caused by failing to exit monitor.
                                     */
                                    if(block.getPreds().size() == 1) {
                                        Iterator<Unit> predBlockIterator = block.getPreds().get(0).iterator();
                                        boolean synchronizedMark = false;
                                        while (predBlockIterator.hasNext()) {
                                            Unit unitInPredBlock = predBlockIterator.next();
                                            if (unitInPredBlock.toString().split(" ")[0].equals("exitmonitor")) {
                                                synchronizedMark = true;
                                                break;
                                            }
                                        }
                                        if(synchronizedMark)
                                            break;
                                    }
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
            if_throwPatternAnalyze(true,"if_throwPatternResult3.0.txt" );
//            nameKeywordPatternAnalyze(true, "nameKeywordPatternResult.txt");

        }


}


