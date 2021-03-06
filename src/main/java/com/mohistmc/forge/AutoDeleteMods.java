package com.mohistmc.forge;

import com.mohistmc.util.FindClassInJar;
import java.util.Arrays;
import java.util.List;

/**
 * Why is there such a class?
 * Because we have included some MOD optimizations and modifications,
 * as well as some mods that are only used on the client, these cannot be loaded in Mohist
 */
public class AutoDeleteMods {
  public static final List<String> classlist;

  static {
    classlist = Arrays.asList(
            "org.spongepowered.mod.SpongeMod" /*SpongeForge*/,
            "lumien.custommainmenu.CustomMainMenu" /*CustomMainMenu*/,
            "com.performant.coremod.Performant" /*Performant*/,
            "shadows.fastbench.proxy.BenchServerProxy" /*FastWorkbench*/,
            "optifine.Differ" /*OptiFine*/,
            "ichttt.mods.firstaid.FirstAid" /*FirstAid*/,
            "guichaguri.betterfps.patches.misc.ServerPatch" /*BetterFps*/);
  }

  public static void jar() throws Exception {
    String libDir = "mods";
    for (String classname : AutoDeleteMods.classlist) {
      classname = classname.replaceAll("\\.", "/") + ".class";

      FindClassInJar ins = new FindClassInJar(libDir, classname);
      ins.checkDirectory(libDir);
    }
  }
}