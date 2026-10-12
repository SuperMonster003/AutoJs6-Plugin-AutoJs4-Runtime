package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.SwitchCompat

/**
 * Builds the flat grouped list used by [SettingsActivity]: 14 sp group titles, 16 sp row
 * titles, 14 sp summaries, 72 dp minimum row height and 24 dp horizontal padding, following
 * the AutoJs6 plugin settings conventions without pulling newer Material components into the
 * AutoJs4 dependency set.
 *
 * zh-CN: 构建 [SettingsActivity] 使用的分组平面列表: 14 sp 分组标题, 16 sp 行标题, 14 sp 摘要,
 * 72 dp 最小行高与 24 dp 左右留白, 遵循 AutoJs6 插件设置页约定, 同时不向 AutoJs4 的依赖集引入
 * 更新的 Material 组件.
 */
internal class SettingsRows(private val context: Context, private val container: LinearLayout) {

    inner class Row(private val summaryView: TextView) {
        fun setSummary(text: CharSequence?) {
            summaryView.text = text
            summaryView.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    fun addGroupTitle(@StringRes titleRes: Int) {
        container.addView(
            TextView(context).apply {
                text = context.getString(titleRes)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setTypeface(typeface, Typeface.BOLD)
                setTextColor(secondaryTextColor())
                setPadding(dp(24), dp(if (container.childCount == 0) 16 else 24), dp(24), dp(8))
            },
        )
    }

    fun addNavigationRow(@StringRes titleRes: Int, showChevron: Boolean = true, onClick: () -> Unit): Row {
        val (row, summary) = createRow(titleRes, null)
        if (showChevron) {
            row.addView(
                TextView(context).apply {
                    text = "›"
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                    setTextColor(secondaryTextColor())
                    setPadding(dp(16), 0, 0, 0)
                },
            )
        }
        row.setOnClickListener { onClick() }
        container.addView(row)
        return Row(summary)
    }

    fun addSwitchRow(@StringRes titleRes: Int, summary: CharSequence?, checked: Boolean, onChanged: (Boolean) -> Unit): Row {
        val (row, summaryView) = createRow(titleRes, summary)
        val switch = SwitchCompat(context).apply {
            isChecked = checked
            isClickable = false
            isFocusable = false
            setPadding(dp(16), 0, 0, 0)
        }
        row.addView(switch)
        row.setOnClickListener {
            switch.isChecked = !switch.isChecked
            onChanged(switch.isChecked)
        }
        container.addView(row)
        return Row(summaryView)
    }

    private fun createRow(@StringRes titleRes: Int, summary: CharSequence?): Pair<LinearLayout, TextView> {
        val summaryView = TextView(context).apply {
            text = summary
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(secondaryTextColor())
            visibility = if (summary.isNullOrEmpty()) View.GONE else View.VISIBLE
            setPadding(0, dp(4), 0, 0)
        }
        val texts = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            addView(
                TextView(context).apply {
                    text = context.getString(titleRes)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    setTextColor(primaryTextColor())
                },
            )
            addView(summaryView)
        }
        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            minimumHeight = dp(72)
            isClickable = true
            isFocusable = true
            setPadding(dp(24), dp(12), dp(24), dp(12))
            setBackgroundResource(selectableItemBackground())
            addView(texts)
        }
        return row to summaryView
    }

    private fun dp(value: Int): Int = (value * context.resources.displayMetrics.density + 0.5f).toInt()

    private fun primaryTextColor(): Int = themeColor(android.R.attr.textColorPrimary)

    private fun secondaryTextColor(): Int = themeColor(android.R.attr.textColorSecondary)

    private fun themeColor(attr: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return if (typedValue.resourceId != 0) {
            context.resources.getColor(typedValue.resourceId, context.theme)
        } else {
            typedValue.data
        }
    }

    private fun selectableItemBackground(): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
        return typedValue.resourceId
    }
}
