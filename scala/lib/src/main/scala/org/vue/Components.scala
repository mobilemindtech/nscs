package org.vue

import org.vue.{Element, TplAttr, TplElement, typ}

trait Components:
  def label(children: TplElement | TplAttr*): TplElement
  def frame(children: TplElement | TplAttr*): TplElement
  def page(children: TplElement | TplAttr*): TplElement
  def actionBar(children: TplElement | TplAttr*): TplElement
  def gridLayout(children: TplElement | TplAttr*): TplElement
  def button(children: TplElement | TplAttr*): TplElement
