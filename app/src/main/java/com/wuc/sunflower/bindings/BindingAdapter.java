package com.wuc.sunflower.bindings;

import android.view.View;

/**
 * @author : wuchao5
 * @date : 3/1/21 4:37 PM
 * @desciption :
 */
public class BindingAdapter {

  @androidx.databinding.BindingAdapter("isGone")
  public static void bingISGone(View view, boolean isGone) {
    view.setVisibility(isGone ? View.GONE : View.VISIBLE);
  }
}