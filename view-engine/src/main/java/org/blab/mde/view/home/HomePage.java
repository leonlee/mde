package org.blab.mde.view.home;

import java.util.ArrayList;
import java.util.List;

import org.blab.mde.core.annotation.Initializer;
import org.blab.mde.core.annotation.Property;
import org.blab.mde.view.annotation.View;
import org.blab.mde.view.core.DashboardMeta;
import org.blab.mde.view.core.HelpCenterMeta;
import org.blab.mde.view.core.MenuMeta;
import org.blab.mde.view.core.PageMeta;
import org.blab.mde.view.core.SmartSearchMeta;
import org.blab.mde.view.core.ViewMeta;


@View
public interface HomePage {
  @Property
  PageMeta getPageMeta();

  void setPageMeta(PageMeta meta);

  @Property
  MenuMeta getMenuMeta();

  void setMenuMeta(MenuMeta menuMeta);

  @Property
  DashboardMeta getDashboardMeta();

  void setDashboardMeta(DashboardMeta dashboardMeta);

  @Property
  SmartSearchMeta getSmartSearchMeta();

  void setSmartSearchMeta(SmartSearchMeta smartSearchMeta);

  @Property
  HelpCenterMeta getHelpCenterMeta();

  void setHelpCenterMeta(HelpCenterMeta helpCenterMeta);

  @Property
  List<ViewMeta> getViewMetas();

  void setViewMetas(List<ViewMeta> viewMetas);

  @Initializer
  default void assemblePage() {
    assembleMenu();
    assembleDashboard();
    assembleSmartSearch();
    assembleHelpCenter();
    assembleViews();

    PageMeta pageMeta = PageMeta.builder()
        .menu(getMenuMeta())
        .dashboard(getDashboardMeta())
        .smartSearch(getSmartSearchMeta())
        .helpCenter(getHelpCenterMeta())
        .views(getViewMetas())
        .build();

    setPageMeta(pageMeta);
  }

  default void assembleViews() {
    setViewMetas(new ArrayList<>());
  }

  default void assembleHelpCenter() {
    setHelpCenterMeta(HelpCenterMeta.builder().build());
  }

  default void assembleSmartSearch() {
    setSmartSearchMeta(SmartSearchMeta.builder().build());
  }

  default void assembleDashboard() {
    setDashboardMeta(DashboardMeta.builder().build());
  }

  default void assembleMenu() {
    setMenuMeta(MenuMeta.builder().build());
  }
}
