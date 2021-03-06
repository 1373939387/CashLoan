package com.dumai.loan.base;

import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dumai.loan.R;
import com.dumai.loan.util.GradientUtils;
import com.dumai.loan.util.ResourcesUtils;
import com.dumai.loan.util.view.ToolbarAttr;
import com.dumai.loan.util.view.ToolbarHelper;
import com.wgallery.android.WGallery;

import butterknife.BindView;

/**
 * @author haoruigang
 *         2017年11月20日11:55:16
 *         Gallery画廊基类
 */
public class BaseWGalleryActivity extends BaseFragment {


    @BindView(R.id.wgallery)
    WGallery gallery;

    @Override
    protected int getContentViewId() {
        return R.layout.dm_fragment_cash;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        //动态改变“colorPrimaryDark”来实现沉浸式状态栏
        GradientUtils.setColor(getActivity(), R.drawable.mine_title_bg, true);
        toolbarHelper.setTitle("读脉分期");
        toolbar.setBackground(ResourcesUtils.getDrawable(R.drawable.mine_title_bg));
        toolbarHelper.setMenuTitle("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, new ToolbarAttr() {
            @Override
            public void TextViewAttr(TextView textView) {
                textView.setBackgroundResource(R.drawable.icon_wechat);
                Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(28, 28);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.rightMargin = 18;
                textView.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public void onUpdateUI() {
        gallery.setAdapter(initGalleryAdapter());//适配器
        gallery.setScalePivot(WGallery.SCALE_PIVOT_CENTER);//显示居中
        gallery.setSelectedScale(0.8f + 0.1f * 4);//选中gallery效果 第三位0-4
        gallery.setUnselectedAlpha(3 * 1.f / 10);//未选中gallery效果 第一位0-10

    }

    protected BaseAdapter initGalleryAdapter() {
        throw new RuntimeException("必须重写该方法");
    }

}
