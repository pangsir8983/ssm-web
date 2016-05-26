package com.pangsir.helper.uuid;

public final class MyUUID {
	 private MyUUID() {
     }

     /**
      * 生成一个 32 位的 UUID，该 UUID 是由一串哈希码组成(全大写)。此方法线程安全。
      * @return
      */
     public static synchronized String getUUID() {
             long time = System.currentTimeMillis();
             String timePattern = Long.toHexString(time);
             int leftBit = 14 - timePattern.length();
             if (leftBit > 0) {
                     timePattern = "0000000000".substring(0, leftBit) + timePattern;
             }

             String uuid = timePattern + Long.toHexString(Double.doubleToLongBits(Math.random())) + Long.toHexString(Double.doubleToLongBits(Math.random())) + "000000000000000000";
             uuid = uuid.substring(0, 32).toUpperCase();
             return uuid;
     }
     
}

