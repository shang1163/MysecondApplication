package cn.edu.sicnu.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import cn.edu.sicnu.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddText.setOnClickListener {
            val inputStr = binding.etInput.text.toString()
            if(inputStr.isNotEmpty()){
                dataList.add(inputStr)
                addTextToContainer(inputStr)
                binding.etInput.text.clear()
            }
        }

        binding.btnQuery.setOnClickListener {
            val keyword = binding.etInput.text.toString()
            val findItem = dataList.find { it.contains(keyword) }
            binding.tvResult.text = if(findItem != null) "找到：$findItem" else "未匹配到数据"
        }
    }

    private fun addTextToContainer(text: String){
        val newTv = TextView(this)
        newTv.text = text
        newTv.textSize = 18f
        newTv.setPadding(0,8,0,8)
        binding.llContainer.addView(newTv)
    }
}
