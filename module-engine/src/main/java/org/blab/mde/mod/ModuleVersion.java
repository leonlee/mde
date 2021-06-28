package org.blab.mde.mod;

import com.google.common.base.Strings;

import com.github.zafarkhaja.semver.Version;

import org.blab.mde.core.exception.CrashedException;
import lombok.Data;

import static org.blab.mde.core.util.Guarder.requireNotBlank;
import static org.blab.mde.core.util.Guarder.requireTrue;


@Data
public class ModuleVersion {
  public static final String SPLITTER = ":";
  public static final String MIN_VER = "0.0.0";
  String name;
  Version version;

  public ModuleVersion(String name, String version) {
    requireNotBlank(name, "invalid module name: {}", name);
    requireNotBlank(name, "invalid module version: {}", version);
    this.name = name;
    this.version = parseVersion(version);
  }

  private Version parseVersion(String version) {
    Version semVer;

    try {
      semVer = Version.valueOf(version);
    } catch (Exception e) {
      throw new CrashedException("invalid version {}", e, version);
    }

    return semVer;
  }

  public boolean gte(ModuleVersion that) {
    requireTrue(name.equalsIgnoreCase(that.name), "mismatched module name");

    return version.greaterThanOrEqualTo(that.version);
  }

  public static ModuleVersion ofDep(String dep) {
    requireNotBlank(dep, "invalid module version: {}", dep);
    String[] slices = dep.split(SPLITTER);

    String name = slices[0];
    String version = slices.length == 2 ? slices[1] : null;
    version = Strings.isNullOrEmpty(version) ? MIN_VER : version;

    return new ModuleVersion(name, version);
  }

  public static ModuleVersion ofMod(String name, String version) {
    requireNotBlank(version, "invalid blank version");

    return new ModuleVersion(name, version);
  }
}
