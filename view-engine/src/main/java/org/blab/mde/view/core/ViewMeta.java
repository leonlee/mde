package org.blab.mde.view.core;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ViewMeta {
  private String name;
  private String model;
}
