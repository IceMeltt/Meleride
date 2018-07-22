package pl.meleride.user.disease;

import java.util.ArrayList;
import java.util.List;

public enum Disease {
  VIRUS("Wirus"),
  FEVER("Goraczka");

  String name;

  Disease(String name) {
  this.name = name;
  }

  public String getName() {
    return name;
  }

  public static List<Disease> getDiseaseFromString(String[] string) {
    List<Disease> gettedDiseases = new ArrayList<>();
    for (String value : string) {
      Disease disease = Enum.valueOf(Disease.class, value);
      gettedDiseases.add(disease);
    }
    return gettedDiseases;
  }

}
