package com.orientechnologies.orient.server.distributed.impl.task.transaction;

public class OTxLockTimeout implements OTransactionResultPayload{
  private static final int ID = 2;

  @Override
  public int getResponseType() {
    return ID;
  }
}