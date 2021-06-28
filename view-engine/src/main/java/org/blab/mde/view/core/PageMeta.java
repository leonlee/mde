package org.blab.mde.view.core;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PageMeta {
  private MenuMeta menu;
  private DashboardMeta dashboard;
  private SmartSearchMeta smartSearch;
  private HelpCenterMeta helpCenter;
  private List<ViewMeta> views;
}
