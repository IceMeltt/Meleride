package pl.meleride.api.user.status;

import java.util.ArrayList;
import java.util.List;

public enum DiseaseStatus {

  FEVER,
  VIRUS;

  public static DiseaseStatus getDiseaseFromString(String string) {
    return Enum.valueOf(DiseaseStatus.class, string);
  }

  public static List<DiseaseStatus> getDiseaseFromString(String[] string) {
    List<DiseaseStatus> gettedDiseases = new ArrayList<>();
    for (String value : string) {
        DiseaseStatus disease = Enum.valueOf(DiseaseStatus.class, value);
        gettedDiseases.add(disease);
    }
    return gettedDiseases;
  }

}
