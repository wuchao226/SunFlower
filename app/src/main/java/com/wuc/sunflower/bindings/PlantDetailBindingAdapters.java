package com.wuc.sunflower.bindings;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wuc.sunflower.R;

/**
 * @author : wuchao5
 * @date : 3/1/21 7:48 PM
 * @desciption :  植物介绍详情 的 Fragment 的 布局文件 BindingAdapter合集
 */
public class PlantDetailBindingAdapters {

  /**
   * 显示图片用的
   * 谁在使用此BindingAdapter =====
   * PlantDetailFragment---fragment_plant_detail.xml(   app:imageFromUrl="@{viewModel.plant.imageUrl}" )
   * list_item_garden_planting.xml( app:imageFromUrl="@{viewModel.imageUrl}" )
   * list_item_plant.xml( app:imageFromUrl="@{plant.imageUrl}" )
   */
  @BindingAdapter("imageFromUrl")
  public static void bindImageFromUrl(ImageView view, String imageUrl) {
    if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(view.getContext())
          .load(imageUrl)
          .transition(DrawableTransitionOptions.withCrossFade())
          .into(view);
    }
  }
  /**
   *  xxxx用的
   *  谁在使用此BindingAdapter =====
   *   PlantDetailFragment---fragment_plant_detail.xml( app:isGone="@{safeUnbox(viewModel.isPlanted)}" )
   */
  @BindingAdapter("isGone")
  public static void bindIsGone(FloatingActionButton view, boolean isGone) {
    if (isGone) {
      view.hide();
    } else {
      view.show();
    }
  }

  /**
   *  xxxx用的
   *  谁在使用此BindingAdapter =====
   *   PlantDetailFragment---fragment_plant_detail.xml( app:renderHtml="@{viewModel.plant.description}" )
   */
  @BindingAdapter("renderHtml")
  public static void bindReaderHtml(TextView view, String description) {
    if (description == null) {
      view.setText("");
    } else {
      view.setText(HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT));
      view.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }

  /**
   *  xxxx用的
   *  谁在使用此BindingAdapter =====
   *   PlantDetailFragment---fragment_plant_detail.xml( app:wateringText="@{viewModel.plant.wateringInterval}" )
   */
  @BindingAdapter("wateringText")
  public static void bindWateringText(TextView textView, int wateringInterval) {
    Resources res = textView.getContext().getResources();
    String quantityString = res.getQuantityString(R.plurals.watering_needs_suffix, wateringInterval, wateringInterval);

    SpannableStringBuilder builder = new SpannableStringBuilder().append(res.getString(R.string.watering_needs_prefix));
    builder.setSpan(new StyleSpan(Typeface.BOLD), 0, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    int start = builder.length();
    builder.append(" " + quantityString);
    builder.setSpan(new StyleSpan(Typeface.ITALIC), start + 1, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    textView.setText(builder);
  }
}