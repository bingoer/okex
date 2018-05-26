package com.weber.okex.ticker.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.weber.okex.ticker.client.domain.OkexTrade;

public class BuyTradesCache {

  private static ConcurrentHashMap<String, Collection<OkexTrade>> map = new ConcurrentHashMap<>();

  public static synchronized void clearAll() {
    map = new ConcurrentHashMap<>();
  }

  public static void add(String symbol, Collection<OkexTrade> collection) {
    map.put(symbol, collection);
  }

  public static Collection<OkexTrade> get(String symbol) {
    return map.get(symbol);
  }

  public static ConcurrentHashMap<String, Collection<OkexTrade>> getAll() {
    return map;
  }
}
