package com.ptsisi.freemarker.model;

import static com.ptsisi.freemarker.model.Engine.Gecko;
import static com.ptsisi.freemarker.model.Engine.Khtml;
import static com.ptsisi.freemarker.model.Engine.Other;
import static com.ptsisi.freemarker.model.Engine.Presto;
import static com.ptsisi.freemarker.model.Engine.Trident;
import static com.ptsisi.freemarker.model.Engine.WebKit;
import static com.ptsisi.freemarker.model.Engine.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by zhaoding on 14-10-22.
 */
public enum BrowserCategory {

  // Gecko series
  Firefox("Firefox", Gecko, "Firefox/(\\S*)->$1", "Firefox"),

  Thunderbird("Thunderbird", Gecko, "Thunderbird/(\\S*)->$1", "Thunderbird"),

  Camino("Camino", Gecko, "Camino/(\\S*)->$1", "Camino"),

  Flock("Flock", Gecko, "Flock/(\\S*)->$1"),

  FirefoxMobile("Firefox Mobile", Gecko, "Firefox/3.5 Maemo->3"), // ??? need
  // validate

  SeaMonkey("SeaMonkey", Gecko, "SeaMonkey"),

  // IE series
  Tencent("Tencent Traveler", Trident, "TencentTraveler (\\S*);->$1"),

  Sogo("Sogo", Trident, "SE(.*)MetaSr"),

  TheWorld("The World", Trident, "theworld"),

  IE360("Internet Explorer 360", Trident, "360SE"),

  IeMobile("IE Mobile", Trident, "IEMobile (\\S*)->$1"),

  IE("Internet Explorer", Trident, "MSIE (\\S*);->$1", "MSIE"),

  OutlookExpress("Windows Live Mail", Trident, "Outlook-Express/7.0->7.0"),

  // WebKit
  Maxthon("Maxthon", WebKit, "Maxthon/(\\S*)->$1", "Maxthon"),

  Chrome("Chrome", WebKit, "Chrome/(\\S*)->$1", "Chrome"),

  Safari("Safari", WebKit, "Version/(\\S*) Safari->$1", "Safari"),

  Omniweb("Omniweb", WebKit, "OmniWeb"),

  AppleMail("Apple Mail", WebKit, "AppleWebKit"),

  ChromeMobile("Chrome Mobile", WebKit, "CrMo/(\\S*)->$1"), // ??? need
  // validate

  SafariMobile("Mobile Safari", WebKit, "Mobile Safari", "Mobile/5A347 Safari", "Mobile/3A101a Safari",
      "Mobile/7B367 Safari"),

  Silk("Silk", WebKit, "Silk/(\\S*)->$1"),

  Dolfin("Samsung Dolphin", WebKit, "Dolfin/(\\S*)->$1"),

  // Presto
  Opera("Opera", Presto, "Opera/(.*?)Version/(\\S*)->$2", "Opera Mini->Mini", "Opera"),

  // Khtml
  Konqueror("Konqueror", Khtml, "Konqueror"),

  // Word
  Outlook("Outlook", Word, "MSOffice 12->2007", "MSOffice 14->2010", "MSOffice"),

  // Others
  LotusNotes("Lotus Notes", Other, "Lotus-Notes"),

  Bot("Robot/Spider", Other, "Googlebot", "bot", "spider", "crawler", "Feedfetcher", "Slurp", "Twiceler", "Nutch",
      "BecomeBot"),

  Mozilla("Mozilla", Other, "Mozilla", "Moozilla"),

  CFNetwork("CFNetwork", Other, "CFNetwork"),

  Eudora("Eudora", Other, "Eudora", "EUDORA"),

  PocoMail("PocoMail", Other, "PocoMail"),

  TheBat("The Bat!", Other, "The Bat"),

  NetFront("NetFront", Other, "NetFront"),

  Evolution("Evolution", Other, "CamelHttpStream"),

  Lynx("Lynx", Other, "Lynx"), // ??? need validate

  UC("UC", Other, "UCWEB"),

  Download("Downloading Tool", Other, "cURL", "wget"),

  Unknown("Unknown", Other);

  private final String name;
  private final Engine engine;
  private final List<Pair<Pattern, String>> versionPairs = new ArrayList<Pair<Pattern, String>>();

  private BrowserCategory(String name, Engine renderEngine, String... versions) {
    this.name = name;
    this.engine = renderEngine;
    engine.addCategory(this);
    for (String version : versions) {
      String matcheTarget = version;
      String versionNum = "";
      if (StringUtils.contains(version, "->")) {
        // regular expression match ignore case
        matcheTarget = "(?i)" + StringUtils.substringBefore(version, "->");
        versionNum = StringUtils.substringAfter(version, "->");
      }
      versionPairs.add(Pair.of(Pattern.compile(matcheTarget), versionNum));
    }
  }

  public String getName() {
    return name;
  }

  /**
   * @return the rendering engine
   */
  public Engine getEngine() {
    return engine;
  }

  public String match(String agentString) {
    for (Pair<Pattern, String> pair : versionPairs) {
      Matcher m = pair.getKey().matcher(agentString);
      if (m.find()) {
        StringBuffer sb = new StringBuffer();
        m.appendReplacement(sb, pair.getValue());
        sb.delete(0, m.start());
        return sb.toString();
      }
    }
    return null;
  }
}
