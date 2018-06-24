package pl.meleride.api.user.status;

import java.util.ArrayList;
import java.util.List;
import pl.meleride.api.util.EnumUtils;

public enum DiseaseStatus {

  FEVER,
  VIRUS;

  public static DiseaseStatus getDiseaseFromString(String string) {
    return EnumUtils.getEnumFromString(DiseaseStatus.class, string);
  }

  public static List<DiseaseStatus> getDiseaseFromString(String[] string) {
    List<DiseaseStatus> gettedDiseases = new ArrayList<>();
    for(String value : string) {
      gettedDiseases.add(EnumUtils.getEnumFromString(DiseaseStatus.class, value));
    }

    return gettedDiseases;
  }

}
