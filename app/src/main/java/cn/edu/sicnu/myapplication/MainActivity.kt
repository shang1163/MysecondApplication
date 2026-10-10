package cn.edu.sicnu.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnu.myapplication.databinding.ActivityMainBinding
import cn.edu.sicnu.myapplication.model.ProgramAdviser
import cn.edu.sicnu.myapplication.model.ProgramLanguage

/**
 * MVC 中的 Controller 层：
 * 负责响应按钮事件，调用 Model 层处理数据，再通过 ViewBinding 刷新 View 层。
 */
class MainActivity : AppCompatActivity() {

    // View 层：ViewBinding 持有布局中的全部控件
    private lateinit var binding: ActivityMainBinding

    // Model 层：程序顾问，负责数据的保存与查询
    private val adviser = ProgramAdviser()

    // 默认文本的序号
    private var count = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 区1：动态添加文本
        binding.btnAddText.setOnClickListener { onAddTextClicked() }

        // 区2：查询模型层并返回结果
        binding.btnQuery.setOnClickListener { onQueryClicked() }

        // 语言选择：选中语言特点后查询模型层并展示推荐语言
        binding.spinnerFeature.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) = onFeatureSelected(position)

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    /**
     * 区1按钮事件：
     * 输入框为空时生成默认文本，否则使用输入内容；
     * 数据交给 Model 保存，同时在 ScrollView 内嵌的 LinearLayout 中动态添加 TextView。
     */
    private fun onAddTextClicked() {
        val content = binding.etInput.text.toString().trim().ifEmpty {
            getString(R.string.new_text_format, count++)
        }
        adviser.addProgram(content)
        addTextToContainer(content)
        binding.etInput.text.clear()
    }

    /**
     * 区2按钮事件（ProgramAdviser 交互）：
     * Controller 取出关键字 -> 查询 Model 层 -> 根据返回结果刷新 View 层。
     */
    private fun onQueryClicked() {
        val keyword = binding.etInput.text.toString().trim()
        val result = adviser.queryProgram(keyword)
        binding.tvResult.text = result?.let {
            getString(R.string.result_found_format, it)
        } ?: getString(R.string.result_not_found)
    }

    /**
     * 语言选择事件：
     * 将选中的特点下标交给 Model 层推荐，再把语言枚举映射为字符串资源展示。
     */
    private fun onFeatureSelected(position: Int) {
        val language = adviser.recommendLanguage(position)
        val languageRes = when (language) {
            ProgramLanguage.C -> R.string.lang_c
            ProgramLanguage.PYTHON -> R.string.lang_python
            ProgramLanguage.KOTLIN -> R.string.lang_kotlin
            ProgramLanguage.JAVA -> R.string.lang_java
            ProgramLanguage.UNKNOWN -> R.string.lang_unknown
        }
        binding.tvResult.text =
            getString(R.string.recommend_language_format, getString(languageRes))
    }

    /** 向 ScrollView 内嵌的 LinearLayout 动态添加一个 TextView */
    private fun addTextToContainer(text: String) {
        val textView = TextView(this).apply {
            this.text = text
            textSize = 18f
            setPadding(0, 8, 0, 8)
        }
        binding.llContainer.addView(textView)
    }
}
