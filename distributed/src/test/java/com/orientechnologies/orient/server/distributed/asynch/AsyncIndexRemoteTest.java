package com.orientechnologies.orient.server.distributed.asynch;

import com.orientechnologies.common.log.OLogManager;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException;
import junit.framework.Assert;

public class AsyncIndexRemoteTest extends BareBoneBase3ServerTest {

  @Override
  protected String getDatabaseName() {
    return "AsyncIndexTest";
  }

  protected void dbClient1() {
    ODatabaseDocumentTx graph = new ODatabaseDocumentTx(getRemoteURL());
    graph.open("admin", "admin");
    try {
      graph.command(new OCommandSQL("create class SMS")).execute();
      graph.command(new OCommandSQL("create property SMS.type string")).execute();
      graph.command(new OCommandSQL("create property SMS.lang string")).execute();
      graph.command(new OCommandSQL("create property SMS.source integer")).execute();
      graph.command(new OCommandSQL("create property SMS.content string")).execute();
      graph.command(new OCommandSQL("alter property SMS.lang min 2")).execute();
      graph.command(new OCommandSQL("alter property SMS.lang max 2")).execute();
      graph.command(new OCommandSQL("create index sms_keys ON SMS (type, lang) unique")).execute();

      graph.command(new OCommandSQL("insert into sms (type, lang, source, content) values ( 'notify', 'en', 1, 'This is a test')"))
          .execute();
      try {
        graph
            .command(new OCommandSQL("insert into sms (type, lang, source, content) values ( 'notify', 'en', 1, 'This is a test')"))
            .execute();
        Assert.fail("violated unique index was not raised");
      } catch (ORecordDuplicatedException e) {
      }

      final Iterable<OElement> result = graph.command(new OSQLSynchQuery<OElement>("select count(*) from SMS")).execute();

      Assert.assertEquals(1, ((Number) result.iterator().next().getProperty("count")).intValue());

    } catch (Throwable e) {
      if (exceptionInThread == null) {
        exceptionInThread = e;
      }
    } finally {
      OLogManager.instance().info(this, "Shutting down db1");
      graph.close();
    }

    // CHECK ON THE 2ND NODE
    ODatabaseDocumentTx graph2 = new ODatabaseDocumentTx(getRemoteURL2());
    graph2.open("admin", "admin");
    try {
      try {
        graph2
            .command(new OCommandSQL("insert into sms (type, lang, source, content) values ( 'notify', 'en', 1, 'This is a test')"))
            .execute();
        Assert.fail("violated unique index was not raised");
      } catch (ORecordDuplicatedException e) {
      }

      final Iterable<OElement> result = graph2.command(new OSQLSynchQuery<OElement>("select count(*) from SMS")).execute();

      Assert.assertEquals(1, ((Number) result.iterator().next().getProperty("count")).intValue());

    } catch (Throwable e) {
      if (exceptionInThread == null) {
        exceptionInThread = e;
      }
    } finally {
      OLogManager.instance().info(this, "Shutting down db2");
      graph2.close();
    }

    // CHECK ON THE 2ND NODE
    ODatabaseDocumentTx graph3 = new ODatabaseDocumentTx(getRemoteURL3());
    graph3.open("admin", "admin");
    try {
      try {
        graph3
            .command(new OCommandSQL("insert into sms (type, lang, source, content) values ( 'notify', 'en', 1, 'This is a test')"))
            .execute();
        Assert.fail("violated unique index was not raised");
      } catch (ORecordDuplicatedException e) {
      }

      final Iterable<OElement> result = graph3.command(new OSQLSynchQuery<OElement>("select count(*) from SMS")).execute();

      Assert.assertEquals(1, ((Number) result.iterator().next().getProperty("count")).intValue());

    } catch (Throwable e) {
      if (exceptionInThread == null) {
        exceptionInThread = e;
      }
    } finally {
      OLogManager.instance().info(this, "Shutting down db3");
      graph3.close();
    }
  }

  @Override
  protected void dbClient2() {

  }
}
