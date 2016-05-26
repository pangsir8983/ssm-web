package com.pangsir.helper.uuid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * @author 刘文铭
 * @描述
 * UUID是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的。通常平台会提供生成UUID的API。UUID按照开放软件基金会(OSF)
 * 制定的标准计算，用到了以太网卡地址、纳秒级时间、芯片ID码和许多可能的数字。由以下几部分的组合：当前日期和时间(UUID的第一个部分与时间有关，
 * 如果你在生成一个UUID之后，过几秒又生成一个UUID，则第一个部分不同，其余相同)，时钟序列，全局唯一的IEEE机器识别号
 * （如果有网卡，从网卡获得，没有网卡以其他方式获得），
 * UUID的唯一缺陷在于生成的结果串会比较长。关于UUID这个标准使用最普遍的是微软的GUID(Globals Unique Identifiers)。
 *
 */
public class GUID {
	 public String valueBeforeMD5 = "";
     public String valueAfterMD5 = "";
     private static Random myRand;
     private static SecureRandom mySecureRand;
     private static String s_id;
     private static final int PAD_BELOW = 0x10;
     private static final int TWO_BYTES = 0xFF;
     /*
      * Static block to take care of one time secureRandom seed. It takes a few
      * seconds to initialize SecureRandom. You might want to consider removing
      * this static block or replacing it with a "time since first loaded" seed
      * to reduce this time. This block will run only once per JVM instance.
      */
     static {
             mySecureRand = new SecureRandom();
             long secureInitializer = mySecureRand.nextLong();
             myRand = new Random(secureInitializer);
             try {
                     s_id = InetAddress.getLocalHost().toString();
             } catch (UnknownHostException e) {
                     e.printStackTrace();
             }
     }

     /*
      * Default constructor. With no specification of security option, this
      * constructor defaults to lower security, high performance.
      */
     public GUID() {
             getRandomGUID(false);
     }

     /*
      * Constructor with security option. Setting secure true enables each random
      * number generated to be cryptographically strong. Secure false defaults to
      * the standard Random function seeded with a single cryptographically
      * strong random number.
      */
     public GUID(boolean secure) {
             getRandomGUID(secure);
     }

     /*
      * Method to generate the random GUID
      */
     private void getRandomGUID(boolean secure) {
             MessageDigest md5 = null;
             StringBuffer sbValueBeforeMD5 = new StringBuffer(128);
             try {
                     md5 = MessageDigest.getInstance("MD5");
             } catch (NoSuchAlgorithmException e) {
//                   logger.error("Error: " + e);
             }
             try {
                     long time = System.currentTimeMillis();
                     long rand = 0;
                     if (secure) {
                             rand = mySecureRand.nextLong();
                     } else {
                             rand = myRand.nextLong();
                     }
                     sbValueBeforeMD5.append(s_id);
                     sbValueBeforeMD5.append(":");
                     sbValueBeforeMD5.append(Long.toString(time));
                     sbValueBeforeMD5.append(":");
                     sbValueBeforeMD5.append(Long.toString(rand));
                     valueBeforeMD5 = sbValueBeforeMD5.toString();
                     md5.update(valueBeforeMD5.getBytes());
                     byte[] array = md5.digest();
                     StringBuffer sb = new StringBuffer(32);
                     for (int j = 0; j < array.length; ++j) {
                             int b = array[j] & TWO_BYTES;
                             if (b < PAD_BELOW)
                                     sb.append('0');
                             sb.append(Integer.toHexString(b));
                     }
                     valueAfterMD5 = sb.toString();
             } catch (Exception e) {
//                   logger.error("Error:" + e);
             }
     }

     /*
      * Convert to the standard format for GUID (Useful for SQL Server
      * UniqueIdentifiers, etc.) Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
      */
     public String toString() {
             String raw = valueAfterMD5.toUpperCase();
             StringBuffer sb = new StringBuffer(64);
             sb.append(raw.substring(0, 8));
             sb.append("-");
             sb.append(raw.substring(8, 12));
             sb.append("-");
             sb.append(raw.substring(12, 16));
             sb.append("-");
             sb.append(raw.substring(16, 20));
             sb.append("-");
             sb.append(raw.substring(20));
             return sb.toString();
     }

     /**
      * Get the next available ID
      * 
      * @param tablename
      *            The table name
      * @param pkname
      *            The primary key column, must be Integer
      * @param conn
      *            The database connection object
      * @return int The new ID
      * @throws SQLException
      */
     public static int getNextID(String tablename, String pkname, Connection conn) throws SQLException {
             int id = 1;
             String str = "Select   MAX( " + pkname + ")   As   A   From   " + tablename;
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(str);
             if (rs.next()) {
                     id += rs.getInt("A ");
             }
             rs.close();
             stmt.close();
             return id;
     }

     // Demonstraton and self test of class
     public static void main(String args[]) {
//           for (int i = 0; i < 100; i++) {
                     GUID myGUID = new GUID();
                     System.out.println("Seeding String=" + myGUID.valueBeforeMD5);
                     System.out.println("rawGUID=" + myGUID.valueAfterMD5);
                     System.out.println("RandomGUID=" + myGUID.toString());
//           }
     }

}