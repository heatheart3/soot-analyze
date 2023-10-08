**Components** 

SootInitializer.java: initial soot, configure options for Soot, load dependencies jars from Dir classes/DependencyJars and load Target Jars from Dir classes/TargetJars
   
SearchSpecificPattern.java: automatically analyze TargetJars, find methods whose body has if_throw pattern or keyword pattern and output corresponding methods' name to a .txt file.
    
TraceExceptionByCallGraph.java(unfinished): intend to trace exception by call graph. However, the guava.jar file of dependency jars leads to an error shown below. 
```error log
Exception in thread "main" soot.SootMethodRefImpl$ClassResolutionFailedException: Class com.google.common.base.Objects doesn't have method toStringHelper([java.lang.Object]) : com.google.common.base.Objects$ToStringHelper; failed to resolve in superclasses and interfaces
	at soot.SootMethodRefImpl.resolve(SootMethodRefImpl.java:291)
	at soot.SootMethodRefImpl.resolve(SootMethodRefImpl.java:207)
	at soot.jimple.internal.AbstractInvokeExpr.getMethod(AbstractInvokeExpr.java:60)
	at soot.jimple.toolkits.callgraph.OnFlyCallGraphBuilder.findReceivers(OnFlyCallGraphBuilder.java:825)
	at soot.jimple.toolkits.callgraph.OnFlyCallGraphBuilder.processNewMethod(OnFlyCallGraphBuilder.java:799)
	at soot.jimple.toolkits.callgraph.OnFlyCallGraphBuilder.processReachables(OnFlyCallGraphBuilder.java:293)
	at soot.jimple.toolkits.callgraph.CallGraphBuilder.build(CallGraphBuilder.java:108)
	at soot.jimple.toolkits.callgraph.CHATransformer.internalTransform(CHATransformer.java:54)
	at soot.SceneTransformer.transform(SceneTransformer.java:36)
	at soot.Transform.apply(Transform.java:105)
	at soot.RadioScenePack.internalApply(RadioScenePack.java:64)
	at soot.jimple.toolkits.callgraph.CallGraphPack.internalApply(CallGraphPack.java:61)
	at soot.Pack.apply(Pack.java:118)
	at soot.PackManager.runWholeProgramPacks(PackManager.java:619)
	at soot.PackManager.runPacksNormally(PackManager.java:500)
	at soot.PackManager.runPacks(PackManager.java:425)
	at org.example.TraceExceptionByCallGraph.loadProcessClass(TraceExceptionByCallGraph.java:19)
	at org.example.TraceExceptionByCallGraph.main(TraceExceptionByCallGraph.java:35)

```

The error is led by the lack of specific method in guava.jar. I tried to replace it using a newer or previous vision, but it always has this error. The version of hadoop source code packed corresponds to guava-11.0-javadoc.jar but using correct version didn't work.
****
**How to use:**   
1. Put jars which you want to be analyzed to classes/TargetJars
2. Put dependency jars to class/DependencyJars
3. Run SearchSpecificPattern.java
4. Check results in if_throwPatternResult.txt and nameKeywordPatternResult.txt