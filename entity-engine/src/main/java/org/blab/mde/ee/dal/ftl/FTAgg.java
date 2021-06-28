package org.blab.mde.ee.dal.ftl;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FTAgg {

  private String sqlFT;

  private FT template;

  private List<FT> childTemp = new ArrayList<>();

  public String sqlFT() {
    return sqlFT;
  }

  public FTAgg(FT template) {
    this.template = template;
    this.sqlFT = template.getTemplate();
  }

  public FT childFT(String childFTName) {

    FT childFT =  template
        .getBelongToFTFile()
        .ft(childFTName);

    addChildFT(childFT);
    return childFT;

  }

  public void addChildFT(FT ft) {
    childTemp.add(ft);

  }

}
