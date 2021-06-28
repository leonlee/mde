package org.blab.mde.view.core;

import java.util.List;

import lombok.Data;


@Data
public abstract class ElementMeta<Type extends Enum<Type>, Classifier extends ElementMeta<?, ?>> {
  String id;
  String label;
  String icon;

  short position;
  String before;
  String after;

  Type type;

  Classifier parent;
  List<Classifier> children;

  abstract Component getComponent();
}
