**Components** 

SootInitializer.java: initial soot, configure options for Soot, load dependencies jars from Dir classes/DependencyJars and load Target Jars from Dir classes/TargetJars
   
SearchSpecificPattern.java: automatically analyze TargetJars, find methods whose body has if_throw pattern or keyword pattern and output corresponding methods' name to a .txt file.
    
TraceExceptionByCallGraph.java: trace specific methods' invoke by call graph. The tool will output the invoke path of a specific method.

****
**How to use:**   
1. Put jars which you want to be analyzed to classes/TargetJars
2. Put dependency jars to class/DependencyJars
3. Run SearchSpecificPattern.java
4. Check results in if_throwPatternResult.txt and nameKeywordPatternResult.txt