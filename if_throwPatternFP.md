stop $r10
```Jimple
    public void stop()
    {
        java.lang.Throwable $r10;
        org.apache.hadoop.hdfs.server.namenode.metrics.NameNodeMetrics $r2, $r5;
        org.apache.hadoop.hdfs.server.namenode.FSNamesystem $r3, $r4;
        org.apache.hadoop.ha.ServiceFailedException $r8;
        org.apache.hadoop.hdfs.server.namenode.ha.HAState $r1, $r7;
        org.apache.commons.logging.Log $r9;
        org.apache.hadoop.hdfs.server.namenode.NameNode r0;
        org.apache.hadoop.hdfs.server.namenode.ha.HAContext $r6;
        boolean $z0;

        r0 := @this: org.apache.hadoop.hdfs.server.namenode.NameNode;

        entermonitor r0;

     label01:
        $z0 = r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: boolean stopRequested>;

        if $z0 == 0 goto label03;

        exitmonitor r0;

     label02:
        return;

     label03:
        r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: boolean stopRequested> = 1;

        exitmonitor r0;

     label04:
        goto label07;

     label05:
        $r10 := @caughtexception;

        exitmonitor r0;

     label06:
        throw $r10;

     label07:
        $r1 = r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.hadoop.hdfs.server.namenode.ha.HAState state>;

        if $r1 == null goto label08;

        $r7 = r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.hadoop.hdfs.server.namenode.ha.HAState state>;

        $r6 = r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.hadoop.hdfs.server.namenode.ha.HAContext haContext>;

        virtualinvoke $r7.<org.apache.hadoop.hdfs.server.namenode.ha.HAState: void exitState(org.apache.hadoop.hdfs.server.namenode.ha.HAContext)>($r6);

     label08:
        goto label10;

     label09:
        $r8 := @caughtexception;

        $r9 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r9.<org.apache.commons.logging.Log: void warn(java.lang.Object,java.lang.Throwable)>("Encountered exception while exiting state ", $r8);

     label10:
        specialinvoke r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: void stopCommonServices()>();

        $r2 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.hadoop.hdfs.server.namenode.metrics.NameNodeMetrics metrics>;

        if $r2 == null goto label11;

        $r5 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.hadoop.hdfs.server.namenode.metrics.NameNodeMetrics metrics>;

        virtualinvoke $r5.<org.apache.hadoop.hdfs.server.namenode.metrics.NameNodeMetrics: void shutdown()>();

     label11:
        $r3 = r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.hadoop.hdfs.server.namenode.FSNamesystem namesystem>;

        if $r3 == null goto label12;

        $r4 = r0.<org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.hadoop.hdfs.server.namenode.FSNamesystem namesystem>;

        virtualinvoke $r4.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: void shutdown()>();

     label12:
        return;

        catch java.lang.Throwable from label01 to label02 with label05;
        catch java.lang.Throwable from label03 to label04 with label05;
        catch java.lang.Throwable from label05 to label06 with label05;
        catch org.apache.hadoop.ha.ServiceFailedException from label07 to label08 with label09;
    }

```

initializeSharedEdits $r34
```Jimple
private static boolean initializeSharedEdits(org.apache.hadoop.conf.Configuration, boolean, boolean) throws java.io.IOException
    {
        org.apache.hadoop.hdfs.server.namenode.FSEditLog $r14, $r16, $r18, $r20, $r22;
        org.apache.hadoop.conf.Configuration r0, $r4;
        org.apache.hadoop.hdfs.server.namenode.FSImage $r8, $r12, $r17, $r19, $r21;
        boolean $z0, z1, z2, $z3;
        org.apache.hadoop.hdfs.server.namenode.FSNamesystem $r7;
        java.util.Collection $r5;
        java.net.InetSocketAddress $r23;
        java.util.List $r6, $r11;
        java.lang.StringBuilder $r25, $r27, $r28, $r29, $r30;
        java.lang.Throwable $r34;
        org.apache.hadoop.hdfs.server.protocol.NamespaceInfo $r10;
        java.util.ArrayList $r13;
        java.lang.String $r1, $r2, $r3, $r24, $r31;
        java.io.IOException $r32, $r35, $r37, $r39, $r41;
        org.apache.hadoop.hdfs.server.namenode.NNStorage $r9, $r15, r43;
        org.apache.commons.logging.Log $r26, $r33, $r36, $r38, $r40, $r42;

        r0 := @parameter0: org.apache.hadoop.conf.Configuration;

        z1 := @parameter1: boolean;

        z2 := @parameter2: boolean;

        $r1 = staticinvoke <org.apache.hadoop.hdfs.DFSUtil: java.lang.String getNamenodeNameServiceId(org.apache.hadoop.conf.Configuration)>(r0);

        $r2 = staticinvoke <org.apache.hadoop.hdfs.HAUtil: java.lang.String getNameNodeId(org.apache.hadoop.conf.Configuration,java.lang.String)>(r0, $r1);

        staticinvoke <org.apache.hadoop.hdfs.server.namenode.NameNode: void initializeGenericKeys(org.apache.hadoop.conf.Configuration,java.lang.String,java.lang.String)>(r0, $r1, $r2);

        $r3 = virtualinvoke r0.<org.apache.hadoop.conf.Configuration: java.lang.String get(java.lang.String)>("dfs.namenode.shared.edits.dir");

        if $r3 != null goto label01;

        $r26 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.commons.logging.Log LOG>;

        $r25 = new java.lang.StringBuilder;

        specialinvoke $r25.<java.lang.StringBuilder: void <init>()>();

        $r27 = virtualinvoke $r25.<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)>("No shared edits directory configured for namespace ");

        $r28 = virtualinvoke $r27.<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)>($r1);

        $r29 = virtualinvoke $r28.<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)>(" namenode ");

        $r30 = virtualinvoke $r29.<java.lang.StringBuilder: java.lang.StringBuilder append(java.lang.String)>($r2);

        $r31 = virtualinvoke $r30.<java.lang.StringBuilder: java.lang.String toString()>();

        interfaceinvoke $r26.<org.apache.commons.logging.Log: void fatal(java.lang.Object)>($r31);

        return 0;

     label01:
        $z0 = staticinvoke <org.apache.hadoop.security.UserGroupInformation: boolean isSecurityEnabled()>();

        if $z0 == 0 goto label02;

        $r23 = staticinvoke <org.apache.hadoop.hdfs.server.namenode.NameNode: java.net.InetSocketAddress getAddress(org.apache.hadoop.conf.Configuration)>(r0);

        $r24 = virtualinvoke $r23.<java.net.InetSocketAddress: java.lang.String getHostName()>();

        staticinvoke <org.apache.hadoop.security.SecurityUtil: void login(org.apache.hadoop.conf.Configuration,java.lang.String,java.lang.String,java.lang.String)>(r0, "dfs.namenode.keytab.file", "dfs.namenode.kerberos.principal", $r24);

     label02:
        r43 = null;

     label03:
        $r4 = new org.apache.hadoop.conf.Configuration;

        specialinvoke $r4.<org.apache.hadoop.conf.Configuration: void <init>(org.apache.hadoop.conf.Configuration)>(r0);

        virtualinvoke $r4.<org.apache.hadoop.conf.Configuration: void unset(java.lang.String)>("dfs.namenode.shared.edits.dir");

        $r5 = staticinvoke <org.apache.hadoop.hdfs.server.namenode.FSNamesystem: java.util.Collection getNamespaceDirs(org.apache.hadoop.conf.Configuration)>(r0);

        $r6 = staticinvoke <org.apache.hadoop.hdfs.server.namenode.FSNamesystem: java.util.List getNamespaceEditsDirs(org.apache.hadoop.conf.Configuration,boolean)>(r0, 0);

        $r7 = staticinvoke <org.apache.hadoop.hdfs.server.namenode.FSNamesystem: org.apache.hadoop.hdfs.server.namenode.FSNamesystem loadFromDisk(org.apache.hadoop.conf.Configuration,java.util.Collection,java.util.List)>($r4, $r5, $r6);

        $r8 = virtualinvoke $r7.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: org.apache.hadoop.hdfs.server.namenode.FSImage getFSImage()>();

        $r9 = virtualinvoke $r8.<org.apache.hadoop.hdfs.server.namenode.FSImage: org.apache.hadoop.hdfs.server.namenode.NNStorage getStorage()>();

        r43 = $r9;

        $r10 = virtualinvoke $r9.<org.apache.hadoop.hdfs.server.namenode.NNStorage: org.apache.hadoop.hdfs.server.protocol.NamespaceInfo getNamespaceInfo()>();

        $r11 = staticinvoke <org.apache.hadoop.hdfs.server.namenode.FSNamesystem: java.util.List getSharedEditsDirs(org.apache.hadoop.conf.Configuration)>(r0);

        $r12 = new org.apache.hadoop.hdfs.server.namenode.FSImage;

        $r13 = staticinvoke <com.google.common.collect.Lists: java.util.ArrayList newArrayList()>();

        specialinvoke $r12.<org.apache.hadoop.hdfs.server.namenode.FSImage: void <init>(org.apache.hadoop.conf.Configuration,java.util.Collection,java.util.List)>(r0, $r13, $r11);

        $r14 = virtualinvoke $r12.<org.apache.hadoop.hdfs.server.namenode.FSImage: org.apache.hadoop.hdfs.server.namenode.FSEditLog getEditLog()>();

        virtualinvoke $r14.<org.apache.hadoop.hdfs.server.namenode.FSEditLog: void initJournalsForWrite()>();

        $z3 = virtualinvoke $r12.<org.apache.hadoop.hdfs.server.namenode.FSImage: boolean confirmFormat(boolean,boolean)>(z1, z2);

        if $z3 != 0 goto label09;

     label04:
        if $r9 == null goto label08;

     label05:
        virtualinvoke $r9.<org.apache.hadoop.hdfs.server.namenode.NNStorage: void unlockAll()>();

     label06:
        goto label08;

     label07:
        $r41 := @caughtexception;

        $r42 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r42.<org.apache.commons.logging.Log: void warn(java.lang.Object,java.lang.Throwable)>("Could not unlock storage directories", $r41);

        return 1;

     label08:
        return 1;

     label09:
        $r15 = virtualinvoke $r12.<org.apache.hadoop.hdfs.server.namenode.FSImage: org.apache.hadoop.hdfs.server.namenode.NNStorage getStorage()>();

        virtualinvoke $r15.<org.apache.hadoop.hdfs.server.namenode.NNStorage: void format(org.apache.hadoop.hdfs.server.protocol.NamespaceInfo)>($r10);

        $r16 = virtualinvoke $r12.<org.apache.hadoop.hdfs.server.namenode.FSImage: org.apache.hadoop.hdfs.server.namenode.FSEditLog getEditLog()>();

        virtualinvoke $r16.<org.apache.hadoop.hdfs.server.namenode.FSEditLog: void formatNonFileJournals(org.apache.hadoop.hdfs.server.protocol.NamespaceInfo)>($r10);

        $r17 = virtualinvoke $r7.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: org.apache.hadoop.hdfs.server.namenode.FSImage getFSImage()>();

        $r18 = virtualinvoke $r17.<org.apache.hadoop.hdfs.server.namenode.FSImage: org.apache.hadoop.hdfs.server.namenode.FSEditLog getEditLog()>();

        virtualinvoke $r18.<org.apache.hadoop.hdfs.server.namenode.FSEditLog: void close()>();

        $r19 = virtualinvoke $r7.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: org.apache.hadoop.hdfs.server.namenode.FSImage getFSImage()>();

        $r20 = virtualinvoke $r19.<org.apache.hadoop.hdfs.server.namenode.FSImage: org.apache.hadoop.hdfs.server.namenode.FSEditLog getEditLog()>();

        virtualinvoke $r20.<org.apache.hadoop.hdfs.server.namenode.FSEditLog: void initJournalsForWrite()>();

        $r21 = virtualinvoke $r7.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: org.apache.hadoop.hdfs.server.namenode.FSImage getFSImage()>();

        $r22 = virtualinvoke $r21.<org.apache.hadoop.hdfs.server.namenode.FSImage: org.apache.hadoop.hdfs.server.namenode.FSEditLog getEditLog()>();

        virtualinvoke $r22.<org.apache.hadoop.hdfs.server.namenode.FSEditLog: void recoverUnclosedStreams()>();

        staticinvoke <org.apache.hadoop.hdfs.server.namenode.NameNode: void copyEditLogSegmentsToSharedDir(org.apache.hadoop.hdfs.server.namenode.FSNamesystem,java.util.Collection,org.apache.hadoop.hdfs.server.namenode.NNStorage,org.apache.hadoop.conf.Configuration)>($r7, $r11, $r15, r0);

     label10:
        if $r9 == null goto label26;

     label11:
        virtualinvoke $r9.<org.apache.hadoop.hdfs.server.namenode.NNStorage: void unlockAll()>();

     label12:
        goto label26;

     label13:
        $r39 := @caughtexception;

        $r40 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r40.<org.apache.commons.logging.Log: void warn(java.lang.Object,java.lang.Throwable)>("Could not unlock storage directories", $r39);

        return 1;

     label14:
        $r37 := @caughtexception;

        $r38 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r38.<org.apache.commons.logging.Log: void error(java.lang.Object,java.lang.Throwable)>("Could not initialize shared edits dir", $r37);

     label15:
        if r43 == null goto label19;

     label16:
        virtualinvoke r43.<org.apache.hadoop.hdfs.server.namenode.NNStorage: void unlockAll()>();

     label17:
        goto label19;

     label18:
        $r35 := @caughtexception;

        $r36 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r36.<org.apache.commons.logging.Log: void warn(java.lang.Object,java.lang.Throwable)>("Could not unlock storage directories", $r35);

        return 1;

     label19:
        return 1;

     label20:
        $r34 := @caughtexception;

     label21:
        if r43 == null goto label25;

     label22:
        virtualinvoke r43.<org.apache.hadoop.hdfs.server.namenode.NNStorage: void unlockAll()>();

     label23:
        goto label25;

     label24:
        $r32 := @caughtexception;

        $r33 = <org.apache.hadoop.hdfs.server.namenode.NameNode: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r33.<org.apache.commons.logging.Log: void warn(java.lang.Object,java.lang.Throwable)>("Could not unlock storage directories", $r32);

        return 1;

     label25:
        throw $r34;

     label26:
        return 0;

        catch java.io.IOException from label05 to label06 with label07;
        catch java.io.IOException from label11 to label12 with label13;
        catch java.io.IOException from label03 to label04 with label14;
        catch java.io.IOException from label09 to label10 with label14;
        catch java.io.IOException from label16 to label17 with label18;
        catch java.lang.Throwable from label03 to label04 with label20;
        catch java.lang.Throwable from label09 to label10 with label20;
        catch java.lang.Throwable from label14 to label15 with label20;
        catch java.io.IOException from label22 to label23 with label24;
        catch java.lang.Throwable from label20 to label21 with label20;
    }

```

doRecovery $r12
```Jimple
private static void doRecovery(org.apache.hadoop.hdfs.server.common.HdfsServerConstants$StartupOption, org.apache.hadoop.conf.Configuration) throws java.io.IOException
    {
        java.lang.Throwable $r9;
        org.apache.hadoop.conf.Configuration r0;
        org.apache.hadoop.hdfs.server.common.HdfsServerConstants$StartupOption r3;
        int $i0;
        java.lang.String $r1, $r2;
        boolean $z0;
        java.io.PrintStream $r8;
        org.apache.hadoop.hdfs.server.namenode.FSNamesystem $r6, r14;
        java.io.IOException $r12;
        java.lang.RuntimeException $r10;
        org.apache.hadoop.hdfs.server.common.HdfsServerConstants$NamenodeRole $r5;
        org.apache.commons.logging.Log $r4, $r7, $r11, $r13;

        r3 := @parameter0: org.apache.hadoop.hdfs.server.common.HdfsServerConstants$StartupOption;

        r0 := @parameter1: org.apache.hadoop.conf.Configuration;

        $r1 = staticinvoke <org.apache.hadoop.hdfs.DFSUtil: java.lang.String getNamenodeNameServiceId(org.apache.hadoop.conf.Configuration)>(r0);

        $r2 = staticinvoke <org.apache.hadoop.hdfs.HAUtil: java.lang.String getNameNodeId(org.apache.hadoop.conf.Configuration,java.lang.String)>(r0, $r1);

        staticinvoke <org.apache.hadoop.hdfs.server.namenode.NameNode: void initializeGenericKeys(org.apache.hadoop.conf.Configuration,java.lang.String,java.lang.String)>(r0, $r1, $r2);

        $i0 = virtualinvoke r3.<org.apache.hadoop.hdfs.server.common.HdfsServerConstants$StartupOption: int getForce()>();

        if $i0 >= 2 goto label1;

        $z0 = staticinvoke <org.apache.hadoop.util.ToolRunner: boolean confirmPrompt(java.lang.String)>("You have selected Metadata Recovery mode.  This mode is intended to recover lost metadata on a corrupt filesystem.  Metadata recovery mode often permanently deletes data from your HDFS filesystem.  Please back up your edit log and fsimage before trying this!\n\nAre you ready to proceed? (Y/N)\n");

        if $z0 != 0 goto label1;

        $r8 = <java.lang.System: java.io.PrintStream err>;

        virtualinvoke $r8.<java.io.PrintStream: void println(java.lang.String)>("Recovery aborted at user request.\n");

        return;

     label1:
        $r4 = <org.apache.hadoop.hdfs.server.namenode.MetaRecoveryContext: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r4.<org.apache.commons.logging.Log: void info(java.lang.Object)>("starting recovery...");

        staticinvoke <org.apache.hadoop.security.UserGroupInformation: void setConfiguration(org.apache.hadoop.conf.Configuration)>(r0);

        $r5 = virtualinvoke r3.<org.apache.hadoop.hdfs.server.common.HdfsServerConstants$StartupOption: org.apache.hadoop.hdfs.server.common.HdfsServerConstants$NamenodeRole toNodeRole()>();

        staticinvoke <org.apache.hadoop.hdfs.server.namenode.NameNode: void initMetrics(org.apache.hadoop.conf.Configuration,org.apache.hadoop.hdfs.server.common.HdfsServerConstants$NamenodeRole)>(r0, $r5);

        r14 = null;

     label2:
        $r6 = staticinvoke <org.apache.hadoop.hdfs.server.namenode.FSNamesystem: org.apache.hadoop.hdfs.server.namenode.FSNamesystem loadFromDisk(org.apache.hadoop.conf.Configuration)>(r0);

        r14 = $r6;

        virtualinvoke $r6.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: void saveNamespace()>();

        $r7 = <org.apache.hadoop.hdfs.server.namenode.MetaRecoveryContext: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r7.<org.apache.commons.logging.Log: void info(java.lang.Object)>("RECOVERY COMPLETE");

     label3:
        if $r6 == null goto label9;

        virtualinvoke $r6.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: void close()>();

        goto label9;

     label4:
        $r12 := @caughtexception;

        $r13 = <org.apache.hadoop.hdfs.server.namenode.MetaRecoveryContext: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r13.<org.apache.commons.logging.Log: void info(java.lang.Object,java.lang.Throwable)>("RECOVERY FAILED: caught exception", $r12);

        throw $r12;

     label5:
        $r10 := @caughtexception;

        $r11 = <org.apache.hadoop.hdfs.server.namenode.MetaRecoveryContext: org.apache.commons.logging.Log LOG>;

        interfaceinvoke $r11.<org.apache.commons.logging.Log: void info(java.lang.Object,java.lang.Throwable)>("RECOVERY FAILED: caught exception", $r10);

        throw $r10;

     label6:
        $r9 := @caughtexception;

     label7:
        if r14 == null goto label8;

        virtualinvoke r14.<org.apache.hadoop.hdfs.server.namenode.FSNamesystem: void close()>();

     label8:
        throw $r9;

     label9:
        return;

        catch java.io.IOException from label2 to label3 with label4;
        catch java.lang.RuntimeException from label2 to label3 with label5;
        catch java.lang.Throwable from label2 to label3 with label6;
        catch java.lang.Throwable from label4 to label7 with label6;
    }

```