package com.mohistmc.bukkit.nms;

import com.mohistmc.util.JarTool;
import com.mohistmc.util.MD5Util;
import com.mohistmc.util.i18n.Message;
import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import com.mohistmc.bukkit.nms.utils.Decoder;
import com.mohistmc.bukkit.nms.utils.Downloader;

/**
 * @author Mgazul
 * @date 2020/6/5 0:44
 */
public class MappingFix {
  private static int percentage = 0;

  public static void init() throws Exception {
    Decoder dc = new Decoder();
    Downloader dw = new Downloader();
    //specify the dir
    String basedir = JarTool.getJarDir();

    // old -> new
    File old = new File(basedir + "/libraries/red/mohist/mappings/nms.srg");
    File lib = new File(basedir + "/libraries/com/mohistmc/mappings/nms.srg");
    if (old.exists() && !lib.exists()) {
      old.renameTo(lib);
    }

    if (!lib.exists() || lib.length() < 4000000) {
      File joined = new File(basedir + "/joined.srg");
      File nms = new File(basedir + "/libraries/com/mohistmc/mappings/nms.srg");
      //start download
      dw.execute(basedir);
      if (new File(basedir + "/libraries/com/mohistmc/mappings").mkdirs())
        System.out.println(Message.getString("mappingfix.created.mappings"));
      if (nms.createNewFile())
        System.out.println(Message.getString("mappingfix.created.nms"));
      //start decode
      System.out.println(Message.getString("mappingfix.decoding.start"));
      System.out.println("#################################################\n" +
              "                 Powered by MCP                  \n" +
              "             http://modcoderpack.com             \n" +
              "     by: Searge, ProfMobius, R4wk, ZeuX          \n" +
              "     Fesh0r, IngisKahn, bspkrs, LexManos         \n" +
              "#################################################");
      System.out.println(Message.getString("mappingfix.decoding.info"));
      Timer t = new Timer();
      t.schedule(new TimerTask() {
        @Override
        public void run() {
          if (percentage != Math.round((float) lib.length() / 4000000 * 100))
            System.out.println(Message.getString("mapping.decoding.progress") + percentage + "%");
          percentage = Math.round((float) lib.length() / 4000000 * 100);
        }
      }, 3000, 3000);
      long startTime = System.currentTimeMillis();
      dc.Decode(joined, nms);
      t.cancel();
      System.out.println(Message.getFormatString("mappingfix.decoding.end", new Object[]{(System.currentTimeMillis() - startTime) / 1000}));
      System.gc();
      Thread.sleep(100);
      joined.delete();
    }
  }
}